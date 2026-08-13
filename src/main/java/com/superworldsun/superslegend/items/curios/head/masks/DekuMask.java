package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.client.model.player.PlayerTransformationModels;
import com.superworldsun.superslegend.entities.projectiles.magic.DekuMagicBubbleEntity;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DekuWaterHopMessage;
import com.superworldsun.superslegend.network.message.DekuWaterHopInputMessage;
import com.superworldsun.superslegend.registries.FluidInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Pose;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)

public class DekuMask extends Item implements IMaskAbility, ICurioItem, IPlayerModelChanger, IEntityResizer {
    private static final float STANDING_HITBOX_HEIGHT_SCALE = 0.68F;
    private static final float STANDING_EYE_HEIGHT_SCALE = 0.73F;
    public static final int MAX_WATER_HOPS = 5;
    public static final float MAGIC_BUBBLE_COST = 0.5F;
    private static final int MAGIC_BUBBLE_FIRE_COOLDOWN_TICKS = 30;
    private static final int NO_MAGIC_SOUND_DURATION_TICKS = 10;
    private static final double NORMAL_HOP_HEIGHT = 0.42D;
    private static final double FOURTH_HOP_HEIGHT = 0.52D;
    private static final double FIFTH_HOP_HEIGHT = 0.64D;
    private static final double HORIZONTAL_HOP_SPEED_BONUS = 0.15D;
    private static final double FOLLOW_UP_HOP_SPEED_BONUS = 0.01D;
    private static final double GLIDE_MOMENTUM_RETENTION = 0.994D;
    private static final double MAXIMUM_HOP_SPEED = 0.56D;
    private static final double MINIMUM_HORIZONTAL_SPEED = 1.0E-4D;
    private static final double HARD_TURN_ALIGNMENT = 0.5D;
    private static final double MINIMUM_HARD_TURN_RETENTION = 0.25D;
    private static final double MAXIMUM_HARD_TURN_RETENTION = 0.6D;
    private static final double WATER_HOP_STEERING_STRENGTH = 0.1D;
    private static final float WATER_HOP_SOUND_VOLUME = 0.8F;
    private static final float FIRST_HOP_SOUND_PITCH = 0.8F;
    private static final float HOP_SOUND_PITCH_INCREASE = 0.15F;
    private static final int FINAL_HOP_FALL_GRACE_TICKS = 60;
    private static final ResourceLocation PLAYER_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/deku_player.png");
    private static final Map<Player, WaterHopState> CLIENT_WATER_HOP_STATES = new WeakHashMap<>();
    private static final Map<Player, WaterHopState> SERVER_WATER_HOP_STATES = new WeakHashMap<>();
    private static final Map<Player, Integer> CLIENT_MAGIC_BUBBLE_CHARGE_STARTS = new WeakHashMap<>();
    private static final Map<Player, Integer> CLIENT_MAGIC_BUBBLE_COOLDOWN_END_TICKS = new WeakHashMap<>();
    private static final Map<UUID, DekuMagicBubbleEntity> ACTIVE_MAGIC_BUBBLES = new HashMap<>();
    private static final Map<UUID, Integer> MAGIC_BUBBLE_COOLDOWN_END_TICKS = new HashMap<>();
    private static final Map<UUID, Integer> NO_MAGIC_SOUND_END_TICKS = new HashMap<>();

    public DekuMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float getScale(Player player) {
        return 0.65F;
    }

    @Override
    public float getEyeHeightScale(Player player, Pose pose) {
        return pose == Pose.STANDING ? STANDING_EYE_HEIGHT_SCALE : getScale(player);
    }

    @Override
    public float getHitboxHeightScale(Player player, Pose pose) {
        return pose == Pose.STANDING ? STANDING_HITBOX_HEIGHT_SCALE : getScale(player);
    }

    @Override
    public boolean hasInstantEyeHeightChanges() {
        return true;
    }

    @Override
    public boolean hasInstantHitboxHeightChanges() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public PlayerModel<AbstractClientPlayer> getPlayerModel(AbstractClientPlayer player) {
        return PlayerTransformationModels.getDeku();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation getPlayerTexture(AbstractClientPlayer player) {
        return PLAYER_TEXTURE;
    }

    @Override
    public void startUsingAbility(Player player) {
        IMaskAbility.super.startUsingAbility(player);
        if (player.level().isClientSide) {
            if (isMagicBubbleOnCooldown(player)) {
                return;
            }
            if (MagicProvider.hasMagic(player, MAGIC_BUBBLE_COST)) {
                CLIENT_MAGIC_BUBBLE_CHARGE_STARTS.put(player, player.tickCount);
            }
            return;
        }

        if (isMagicBubbleOnCooldown(player)) {
            return;
        }

        DekuMagicBubbleEntity activeBubble = ACTIVE_MAGIC_BUBBLES.get(player.getUUID());
        if (activeBubble != null && activeBubble.isAlive()) {
            return;
        }
        ACTIVE_MAGIC_BUBBLES.remove(player.getUUID());

        if (!MagicProvider.hasMagic(player, MAGIC_BUBBLE_COST)) {
            playNoMagicSound(player);
            return;
        }

        DekuMagicBubbleEntity bubble = new DekuMagicBubbleEntity(player.level(), player);
        if (player.level().addFreshEntity(bubble)) {
            MagicProvider.spendMagic(player, MAGIC_BUBBLE_COST);
            ACTIVE_MAGIC_BUBBLES.put(player.getUUID(), bubble);
        }
    }

    private static void playNoMagicSound(Player player) {
        int currentTick = player.tickCount;
        int soundEndTick = NO_MAGIC_SOUND_END_TICKS.getOrDefault(player.getUUID(), Integer.MIN_VALUE);
        if (currentTick < soundEndTick) {
            return;
        }

        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundInit.DEKU_LINK_BUBBLE_SPIT.get(),
                SoundSource.PLAYERS,
                0.9F,
                1.0F
        );
        NO_MAGIC_SOUND_END_TICKS.put(player.getUUID(), currentTick + NO_MAGIC_SOUND_DURATION_TICKS);
    }

    @Override
    public void stopUsingAbility(Player player) {
        if (player.level().isClientSide) {
            Integer chargeStartTick = CLIENT_MAGIC_BUBBLE_CHARGE_STARTS.remove(player);
            if (chargeStartTick != null
                    && player.tickCount - chargeStartTick < DekuMagicBubbleEntity.MAX_CHARGE_TICKS) {
                CLIENT_MAGIC_BUBBLE_COOLDOWN_END_TICKS.put(
                        player,
                        player.tickCount + MAGIC_BUBBLE_FIRE_COOLDOWN_TICKS
                );
            }
        } else {
            DekuMagicBubbleEntity bubble = ACTIVE_MAGIC_BUBBLES.remove(player.getUUID());
            if (bubble != null && bubble.isAlive() && bubble.isCharging()) {
                bubble.fire(player.getLookAngle());
                MAGIC_BUBBLE_COOLDOWN_END_TICKS.put(
                        player.getUUID(),
                        player.tickCount + MAGIC_BUBBLE_FIRE_COOLDOWN_TICKS
                );
            }
        }
        IMaskAbility.super.stopUsingAbility(player);
    }

    private static boolean isMagicBubbleOnCooldown(Player player) {
        if (player.level().isClientSide) {
            return player.tickCount < CLIENT_MAGIC_BUBBLE_COOLDOWN_END_TICKS.getOrDefault(
                    player,
                    Integer.MIN_VALUE
            );
        }
        return player.tickCount < MAGIC_BUBBLE_COOLDOWN_END_TICKS.getOrDefault(
                player.getUUID(),
                Integer.MIN_VALUE
        );
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!player.level().isClientSide
                && event.getSource().is(DamageTypes.FALL)
                && consumeFinalHopFallProtection(player)) {
            event.setCanceled(true);
            player.fallDistance = 0.0F;
            return;
        }
        ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (!stack0.isEmpty()) {
            if (event.getSource().is(DamageTypes.LAVA)) {
                event.setAmount(event.getAmount() * 4);
            } else if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
                event.setAmount(event.getAmount() * 2);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide) {
            return;
        }

        if (consumeFinalHopFallProtection(player)) {
            event.setDistance(0.0F);
            player.fallDistance = 0.0F;
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        Player player = event.player;
        if (player.level().isClientSide && !player.isLocalPlayer()) {
            return;
        }

        Map<Player, WaterHopState> hopStates = player.level().isClientSide
                ? CLIENT_WATER_HOP_STATES
                : SERVER_WATER_HOP_STATES;
        if (!isDekuMaskEquipped(player)) {
            if (player.level().isClientSide) {
                CLIENT_MAGIC_BUBBLE_CHARGE_STARTS.remove(player);
            } else {
                discardActiveMagicBubble(player);
            }
            hopStates.remove(player);
            return;
        }

        boolean isInWater = isInDekuHopFluid(player);
        WaterHopState hopState = hopStates.computeIfAbsent(player, ignored -> new WaterHopState(isInWater));
        if (!player.level().isClientSide && hopState.finalHopFallGraceTicks > 0) {
            hopState.finalHopFallGraceTicks--;
        }
        if (!player.level().isClientSide && hopState.steeringInputTicks > 0) {
            hopState.steeringInputTicks--;
        }
        if (isChargingMagicBubble(player)) {
            stopPlayerMovementWhileCharging(player);
            hopState.wasInWater = isInWater;
            return;
        }

        // Creative flight controls movement independently. Starting or preserving
        // a water-hop glide here would overwrite those controls until landing.
        if (player.getAbilities().flying) {
            hopState.resetSequence();
            hopState.wasInWater = isInWater;
            hopState.lastAirMomentum = Vec3.ZERO;
            hopState.finalHopFallGraceTicks = 0;
            return;
        }

        if (!isInWater && player.onGround()) {
            if (hopState.completedHops >= MAX_WATER_HOPS && player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                ModAdvancementHelper.award(serverPlayer, "five_hop_finish", "landed_after_five_hops");
            }
            hopState.resetSequence();
        } else if (isInWater && !hopState.wasInWater) {
            if (hopState.completedHops < MAX_WATER_HOPS) {
                hopState.completedHops++;
                performWaterHop(player, hopState, hopState.completedHops);
            } else {
                hopState.sequenceFinished = true;
                hopState.glideMomentum = Vec3.ZERO;
            }
        } else if (!isInWater && hopState.completedHops > 0 && !hopState.sequenceFinished) {
            preserveGlideMomentum(player, hopState);
        }

        if (!isInWater) {
            hopState.lastAirMomentum = getHorizontalMovement(player);
        }

        hopState.wasInWater = isInWater;
        if (player.level().isClientSide
                && hopState.completedHops > 0
                && !hopState.sequenceFinished) {
            NetworkDispatcher.network_channel.sendToServer(new DekuWaterHopInputMessage(
                    player.xxa,
                    player.zza,
                    player.getYRot()
            ));
        }
    }

    public static int getDisplayedWaterHopsRemaining(Player player) {
        WaterHopState hopState = CLIENT_WATER_HOP_STATES.get(player);
        return hopState != null && hopState.completedHops > 0 && !hopState.sequenceFinished
                ? MAX_WATER_HOPS - hopState.completedHops + 1
                : -1;
    }

    public static boolean isChargingMagicBubble(Player player) {
        if (player.level().isClientSide) {
            Integer chargeStartTick = CLIENT_MAGIC_BUBBLE_CHARGE_STARTS.get(player);
            return chargeStartTick != null
                    && player.tickCount - chargeStartTick < DekuMagicBubbleEntity.MAX_CHARGE_TICKS;
        }

        DekuMagicBubbleEntity bubble = ACTIVE_MAGIC_BUBBLES.get(player.getUUID());
        return bubble != null && bubble.isAlive() && bubble.isCharging();
    }

    private static void stopPlayerMovementWhileCharging(Player player) {
        Vec3 movement = player.getDeltaMovement();
        player.setDeltaMovement(0.0D, Math.min(movement.y, 0.0D), 0.0D);
        player.setSprinting(false);
        player.hasImpulse = true;
        if (!player.level().isClientSide) {
            player.hurtMarked = true;
        }
    }

    public static boolean isDekuMaskEquipped(Player player) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), player).isPresent();
    }

    private static void performWaterHop(Player player, WaterHopState hopState, int hopNumber) {
        Vec3 movement = player.getDeltaMovement();
        Vec3 currentHorizontalMovement = getHorizontalMovement(player);
        double currentHorizontalSpeed = currentHorizontalMovement.length();
        double savedHorizontalSpeed = hopState.lastAirMomentum.length();
        Vec3 movementDirection = currentHorizontalSpeed > MINIMUM_HORIZONTAL_SPEED
                ? currentHorizontalMovement.scale(1.0D / currentHorizontalSpeed)
                : savedHorizontalSpeed > MINIMUM_HORIZONTAL_SPEED
                ? hopState.lastAirMomentum.scale(1.0D / savedHorizontalSpeed)
                : Vec3.ZERO;
        double preservedHorizontalSpeed = Math.max(currentHorizontalSpeed, savedHorizontalSpeed);
        preservedHorizontalSpeed *= getTurnMomentumRetention(hopState.lastAirMomentum, movementDirection);
        double hopSpeedBonus = hopNumber == 1
                ? HORIZONTAL_HOP_SPEED_BONUS
                : FOLLOW_UP_HOP_SPEED_BONUS;
        double boostedHorizontalSpeed = Math.min(
                preservedHorizontalSpeed > MINIMUM_HORIZONTAL_SPEED
                        ? preservedHorizontalSpeed + hopSpeedBonus
                        : 0.0D,
                MAXIMUM_HOP_SPEED
        );
        Vec3 boostedHorizontalMovement = movementDirection.scale(boostedHorizontalSpeed);
        double verticalSpeed = hopNumber == MAX_WATER_HOPS
                ? FIFTH_HOP_HEIGHT
                : hopNumber == MAX_WATER_HOPS - 1 ? FOURTH_HOP_HEIGHT : NORMAL_HOP_HEIGHT;

        if (!player.level().isClientSide && hopNumber == MAX_WATER_HOPS) {
            hopState.lastWaterBlockY = findCurrentWaterBlockY(player);
            hopState.finalHopFallGraceTicks = FINAL_HOP_FALL_GRACE_TICKS;
        }

        player.setDeltaMovement(
                boostedHorizontalMovement.x,
                verticalSpeed,
                boostedHorizontalMovement.z
        );
        hopState.glideMomentum = boostedHorizontalMovement;
        player.fallDistance = 0.0F;
        player.hasImpulse = true;
        float soundPitch = FIRST_HOP_SOUND_PITCH
                + (hopNumber - 1) * HOP_SOUND_PITCH_INCREASE;
        if (player.level().isClientSide) {
            if (hopNumber == MAX_WATER_HOPS) {
                NetworkDispatcher.network_channel.sendToServer(
                        new DekuWaterHopMessage(findCurrentWaterBlockY(player))
                );
            }
            player.level().playLocalSound(
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundInit.DEKU_LINK_WATER_HOP.get(),
                    SoundSource.PLAYERS,
                    WATER_HOP_SOUND_VOLUME,
                    soundPitch,
                    false
            );
        } else {
            player.hurtMarked = true;
            player.level().playSound(
                    player,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundInit.DEKU_LINK_WATER_HOP.get(),
                    SoundSource.PLAYERS,
                    WATER_HOP_SOUND_VOLUME,
                    soundPitch
            );
        }
    }

    private static void preserveGlideMomentum(Player player, WaterHopState hopState) {
        if (player.horizontalCollision) {
            hopState.glideMomentum = getHorizontalMovement(player);
            return;
        }

        Vec3 currentHorizontalMovement = getHorizontalMovement(player);
        double currentHorizontalSpeed = currentHorizontalMovement.length();
        double previousGlideSpeed = hopState.glideMomentum.length();
        Vec3 desiredDirection = getDesiredWaterHopDirection(player, hopState);
        Vec3 steeredHorizontalMovement = steerHorizontalMovement(
                currentHorizontalMovement,
                hopState.glideMomentum,
                desiredDirection
        );
        double turnMomentumRetention = getTurnMomentumRetention(
                hopState.glideMomentum,
                steeredHorizontalMovement
        );
        if (turnMomentumRetention < 1.0D) {
            double reducedSpeed = Math.min(
                    currentHorizontalSpeed,
                    previousGlideSpeed * turnMomentumRetention
            );
            Vec3 turnedMovement = steeredHorizontalMovement.normalize().scale(reducedSpeed);
            Vec3 movement = player.getDeltaMovement();
            player.setDeltaMovement(turnedMovement.x, movement.y, turnedMovement.z);
            hopState.glideMomentum = turnedMovement;
            player.hasImpulse = true;
            return;
        }

        double retainedGlideSpeed = previousGlideSpeed * GLIDE_MOMENTUM_RETENTION;
        if (currentHorizontalSpeed <= MINIMUM_HORIZONTAL_SPEED
                || (retainedGlideSpeed <= currentHorizontalSpeed && desiredDirection == Vec3.ZERO)) {
            hopState.glideMomentum = currentHorizontalMovement;
            return;
        }

        double glideSpeed = Math.min(
                Math.max(currentHorizontalSpeed, retainedGlideSpeed),
                MAXIMUM_HOP_SPEED
        );
        Vec3 smoothedMovement = steeredHorizontalMovement.normalize().scale(glideSpeed);
        Vec3 movement = player.getDeltaMovement();
        player.setDeltaMovement(smoothedMovement.x, movement.y, smoothedMovement.z);
        hopState.glideMomentum = smoothedMovement;
        player.hasImpulse = true;
    }

    private static Vec3 getDesiredWaterHopDirection(Player player, WaterHopState hopState) {
        float strafeInput;
        float forwardInput;
        float yaw;
        if (player.level().isClientSide) {
            strafeInput = player.xxa;
            forwardInput = player.zza;
            yaw = player.getYRot();
        } else if (hopState.steeringInputTicks > 0) {
            strafeInput = hopState.steeringStrafe;
            forwardInput = hopState.steeringForward;
            yaw = hopState.steeringYaw;
        } else {
            return Vec3.ZERO;
        }

        if (Math.abs(strafeInput) <= 1.0E-3F && Math.abs(forwardInput) <= 1.0E-3F) {
            return Vec3.ZERO;
        }

        double yawRadians = Math.toRadians(yaw);
        Vec3 forwardDirection = new Vec3(-Math.sin(yawRadians), 0.0D, Math.cos(yawRadians));
        Vec3 leftDirection = new Vec3(forwardDirection.z, 0.0D, -forwardDirection.x);
        return forwardDirection.scale(forwardInput)
                .add(leftDirection.scale(strafeInput))
                .normalize();
    }

    private static Vec3 steerHorizontalMovement(Vec3 currentMovement,
                                                Vec3 previousGlideMovement,
                                                Vec3 desiredDirection) {
        if (desiredDirection == Vec3.ZERO) {
            return currentMovement;
        }

        double speed = currentMovement.length();
        Vec3 startingMovement = speed > MINIMUM_HORIZONTAL_SPEED
                ? currentMovement
                : previousGlideMovement;
        if (startingMovement.lengthSqr() <= MINIMUM_HORIZONTAL_SPEED * MINIMUM_HORIZONTAL_SPEED) {
            return desiredDirection;
        }

        Vec3 steeredDirection = startingMovement.normalize()
                .scale(1.0D - WATER_HOP_STEERING_STRENGTH)
                .add(desiredDirection.scale(WATER_HOP_STEERING_STRENGTH))
                .normalize();
        return steeredDirection.scale(Math.max(speed, startingMovement.length()));
    }

    private static double getTurnMomentumRetention(Vec3 previousMovement, Vec3 currentMovement) {
        double previousSpeed = previousMovement.length();
        double currentSpeed = currentMovement.length();
        if (previousSpeed <= MINIMUM_HORIZONTAL_SPEED || currentSpeed <= MINIMUM_HORIZONTAL_SPEED) {
            return 1.0D;
        }

        double alignment = Math.max(
                -1.0D,
                Math.min(1.0D, previousMovement.dot(currentMovement) / (previousSpeed * currentSpeed))
        );
        if (alignment >= HARD_TURN_ALIGNMENT) {
            return 1.0D;
        }

        double turnSeverity = (alignment + 1.0D) / (HARD_TURN_ALIGNMENT + 1.0D);
        return MINIMUM_HARD_TURN_RETENTION
                + turnSeverity * (MAXIMUM_HARD_TURN_RETENTION - MINIMUM_HARD_TURN_RETENTION);
    }

    private static Vec3 getHorizontalMovement(Player player) {
        Vec3 movement = player.getDeltaMovement();
        return new Vec3(movement.x, 0.0D, movement.z);
    }

    private static boolean consumeFinalHopFallProtection(Player player) {
        WaterHopState hopState = SERVER_WATER_HOP_STATES.get(player);
        if (hopState == null
                || hopState.finalHopFallGraceTicks <= 0
                || !isDekuMaskEquipped(player)) {
            return false;
        }

        int landingBlockY = Mth.floor(player.getBoundingBox().minY - 0.01D);
        if (landingBlockY != hopState.lastWaterBlockY) {
            return false;
        }

        hopState.finalHopFallGraceTicks = 0;
        return true;
    }

    public static void confirmFinalWaterHop(Player player, int waterBlockY) {
        if (player.level().isClientSide || !isDekuMaskEquipped(player)) {
            return;
        }

        int playerY = Mth.floor(player.getBoundingBox().minY);
        if (Math.abs(playerY - waterBlockY) > 4) {
            return;
        }

        WaterHopState hopState = SERVER_WATER_HOP_STATES.computeIfAbsent(
                player,
                ignored -> new WaterHopState(isInDekuHopFluid(player))
        );
        hopState.lastWaterBlockY = waterBlockY;
        hopState.finalHopFallGraceTicks = FINAL_HOP_FALL_GRACE_TICKS;
    }

    public static void setWaterHopSteeringInput(Player player,
                                                float strafeInput,
                                                float forwardInput,
                                                float yaw) {
        if (player.level().isClientSide || !isDekuMaskEquipped(player) || !Float.isFinite(yaw)) {
            return;
        }

        WaterHopState hopState = SERVER_WATER_HOP_STATES.get(player);
        if (hopState == null || hopState.completedHops <= 0 || hopState.sequenceFinished) {
            return;
        }

        hopState.steeringStrafe = Mth.clamp(strafeInput, -1.0F, 1.0F);
        hopState.steeringForward = Mth.clamp(forwardInput, -1.0F, 1.0F);
        hopState.steeringYaw = yaw;
        hopState.steeringInputTicks = 5;
    }

    private static int findCurrentWaterBlockY(Player player) {
        var bounds = player.getBoundingBox();
        int minX = Mth.floor(bounds.minX + 1.0E-3D);
        int maxX = Mth.floor(bounds.maxX - 1.0E-3D);
        int minY = Mth.floor(bounds.minY) - 1;
        int maxY = Mth.floor(bounds.maxY - 1.0E-3D);
        int minZ = Mth.floor(bounds.minZ + 1.0E-3D);
        int maxZ = Mth.floor(bounds.maxZ - 1.0E-3D);
        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();

        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    position.set(x, y, z);
                    if (isDekuHopFluid(player.level().getFluidState(position))) {
                        return y;
                    }
                }
            }
        }

        return Mth.floor(player.getY());
    }

    private static boolean isInDekuHopFluid(Player player) {
        return player.isInWater()
                || player.getFluidTypeHeight(FluidInit.MUD_TYPE.get()) > 0.0D
                || player.getFluidTypeHeight(FluidInit.POISON_TYPE.get()) > 0.0D;
    }

    private static boolean isDekuHopFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.WATER)
                || fluidState.getType().isSame(FluidInit.MUD_SOURCE.get())
                || fluidState.getType().isSame(FluidInit.POISON_SOURCE.get());
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        SERVER_WATER_HOP_STATES.remove(event.getEntity());
        discardActiveMagicBubble(event.getEntity());
        CLIENT_MAGIC_BUBBLE_COOLDOWN_END_TICKS.remove(event.getEntity());
        MAGIC_BUBBLE_COOLDOWN_END_TICKS.remove(event.getEntity().getUUID());
        NO_MAGIC_SOUND_END_TICKS.remove(event.getEntity().getUUID());
        IMaskAbility.PLAYERS_USING_MASKS.remove(event.getEntity());
    }

    private static void discardActiveMagicBubble(Player player) {
        DekuMagicBubbleEntity bubble = ACTIVE_MAGIC_BUBBLES.remove(player.getUUID());
        if (bubble != null && bubble.isAlive() && bubble.isCharging()) {
            bubble.discard();
        }
    }

    private static class WaterHopState {
        private int completedHops;
        private boolean wasInWater;
        private boolean sequenceFinished;
        private Vec3 lastAirMomentum = Vec3.ZERO;
        private Vec3 glideMomentum = Vec3.ZERO;
        private int finalHopFallGraceTicks;
        private int lastWaterBlockY;
        private float steeringStrafe;
        private float steeringForward;
        private float steeringYaw;
        private int steeringInputTicks;

        private WaterHopState(boolean wasInWater) {
            this.wasInWater = wasInWater;
        }

        private void resetSequence() {
            completedHops = 0;
            sequenceFinished = false;
            glideMomentum = Vec3.ZERO;
            steeringInputTicks = 0;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("The face of a Deku").withStyle(ChatFormatting.DARK_GREEN));
        tooltip.add(Component.literal("Your wooden skin is unlikely to be poisoned").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("Your wooden body will burn faster").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal("Hold the mask ability key to create a magic bubble, then release it to fire").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
