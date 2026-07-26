package com.superworldsun.superslegend.client.sound;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class BremenMaskSound extends AbstractTickableSoundInstance {
	private static final Map<Integer, BremenMaskSound> ACTIVE_SOUNDS = new HashMap<>();

	private final Player player;
	private boolean stopping;

	public BremenMaskSound(Player player) {
		super(SoundInit.BREMEN_MARCH.get(), SoundSource.PLAYERS , RandomSource.create());
		this.player = player;
		this.looping = true;
		this.delay = 0;
		this.volume = 1.0F;
		this.x = player.getX();
		this.y = player.getY();
		this.z = player.getZ();
	}

	public static void setPlaying(int playerId, boolean playing) {
		if (!playing) {
			stopPlaying(playerId);
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null || !(minecraft.level.getEntity(playerId) instanceof Player player)) {
			return;
		}

		BremenMaskSound currentSound = ACTIVE_SOUNDS.get(playerId);
		if (currentSound != null && !currentSound.stopping && currentSound.player == player) {
			return;
		}

		stopPlaying(playerId);
		BremenMaskSound sound = new BremenMaskSound(player);
		ACTIVE_SOUNDS.put(playerId, sound);
		minecraft.getSoundManager().play(sound);
	}

	private static void stopPlaying(int playerId) {
		BremenMaskSound sound = ACTIVE_SOUNDS.remove(playerId);
		if (sound != null) {
			sound.stopping = true;
			sound.stop();
		}
	}

	@Override
	public boolean canPlaySound() {
		return !player.isSilent();
	}

	@Override
	public boolean canStartSilent() {
		return true;
	}

	@Override
	public void tick() {
		if (stopping || !player.isAlive() || player.isRemoved()) {
			ACTIVE_SOUNDS.remove(player.getId(), this);
			stop();
			return;
		}

		x = player.getX();
		y = player.getY();
		z = player.getZ();
	}
}
