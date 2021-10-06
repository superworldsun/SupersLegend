package com.superworldsun.superslegend.network;

import com.superworldsun.superslegend.client.screen.SelectScreen;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.network.message.SyncManaMessage;

import com.superworldsun.superslegend.network.message.SyncMenuMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ClientPacketHandlerClass
{
	@OnlyIn(value = Dist.CLIENT)
	public static void handlePacket(SyncManaMessage message, Context ctx)
	{
		Minecraft client = Minecraft.getInstance();
		ManaProvider.MANA_CAPABILITY.readNBT(ManaProvider.get(client.player), null, message.nbt);
	}

	@OnlyIn(value = Dist.CLIENT)
	public static void handleMenu(SyncMenuMessage message, Context ctx)
	{
		if(Minecraft.getInstance().screen instanceof SelectScreen)
		{
			((SelectScreen) Minecraft.getInstance().screen).resetScreen(message.menu);
		}
	}
}