package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

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
		
	}
}
