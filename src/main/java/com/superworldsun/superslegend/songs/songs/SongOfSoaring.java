package com.superworldsun.superslegend.songs.songs;

import java.util.Iterator;

import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.ShowWaystonesScreenMessage;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;
import com.superworldsun.superslegend.waypoints.Waypoint;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;
import com.superworldsun.superslegend.waypoints.WaypointsServerData;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class SongOfSoaring extends OcarinaSong
{
	public SongOfSoaring()
	{
		super("dludlu");
	}
	
	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.SONG_OF_SOARING.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		Iterator<Waypoint> savedWaypointsIterator = WaypointsProvider.get(player).getWaypoints().iterator();
		boolean syncRequired = false;
		
		while (savedWaypointsIterator.hasNext())
		{
			Waypoint savedWaypoint = savedWaypointsIterator.next();
			
			// if saved waypoint doesn't exist on server anymore
			if (WaypointsServerData.instance().getWaypoint(savedWaypoint.getStatuePosition()) == null)
			{
				// remove it
				WaypointsProvider.get(player).removeWaypoint(savedWaypoint.getStatuePosition());
				syncRequired = true;
			}
		}
		
		if (syncRequired)
		{
			WaypointsProvider.sync((ServerPlayerEntity) player);
		}
		
		if (WaypointsProvider.get(player).getWaypoints().isEmpty())
		{
			player.sendMessage(new TranslationTextComponent("song.superslegend.song_of_soaring.no_statues"), null);
		}
		else
		{
			NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new ShowWaystonesScreenMessage());
		}
	}
}
