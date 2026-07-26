package com.superworldsun.superslegend.client.sound;

import com.superworldsun.superslegend.entities.projectiles.magic.DekuMagicBubbleEntity;
import com.superworldsun.superslegend.items.curios.head.masks.DekuMask;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class DekuMagicBubbleChargeSound extends AbstractTickableSoundInstance {
    // Volume of the bubble-blowing sound while the bubble is being charged.
    private static final float CHARGE_VOLUME = 0.7F;

    // Playback pitch of the bubble-blowing sound. Values above 1 are higher/faster.
    private static final float CHARGE_PITCH = 1.0F;

    private final DekuMagicBubbleEntity bubble;

    public DekuMagicBubbleChargeSound(DekuMagicBubbleEntity bubble) {
        super(SoundInit.DEKU_LINK_BUBBLE_BLOW.get(), SoundSource.PLAYERS, RandomSource.create());
        this.bubble = bubble;
        this.looping = false;
        this.delay = 0;
        this.volume = CHARGE_VOLUME;
        this.pitch = CHARGE_PITCH;
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
        boolean localPlayerReleased = bubble.getOwner() == Minecraft.getInstance().player
                && Minecraft.getInstance().player != null
                && !DekuMask.isChargingMagicBubble(Minecraft.getInstance().player);
        if (!bubble.isAlive() || !bubble.isCharging() || localPlayerReleased) {
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
