package com.superworldsun.superslegend.menus;

import com.superworldsun.superslegend.registries.MenuTypeInit;
import com.superworldsun.superslegend.trading.rupee.RupeeTrade;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeRegistry;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeService;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A slotless, server-authoritative menu for wallet-backed villager trades.
 * The client receives only display data; purchases are resolved against the
 * canonical registry, inventory, wallet, distance, and stock on the server.
 */
public class RupeeTradeMenu extends AbstractContainerMenu {
    private static final double MAX_TRADING_DISTANCE_SQUARED = 64.0D;
    private static final int BALANCE_DATA_INDEX = 0;
    private static final int TRADER_LEVEL_DATA_INDEX = 1;
    private static final int TRADER_XP_DATA_INDEX = 2;
    private static final int SHOW_PROGRESS_DATA_INDEX = 3;
    private static final int WALLET_CAPACITY_DATA_INDEX = 4;
    private static final int STOCK_DATA_START = 5;

    private final Player player;
    private final AbstractVillager trader;
    private final List<RupeeTrade> trades;
    private final int[] syncedValues;
    private final ContainerData data;

    /** Forge network constructor. */
    public RupeeTradeMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, inventory,
                resolveTrader(inventory.player, buffer.readVarInt()), readTradeIds(buffer));
    }

    /** Server constructor. */
    public RupeeTradeMenu(int containerId, Inventory inventory, AbstractVillager trader) {
        this(containerId, inventory, trader, null);
    }

    private RupeeTradeMenu(int containerId, Inventory inventory, AbstractVillager trader,
                           List<ResourceLocation> serverTradeIds) {
        super(MenuTypeInit.RUPEE_TRADE_MENU.get(), containerId);
        this.player = inventory.player;
        this.trader = trader;
        this.trades = serverTradeIds == null
                ? trader == null ? List.of() : RupeeTradeRegistry.getTrades(trader)
                : RupeeTradeRegistry.getTradesById(serverTradeIds);
        this.syncedValues = new int[trades.size() + STOCK_DATA_START];
        this.data = createDataView();
        addDataSlots(data);
    }

    private ContainerData createDataView() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                if (index < 0 || index >= syncedValues.length) {
                    return 0;
                }
                if (!player.level().isClientSide) {
                    if (index == BALANCE_DATA_INDEX) {
                        return RupeeWalletUtil.getStoredRupees(player);
                    }
                    if (index == TRADER_LEVEL_DATA_INDEX) {
                        return trader instanceof Villager villager
                                ? villager.getVillagerData().getLevel() : 0;
                    }
                    if (index == TRADER_XP_DATA_INDEX) {
                        return trader == null ? 0 : trader.getVillagerXp();
                    }
                    if (index == SHOW_PROGRESS_DATA_INDEX) {
                        return trader != null && trader.showProgressBar() ? 1 : 0;
                    }
                    if (index == WALLET_CAPACITY_DATA_INDEX) {
                        return RupeeWalletUtil.getWalletCapacity(player);
                    }
                    int tradeIndex = index - STOCK_DATA_START;
                    return trader == null || tradeIndex < 0 || tradeIndex >= trades.size() ? 0
                            : RupeeTradeService.getRemainingStock(trader, trades.get(tradeIndex));
                }
                return syncedValues[index];
            }

            @Override
            public void set(int index, int value) {
                if (index >= 0 && index < syncedValues.length) {
                    syncedValues[index] = value;
                }
            }

            @Override
            public int getCount() {
                return syncedValues.length;
            }
        };
    }

    public AbstractVillager getTrader() {
        return trader;
    }

    public List<RupeeTrade> getTrades() {
        return trades;
    }

    public int getBalance() {
        return data.get(BALANCE_DATA_INDEX);
    }

    public int getTraderLevel() {
        return data.get(TRADER_LEVEL_DATA_INDEX);
    }

    public int getTraderXp() {
        return data.get(TRADER_XP_DATA_INDEX);
    }

    public boolean showProgressBar() {
        return data.get(SHOW_PROGRESS_DATA_INDEX) != 0;
    }

    public int getWalletCapacity() {
        return data.get(WALLET_CAPACITY_DATA_INDEX);
    }

    public int getRemainingStock(int tradeIndex) {
        return tradeIndex >= 0 && tradeIndex < trades.size()
                ? data.get(STOCK_DATA_START + tradeIndex) : 0;
    }

    public int getRemainingStock(RupeeTrade trade) {
        if (trade == null) {
            return 0;
        }
        for (int index = 0; index < trades.size(); index++) {
            if (trades.get(index).id().equals(trade.id())) {
                return getRemainingStock(index);
            }
        }
        return 0;
    }

    public RupeeTradeService.PurchaseStatus purchase(ServerPlayer serverPlayer,
                                                     ResourceLocation tradeId, int quantity) {
        if (serverPlayer != player || trader == null || !stillValid(serverPlayer)) {
            return RupeeTradeService.PurchaseStatus.INVALID_TRADER;
        }
        RupeeTradeService.PurchaseStatus status = RupeeTradeService.purchase(
                serverPlayer, trader, tradeId, quantity);
        broadcastChanges();
        return status;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return trader != null
                && player.isAlive()
                && !player.isSpectator()
                && trader.isAlive()
                && !trader.isBaby()
                && player.level() == trader.level()
                && player.distanceToSqr(trader) <= MAX_TRADING_DISTANCE_SQUARED
                && (trader.getTradingPlayer() == null || trader.getTradingPlayer() == player);
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        if (trader != null && trader.getTradingPlayer() == player) {
            trader.setTradingPlayer(null);
        }
    }

    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    private static AbstractVillager resolveTrader(Player player, int entityId) {
        Entity entity = player.level().getEntity(entityId);
        return entity instanceof AbstractVillager abstractVillager ? abstractVillager : null;
    }

    private static List<ResourceLocation> readTradeIds(FriendlyByteBuf buffer) {
        int count = buffer.readVarInt();
        if (count < 0 || count > 256) {
            throw new IllegalArgumentException("Invalid rupee trade count: " + count);
        }
        java.util.ArrayList<ResourceLocation> ids = new java.util.ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            ids.add(buffer.readResourceLocation());
        }
        return List.copyOf(ids);
    }
}
