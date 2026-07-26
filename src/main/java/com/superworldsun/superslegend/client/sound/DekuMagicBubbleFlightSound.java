package com.superworldsun.superslegend.client.sound;

import com.superworldsun.superslegend.entities.projectiles.magic.DekuMagicBubbleEntity;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class DekuMagicBubbleFlightSound extends AbstractTickableSoundInstance {
    // Volume of the fire/flight sound that follows the bubble through the world.
    private static final float FLIGHT_VOLUME = 0.6F;
    private final DekuMagicBubbleEntity bubble;

    public DekuMagicBubbleFlightSound(DekuMagicBubbleEntity bubble) {
        super(SoundInit.DEKU_LINK_BUBBLE_FIRE.get(), SoundSource.PLAYERS, RandomSource.create());
        this.bubble = bubble;
        this.looping = false;
        this.delay = 0;
        this.volume = FLIGHT_VOLUME;
        this.pitch = 1.0F;
        this.attenuation = SoundInstance.Attenuation.LINEAR;
        this.relative = false;
        updatePosition();
    }

    @Override
    public boolean canPlaySound() {
        return !bubble.isSilent();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public void tick() {
        if (!bubble.isAlive() || bubble.isCharging()) {
            stop();
            return;
        }

        updatePosition();
    }

    private void updatePosition() {
        x = bubble.getX();
        y = bubble.getY();
        z = bubble.getZ();
    }
}
