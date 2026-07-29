package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.trading.rupee.RupeeTradeNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Requests one of the two server-owned villager trading flows. */
public record SelectTradeModeMessage(int traderEntityId, Mode mode) {
    public enum Mode {
        EMERALDS,
        RUPEES
    }

    public static SelectTradeModeMessage decode(FriendlyByteBuf buffer) {
        int traderId = buffer.readVarInt();
        int ordinal = buffer.readUnsignedByte();
        Mode mode = ordinal >= 0 && ordinal < Mode.values().length ? Mode.values()[ordinal] : null;
        return new SelectTradeModeMessage(traderId, mode);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(traderEntityId);
        buffer.writeByte(mode == null ? -1 : mode.ordinal());
    }

    public static void receive(SelectTradeModeMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> RupeeTradeNetworking.selectMode(
                    player, message.traderEntityId, message.mode));
        }
        context.setPacketHandled(true);
    }
}
