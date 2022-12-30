package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.network.NetworkDispatcher;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class SyncHookShot {

    private final CompoundNBT skillModel;

    public SyncHookShot(CompoundNBT skillModel) {
        this.skillModel = skillModel;
    }

    public SyncHookShot(PacketBuffer buffer) {
        skillModel = buffer.readNbt();
    }

    public static void send(PlayerEntity player) {
        NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SyncHookShot(HookModel.get(player).serializeNBT()));
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt(skillModel);
    }

    // Send Packet

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> HookModel.get().deserializeNBT(skillModel));
        context.get().setPacketHandled(true);
    }
}