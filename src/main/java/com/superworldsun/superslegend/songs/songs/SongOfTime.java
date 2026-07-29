package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.blocks.TimeBlock;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
	public boolean requiresOcarinaOfTime() {
		return true;
	}

	@Override
	public void onSongPlayed(Player player, Level level) {
		getBlocksInAreaOfEffect(player).forEach(pos -> {
			toggleTimeBlock(level, pos);
		});
	}

	private void toggleTimeBlock(Level level, BlockPos pos) {
		BlockState blockState = level.getBlockState(pos);
		Block block = blockState.getBlock();

		if (block instanceof TimeBlock timeBlock) {
			timeBlock.toggle(level, blockState, pos);
		}
	}

	private Iterable<BlockPos> getBlocksInAreaOfEffect(Player player) {
		BlockPos start = player.blockPosition().offset(-EFFECT_RADIUS, -EFFECT_RADIUS, -EFFECT_RADIUS);
		BlockPos end = player.blockPosition().offset(EFFECT_RADIUS, EFFECT_RADIUS, EFFECT_RADIUS);
		return BlockPos.betweenClosed(start, end);
	}
}
