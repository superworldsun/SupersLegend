package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class BoleroOfFire extends OcarinaSong
{
	public BoleroOfFire()
	{
		super("dadardrd", 0xCD2012);
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.BOLERO_OF_FIRE.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
