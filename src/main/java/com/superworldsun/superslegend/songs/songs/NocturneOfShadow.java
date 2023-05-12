package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.WarpSong;

import net.minecraft.util.SoundEvent;

public class NocturneOfShadow extends WarpSong {
	public NocturneOfShadow() {
		super("lrralrd", 0x704D7B, BlockInit.WARP_PAD_SHADOW);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.NOCTURNE_OF_SHADOW.get();
	}
}
