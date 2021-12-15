package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class NewWaveBossaNova extends OcarinaSong
{
	public NewWaveBossaNova()
	{
		super("lulrdlr");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.NEW_WAVE_BOSSA_NOVA.get();
	}

	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
