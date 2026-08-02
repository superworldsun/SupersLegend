package com.superworldsun.superslegend.network;

import java.util.Optional;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.message.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

@EventBusSubscriber(bus = Bus.MOD, modid = SupersLegendMain.MOD_ID)
public class NetworkDispatcher {
	private static final ResourceLocation CHANNEL_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "channel");
	public static SimpleChannel network_channel;

	@SubscribeEvent
	public static void registerNetworkChannel(FMLCommonSetupEvent event) {
		network_channel = NetworkRegistry.newSimpleChannel(CHANNEL_ID, () -> "1.0", s -> true, s -> true);
		network_channel.registerMessage(1, SyncMagicMessage.class, SyncMagicMessage::encode, SyncMagicMessage::decode, SyncMagicMessage::receive, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(2, MaskAbilityMessage.class, MaskAbilityMessage::encode, MaskAbilityMessage::decode, MaskAbilityMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(8, DoubleJumpMessage.class, DoubleJumpMessage::encode, DoubleJumpMessage::decode, DoubleJumpMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(6, SyncLearnedSongsMessage.class, SyncLearnedSongsMessage::encode, SyncLearnedSongsMessage::decode, SyncLearnedSongsMessage::receive, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(17, ToggleCrawlingMessage.class, ToggleCrawlingMessage::encode, ToggleCrawlingMessage::decode, ToggleCrawlingMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(7, PlaySongMessage.class, PlaySongMessage::encode, PlaySongMessage::decode, PlaySongMessage::handle, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(10, SyncWaypointsMessage.class, SyncWaypointsMessage::encode, SyncWaypointsMessage::decode, SyncWaypointsMessage::handle, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(12, SetWaypointNameMessage.class, SetWaypointNameMessage::encode, SetWaypointNameMessage::decode, SetWaypointNameMessage::handle, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(15, ShowWaystoneCreationScreenMessage.class, ShowWaystoneCreationScreenMessage::encode, ShowWaystoneCreationScreenMessage::decode, ShowWaystoneCreationScreenMessage::handle, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(11, ShowWaystonesScreenMessage.class, ShowWaystonesScreenMessage::encode, ShowWaystonesScreenMessage::decode, ShowWaystonesScreenMessage::handle, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(13, AttemptTeleportationMessage.class, AttemptTeleportationMessage::encode, AttemptTeleportationMessage::decode, AttemptTeleportationMessage::handle, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(14, RemoveWaypointMessage.class, RemoveWaypointMessage::encode, RemoveWaypointMessage::decode, RemoveWaypointMessage::handle, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(3, SetGossipStoneTextMessage.class, SetGossipStoneTextMessage::encode, SetGossipStoneTextMessage::decode, SetGossipStoneTextMessage::handle, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(18, SyncHookshot.class, SyncHookshot::encode, SyncHookshot::new, SyncHookshot::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		network_channel.registerMessage(16, SyncFreezeEffectMessage.class, SyncFreezeEffectMessage::encode, SyncFreezeEffectMessage::decode, SyncFreezeEffectMessage::receive, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(19, DropBombMessage.class, DropBombMessage::encode, DropBombMessage::decode, DropBombMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(20, PegasusBootsInputMessage.class, PegasusBootsInputMessage::encode, PegasusBootsInputMessage::decode, PegasusBootsInputMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(21, SelectAmmoContainerItemMessage.class, SelectAmmoContainerItemMessage::encode, SelectAmmoContainerItemMessage::decode, SelectAmmoContainerItemMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(22, SyncTimeSongModeMessage.class, SyncTimeSongModeMessage::encode, SyncTimeSongModeMessage::decode, SyncTimeSongModeMessage::receive, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(23, DekuWaterHopMessage.class, DekuWaterHopMessage::encode, DekuWaterHopMessage::decode, DekuWaterHopMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(24, DekuWaterHopInputMessage.class, DekuWaterHopInputMessage::encode, DekuWaterHopInputMessage::decode, DekuWaterHopInputMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(25, GiantGroundParticleMessage.class, GiantGroundParticleMessage::encode, GiantGroundParticleMessage::decode, GiantGroundParticleMessage::receive, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(26, PlayOcarinaNoteMessage.class, PlayOcarinaNoteMessage::encode, PlayOcarinaNoteMessage::decode, PlayOcarinaNoteMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(27, SetOcarinaPlayingMessage.class, SetOcarinaPlayingMessage::encode, SetOcarinaPlayingMessage::decode, SetOcarinaPlayingMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(28, DekuFlowerInputMessage.class, DekuFlowerInputMessage::encode, DekuFlowerInputMessage::decode, DekuFlowerInputMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(29, BremenMaskSoundMessage.class, BremenMaskSoundMessage::encode, BremenMaskSoundMessage::decode, BremenMaskSoundMessage::receive, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(30, WithdrawRupeesMessage.class, WithdrawRupeesMessage::encode, WithdrawRupeesMessage::decode, WithdrawRupeesMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(31, OpenTradeChoiceMessage.class, OpenTradeChoiceMessage::encode, OpenTradeChoiceMessage::decode, OpenTradeChoiceMessage::receive, Optional.of(PLAY_TO_CLIENT));
		network_channel.registerMessage(32, SelectTradeModeMessage.class, SelectTradeModeMessage::encode, SelectTradeModeMessage::decode, SelectTradeModeMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(33, BuyRupeeTradeMessage.class, BuyRupeeTradeMessage::encode, BuyRupeeTradeMessage::decode, BuyRupeeTradeMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(34, SwitchToRupeeTradeMessage.class, SwitchToRupeeTradeMessage::encode, SwitchToRupeeTradeMessage::decode, SwitchToRupeeTradeMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(35, RequestRupeeTradeAvailabilityMessage.class, RequestRupeeTradeAvailabilityMessage::encode, RequestRupeeTradeAvailabilityMessage::decode, RequestRupeeTradeAvailabilityMessage::receive, Optional.of(PLAY_TO_SERVER));
		network_channel.registerMessage(36, RupeeTradeAvailabilityMessage.class, RupeeTradeAvailabilityMessage::encode, RupeeTradeAvailabilityMessage::decode, RupeeTradeAvailabilityMessage::receive, Optional.of(PLAY_TO_CLIENT));
	}
}
