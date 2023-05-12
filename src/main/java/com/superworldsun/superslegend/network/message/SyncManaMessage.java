package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.capability.mana.ManaCapability;
import com.superworldsun.superslegend.capability.mana.ManaCapabilityProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SyncManaMessage {
	public CompoundNBT nbt;

	public SyncManaMessage(PlayerEntity player) {
		nbt = (CompoundNBT) ManaCapabilityProvider.CAPABILITY.writeNBT(getManaCapability(player), null);
	}

	private SyncManaMessage() {
	}

	public static SyncManaMessage decode(PacketBuffer buf) {
		SyncManaMessage result = new SyncManaMessage();
		result.nbt = buf.readAnySizeNbt();
		return result;
	}

	public void encode(PacketBuffer buf) {
		buf.writeNbt(nbt);
	}

	public static void receive(SyncManaMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SyncManaMessage.handlePacket(message, ctx)));
	}

	@OnlyIn(Dist.CLIENT)
	public static void handlePacket(SyncManaMessage message, Context ctx) {
		Minecraft client = Minecraft.getInstance();
		ManaCapabilityProvider.CAPABILITY.readNBT(getManaCapability(client.player), null, message.nbt);
	}

	private static ManaCapability getManaCapability(PlayerEntity player) {
		return player.getCapability(ManaCapabilityProvider.CAPABILITY).orElse(new ManaCapability());
	}
}
