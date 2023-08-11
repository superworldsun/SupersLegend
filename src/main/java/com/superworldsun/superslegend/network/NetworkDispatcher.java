package com.superworldsun.superslegend.network;

import java.util.Optional;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.message.SyncMagicMessage;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@EventBusSubscriber(bus = Bus.MOD, modid = SupersLegendMain.MOD_ID)
public class NetworkDispatcher {
	private static final ResourceLocation CHANNEL_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "channel");
	public static SimpleChannel network_channel;

	@SubscribeEvent
	public static void registerNetworkChannel(FMLCommonSetupEvent event) {
		network_channel = NetworkRegistry.newSimpleChannel(CHANNEL_ID, () -> "1.0", s -> true, s -> true);
		network_channel.registerMessage(1, SyncMagicMessage.class, SyncMagicMessage::encode, SyncMagicMessage::decode, SyncMagicMessage::receive,
				Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}
}
