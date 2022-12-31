package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class PreludeOfLight extends OcarinaSong
{
	public PreludeOfLight()
	{
		super("ururlu", 0xC4CA02);
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.PRELUDE_OF_LIGHT.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
