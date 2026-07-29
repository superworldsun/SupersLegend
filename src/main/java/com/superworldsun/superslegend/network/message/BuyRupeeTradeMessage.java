package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.menus.RupeeTradeMenu;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeService;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Carries only a stable ID and quantity; all purchase facts come from the server. */
public record BuyRupeeTradeMessage(int containerId, ResourceLocation tradeId, int quantity) {
    public static BuyRupeeTradeMessage decode(FriendlyByteBuf buffer) {
        return new BuyRupeeTradeMessage(buffer.readVarInt(), buffer.readResourceLocation(), buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
        buffer.writeResourceLocation(tradeId);
        buffer.writeVarInt(quantity);
    }

    public static void receive(BuyRupeeTradeMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> purchase(player, message));
        }
        context.setPacketHandled(true);
    }

    private static void purchase(ServerPlayer player, BuyRupeeTradeMessage message) {
        if (!(player.containerMenu instanceof RupeeTradeMenu menu)
                || menu.containerId != message.containerId) {
            return;
        }

        RupeeTradeService.PurchaseStatus status = menu.purchase(player, message.tradeId, message.quantity);
        if (menu.getTrader() == null) {
            return;
        }

        if (status.successful()) {
            menu.getTrader().level().playSound(null, menu.getTrader().blockPosition(),
                    SoundEvents.VILLAGER_YES, SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            menu.getTrader().level().playSound(null, menu.getTrader().blockPosition(),
                    SoundEvents.VILLAGER_NO, SoundSource.NEUTRAL, 0.8F, 1.0F);
            player.displayClientMessage(Component.translatable(
                    "screen.superslegend.rupee_trade.error." + status.name().toLowerCase()), true);
        }
    }
}
