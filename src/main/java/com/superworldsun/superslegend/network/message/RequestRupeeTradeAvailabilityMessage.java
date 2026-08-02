package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.trading.rupee.RupeeTradeNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Requests the server-authoritative Rupee-trade state for an open merchant menu. */
public record RequestRupeeTradeAvailabilityMessage(int containerId) {
    public static RequestRupeeTradeAvailabilityMessage decode(FriendlyByteBuf buffer) {
        return new RequestRupeeTradeAvailabilityMessage(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
    }

    public static void receive(RequestRupeeTradeAvailabilityMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> RupeeTradeNetworking.sendTradeAvailability(
                    player, message.containerId));
        }
        context.setPacketHandled(true);
    }
}
