package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.client.sound.BremenMaskSound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BremenMaskSoundMessage {
    private final int playerId;
    private final boolean playing;

    public BremenMaskSoundMessage(int playerId, boolean playing) {
        this.playerId = playerId;
        this.playing = playing;
    }

    public static BremenMaskSoundMessage decode(FriendlyByteBuf buffer) {
        return new BremenMaskSoundMessage(buffer.readVarInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(playerId);
        buffer.writeBoolean(playing);
    }

    public static void receive(BremenMaskSoundMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT,
                () -> () -> BremenMaskSound.setPlaying(message.playerId, message.playing)
        ));
        context.setPacketHandled(true);
    }
}
