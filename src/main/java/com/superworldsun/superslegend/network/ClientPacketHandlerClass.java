package com.superworldsun.superslegend.network;

import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.network.message.SyncManaMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ClientPacketHandlerClass
{
	@OnlyIn(value = Dist.CLIENT)
	public static void handlePacket(Object message, Context ctx)
	{
		if (message instanceof SyncManaMessage)
		{
			Minecraft client = Minecraft.getInstance();
			PlayerEntity player = client.player;
			ManaProvider.MANA_CAPABILITY.readNBT(ManaProvider.get(player), null, ((SyncManaMessage) message).nbt);
		}
	}
}
