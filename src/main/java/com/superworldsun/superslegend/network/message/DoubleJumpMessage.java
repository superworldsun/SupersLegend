package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.interfaces.IJumpingEntity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class DoubleJumpMessage
{	
	public DoubleJumpMessage()
	{
	}
	
	public static DoubleJumpMessage decode(PacketBuffer buf)
	{
		DoubleJumpMessage result = new DoubleJumpMessage();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
	}
	
	public static void receive(DoubleJumpMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		ServerPlayerEntity player = ctxSupplier.get().getSender();
		
		if (player != null)
		{
			ctxSupplier.get().enqueueWork(() -> ((IJumpingEntity) player).doubleJump());
		}
		
		ctxSupplier.get().setPacketHandled(true);
	}
}
