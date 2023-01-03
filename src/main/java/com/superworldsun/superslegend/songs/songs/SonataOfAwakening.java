package com.superworldsun.superslegend.songs.songs;

import java.util.List;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

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
	public void onSongPlayed(PlayerEntity player, World world) {
		getPlayersInAreaOfEffect(player, world).stream().filter(PlayerEntity::isSleeping).forEach(PlayerEntity::stopSleeping);
	}

	private List<PlayerEntity> getPlayersInAreaOfEffect(PlayerEntity player, World world) {
		AxisAlignedBB areaOfEffect = player.getBoundingBox().inflate(EFFECT_RADIUS);
		return world.getNearbyEntities(PlayerEntity.class, EntityPredicate.DEFAULT, player, areaOfEffect);
	}
}
