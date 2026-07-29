package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.events.OcarinaPoseEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetOcarinaPlayingMessage {
    private final boolean playing;
    private final InteractionHand hand;

    public SetOcarinaPlayingMessage(boolean playing, InteractionHand hand) {
        this.playing = playing;
        this.hand = hand;
    }

    public static SetOcarinaPlayingMessage decode(FriendlyByteBuf buffer) {
        return new SetOcarinaPlayingMessage(buffer.readBoolean(), buffer.readEnum(InteractionHand.class));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(playing);
        buffer.writeEnum(hand);
    }

    public static void receive(SetOcarinaPlayingMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                OcarinaPoseEvents.setPlaying(player, message.hand, message.playing);
            }
        });
        context.setPacketHandled(true);
    }
}
