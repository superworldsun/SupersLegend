package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {
    private static final double DEKU_FLOWER_BURIED_CAMERA_OFFSET = 0.35D;

    @Shadow
    public abstract Vec3 getPosition();

    @Shadow
    protected abstract void setPosition(Vec3 position);

    @Inject(method = "setup", at = @At("TAIL"))
    private void superslegend$lowerBuriedDekuFlowerCamera(
            BlockGetter level,
            Entity entity,
            boolean detached,
            boolean mirrored,
            float partialTick,
            CallbackInfo callbackInfo) {
        if (!detached && entity instanceof Player player
                && DekuFlowerFlightEvents.isBuried(player)) {
            setPosition(getPosition().add(0.0D, -DEKU_FLOWER_BURIED_CAMERA_OFFSET, 0.0D));
        }
    }
}
