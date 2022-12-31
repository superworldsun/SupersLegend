package com.superworldsun.superslegend.songs.songs;

import java.util.Iterator;

import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.ShowWaystonesScreenMessage;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;
import com.superworldsun.superslegend.waypoints.IWaypoints;
import com.superworldsun.superslegend.waypoints.Waypoint;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;
import com.superworldsun.superslegend.waypoints.WaypointsServerData;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class SongOfSoaring extends OcarinaSong {
	public SongOfSoaring() {
		super("dludlu", 0xCDD5E3);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.SONG_OF_SOARING.get();
	}

	@Override
	public void onSongPlayed(PlayerEntity player, World level) {
		removeNonExistentWaypoints(player);
		boolean noWaypoints = WaypointsProvider.get(player).getWaypoints().isEmpty();

		if (noWaypoints) {
			player.sendMessage(new TranslationTextComponent("song.superslegend.song_of_soaring.no_statues"), Util.NIL_UUID);
		} else {
			NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new ShowWaystonesScreenMessage());
		}
	}

	private static void removeNonExistentWaypoints(PlayerEntity player) {
		IWaypoints playerWaypointsData = WaypointsProvider.get(player);
		Iterator<Waypoint> waypointsIterator = playerWaypointsData.getWaypoints().iterator();
		boolean syncRequired = false;

		while (waypointsIterator.hasNext()) {
			Waypoint waypoint = waypointsIterator.next();
			boolean doesWaypointExist = WaypointsServerData.instance().getWaypoint(waypoint.getStatuePosition()) != null;

			if (!doesWaypointExist) {
				playerWaypointsData.removeWaypoint(waypoint.getStatuePosition());
				syncRequired = true;
			}
		}

		if (syncRequired) {
			WaypointsProvider.sync((ServerPlayerEntity) player);
		}
	}
}
