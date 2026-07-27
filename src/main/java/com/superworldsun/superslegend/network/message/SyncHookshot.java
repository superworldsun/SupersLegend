package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.capability.hookshot.HookModel;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class SyncHookshot {

    private final CompoundTag hookModelData;

    public SyncHookshot(CompoundTag hookModelData) {
        this.hookModelData = hookModelData;
    }

    public SyncHookshot(FriendlyByteBuf buffer) {
        this.hookModelData = buffer.readNbt();
    }

    public static void send(Player player) {
        NetworkDispatcher.network_channel.send(
                PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                new SyncHookshot(HookModel.get(player).serializeNBT())
        );
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt(hookModelData);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT,
                () -> () -> handleClientPacket(hookModelData)
        ));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClientPacket(CompoundTag hookModelData) {
        ClientLevel world = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;
        if (world != null && player != null) {
            HookModel.get(player).deserializeNBT(hookModelData);
        }
    }

    // Optional: Method to trigger a sync from server to client
    public static void syncToClient(ServerPlayer player) {
        send(player);
    }
}
