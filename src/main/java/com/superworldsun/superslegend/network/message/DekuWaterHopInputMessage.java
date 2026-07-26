package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.items.curios.head.masks.DekuMask;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DekuWaterHopInputMessage {
    private final float strafeInput;
    private final float forwardInput;
    private final float yaw;

    public DekuWaterHopInputMessage(float strafeInput, float forwardInput, float yaw) {
        this.strafeInput = strafeInput;
        this.forwardInput = forwardInput;
        this.yaw = yaw;
    }

    public static DekuWaterHopInputMessage decode(FriendlyByteBuf buffer) {
        return new DekuWaterHopInputMessage(
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat()
        );
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(strafeInput);
        buffer.writeFloat(forwardInput);
        buffer.writeFloat(yaw);
    }

    public static void receive(DekuWaterHopInputMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> DekuMask.setWaterHopSteeringInput(
                    player,
                    message.strafeInput,
                    message.forwardInput,
                    message.yaw
            ));
        }
        context.setPacketHandled(true);
    }
}
