package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.songs.OcarinaSong;
import com.superworldsun.superslegend.songs.epona.EponasHorseManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class EponasSong extends OcarinaSong {
	public EponasSong() {
		super("ulrulr", 0xCD5819);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.EPONAS_SONG.get();
	}

	@Override
	public boolean requiresOcarinaOfTime() {
		return false;
	}

	@Override
	public void onSongPlayed(Player player, Level level) {
		if (player instanceof ServerPlayer serverPlayer) {
			EponasHorseManager.summonMarkedHorse(serverPlayer, false);
			ModAdvancementHelper.award(serverPlayer, "call_of_the_wild", "called_epona");
		}
	}

	@Override
	public void onSongPlayed(Player player, Level level, boolean rearFacingCamera) {
		if (player instanceof ServerPlayer serverPlayer) {
			EponasHorseManager.summonMarkedHorse(serverPlayer, rearFacingCamera);
			ModAdvancementHelper.award(serverPlayer, "call_of_the_wild", "called_epona");
		}
	}
}
