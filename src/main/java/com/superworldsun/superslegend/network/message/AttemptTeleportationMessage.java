package com.superworldsun.superslegend.network.message;

import java.util.UUID;
import java.util.function.Supplier;

import com.superworldsun.superslegend.waypoints.Waypoint;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;
import com.superworldsun.superslegend.waypoints.WaypointsServerData;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class AttemptTeleportationMessage
{
	private BlockPos pos;
	
	public AttemptTeleportationMessage(BlockPos pos)
	{
		this.pos = pos;
	}
	
	private AttemptTeleportationMessage()
	{
	}
	
	public static AttemptTeleportationMessage decode(PacketBuffer buf)
	{
		AttemptTeleportationMessage result = new AttemptTeleportationMessage();
		result.pos = buf.readBlockPos();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeBlockPos(pos);
	}
	
	public static void receive(AttemptTeleportationMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() ->
		{
			Waypoint waypoint = WaypointsServerData.instance().getWaypoint(message.pos);
			
			if(waypoint == null)
			{
				WaypointsProvider.get(player).removeWaypoint(message.pos);
			}
			else
			{
				MinecraftServer server = player.getLevel().getServer();
				String playerName = player.getName().getString();
				new Commands(Commands.EnvironmentType.ALL).performCommand(server.createCommandSourceStack(), "tp " + playerName + " " + message.pos.getX() + " " + message.pos.getY() + " " + message.pos.getZ());
			}
		});
	}
}
