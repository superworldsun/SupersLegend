package com.superworldsun.superslegend.network;

import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.network.message.SyncManaMessage;

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
}
