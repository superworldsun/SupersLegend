package com.superworldsun.superslegend.songs.songs;

import java.util.List;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SonataOfAwakening extends OcarinaSong {
	private static final int EFFECT_RADIUS = 5;

	public SonataOfAwakening() {
		super("ululara", 0x00A915);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.SONATA_OF_AWAKENING.get();
	}

	@Override
	public void onSongPlayed(Player player, Level world) {
		getPlayersInAreaOfEffect(player, world).stream().filter(Player::isSleeping).forEach(Player::stopSleeping);
		if (world instanceof ServerLevel serverLevel) {
			awakenSilverfish(player, serverLevel);
		}
	}

	private void awakenSilverfish(Player player, ServerLevel level) {
		BlockPos center = player.blockPosition();
		BlockPos min = center.offset(-EFFECT_RADIUS, -EFFECT_RADIUS, -EFFECT_RADIUS);
		BlockPos max = center.offset(EFFECT_RADIUS, EFFECT_RADIUS, EFFECT_RADIUS);
		int radiusSquared = EFFECT_RADIUS * EFFECT_RADIUS;

		for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
			if (center.distSqr(pos) > radiusSquared) {
				continue;
			}

			BlockState state = level.getBlockState(pos);
			if (!(state.getBlock() instanceof InfestedBlock)) {
				continue;
			}

			Silverfish silverfish = EntityType.SILVERFISH.create(level);
			if (silverfish == null) {
				continue;
			}

			BlockPos spawnPos = pos.immutable();
			level.setBlock(spawnPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
			level.levelEvent(2001, spawnPos, Block.getId(state));
			silverfish.moveTo(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D,
					level.random.nextFloat() * 360.0F, 0.0F);
			level.addFreshEntity(silverfish);
			silverfish.spawnAnim();
		}
	}

	private List<Player> getPlayersInAreaOfEffect(Player player, Level world) {
		AABB areaOfEffect = player.getBoundingBox().inflate(EFFECT_RADIUS);
		return world.getEntitiesOfClass(Player.class, areaOfEffect,
				nearbyPlayer -> nearbyPlayer != player && nearbyPlayer.distanceToSqr(player) <= EFFECT_RADIUS * EFFECT_RADIUS);
	}
}
