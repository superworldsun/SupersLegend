package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.PegasusBootsArmor;
import com.superworldsun.superslegend.items.item.RocsFeather;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.PegasusBootsInputMessage;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class PegasusBootsInputEvents {
    private static UUID lastPlayerId;
    private static boolean lastForwardOnly;
    private static boolean wasRunEligible;
    private static boolean shortDropGrace;
    private static int gapCrossingTicks;
    private static double lastRunningGroundHeight = Double.NaN;
    private static int warmUpTicksRemaining;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            lastPlayerId = null;
            lastForwardOnly = false;
            wasRunEligible = false;
            shortDropGrace = false;
            gapCrossingTicks = 0;
            lastRunningGroundHeight = Double.NaN;
            warmUpTicksRemaining = 0;
            return;
        }

        boolean forwardOnly = hasChargeInput(minecraft, player);
        UUID playerId = player.getUUID();
        boolean playerChanged = !playerId.equals(lastPlayerId);
        if (playerChanged) {
            shortDropGrace = false;
            gapCrossingTicks = 0;
            lastRunningGroundHeight = Double.NaN;
        }
        if (player.onGround() && wasRunEligible) {
            lastRunningGroundHeight = player.getY();
            gapCrossingTicks = 0;
        }

        double runningGroundHeight = Double.isNaN(lastRunningGroundHeight)
                ? player.getY()
                : lastRunningGroundHeight;
        Vec3 gapLanding = !player.onGround()
                && wasRunEligible
                && gapCrossingTicks < 12
                && player.getY() >= runningGroundHeight - 0.35D
                ? findOneBlockGapLanding(player, runningGroundHeight)
                : null;
        boolean crossingOneBlockGap = gapLanding != null;

        if (crossingOneBlockGap) {
            carryAcrossGap(player, gapLanding);
            player.fallDistance = 0.0F;
            shortDropGrace = true;
            gapCrossingTicks++;
        } else if (!player.onGround() && gapCrossingTicks > 0) {
            gapCrossingTicks = 12;
        }

        if (!crossingOneBlockGap && !player.onGround() && wasRunEligible && player.getDeltaMovement().y <= 0.0D
                && player.fallDistance <= 1.35F) {
            shortDropGrace = true;
        } else if (!player.onGround() && player.fallDistance > 1.35F) {
            shortDropGrace = false;
        }

        if (player.onGround() && shortDropGrace) {
            if (forwardOnly && player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.PEGASUS_BOOTS.get())) {
                player.setSprinting(true);
            }
            shortDropGrace = false;
        }

        if (playerChanged || forwardOnly != lastForwardOnly) {
            NetworkDispatcher.network_channel.sendToServer(new PegasusBootsInputMessage(forwardOnly));
            lastPlayerId = playerId;
            lastForwardOnly = forwardOnly;
        }

        boolean runEligible = isRunEligible(minecraft, player);
        boolean showWarmUpMovement = runEligible
                && (playerChanged || !wasRunEligible || warmUpTicksRemaining > 0);
        if (!runEligible) {
            warmUpTicksRemaining = 0;
        } else if (playerChanged || !wasRunEligible) {
            warmUpTicksRemaining = PegasusBootsArmor.WARM_UP_DURATION_TICKS;
        } else if (warmUpTicksRemaining > 0) {
            warmUpTicksRemaining--;
        }
        wasRunEligible = runEligible;

        if (showWarmUpMovement) {
            player.walkDist += 0.2F;
            player.bob = Math.max(player.bob, 0.1F);
        }
    }

    public static boolean shouldLockHorizontalLook() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        return player != null && isRunEligible(minecraft, player);
    }

    public static boolean isWarmingUp() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        return warmUpTicksRemaining > 0 && player != null && isRunEligible(minecraft, player);
    }

    public static boolean isCharging() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        return player != null && isRunEligible(minecraft, player);
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.getEntity() == minecraft.player && isCharging()
                && !RocsFeather.isHeldBy(minecraft.player)) {
            Vec3 movement = minecraft.player.getDeltaMovement();
            minecraft.player.setDeltaMovement(movement.x, 0.0D, movement.z);
        }
    }

    private static boolean isRunEligible(Minecraft minecraft, LocalPlayer player) {
        return hasChargeInput(minecraft, player)
                && player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.PEGASUS_BOOTS.get())
                && (player.onGround() || shortDropGrace || wasRunEligible)
                && (player.isSprinting() || shortDropGrace)
                && !player.isInWater()
                && player.getFoodData().getFoodLevel() > 0;
    }

    private static boolean hasChargeInput(Minecraft minecraft, LocalPlayer player) {
        return minecraft.screen == null
                && player.input != null
                && player.input.up
                && !player.input.down;
    }

    private static Vec3 findOneBlockGapLanding(LocalPlayer player, double groundHeight) {
        int supportY = (int) Math.floor(groundHeight - 0.1D);
        double yaw = Math.toRadians(player.getYRot());
        Vec3 forward = new Vec3(-Math.sin(yaw), 0.0D, Math.cos(yaw));
        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);
        double[] laneOffsets = {0.0D, -0.28D, 0.28D, -0.5D, 0.5D};

        for (double laneOffset : laneOffsets) {
            double gapDistance = -1.0D;
            for (double distance = 0.15D; distance <= 1.05D; distance += 0.15D) {
                if (!hasGroundSupport(player, player.getX() + forward.x * distance + right.x * laneOffset,
                        supportY, player.getZ() + forward.z * distance + right.z * laneOffset)) {
                    gapDistance = distance;
                    break;
                }
            }
            if (gapDistance < 0.0D) {
                continue;
            }

            for (double distance = gapDistance + 0.55D; distance <= gapDistance + 1.8D; distance += 0.15D) {
                double landingX = player.getX() + forward.x * distance + right.x * laneOffset;
                double landingZ = player.getZ() + forward.z * distance + right.z * laneOffset;
                if (hasGroundSupport(player, landingX, supportY, landingZ)) {
                    return new Vec3(landingX + forward.x * 0.2D, groundHeight,
                            landingZ + forward.z * 0.2D);
                }
            }
        }
        return null;
    }

    private static boolean hasGroundSupport(LocalPlayer player, double x, int supportY, double z) {
        BlockPos pos = BlockPos.containing(x, supportY, z);
        BlockState state = player.level().getBlockState(pos);
        return !state.getCollisionShape(player.level(), pos).isEmpty()
                && state.isFaceSturdy(player.level(), pos, Direction.UP);
    }

    private static void carryAcrossGap(LocalPlayer player, Vec3 landing) {
        Vec3 movement = player.getDeltaMovement();
        Vec3 towardLanding = new Vec3(landing.x - player.getX(), 0.0D, landing.z - player.getZ());
        if (towardLanding.lengthSqr() < 1.0E-6D) {
            double yaw = Math.toRadians(player.getYRot());
            towardLanding = new Vec3(-Math.sin(yaw), 0.0D, Math.cos(yaw));
        } else {
            towardLanding = towardLanding.normalize();
        }

        Vec3 currentDirection = new Vec3(movement.x, 0.0D, movement.z);
        double speed = Math.max(0.4D, currentDirection.length());
        if (currentDirection.lengthSqr() > 1.0E-6D) {
            currentDirection = currentDirection.normalize();
            towardLanding = currentDirection.scale(0.6D).add(towardLanding.scale(0.4D)).normalize();
        }
        player.setDeltaMovement(towardLanding.x * speed, movement.y, towardLanding.z * speed);
    }
}
