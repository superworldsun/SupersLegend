package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.waypoints.WaypointsProvider;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class RemoveWaypointMessage
{
	private BlockPos waypointPos;
	
	public RemoveWaypointMessage(BlockPos waypointPos)
	{
		this.waypointPos = waypointPos;
	}
	
	private RemoveWaypointMessage()
	{
	}
	
	public static RemoveWaypointMessage decode(PacketBuffer buf)
	{
		RemoveWaypointMessage result = new RemoveWaypointMessage();
		result.waypointPos = buf.readBlockPos();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeBlockPos(waypointPos);
	}
	
	public static void receive(RemoveWaypointMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() ->
		{
			WaypointsProvider.get(player).removeWaypoint(message.waypointPos);
			WaypointsProvider.sync(player);
		});
		
	}
}
