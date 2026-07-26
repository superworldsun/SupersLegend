package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record DekuFlowerInputMessage(float strafeInput, float forwardInput, float yaw,
                                     boolean cancelRequested) {
    public static void encode(DekuFlowerInputMessage message, FriendlyByteBuf buffer) {
        buffer.writeFloat(message.strafeInput);
        buffer.writeFloat(message.forwardInput);
        buffer.writeFloat(message.yaw);
        buffer.writeBoolean(message.cancelRequested);
    }

    public static DekuFlowerInputMessage decode(FriendlyByteBuf buffer) {
        return new DekuFlowerInputMessage(
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readBoolean()
        );
    }

    public static void receive(DekuFlowerInputMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> DekuFlowerFlightEvents.setGlideInput(
                    player,
                    message.strafeInput,
                    message.forwardInput,
                    message.yaw,
                    message.cancelRequested
            ));
        }
        context.setPacketHandled(true);
    }
}
