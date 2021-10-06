package com.superworldsun.superslegend.network;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_SERVER;

import java.util.Optional;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.message.*;

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
		networkChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(SupersLegendMain.MOD_ID, "channel"), () -> "1.0", s -> true, s -> true);
		networkChannel.registerMessage(1, SyncManaMessage.class, SyncManaMessage::encode, SyncManaMessage::decode, SyncManaMessage::receive, Optional.of(PLAY_TO_CLIENT));
		networkChannel.registerMessage(2, MaskAbilityMessage.class, MaskAbilityMessage::encode, MaskAbilityMessage::decode, MaskAbilityMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(3, SetGossipStoneTextMessage.class, SetGossipStoneTextMessage::encode, SetGossipStoneTextMessage::decode, SetGossipStoneTextMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(4, SelectInteractionMessage.class, SelectInteractionMessage::encode, SelectInteractionMessage::decode, SelectInteractionMessage::handle, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(5, SyncMenuMessage.class, SyncMenuMessage::encode, SyncMenuMessage::decode, SyncMenuMessage::handle, Optional.of(PLAY_TO_CLIENT));
	}
}
