package com.superworldsun.superslegend.client.sound;

import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public final class DekuFlowerGlideSound extends AbstractTickableSoundInstance {
    private final Player player;

    public DekuFlowerGlideSound(Player player) {
        super(SoundInit.DEKU_LINK_FLAP.get(), SoundSource.PLAYERS, RandomSource.create());
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.8F;
        this.pitch = 1.0F;
        this.attenuation = SoundInstance.Attenuation.NONE;
        this.relative = true;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public void tick() {
        if (!player.isAlive() || !DekuFlowerFlightEvents.isGliding(player)) {
            stop();
        }
    }
}
