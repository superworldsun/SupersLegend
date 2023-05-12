package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.WarpSong;

import net.minecraft.util.SoundEvent;

public class RequiemOfSpirit extends WarpSong {
	public RequiemOfSpirit() {
		super("adarda", 0xD8541C, BlockInit.WARP_PAD_SPIRIT);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.REQUIEM_OF_SPIRIT.get();
	}
}
