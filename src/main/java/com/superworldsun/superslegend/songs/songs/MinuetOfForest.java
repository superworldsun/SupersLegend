package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.WarpSong;

import net.minecraft.util.SoundEvent;

public class MinuetOfForest extends WarpSong {
	public MinuetOfForest() {
		super("aulrlr", 0x18893D, BlockInit.WARP_PAD_FOREST);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.MINUET_OF_FOREST.get();
	}
}
