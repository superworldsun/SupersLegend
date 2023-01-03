package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.blocks.TimeBlock;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SongOfTime extends OcarinaSong {
	private static final int EFFECT_RADIUS = 5;

	public SongOfTime() {
		super("radrad", 0x3170CF);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.SONG_OF_TIME.get();
	}

	@Override
	public void onSongPlayed(PlayerEntity player, World level) {
		getBlocksInAreaOfEffect(player).forEach(pos -> {
			toggleTimeBlock(level, pos);
		});
	}

	private void toggleTimeBlock(World level, BlockPos pos) {
		BlockState blockState = level.getBlockState(pos);
		Block block = blockState.getBlock();

		if (block instanceof TimeBlock) {
			TimeBlock timeBlock = (TimeBlock) block;
			timeBlock.toggle(level, blockState, pos);
		}
	}

	private Iterable<BlockPos> getBlocksInAreaOfEffect(PlayerEntity player) {
		BlockPos start = player.blockPosition().offset(-EFFECT_RADIUS, -EFFECT_RADIUS, -EFFECT_RADIUS);
		BlockPos end = player.blockPosition().offset(EFFECT_RADIUS, EFFECT_RADIUS, EFFECT_RADIUS);
		return BlockPos.betweenClosed(start, end);
	}
}
