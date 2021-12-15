package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.IAngerable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class GoronLullaby extends OcarinaSong
{
	public GoronLullaby()
	{
		super("arlarlra");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.GORON_LULLABY.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		int radius = 5;
		level.getEntities(player, player.getBoundingBox().inflate(radius)).forEach(entity ->
		{
			if (entity instanceof IAngerable && ((IAngerable) entity).isAngry())
			{
				((IAngerable) entity).stopBeingAngry();
			}
		});
	}
}
