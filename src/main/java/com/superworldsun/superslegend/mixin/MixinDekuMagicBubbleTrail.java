package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.entities.projectiles.magic.DekuMagicBubbleEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractHurtingProjectile.class)
public abstract class MixinDekuMagicBubbleTrail {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
            )
    )
    private void superslegend$removeDekuBubbleFlightParticles(
            Level level,
            ParticleOptions particle,
            double x,
            double y,
            double z,
            double velocityX,
            double velocityY,
            double velocityZ
    ) {
        if (!((Object) this instanceof DekuMagicBubbleEntity)) {
            level.addParticle(particle, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
}
