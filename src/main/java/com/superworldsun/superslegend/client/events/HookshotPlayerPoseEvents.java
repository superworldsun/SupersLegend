package com.superworldsun.superslegend.client.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.entities.projectiles.hooks.LongshotEntity;
import com.superworldsun.superslegend.events.HookshotPullPoseEvents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class HookshotPlayerPoseEvents {
    private static final double HOOK_SEARCH_RANGE = 64.0D;
    private static final Map<UUID, RotationState> SAVED_ROTATIONS = new HashMap<>();

    private HookshotPlayerPoseEvents() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void keepFiringArmRaised(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        InteractionHand firedHand = findActiveHookHand(player);
        if (firedHand == null) {
            return;
        }

        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
        HumanoidArm firingArm = firedHand == InteractionHand.MAIN_HAND
                ? player.getMainArm()
                : opposite(player.getMainArm());
        if (firingArm == HumanoidArm.RIGHT) {
            model.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
        } else {
            model.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
        }

        Vec3 pullDirection = getPullDirection(player, event.getPartialTick());
        if (pullDirection == null || pullDirection.lengthSqr() < 1.0E-7D) {
            return;
        }

        SAVED_ROTATIONS.put(player.getUUID(), RotationState.capture(player));
        float horizontalDistance = Mth.sqrt((float) (pullDirection.x * pullDirection.x + pullDirection.z * pullDirection.z));
        float pullYaw = (float) (Mth.atan2(-pullDirection.x, pullDirection.z) * Mth.RAD_TO_DEG);
        float pullPitch = (float) (Mth.atan2(-pullDirection.y, horizontalDistance) * Mth.RAD_TO_DEG);
        player.setYRot(pullYaw);
        player.yRotO = pullYaw;
        player.yBodyRot = pullYaw;
        player.yBodyRotO = pullYaw;
        player.yHeadRot = pullYaw;
        player.yHeadRotO = pullYaw;
        player.setXRot(pullPitch);
        player.xRotO = pullPitch;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void restorePlayerRotations(RenderPlayerEvent.Post event) {
        RotationState saved = SAVED_ROTATIONS.remove(event.getEntity().getUUID());
        if (saved != null) {
            saved.restore(event.getEntity());
        }
    }

    public static Vec3 getPullDirection(Player player, float partialTick) {
        Vec3 playerPosition = new Vec3(
                Mth.lerp(partialTick, player.xo, player.getX()),
                Mth.lerp(partialTick, player.yo, player.getY()) + HookshotPullPoseEvents.getPullEyeHeight(player),
                Mth.lerp(partialTick, player.zo, player.getZ()));
        AABB searchArea = player.getBoundingBox().inflate(HOOK_SEARCH_RANGE);
        for (HookshotEntity hook : player.level().getEntitiesOfClass(HookshotEntity.class, searchArea,
                entity -> entity.isAlive() && entity.isPullingPlayer())) {
            if (hook.getHookOwner() == player) {
                return interpolatedPosition(hook, partialTick).subtract(playerPosition);
            }
        }
        for (LongshotEntity hook : player.level().getEntitiesOfClass(LongshotEntity.class, searchArea,
                entity -> entity.isAlive() && entity.isPullingPlayer())) {
            if (hook.getHookOwner() == player) {
                return interpolatedPosition(hook, partialTick).subtract(playerPosition);
            }
        }
        return null;
    }

    private static Vec3 interpolatedPosition(net.minecraft.world.entity.Entity entity, float partialTick) {
        return new Vec3(
                Mth.lerp(partialTick, entity.xo, entity.getX()),
                Mth.lerp(partialTick, entity.yo, entity.getY()),
                Mth.lerp(partialTick, entity.zo, entity.getZ()));
    }

    public static InteractionHand findActiveHookHand(Player player) {
        AABB searchArea = player.getBoundingBox().inflate(HOOK_SEARCH_RANGE);
        for (HookshotEntity hook : player.level().getEntitiesOfClass(HookshotEntity.class, searchArea, Entity -> Entity.isAlive())) {
            if (hook.getHookOwner() == player) {
                return hook.getFiredHand();
            }
        }
        for (LongshotEntity hook : player.level().getEntitiesOfClass(LongshotEntity.class, searchArea, Entity -> Entity.isAlive())) {
            if (hook.getHookOwner() == player) {
                return hook.getFiredHand();
            }
        }
        return null;
    }

    private static HumanoidArm opposite(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    private record RotationState(float xRot, float xRotO, float yRot, float yRotO,
                                 float bodyRot, float bodyRotO, float headRot, float headRotO) {
        private static RotationState capture(Player player) {
            return new RotationState(player.getXRot(), player.xRotO, player.getYRot(), player.yRotO,
                    player.yBodyRot, player.yBodyRotO, player.yHeadRot, player.yHeadRotO);
        }

        private void restore(Player player) {
            player.setXRot(xRot);
            player.xRotO = xRotO;
            player.setYRot(yRot);
            player.yRotO = yRotO;
            player.yBodyRot = bodyRot;
            player.yBodyRotO = bodyRotO;
            player.yHeadRot = headRot;
            player.yHeadRotO = headRotO;
        }
    }
}
