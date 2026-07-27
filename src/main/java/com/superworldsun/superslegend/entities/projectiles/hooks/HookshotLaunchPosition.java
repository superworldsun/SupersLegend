package com.superworldsun.superslegend.entities.projectiles.hooks;

import net.minecraft.util.Mth;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

final class HookshotLaunchPosition {
    private static final double SIDE_OFFSET = 0.30D;
    private static final double FORWARD_OFFSET = 0.88D;
    private static final double VERTICAL_REACH = 0.55D;
    private static final double HEIGHT_BELOW_EYES = 0.38D;
    private static final double BLOCK_FACE_CLEARANCE = 0.02D;

    private HookshotLaunchPosition() {
    }

    static Vec3 getFiringHandPosition(LivingEntity owner, InteractionHand hand) {
        HumanoidArm heldArm = hand == InteractionHand.MAIN_HAND
                ? owner.getMainArm()
                : opposite(owner.getMainArm());
        double sideOffset = (heldArm == HumanoidArm.RIGHT ? 1.0D : -1.0D) * SIDE_OFFSET;
        float bodyYaw = owner.yBodyRot * Mth.DEG_TO_RAD;
        double sinYaw = Mth.sin(bodyYaw);
        double cosYaw = Mth.cos(bodyYaw);
        Vec3 lookDirection = owner.getLookAngle();

        double x = owner.getX() - cosYaw * sideOffset + lookDirection.x * FORWARD_OFFSET;
        double y = owner.getY() + owner.getEyeHeight() - HEIGHT_BELOW_EYES
                + lookDirection.y * VERTICAL_REACH
                + (owner.isCrouching() ? -0.1875D : 0.0D);
        double z = owner.getZ() - sinYaw * sideOffset + lookDirection.z * FORWARD_OFFSET;
        return new Vec3(x, y, z);
    }

    static void moveToFiringHand(AbstractArrow hook, LivingEntity owner, InteractionHand hand, double aimDistance) {
        Vec3 firingPosition = getSafeFiringPosition(hook, owner, hand);
        hook.setPos(firingPosition.x, firingPosition.y, firingPosition.z);

        if (!owner.level().noCollision(hook, hook.getBoundingBox())) {
            Vec3 eyePosition = owner.getEyePosition();
            hook.setPos(eyePosition.x, eyePosition.y, eyePosition.z);
        }

        double speed = hook.getDeltaMovement().length();
        if (speed > 0.0D) {
            Vec3 aimPoint = owner.getEyePosition().add(owner.getLookAngle().scale(Math.max(aimDistance, 1.0D)));
            Vec3 aimDirection = aimPoint.subtract(hook.position());
            hook.shoot(aimDirection.x, aimDirection.y, aimDirection.z, (float) speed, 0.0F);
        }
    }

    private static Vec3 getSafeFiringPosition(AbstractArrow hook, LivingEntity owner, InteractionHand hand) {
        Vec3 eyePosition = owner.getEyePosition();
        Vec3 desiredPosition = getFiringHandPosition(owner, hand);
        BlockHitResult hitResult = owner.level().clip(new ClipContext(
                eyePosition,
                desiredPosition,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                owner
        ));
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return desiredPosition;
        }

        Direction face = hitResult.getDirection();
        double halfExtent = face.getAxis() == Direction.Axis.Y
                ? hook.getBbHeight() * 0.5D
                : hook.getBbWidth() * 0.5D;
        double offset = halfExtent + BLOCK_FACE_CLEARANCE;
        return hitResult.getLocation().add(
                face.getStepX() * offset,
                face.getStepY() * offset,
                face.getStepZ() * offset
        );
    }

    private static HumanoidArm opposite(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }
}
