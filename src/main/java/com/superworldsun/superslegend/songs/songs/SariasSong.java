package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SariasSong extends OcarinaSong
{
	public SariasSong()
	{
		super("drldrl");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.SARIAS_SONG.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
