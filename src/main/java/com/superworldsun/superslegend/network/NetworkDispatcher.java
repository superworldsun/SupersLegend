package com.superworldsun.superslegend.network;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;

import java.util.Optional;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.message.SyncManaMessage;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@EventBusSubscriber(bus = Bus.MOD, modid = SupersLegendMain.MOD_ID)
public class NetworkDispatcher
{
	public static SimpleChannel networkChannel;
	
	@SubscribeEvent
	public static void onCommonSetupEvent(FMLCommonSetupEvent event)
	{
		int ID = 0;
		networkChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(SupersLegendMain.MOD_ID, "channel"), () -> "1.0", (s) -> true, (s) -> true);
		networkChannel.registerMessage(ID++, SyncManaMessage.class, SyncManaMessage::encode, SyncManaMessage::decode,
				SyncManaMessage.Handler::onMessageReceived, Optional.of(PLAY_TO_CLIENT));
	}
}
