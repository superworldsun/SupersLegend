package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.WarpSong;

import net.minecraft.util.SoundEvent;

public class BoleroOfFire extends WarpSong {
	public BoleroOfFire() {
		super("dadardrd", 0xCD2012, BlockInit.WARP_PAD_FIRE);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.BOLERO_OF_FIRE.get();
	}
}
