package com.superworldsun.superslegend.entities.projectiles.hooks;

import com.superworldsun.superslegend.entities.HeartEntity;
import com.superworldsun.superslegend.entities.LargeMagicJarEntity;
import com.superworldsun.superslegend.entities.MagicJarEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

final class HookshotRetrievalTarget {
    private static final double RETRIEVAL_HITBOX_HALF_SIZE = 0.25D;

    private HookshotRetrievalTarget() {
    }

    static boolean canRetrieve(Entity entity) {
        return entity instanceof ItemEntity
                || entity instanceof HeartEntity
                || entity instanceof MagicJarEntity
                || entity instanceof LargeMagicJarEntity;
    }

    static Entity findAlongPath(AbstractArrow hook) {
        Vec3 start = hook.position();
        Vec3 movement = hook.getDeltaMovement();
        if (movement.lengthSqr() < 1.0E-7D) {
            return null;
        }

        Vec3 end = start.add(movement);
        AABB searchArea = new AABB(start, start)
                .expandTowards(movement)
                .inflate(RETRIEVAL_HITBOX_HALF_SIZE);
        Entity nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Entity candidate : hook.level().getEntities(hook, searchArea,
                entity -> entity.isAlive() && canRetrieve(entity))) {
            AABB targetBox = candidate.getBoundingBox().inflate(RETRIEVAL_HITBOX_HALF_SIZE);
            Optional<Vec3> intersection = targetBox.clip(start, end);
            double distance;
            if (targetBox.contains(start)) {
                distance = 0.0D;
            } else if (intersection.isPresent()) {
                distance = start.distanceToSqr(intersection.get());
            } else {
                continue;
            }

            if (distance < nearestDistance) {
                nearest = candidate;
                nearestDistance = distance;
            }
        }

        if (nearest == null) {
            return null;
        }

        BlockHitResult blockHit = hook.level().clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, hook));
        if (blockHit.getType() != HitResult.Type.MISS
                && start.distanceToSqr(blockHit.getLocation()) < nearestDistance) {
            return null;
        }
        return nearest;
    }
}
