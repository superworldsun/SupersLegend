package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.waypoints.WaypointsServerData;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class SetWaypointNameMessage
{
	private BlockPos pos;
	private String text;
	
	public SetWaypointNameMessage(BlockPos pos, String text)
	{
		this.pos = pos;
		this.text = text;
	}
	
	private SetWaypointNameMessage()
	{
	}
	
	public static SetWaypointNameMessage decode(PacketBuffer buf)
	{
		SetWaypointNameMessage result = new SetWaypointNameMessage();
		result.pos = buf.readBlockPos();
		result.text = buf.readUtf();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeBlockPos(pos);
		buf.writeUtf(text);
	}
	
	public static void receive(SetWaypointNameMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> WaypointsServerData.instance().createWaypoint(message.pos, message.text));
	}
}
