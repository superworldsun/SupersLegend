package com.superworldsun.superslegend.trading.rupee;

import com.superworldsun.superslegend.mixin.accessor.MerchantMenuAccessor;
import com.superworldsun.superslegend.menus.RupeeTradeMenu;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.OpenTradeChoiceMessage;
import com.superworldsun.superslegend.network.message.SelectTradeModeMessage;
import com.superworldsun.superslegend.network.message.RupeeTradeAvailabilityMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Coordinates the short-lived currency chooser and opens the chosen menu. */
public final class RupeeTradeNetworking {
    private static final double MAX_TRADING_DISTANCE_SQUARED = 64.0D;
    private static final long CHOICE_LIFETIME_TICKS = 200L;
    private static final Map<UUID, PendingChoice> PENDING_CHOICES = new ConcurrentHashMap<>();

    private RupeeTradeNetworking() {
    }

    public static void openChoice(ServerPlayer player, AbstractVillager trader, InteractionHand hand) {
        if (!isValidTrader(player, trader, false)) {
            return;
        }

        long expiresAt = player.serverLevel().getGameTime() + CHOICE_LIFETIME_TICKS;
        PENDING_CHOICES.put(player.getUUID(), new PendingChoice(trader.getUUID(), expiresAt));
        NetworkDispatcher.network_channel.send(
                PacketDistributor.PLAYER.with(() -> player),
                new OpenTradeChoiceMessage(trader.getId(), traderTypeName(trader)));
    }

    public static void selectMode(ServerPlayer player, int traderEntityId, SelectTradeModeMessage.Mode mode) {
        Entity entity = player.serverLevel().getEntity(traderEntityId);
        if (!(entity instanceof AbstractVillager trader) || mode == null) {
            PENDING_CHOICES.remove(player.getUUID());
            return;
        }

        boolean fromOpenRupeeMenu = player.containerMenu instanceof RupeeTradeMenu menu
                && menu.getTrader() == trader;
        if (!fromOpenRupeeMenu && !consumePendingChoice(player, trader)) {
            return;
        }
        if (!isValidTrader(player, trader, fromOpenRupeeMenu)) {
            return;
        }

        RupeeTradeService.removeLegacyOffers(trader);
        if (fromOpenRupeeMenu) {
            player.closeContainer();
        }

        if (mode == SelectTradeModeMessage.Mode.EMERALDS) {
            VanillaTradeOpener.open(player, trader);
        } else {
            openRupeeMenu(player, trader);
        }
    }

    /**
     * Replaces the player's current vanilla merchant menu with the Rupee menu.
     * The server derives the trader from that exact open container rather than
     * trusting a client-supplied entity id.
     */
    public static void switchVanillaToRupees(ServerPlayer player, int containerId) {
        if (!(player.containerMenu instanceof MerchantMenu merchantMenu)
                || merchantMenu.containerId != containerId) {
            return;
        }

        Object merchant = ((MerchantMenuAccessor) merchantMenu).superslegend$getTrader();
        if (!(merchant instanceof AbstractVillager trader)
                || trader.getTradingPlayer() != player
                || !isValidTrader(player, trader, true)) {
            return;
        }

        RupeeTradeService.removeLegacyOffers(trader);
        // Closing first safely returns anything placed in the vanilla payment
        // slots and releases its reservation before the Rupee menu reclaims it.
        player.closeContainer();
        openRupeeMenu(player, trader);
    }

    /** Reports whether the trader belonging to the player's current vanilla menu has Rupee trades. */
    public static void sendTradeAvailability(ServerPlayer player, int containerId) {
        boolean available = false;
        if (player.containerMenu instanceof MerchantMenu merchantMenu
                && merchantMenu.containerId == containerId) {
            Object merchant = ((MerchantMenuAccessor) merchantMenu).superslegend$getTrader();
            available = merchant instanceof AbstractVillager trader
                    && trader.getTradingPlayer() == player
                    && !RupeeTradeRegistry.getVisibleTrades(trader).isEmpty();
        }

        boolean finalAvailable = available;
        NetworkDispatcher.network_channel.send(
                PacketDistributor.PLAYER.with(() -> player),
                new RupeeTradeAvailabilityMessage(containerId, finalAvailable));
    }

    private static void openRupeeMenu(ServerPlayer player, AbstractVillager trader) {
        if (trader.getTradingPlayer() != null && trader.getTradingPlayer() != player) {
            return;
        }
        trader.setTradingPlayer(player);
        Component title = traderTypeName(trader);
        var availableTrades = RupeeTradeRegistry.getVisibleTrades(trader);
        var discoveredDailyItems = RupeeTradeService.getDiscoveredDailyTradeItems(player).stream()
                .sorted(java.util.Comparator.comparing(ResourceLocation::toString))
                .toList();
        NetworkHooks.openScreen(player,
                new SimpleMenuProvider((containerId, inventory, ignored) ->
                        new RupeeTradeMenu(containerId, inventory, trader), title),
                buffer -> {
                    buffer.writeVarInt(trader.getId());
                    buffer.writeVarInt(availableTrades.size());
                    for (var trade : availableTrades) {
                        trade.write(buffer);
                    }
                    buffer.writeVarInt(discoveredDailyItems.size());
                    for (var itemId : discoveredDailyItems) {
                        buffer.writeResourceLocation(itemId);
                    }
                });
    }

    private static Component traderTypeName(AbstractVillager trader) {
        if (trader instanceof Villager villager) {
            var professionId = BuiltInRegistries.VILLAGER_PROFESSION
                    .getKey(villager.getVillagerData().getProfession());
            return Component.translatable("entity.minecraft.villager." + professionId.getPath());
        }
        return trader.getDisplayName();
    }

    private static boolean consumePendingChoice(ServerPlayer player, AbstractVillager trader) {
        PendingChoice pending = PENDING_CHOICES.remove(player.getUUID());
        return pending != null
                && pending.traderUuid.equals(trader.getUUID())
                && pending.expiresAt >= player.serverLevel().getGameTime();
    }

    private static boolean isValidTrader(ServerPlayer player, AbstractVillager trader,
                                         boolean allowOwnedReservation) {
        if (!player.isAlive() || player.isSpectator() || !trader.isAlive() || trader.isBaby()
                || player.level() != trader.level()
                || player.distanceToSqr(trader) > MAX_TRADING_DISTANCE_SQUARED
                || RupeeTradeRegistry.getVisibleTrades(trader).isEmpty()) {
            return false;
        }
        return trader.getTradingPlayer() == null
                || allowOwnedReservation && trader.getTradingPlayer() == player;
    }

    private record PendingChoice(UUID traderUuid, long expiresAt) {
    }
}
