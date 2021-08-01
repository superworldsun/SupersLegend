package com.superworldsun.superslegend.hookshotCap;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class SyncToClient {

    private final CompoundNBT skillModel;

    public SyncToClient(CompoundNBT skillModel) {
        this.skillModel = skillModel;
    }

    public SyncToClient(PacketBuffer buffer) {
        skillModel = buffer.readNbt();
    }

    public static void send(PlayerEntity player) {
        SupersLegendMain.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SyncToClient(HookModel.get(player).serializeNBT()));
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