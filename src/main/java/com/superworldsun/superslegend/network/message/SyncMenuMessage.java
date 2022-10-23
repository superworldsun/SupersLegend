package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.client.screen.SelectScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SyncMenuMessage
{
	public final int menu;
	
	public SyncMenuMessage(int menu)
	{
		this.menu = menu;
	}
	
	public static SyncMenuMessage decode(PacketBuffer buf)
	{
		return new SyncMenuMessage(buf.readInt());
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeInt(menu);
	}
	
	public static void handle(SyncMenuMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SyncMenuMessage.handleMenu(message, ctx)));
		ctx.setPacketHandled(true);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void handleMenu(SyncMenuMessage message, Context ctx)
	{
		Minecraft minecraft = Minecraft.getInstance();
		
		if (minecraft.screen instanceof SelectScreen)
		{
			((SelectScreen) minecraft.screen).resetScreen(message.menu);
		}
	}
}
