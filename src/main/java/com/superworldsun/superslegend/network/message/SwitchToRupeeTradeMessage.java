package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.trading.rupee.RupeeTradeNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Requests a server-validated transition from an open vanilla trade menu. */
public record SwitchToRupeeTradeMessage(int containerId) {
    public static SwitchToRupeeTradeMessage decode(FriendlyByteBuf buffer) {
        return new SwitchToRupeeTradeMessage(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
    }

    public static void receive(SwitchToRupeeTradeMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> RupeeTradeNetworking.switchVanillaToRupees(
                    player, message.containerId));
        }
        context.setPacketHandled(true);
    }
}
