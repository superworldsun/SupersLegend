package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SongOfStorms extends OcarinaSong
{
	public SongOfStorms()
	{
		super("aduadu", 0xC87D8E);
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.SONG_OF_STORMS.get();
	}
	
	@Override
	public void onSongPlayed(Player player, Level level)
	{
		if (level.dimension() == Level.OVERWORLD && !level.isRaining() && !level.isClientSide)
		{
			if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
				ModAdvancementHelper.award(serverPlayer, "storm_warning", "called_storm");
			}
			((ServerLevel) level).setWeatherParameters(0, 6000, true, false);
		}
	}
}
