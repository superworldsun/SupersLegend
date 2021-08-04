package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.blocks.tile.GossipStoneTileEntity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class SetGossipStoneTextMessage
{
	private BlockPos pos;
	private String text;
	
	public SetGossipStoneTextMessage(BlockPos pos, String text)
	{
		this.pos = pos;
		this.text = text;
	}
	
	private SetGossipStoneTextMessage()
	{
	}
	
	public static SetGossipStoneTextMessage decode(PacketBuffer buf)
	{
		SetGossipStoneTextMessage result = new SetGossipStoneTextMessage();
		result.pos = buf.readBlockPos();
		result.text = buf.readUtf();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeBlockPos(pos);
		buf.writeUtf(text);
	}
	
	public static void receive(SetGossipStoneTextMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);		
		ctx.enqueueWork(() -> ((GossipStoneTileEntity) player.level.getBlockEntity(message.pos)).setMessage(message.text));		
	}
}
