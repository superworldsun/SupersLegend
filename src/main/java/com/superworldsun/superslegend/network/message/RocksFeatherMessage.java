package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.SupersLegendRegistries;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.songs.OcarinaSong;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RocksFeatherMessage {

    public RocksFeatherMessage() {
    }

    public static RocksFeatherMessage decode(PacketBuffer buf) {
        RocksFeatherMessage result = new RocksFeatherMessage();
        return result;
    }

    public void encode(PacketBuffer buf) {
    }

    public static void receive(RocksFeatherMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        ServerPlayerEntity player = ctxSupplier.get().getSender();
        if (player != null) {
            ctxSupplier.get().enqueueWork(() -> ItemInit.ROCS_FEATHER.get().jump(player));
        }
        ctxSupplier.get().setPacketHandled(true);
    }
}
