package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;
import com.superworldsun.superslegend.world.ElegyStatueManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class ElegyOfEmptyness extends OcarinaSong
{
	public ElegyOfEmptyness()
	{
		super("rlrdrul", 0x2E2719);
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.ELEGY_OF_EMPTYNESS.get();
	}

	@Override
	public void onSongPlayed(Player player, Level level)
	{
		if (player instanceof ServerPlayer serverPlayer) {
			ElegyStatueManager.selectVariant(player)
					.ifPresent(variant -> ElegyStatueManager.summon(serverPlayer, variant));
		}
	}
}
