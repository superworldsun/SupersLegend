package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.WarpSong;

import net.minecraft.util.SoundEvent;

public class PreludeOfLight extends WarpSong {
	public PreludeOfLight() {
		super("ururlu", 0xC4CA02, BlockInit.WARP_PAD_LIGHT);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.PRELUDE_OF_LIGHT.get();
	}
}
