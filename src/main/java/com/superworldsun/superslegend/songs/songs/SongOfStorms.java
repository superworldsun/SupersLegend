package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SongOfStorms extends OcarinaSong
{
	public SongOfStorms()
	{
		super("aduadu");
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		if (level.dimension() == World.OVERWORLD && !level.isRaining() && !level.isClientSide)
		{
			((ServerWorld) level).setWeatherParameters(0, 6000, true, false);
		}
	}
}
