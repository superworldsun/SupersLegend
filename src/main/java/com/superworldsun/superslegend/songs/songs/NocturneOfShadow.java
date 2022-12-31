package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class NocturneOfShadow extends OcarinaSong
{
	public NocturneOfShadow()
	{
		super("lrralrd", 0x704D7B);
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.NOCTURNE_OF_SHADOW.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		
	}
}
