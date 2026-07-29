package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.items.curios.head.masks.GiantsMask;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class MixinGiantLandingParticles {
    @Redirect(
            method = "checkFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
            )
    )
    private int superslegend$replaceGiantLandingParticles(ServerLevel level,
                                                           ParticleOptions particle,
                                                           double x,
                                                           double y,
                                                           double z,
                                                           int count,
                                                           double xSpread,
                                                           double ySpread,
                                                           double zSpread,
                                                           double speed) {
        if ((Object) this instanceof Player player
                && GiantsMask.isGiantTransformationActive(player)) {
            return 0;
        }

        return level.sendParticles(
                particle,
                x,
                y,
                z,
                count,
                xSpread,
                ySpread,
                zSpread,
                speed
        );
    }
}
