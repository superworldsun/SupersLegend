package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.WarpSong;

import net.minecraft.util.SoundEvent;

public class SerenadeOfWater extends WarpSong {
	public SerenadeOfWater() {
		super("adrrl", 0x1211BA, BlockInit.WARP_PAD_WATER);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.SERENADE_OF_WATER.get();
	}
}
