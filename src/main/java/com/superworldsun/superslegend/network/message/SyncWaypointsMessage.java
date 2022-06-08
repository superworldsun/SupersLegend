package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.client.screen.WaypointsScreen;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SyncWaypointsMessage
{
	public CompoundNBT nbt;
	
	public SyncWaypointsMessage(PlayerEntity player)
	{
		nbt = (CompoundNBT) WaypointsProvider.WAYPOINTS_CAPABILITY.writeNBT(WaypointsProvider.get(player), null);
	}
	
	private SyncWaypointsMessage()
	{
	}
	
	public static SyncWaypointsMessage decode(PacketBuffer buf)
	{
		SyncWaypointsMessage result = new SyncWaypointsMessage();
		result.nbt = buf.readAnySizeNbt();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeNbt(nbt);
	}
	
	public static void receive(SyncWaypointsMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handlePacket(message, ctx)));
	}
	
	@OnlyIn(Dist.CLIENT)
	private static void handlePacket(SyncWaypointsMessage message, Context ctx)
	{
		Minecraft client = Minecraft.getInstance();
		WaypointsProvider.WAYPOINTS_CAPABILITY.readNBT(WaypointsProvider.get(client.player), null, message.nbt);
		
		if (client.screen instanceof WaypointsScreen)
		{
			client.setScreen(new WaypointsScreen());
		}
	}
}
