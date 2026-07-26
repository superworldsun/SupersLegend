package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.items.curios.head.masks.DekuMask;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DekuWaterHopMessage {
    private final int waterBlockY;

    public DekuWaterHopMessage(int waterBlockY) {
        this.waterBlockY = waterBlockY;
    }

    public static DekuWaterHopMessage decode(FriendlyByteBuf buffer) {
        return new DekuWaterHopMessage(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(waterBlockY);
    }

    public static void receive(DekuWaterHopMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> DekuMask.confirmFinalWaterHop(player, message.waterBlockY));
        }
        context.setPacketHandled(true);
    }
}
