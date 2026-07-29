package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.client.screen.WaypointsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ShowWaystonesScreenMessage {
    private UUID playerUUID;
    public ShowWaystonesScreenMessage(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public static ShowWaystonesScreenMessage decode(FriendlyByteBuf buf) {
        return new ShowWaystonesScreenMessage(buf.readUUID());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(playerUUID);
    }

    public static void handle(ShowWaystonesScreenMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleClientPacket(message.playerUUID))
        );
        ctx.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClientPacket(UUID playerUUID) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(playerUUID);
        if (player != null)
            Minecraft.getInstance().setScreen(new WaypointsScreen(player));
    }
}
