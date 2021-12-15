package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SerenadeOfWater extends OcarinaSong
{
	public SerenadeOfWater()
	{
		super("adrrl");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.SERENADE_OF_WATER.get();
	}

	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
