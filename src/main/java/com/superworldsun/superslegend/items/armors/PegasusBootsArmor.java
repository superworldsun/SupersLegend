package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.TagInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.PlayerAnimationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class PegasusBootsArmor extends NonEnchantArmor {
    public static final int WARM_UP_DURATION_TICKS = 20;
    private static final UUID SPEED_MODIFIER_ID = UUID.fromString("eb65b146-8dc1-4b48-a927-5a1042935012");
    private static final UUID STEP_HEIGHT_MODIFIER_ID = UUID.fromString("9595d7cd-a755-42ac-87c6-85df9132958d");
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(
            SPEED_MODIFIER_ID,
            "Pegasus Boots speed boost",
            0.4D,
            AttributeModifier.Operation.MULTIPLY_TOTAL
    );
    private static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(
            STEP_HEIGHT_MODIFIER_ID,
            "Pegasus Boots one-block step",
            0.4D,
            AttributeModifier.Operation.ADDITION
    );
    private static final float SHORT_DROP_MAX_FALL_DISTANCE = 1.35F;
    private static final int WALL_IMPACT_COOLDOWN_TICKS = 10;
    private static final int GAP_CROSSING_MAX_TICKS = 12;
    private static final Set<UUID> FORWARD_ONLY_PLAYERS = new HashSet<>();
    private static final Set<UUID> SHORT_DROP_PLAYERS = new HashSet<>();
    private static final Map<UUID, Integer> WARM_UP_TICKS_BY_PLAYER = new HashMap<>();
    private static final Map<UUID, Integer> WALL_IMPACT_COOLDOWNS = new HashMap<>();
    private static final Map<UUID, Integer> GAP_CROSSING_TICKS = new HashMap<>();
    private static final Map<UUID, Double> LAST_RUNNING_GROUND_HEIGHT = new HashMap<>();

    public PegasusBootsArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public static void setForwardOnlyInput(Player player, boolean forwardOnly) {
        if (forwardOnly) {
            FORWARD_ONLY_PLAYERS.add(player.getUUID());
        } else {
            FORWARD_ONLY_PLAYERS.remove(player.getUUID());
            WARM_UP_TICKS_BY_PLAYER.remove(player.getUUID());
            GAP_CROSSING_TICKS.remove(player.getUUID());
            LAST_RUNNING_GROUND_HEIGHT.remove(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        Player player = event.player;
        UUID playerId = player.getUUID();
        boolean isWearingBoots = player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.PEGASUS_BOOTS.get());
        boolean hasForwardInput = FORWARD_ONLY_PLAYERS.contains(playerId);
        boolean wasRunning = WARM_UP_TICKS_BY_PLAYER.containsKey(playerId);
        if (player.onGround() && wasRunning) {
            LAST_RUNNING_GROUND_HEIGHT.put(playerId, player.getY());
            GAP_CROSSING_TICKS.remove(playerId);
        }

        double runningGroundHeight = LAST_RUNNING_GROUND_HEIGHT.getOrDefault(playerId, player.getY());
        int gapCrossingTicks = GAP_CROSSING_TICKS.getOrDefault(playerId, 0);
        Vec3 gapLanding = !player.onGround() && wasRunning
                && gapCrossingTicks < GAP_CROSSING_MAX_TICKS
                && player.getY() >= runningGroundHeight - 0.35D
                ? findOneBlockGapLanding((ServerLevel) player.level(), player, runningGroundHeight)
                : null;
        boolean crossingOneBlockGap = gapLanding != null;

        if (crossingOneBlockGap) {
            carryAcrossGap(player, gapLanding);
            player.fallDistance = 0.0F;
            SHORT_DROP_PLAYERS.add(playerId);
            GAP_CROSSING_TICKS.put(playerId, gapCrossingTicks + 1);
        } else if (!player.onGround() && gapCrossingTicks > 0) {
            GAP_CROSSING_TICKS.put(playerId, GAP_CROSSING_MAX_TICKS);
        }

        if (!crossingOneBlockGap && !player.onGround() && wasRunning && player.getDeltaMovement().y <= 0.0D
                && player.fallDistance <= SHORT_DROP_MAX_FALL_DISTANCE) {
            SHORT_DROP_PLAYERS.add(playerId);
        } else if (!player.onGround() && player.fallDistance > SHORT_DROP_MAX_FALL_DISTANCE) {
            SHORT_DROP_PLAYERS.remove(playerId);
        }

        boolean completedShortDrop = player.onGround() && SHORT_DROP_PLAYERS.remove(playerId);
        if (completedShortDrop && isWearingBoots && hasForwardInput) {
            player.setSprinting(true);
        }

        boolean inShortDrop = SHORT_DROP_PLAYERS.contains(playerId);
        boolean canRun = isWearingBoots
                && hasForwardInput
                && (player.onGround() || inShortDrop)
                && (player.isSprinting() || inShortDrop)
                && !player.isInWater()
                && player.getFoodData().getFoodLevel() > 0;
        int warmUpTicks = 0;

        if (canRun) {
            if (player.tickCount % 2 == 0) {
                spawnRunningParticles((ServerLevel) player.level(), player);
            }
            warmUpTicks = Math.min(
                    WARM_UP_DURATION_TICKS,
                    WARM_UP_TICKS_BY_PLAYER.getOrDefault(playerId, 0) + 1
            );
            WARM_UP_TICKS_BY_PLAYER.put(playerId, warmUpTicks);
        } else {
            WARM_UP_TICKS_BY_PLAYER.remove(playerId);
            SHORT_DROP_PLAYERS.remove(playerId);
            GAP_CROSSING_TICKS.remove(playerId);
            LAST_RUNNING_GROUND_HEIGHT.remove(playerId);
        }

        boolean shouldBoost = canRun && warmUpTicks >= WARM_UP_DURATION_TICKS;

        setStepHeight(player, canRun);

        InteractionHand chargeWeaponHand = getChargeWeaponHand(player);
        boolean holdingChargeWeapon = chargeWeaponHand != null;
        boolean brokeChargeBlock = false;
        if (shouldBoost && holdingChargeWeapon) {
            brokeChargeBlock = breakChargeBlocks((ServerLevel) player.level(), player);
            attackEntitiesInFront(player, chargeWeaponHand);
        }

        int wallCooldown = Math.max(0, WALL_IMPACT_COOLDOWNS.getOrDefault(playerId, 0) - 1);
        if (wallCooldown > 0) {
            WALL_IMPACT_COOLDOWNS.put(playerId, wallCooldown);
        } else {
            WALL_IMPACT_COOLDOWNS.remove(playerId);
        }

        if (shouldBoost && !brokeChargeBlock && wallCooldown == 0
                && isTwoBlockTallWallAhead((ServerLevel) player.level(), player)) {
            recoilFromWall((ServerLevel) player.level(), player);
            WALL_IMPACT_COOLDOWNS.put(playerId, WALL_IMPACT_COOLDOWN_TICKS);
            WARM_UP_TICKS_BY_PLAYER.remove(playerId);
            SHORT_DROP_PLAYERS.remove(playerId);
            shouldBoost = false;
        }

        PlayerAnimationUtil.setPegasusWeaponForward(player, shouldBoost && holdingChargeWeapon);

        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier currentModifier = movementSpeed.getModifier(SPEED_MODIFIER_ID);

        if (shouldBoost) {
            if (currentModifier == null) {
                movementSpeed.addTransientModifier(SPEED_MODIFIER);
            }
        } else if (currentModifier != null) {
            movementSpeed.removeModifier(currentModifier);
        }
    }

    private static void setStepHeight(Player player, boolean enabled) {
        AttributeInstance stepHeight = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (stepHeight == null) {
            return;
        }
        AttributeModifier currentModifier = stepHeight.getModifier(STEP_HEIGHT_MODIFIER_ID);
        if (enabled && currentModifier == null) {
            stepHeight.addTransientModifier(STEP_HEIGHT_MODIFIER);
        } else if (!enabled && currentModifier != null) {
            stepHeight.removeModifier(currentModifier);
        }
    }

    private static InteractionHand getChargeWeaponHand(Player player) {
        if (isChargeWeapon(player.getMainHandItem())) {
            return InteractionHand.MAIN_HAND;
        }
        if (isChargeWeapon(player.getOffhandItem())) {
            return InteractionHand.OFF_HAND;
        }
        return null;
    }

    private static boolean isChargeWeapon(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof TridentItem;
    }

    private static void attackEntitiesInFront(Player player, InteractionHand weaponHand) {
        ItemStack weapon = player.getItemInHand(weaponHand);
        float chargeDamage = getWeaponAttackDamage(player, weapon);
        Vec3 forward = horizontalLook(player);
        AABB hitArea = player.getBoundingBox().expandTowards(forward.scale(1.55D)).inflate(0.35D, 0.2D, 0.35D);
        for (LivingEntity target : player.level().getEntitiesOfClass(LivingEntity.class, hitArea,
                entity -> entity != player && entity.isAlive() && player.canAttack(entity))) {
            if (weapon.isEmpty()) {
                break;
            }
            if (target.hurt(player.damageSources().playerAttack(player), chargeDamage)) {
                // Durability is consumed only by a successful entity hit.
                // Charge-breaking foliage and pots never reaches this code.
                weapon.hurtAndBreak(1, player, breakingPlayer -> breakingPlayer.broadcastBreakEvent(weaponHand));
                Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);
                double side = target.position().subtract(player.position()).dot(right);
                if (Math.abs(side) < 0.05D) {
                    side = (target.getUUID().getLeastSignificantBits() & 1L) == 0L ? 1.0D : -1.0D;
                }
                Vec3 knockbackDirection = right.scale(Math.signum(side)).add(forward.scale(0.2D)).normalize();
                target.knockback(0.95D, -knockbackDirection.x, -knockbackDirection.z);
            }
        }
    }

    private static float getWeaponAttackDamage(Player player, ItemStack weapon) {
        double baseDamage = player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
        double additiveDamage = 0.0D;
        double multiplyBase = 0.0D;
        double multiplyTotal = 1.0D;

        for (AttributeModifier modifier : weapon.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_DAMAGE)) {
            switch (modifier.getOperation()) {
                case ADDITION -> additiveDamage += modifier.getAmount();
                case MULTIPLY_BASE -> multiplyBase += modifier.getAmount();
                case MULTIPLY_TOTAL -> multiplyTotal *= 1.0D + modifier.getAmount();
            }
        }

        double damage = (baseDamage + additiveDamage + baseDamage * multiplyBase) * multiplyTotal;
        return (float) Math.max(0.0D, damage);
    }

    private static boolean isTwoBlockTallWallAhead(ServerLevel level, Player player) {
        Vec3 forward = horizontalLook(player);
        AABB playerBox = player.getBoundingBox();
        AABB lowerProbe = new AABB(
                playerBox.minX, playerBox.minY + 0.08D, playerBox.minZ,
                playerBox.maxX, playerBox.minY + 0.92D, playerBox.maxZ)
                .move(forward.scale(0.24D)).deflate(0.04D, 0.0D, 0.04D);
        AABB upperProbe = new AABB(
                playerBox.minX, playerBox.minY + 1.02D, playerBox.minZ,
                playerBox.maxX, Math.min(playerBox.maxY - 0.04D, playerBox.minY + 1.76D), playerBox.maxZ)
                .move(forward.scale(0.24D)).deflate(0.04D, 0.0D, 0.04D);
        return !level.noCollision(player, lowerProbe) && !level.noCollision(player, upperProbe);
    }

    private static Vec3 findOneBlockGapLanding(ServerLevel level, Player player, double groundHeight) {
        int supportY = (int) Math.floor(groundHeight - 0.1D);
        Vec3 forward = horizontalLook(player);
        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);
        double[] laneOffsets = {0.0D, -0.28D, 0.28D, -0.5D, 0.5D};

        for (double laneOffset : laneOffsets) {
            double gapDistance = -1.0D;
            for (double distance = 0.15D; distance <= 1.05D; distance += 0.15D) {
                if (!hasGroundSupport(level, player.getX() + forward.x * distance + right.x * laneOffset,
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
                if (hasGroundSupport(level, landingX, supportY, landingZ)) {
                    return new Vec3(landingX + forward.x * 0.2D, groundHeight,
                            landingZ + forward.z * 0.2D);
                }
            }
        }
        return null;
    }

    private static boolean hasGroundSupport(ServerLevel level, double x, int supportY, double z) {
        BlockPos pos = BlockPos.containing(x, supportY, z);
        BlockState state = level.getBlockState(pos);
        return !state.getCollisionShape(level, pos).isEmpty()
                && state.isFaceSturdy(level, pos, Direction.UP);
    }

    private static void carryAcrossGap(Player player, Vec3 landing) {
        Vec3 movement = player.getDeltaMovement();
        Vec3 towardLanding = new Vec3(landing.x - player.getX(), 0.0D, landing.z - player.getZ());
        if (towardLanding.lengthSqr() < 1.0E-6D) {
            towardLanding = horizontalLook(player);
        } else {
            towardLanding = towardLanding.normalize();
        }

        Vec3 currentDirection = new Vec3(movement.x, 0.0D, movement.z);
        // Cross the trench through running momentum instead of suspending the
        // player over it. The short burst gives the one-block step modifier a
        // chance to catch the far edge, including at diagonal approaches.
        double speed = Math.max(0.4D, currentDirection.length());
        if (currentDirection.lengthSqr() > 1.0E-6D) {
            currentDirection = currentDirection.normalize();
            towardLanding = currentDirection.scale(0.6D).add(towardLanding.scale(0.4D)).normalize();
        }
        player.setDeltaMovement(towardLanding.x * speed, movement.y, towardLanding.z * speed);
        player.hurtMarked = true;
    }

    private static boolean breakChargeBlocks(ServerLevel level, Player player) {
        Vec3 forward = horizontalLook(player);
        AABB breakArea = player.getBoundingBox().expandTowards(forward.scale(0.75D)).inflate(0.12D, 0.05D, 0.12D);
        boolean brokeAny = false;

        for (BlockPos pos : BlockPos.betweenClosed(
                BlockPos.containing(breakArea.minX, breakArea.minY, breakArea.minZ),
                BlockPos.containing(breakArea.maxX, breakArea.maxY, breakArea.maxZ))) {
            BlockState state = level.getBlockState(pos);
            boolean canBreakJar = Config.pegasusBootsBreakJarsAndPots()
                    && state.is(TagInit.PEGASUS_BOOTS_BREAKABLE_JARS);
            boolean canBreakPlant = Config.pegasusBootsBreakPlantsAndCrops()
                    && state.is(TagInit.PEGASUS_BOOTS_BREAKABLE_PLANTS);
            if (canBreakJar || canBreakPlant) {
                brokeAny |= level.destroyBlock(pos, true, player);
            }
        }
        return brokeAny;
    }

    private static void recoilFromWall(ServerLevel level, Player player) {
        Vec3 forward = horizontalLook(player);
        player.setDeltaMovement(-forward.x * 0.55D, 0.34D, -forward.z * 0.55D);
        player.hurtMarked = true;
        player.setSprinting(false);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_BIG_FALL,
                SoundSource.PLAYERS, 1.0F, 0.8F);
    }

    private static Vec3 horizontalLook(Player player) {
        double yaw = Math.toRadians(player.getYRot());
        return new Vec3(-Math.sin(yaw), 0.0D, Math.cos(yaw));
    }

    private static void spawnRunningParticles(ServerLevel level, Player player) {
        double yawRadians = Math.toRadians(player.getYRot());
        double particleX = player.getX() + Math.sin(yawRadians) * 0.45D;
        double particleZ = player.getZ() - Math.cos(yawRadians) * 0.45D;

        for (int particle = 0; particle < 2; particle++) {
            double spreadX = (player.getRandom().nextDouble() - 0.5D) * 0.24D;
            double spreadY = player.getRandom().nextDouble() * 0.04D;
            double spreadZ = (player.getRandom().nextDouble() - 0.5D) * 0.24D;

            level.sendParticles(
                    ParticleTypes.CLOUD,
                    particleX + spreadX,
                    player.getY() + 0.1D + spreadY,
                    particleZ + spreadZ,
                    0,
                    Math.sin(yawRadians) * 0.25D,
                    1.0D,
                    -Math.cos(yawRadians) * 0.25D,
                    0.05D
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        FORWARD_ONLY_PLAYERS.remove(event.getEntity().getUUID());
        WARM_UP_TICKS_BY_PLAYER.remove(event.getEntity().getUUID());
        SHORT_DROP_PLAYERS.remove(event.getEntity().getUUID());
        WALL_IMPACT_COOLDOWNS.remove(event.getEntity().getUUID());
        GAP_CROSSING_TICKS.remove(event.getEntity().getUUID());
        LAST_RUNNING_GROUND_HEIGHT.remove(event.getEntity().getUUID());
    }
}
