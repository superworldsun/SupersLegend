package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class RequiemOfSpirit extends OcarinaSong
{
	public RequiemOfSpirit()
	{
		super("adarda", 0xD8541C);
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.REQUIEM_OF_SPIRIT.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
