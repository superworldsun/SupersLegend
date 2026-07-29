package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.capability.waypoint.Waypoint;
import com.superworldsun.superslegend.capability.waypoint.Waypoints;
import com.superworldsun.superslegend.capability.waypoint.WaypointsProvider;
import com.superworldsun.superslegend.capability.waypoint.WaypointsServerData;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.ShowWaystonesScreenMessage;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import java.util.Iterator;
import java.util.List;

public class SongOfSoaring extends OcarinaSong {
	public SongOfSoaring() {
		super("dludlu", 0xCDD5E3);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.SONG_OF_SOARING.get();
	}

	@Override
	public void onSongPlayed(Player player, Level level) {
		if (!(player instanceof ServerPlayer serverPlayer)) {
			return;
		}

		removeNonExistentWaypoints(serverPlayer);
		boolean noWaypoints = WaypointsProvider.get(serverPlayer).getWaypoints().isEmpty();

		if (noWaypoints) {
			player.sendSystemMessage(Component.translatable("song.superslegend.song_of_soaring.no_statues")
					.withStyle(ChatFormatting.DARK_RED));
		} else {
			NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ShowWaystonesScreenMessage(player.getUUID()));
		}
	}

	private static void removeNonExistentWaypoints(ServerPlayer player) {
		Waypoints playerWaypointsData = WaypointsProvider.get(player);
		Iterator<Waypoint> waypointsIterator = playerWaypointsData.getWaypoints().iterator();

		boolean syncRequired = false;

		while (waypointsIterator.hasNext()) {
			Waypoint waypoint = waypointsIterator.next();
			ResourceLocation dimensionId = ResourceLocation.tryParse(waypoint.getDimension());
			ServerLevel waypointLevel = dimensionId == null ? null : player.getServer().getLevel(
					ResourceKey.create(Registries.DIMENSION, dimensionId));
			boolean doesWaypointExist = waypointLevel != null
					&& WaypointsServerData.get(waypointLevel).getWaypoint(waypoint.getStatuePosition()) != null;
			if (!doesWaypointExist) {
				playerWaypointsData.removeWaypoint(waypoint.getStatuePosition());
				syncRequired = true;
			}
		}

		if (syncRequired) {
			WaypointsProvider.sync(player);
		}
	}
}
