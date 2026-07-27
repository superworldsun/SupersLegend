package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.events.HookshotPullPoseEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleCrawlingMessage {
    private final boolean crawling;

    public ToggleCrawlingMessage(boolean crawling) {
        this.crawling = crawling;
    }

    public static ToggleCrawlingMessage decode(FriendlyByteBuf buf) {
        return new ToggleCrawlingMessage(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(crawling);
    }

    public static void receive(ToggleCrawlingMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ServerPlayer player = ctx.getSender();
        ctx.enqueueWork(() -> {
            if (player != null) {
                HookshotPullPoseEvents.setManualCrawling(player, message.crawling);
            }
        });
        ctx.setPacketHandled(true);
    }
}
