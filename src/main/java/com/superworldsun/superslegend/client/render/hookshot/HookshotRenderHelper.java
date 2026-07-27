package com.superworldsun.superslegend.client.render.hookshot;

import com.superworldsun.superslegend.events.HookshotPullPoseEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

final class HookshotRenderHelper {
    private static final double VIEW_BOBBING_SCALE = 960.0D;
    private static final float FIRST_PERSON_HAND_HEIGHT = -0.80F;
    private static final double THIRD_PERSON_PULL_MODEL_LIFT = 0.70D;

    private HookshotRenderHelper() {
    }

    static Vec3 getChainVector(Entity hook, Player player, InteractionHand firedHand, float partialTick,
                               EntityRenderDispatcher dispatcher) {
        HumanoidArm heldArm = firedHand == InteractionHand.MAIN_HAND
                ? player.getMainArm()
                : opposite(player.getMainArm());
        int armDirection = heldArm == HumanoidArm.RIGHT ? 1 : -1;

        double handX;
        double handY;
        double handZ;
        Minecraft minecraft = Minecraft.getInstance();
        boolean firstPerson = dispatcher.options != null
                && dispatcher.options.getCameraType().isFirstPerson()
                && player == minecraft.player;
        Vec3 hookPosition = new Vec3(
                Mth.lerp(partialTick, hook.xo, hook.getX()),
                Mth.lerp(partialTick, hook.yo, hook.getY()),
                Mth.lerp(partialTick, hook.zo, hook.getZ()));

        if (firstPerson) {
            double fovScale = VIEW_BOBBING_SCALE / dispatcher.options.fov().get();
            Vec3 handOffset = dispatcher.camera.getNearPlane()
                    .getPointOnPlane(armDirection * 0.525F, FIRST_PERSON_HAND_HEIGHT)
                    .scale(fovScale);
            Vec3 cameraPosition = dispatcher.camera.getPosition();
            handX = cameraPosition.x + handOffset.x;
            handY = cameraPosition.y + handOffset.y;
            handZ = cameraPosition.z + handOffset.z;
        } else if (HookshotPullPoseEvents.isHookPullPoseActive(player)) {
            Vec3 playerPosition = new Vec3(
                    Mth.lerp(partialTick, player.xo, player.getX()),
                    Mth.lerp(partialTick, player.yo, player.getY()),
                    Mth.lerp(partialTick, player.zo, player.getZ()));
            Vec3 raisedBodyPosition = playerPosition.add(0.0D, THIRD_PERSON_PULL_MODEL_LIFT, 0.0D);
            Vec3 pullDirection = hookPosition.subtract(raisedBodyPosition);
            Vec3 forward = pullDirection.lengthSqr() > 1.0E-7D
                    ? pullDirection.normalize()
                    : player.getLookAngle();
            Vec3 side = forward.cross(new Vec3(0.0D, 1.0D, 0.0D));
            if (side.lengthSqr() < 1.0E-7D) {
                float bodyYaw = Mth.lerp(partialTick, player.yBodyRotO, player.yBodyRot) * Mth.DEG_TO_RAD;
                side = new Vec3(-Mth.cos(bodyYaw), 0.0D, -Mth.sin(bodyYaw));
            } else {
                side = side.normalize();
            }
            Vec3 handPosition = raisedBodyPosition
                    .add(forward.scale(0.92D))
                    .add(side.scale(armDirection * 0.30D));
            handX = handPosition.x;
            handY = handPosition.y;
            handZ = handPosition.z;
        } else {
            float bodyYaw = Mth.lerp(partialTick, player.yBodyRotO, player.yBodyRot) * Mth.DEG_TO_RAD;
            float headYaw = Mth.lerp(partialTick, player.yHeadRotO, player.yHeadRot) * Mth.DEG_TO_RAD;
            float headPitch = Mth.lerp(partialTick, player.xRotO, player.getXRot()) * Mth.DEG_TO_RAD;
            double bodySin = Mth.sin(bodyYaw);
            double bodyCos = Mth.cos(bodyYaw);
            double pitchCos = Mth.cos(headPitch);
            double lookX = -Mth.sin(headYaw) * pitchCos;
            double lookY = -Mth.sin(headPitch);
            double lookZ = Mth.cos(headYaw) * pitchCos;
            double sideOffset = armDirection * 0.30D;
            double forwardOffset = 0.88D;
            double verticalReach = 0.55D;
            handX = Mth.lerp(partialTick, player.xo, player.getX()) - bodyCos * sideOffset
                    + lookX * forwardOffset;
            handY = Mth.lerp(partialTick, player.yo, player.getY()) + player.getEyeHeight() - 0.38D
                    + lookY * verticalReach
                    + (player.isCrouching() ? -0.1875D : 0.0D);
            handZ = Mth.lerp(partialTick, player.zo, player.getZ()) - bodySin * sideOffset
                    + lookZ * forwardOffset;
        }

        return new Vec3(handX, handY, handZ).subtract(hookPosition);
    }

    private static HumanoidArm opposite(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }
}
