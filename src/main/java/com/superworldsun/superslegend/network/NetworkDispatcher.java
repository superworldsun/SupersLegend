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
import net.minecraftforge.fml.network.NetworkDirection;
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
		networkChannel.registerMessage(6, SyncLearnedSongsMessage.class, SyncLearnedSongsMessage::encode, SyncLearnedSongsMessage::decode, SyncLearnedSongsMessage::receive, Optional.of(PLAY_TO_CLIENT));
		networkChannel.registerMessage(7, PlaySongMessage.class, PlaySongMessage::encode, PlaySongMessage::decode, PlaySongMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(8, DoubleJumpMessage.class, DoubleJumpMessage::encode, DoubleJumpMessage::decode, DoubleJumpMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(9, DropBombMessage.class, DropBombMessage::encode, DropBombMessage::decode, DropBombMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(10, SyncWaypointsMessage.class, SyncWaypointsMessage::encode, SyncWaypointsMessage::decode, SyncWaypointsMessage::receive, Optional.of(PLAY_TO_CLIENT));
		networkChannel.registerMessage(11, ShowWaystonesScreenMessage.class, ShowWaystonesScreenMessage::encode, ShowWaystonesScreenMessage::decode, ShowWaystonesScreenMessage::receive, Optional.of(PLAY_TO_CLIENT));
		networkChannel.registerMessage(12, SetWaypointNameMessage.class, SetWaypointNameMessage::encode, SetWaypointNameMessage::decode, SetWaypointNameMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(13, AttemptTeleportationMessage.class, AttemptTeleportationMessage::encode, AttemptTeleportationMessage::decode, AttemptTeleportationMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(14, RemoveWaypointMessage.class, RemoveWaypointMessage::encode, RemoveWaypointMessage::decode, RemoveWaypointMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(15, ShowWaystoneCreationScreenMessage.class, ShowWaystoneCreationScreenMessage::encode, ShowWaystoneCreationScreenMessage::decode, ShowWaystoneCreationScreenMessage::receive, Optional.of(PLAY_TO_CLIENT));
		networkChannel.registerMessage(16, SyncFreezeEffectMessage.class, SyncFreezeEffectMessage::encode, SyncFreezeEffectMessage::decode, SyncFreezeEffectMessage::receive, Optional.of(PLAY_TO_CLIENT));
		networkChannel.registerMessage(17, ToggleCrawlingMessage.class, ToggleCrawlingMessage::encode, ToggleCrawlingMessage::decode, ToggleCrawlingMessage::receive, Optional.of(PLAY_TO_SERVER));
		networkChannel.registerMessage(18, SyncHookShot.class, SyncHookShot::encode, SyncHookShot::new, SyncHookShot::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}
}
