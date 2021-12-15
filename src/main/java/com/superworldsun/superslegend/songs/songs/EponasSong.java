package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EponasSong extends OcarinaSong
{
	public EponasSong()
	{
		super("ulrulr");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.EPONAS_SONG.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
