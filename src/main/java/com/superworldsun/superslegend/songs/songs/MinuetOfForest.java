package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MinuetOfForest extends OcarinaSong
{
	public MinuetOfForest()
	{
		super("aulrlr");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.MINUET_OF_FOREST.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
