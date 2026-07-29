package com.superworldsun.superslegend.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/** Detects the first fluid surface crossed by a fast-moving projectile. */
public final class ProjectileFluidCollisionHelper {
    private ProjectileFluidCollisionHelper() {
    }

    public static @Nullable FluidContact findContact(Level level, Entity projectile, Vec3 start, Vec3 end,
                                                     TagKey<Fluid> fluidTag, boolean sourceOnly) {
        ClipContext.Fluid clipMode = sourceOnly ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.ANY;
        BlockHitResult hit = level.clip(new ClipContext(
                start, end, ClipContext.Block.COLLIDER, clipMode, projectile));

        if (hit.getType() != HitResult.Type.MISS) {
            var fluidState = level.getFluidState(hit.getBlockPos());
            if (fluidState.is(fluidTag) && (!sourceOnly || fluidState.isSource())) {
                return new FluidContact(hit.getLocation(), hit.getBlockPos());
            }
        }

        // Covers projectiles spawned inside fluid and movement segments too
        // short to produce a useful ray intersection.
        BlockPos currentPos = projectile.blockPosition();
        var currentFluid = level.getFluidState(currentPos);
        if (currentFluid.is(fluidTag) && (!sourceOnly || currentFluid.isSource())) {
            return new FluidContact(projectile.position(), currentPos);
        }

        return null;
    }

    public record FluidContact(Vec3 location, BlockPos blockPos) {
    }
}
