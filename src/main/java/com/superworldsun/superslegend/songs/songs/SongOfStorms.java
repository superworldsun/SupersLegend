package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SongOfStorms extends OcarinaSong
{
	public SongOfStorms()
	{
		super("aduadu");
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		if (level.dimension() == World.OVERWORLD && !level.isRaining())
		{
			level.getLevelData().setRaining(true);
		}
	}
}
