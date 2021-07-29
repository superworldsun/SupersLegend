package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.network.ClientPacketHandlerClass;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SyncManaMessage
{
	public CompoundNBT nbt;
	
	public SyncManaMessage(PlayerEntity player)
	{
		nbt = (CompoundNBT) ManaProvider.MANA_CAPABILITY.writeNBT(ManaProvider.get(player), null);
	}
	
	private SyncManaMessage()
	{
	}
	
	public static SyncManaMessage decode(PacketBuffer buf)
	{
		SyncManaMessage result = new SyncManaMessage();
		result.nbt = buf.readAnySizeNbt();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeNbt(nbt);
	}
	
	public static void receive(SyncManaMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handlePacket(message, ctx)));
	}
}
