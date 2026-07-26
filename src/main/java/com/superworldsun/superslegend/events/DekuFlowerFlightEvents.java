package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DekuFlowerFlightEvents {
    public static final int CHARGE_TICKS = 30;
    public static final double NORMAL_ASCENT_HEIGHT = 6.0D;
    public static final double YELLOW_ASCENT_HEIGHT = 11.0D;
    public static final double NORMAL_GLIDE_DISTANCE = 15.0D;
    public static final double YELLOW_GLIDE_DISTANCE = 25.0D;

    private static final double FLOWER_HEIGHT = 6.0D / 16.0D;
    private static final double ASCENT_SPEED = 0.62D;
    private static final double INCOMPLETE_LAUNCH_SPEED = 0.36D;
    private static final double STARTING_GLIDE_LIFT = 0.10D;
    private static final double MINIMUM_GLIDE_FALL_SPEED = -0.025D;
    private static final double FORWARD_ACCELERATION = 0.038D;
    private static final double STRAFE_ACCELERATION = 0.021D;
    private static final double MAXIMUM_GLIDE_SPEED = 0.42D;
    private static final double GLIDE_DRAG = 0.965D;
    private static final int INPUT_TIMEOUT_TICKS = 8;
    private static final double SAFE_LANDING_DEPTH = 2.0D;
    private static final int FALL_PROTECTION_TICKS = 600;

    private static final Map<Player, FlowerFlightState> CLIENT_STATES = new WeakHashMap<>();
    private static final Map<Player, FlowerFlightState> SERVER_STATES = new WeakHashMap<>();
    private static final Map<UUID, FlowerFallProtection> FALL_PROTECTION = new HashMap<>();

    private DekuFlowerFlightEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;
        if (player.level().isClientSide && !player.isLocalPlayer()) {
            return;
        }

        Map<Player, FlowerFlightState> states = getStates(player);
        FlowerFlightState state = states.get(player);
        if (state != null) {
            if (!player.isAlive() || player.isSpectator() || !isDekuLink(player)
                    || !player.level().dimension().equals(state.dimension)) {
                finish(player, states, state, false);
                return;
            }

            tickState(player, states, state);
            return;
        }

        tickFallProtection(player);
        if (!player.isAlive() || player.isSpectator() || !isDekuLink(player)
                || !player.onGround() || !player.isShiftKeyDown()) {
            return;
        }

        BlockPos flowerPos = findFlowerUnderPlayer(player);
        if (flowerPos != null) {
            beginBurrowing(player, states, flowerPos);
        }
    }

    @SubscribeEvent
    public static void onPlayerAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player && isBuried(player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (isBuried(player)) {
            event.setCanceled(true);
            return;
        }

        if (!player.level().isClientSide && event.getSource().is(DamageTypes.FALL)
                && (hasSafeActiveFlightLanding(player) || consumeFallProtection(player))) {
            event.setCanceled(true);
            player.fallDistance = 0.0F;
        }
    }

    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide) {
            return;
        }

        if (hasSafeActiveFlightLanding(player) || consumeFallProtection(player)) {
            event.setDistance(0.0F);
            player.fallDistance = 0.0F;
        }
    }

    @SubscribeEvent
    public static void onPlayerAttackEntity(AttackEntityEvent event) {
        if (isFlowerAbilityActive(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (isFlowerAbilityActive(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (isFlowerAbilityActive(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (isFlowerAbilityActive(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (isFlowerAbilityActive(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        if (isFlowerAbilityActive(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerBreakBlock(BlockEvent.BreakEvent event) {
        if (isFlowerAbilityActive(event.getPlayer())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        removeState(event.getEntity(), false);
        FALL_PROTECTION.remove(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        removeState(event.getEntity(), false);
        FALL_PROTECTION.remove(event.getEntity().getUUID());
    }

    public static boolean isBuried(Player player) {
        FlowerFlightState state = getStates(player).get(player);
        return state != null && state.phase == FlightPhase.BURIED;
    }

    public static boolean isFlying(Player player) {
        FlowerFlightState state = getStates(player).get(player);
        return state != null && state.phase != FlightPhase.BURIED;
    }

    public static boolean isGliding(Player player) {
        FlowerFlightState state = getStates(player).get(player);
        return state != null && state.phase == FlightPhase.GLIDING;
    }

    public static boolean isFlowerAbilityActive(Player player) {
        return getStates(player).containsKey(player);
    }

    public static double getDisplayedGlideDistanceRemaining(Player player) {
        FlowerFlightState state = getStates(player).get(player);
        if (state == null || state.phase == FlightPhase.BURIED) {
            return -1.0D;
        }

        double x = player.getX() - (state.flowerPos.getX() + 0.5D);
        double z = player.getZ() - (state.flowerPos.getZ() + 0.5D);
        return Math.max(0.0D, state.maximumGlideDistance - Math.sqrt(x * x + z * z));
    }

    public static double getDisplayedMaximumGlideDistance(Player player) {
        FlowerFlightState state = getStates(player).get(player);
        return state != null && state.phase != FlightPhase.BURIED
                ? state.maximumGlideDistance
                : -1.0D;
    }

    public static void setGlideInput(ServerPlayer player, float strafe, float forward, float yaw,
                                     boolean cancelRequested) {
        FlowerFlightState state = SERVER_STATES.get(player);
        if (state == null || state.phase == FlightPhase.BURIED) {
            return;
        }

        state.strafeInput = Mth.clamp(strafe, -1.0F, 1.0F);
        state.forwardInput = Mth.clamp(forward, -1.0F, 1.0F);
        state.inputYaw = yaw;
        state.inputTicks = INPUT_TIMEOUT_TICKS;
        state.cancelRequested = cancelRequested;
    }

    private static void tickState(Player player, Map<Player, FlowerFlightState> states,
                                  FlowerFlightState state) {
        if (state.inputTicks > 0) {
            state.inputTicks--;
        }

        switch (state.phase) {
            case BURIED -> tickBuried(player, states, state);
            case ASCENDING -> tickAscending(player, states, state);
            case GLIDING -> tickGliding(player, states, state);
        }
    }

    private static void tickBuried(Player player, Map<Player, FlowerFlightState> states,
                                   FlowerFlightState state) {
        BlockState flowerState = player.level().getBlockState(state.flowerPos);
        if (!isDekuFlower(flowerState.getBlock())) {
            forceOutOfBrokenFlower(player, states, state);
            return;
        }

        double standingY = state.flowerPos.getY() + FLOWER_HEIGHT;
        player.setPos(state.flowerPos.getX() + 0.5D, standingY, state.flowerPos.getZ() + 0.5D);
        player.setDeltaMovement(Vec3.ZERO);
        player.setSprinting(false);
        player.stopUsingItem();
        player.fallDistance = 0.0F;
        player.hasImpulse = true;

        if (!player.isShiftKeyDown()) {
            if (state.charged) {
                launch(player, state);
            } else {
                launchIncomplete(player, states, state);
            }
            return;
        }

        state.chargeTicks++;
        if (!state.charged && state.chargeTicks >= CHARGE_TICKS) {
            state.charged = true;
            if (!player.level().isClientSide) {
//                player.level().playSound(null, player.getX(), standingY + 0.25D, player.getZ(),
//                        SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.PLAYERS, 1.0F, 0.85F);
                sendFlowerParticles(player, state, ParticleBurst.CHARGED);
            }
        } else if (!player.level().isClientSide && state.chargeTicks % 10 == 0) {
            sendFlowerParticles(player, state, ParticleBurst.CHARGING);
        }

        if (!player.level().isClientSide) {
            player.hurtMarked = true;
        }
    }

    private static void tickAscending(Player player, Map<Player, FlowerFlightState> states,
                                      FlowerFlightState state) {
        if (player.isShiftKeyDown() || state.cancelRequested) {
            finishFlight(player, states, state);
            return;
        }

        player.stopUsingItem();
        double heightGained = player.getY() - state.launchY;
        if (heightGained >= state.ascentHeight || player.verticalCollision) {
            state.phase = FlightPhase.GLIDING;
            Vec3 movement = player.getDeltaMovement();
            player.setDeltaMovement(movement.x, STARTING_GLIDE_LIFT, movement.z);
            player.hasImpulse = true;
            return;
        }

        player.setDeltaMovement(0.0D, ASCENT_SPEED, 0.0D);
        player.fallDistance = 0.0F;
        player.hasImpulse = true;
        if (!player.level().isClientSide) {
            player.hurtMarked = true;
            if (player.tickCount % 2 == 0) {
                sendFlowerParticles(player, state, ParticleBurst.ASCENDING);
            }
        }
    }

    private static void tickGliding(Player player, Map<Player, FlowerFlightState> states,
                                    FlowerFlightState state) {
        if (player.onGround() || player.isShiftKeyDown() || state.cancelRequested) {
            player.fallDistance = 0.0F;
            finishFlight(player, states, state);
            return;
        }

        player.stopUsingItem();
        double flowerCenterX = state.flowerPos.getX() + 0.5D;
        double flowerCenterZ = state.flowerPos.getZ() + 0.5D;
        double distanceFromFlowerSqr = Mth.square(player.getX() - flowerCenterX)
                + Mth.square(player.getZ() - flowerCenterZ);
        if (distanceFromFlowerSqr >= Mth.square(state.maximumGlideDistance)) {
            finishFlight(player, states, state);
            return;
        }

        Vec3 steered = applySteering(player, state, player.getDeltaMovement());
        double verticalSpeed = Math.max(
                MINIMUM_GLIDE_FALL_SPEED,
                player.getDeltaMovement().y * 0.975D - 0.003D
        );
        player.setDeltaMovement(steered.x, verticalSpeed, steered.z);
        player.fallDistance = 0.0F;
        player.hasImpulse = true;

        if (!player.level().isClientSide) {
            player.hurtMarked = true;
            if (player.tickCount % 3 == 0) {
                sendFlowerParticles(player, state, ParticleBurst.GLIDING);
            }
        }
    }

    private static Vec3 applySteering(Player player, FlowerFlightState state, Vec3 movement) {
        float strafe;
        float forward;
        float yaw;
        if (player.level().isClientSide) {
            strafe = player.xxa;
            forward = player.zza;
            yaw = player.getYRot();
        } else if (state.inputTicks > 0) {
            strafe = state.strafeInput;
            forward = state.forwardInput;
            yaw = state.inputYaw;
        } else {
            strafe = 0.0F;
            forward = 0.0F;
            yaw = player.getYRot();
        }

        double yawRadians = Math.toRadians(yaw);
        Vec3 forwardDirection = new Vec3(-Math.sin(yawRadians), 0.0D, Math.cos(yawRadians));
        Vec3 leftDirection = new Vec3(forwardDirection.z, 0.0D, -forwardDirection.x);
        Vec3 horizontal = new Vec3(movement.x, 0.0D, movement.z).scale(GLIDE_DRAG)
                .add(forwardDirection.scale(forward * FORWARD_ACCELERATION))
                .add(leftDirection.scale(strafe * STRAFE_ACCELERATION));
        if (horizontal.lengthSqr() > MAXIMUM_GLIDE_SPEED * MAXIMUM_GLIDE_SPEED) {
            horizontal = horizontal.normalize().scale(MAXIMUM_GLIDE_SPEED);
        }
        return horizontal;
    }

    private static void beginBurrowing(Player player, Map<Player, FlowerFlightState> states,
                                       BlockPos flowerPos) {
        BlockState flowerState = player.level().getBlockState(flowerPos);
        boolean yellow = flowerState.is(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get());
        FlowerFlightState state = new FlowerFlightState(
                flowerPos.immutable(),
                player.level().dimension(),
                flowerState,
                yellow,
                player.isInvisible()
        );
        states.put(player, state);
        if (!player.level().isClientSide) {
            FALL_PROTECTION.remove(player.getUUID());
        }

        player.setDeltaMovement(Vec3.ZERO);
        player.setSprinting(false);
        player.fallDistance = 0.0F;
        if (!player.level().isClientSide) {
            player.setInvisible(true);
            player.hurtMarked = true;
            player.level().playSound(null, flowerPos, SoundInit.DEKU_LINK_DIVE.get(),
                    SoundSource.PLAYERS, 0.9F, 1.0F);
            sendFlowerParticles(player, state, ParticleBurst.BURROW);
        }
    }

    private static void launch(Player player, FlowerFlightState state) {
        restoreVisibility(player, state);
        state.phase = FlightPhase.ASCENDING;
        state.launchY = player.getY();
        player.setDeltaMovement(0.0D, ASCENT_SPEED, 0.0D);
        player.fallDistance = 0.0F;
        player.hasImpulse = true;
        if (!player.level().isClientSide) {
            player.hurtMarked = true;
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundInit.DEKU_LINK_LAUNCH.get(), SoundSource.PLAYERS, 0.9F, 1.0F);
            sendFlowerParticles(player, state, ParticleBurst.LAUNCH);
        }
    }

    private static void launchIncomplete(Player player, Map<Player, FlowerFlightState> states,
                                         FlowerFlightState state) {
        restoreVisibility(player, state);
        player.setDeltaMovement(0.0D, INCOMPLETE_LAUNCH_SPEED, 0.0D);
        player.fallDistance = 0.0F;
        player.hasImpulse = true;
        if (!player.level().isClientSide) {
            player.hurtMarked = true;
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.PLAYERS, 0.65F, 1.35F);
            sendFlowerParticles(player, state, ParticleBurst.INCOMPLETE_LAUNCH);
        }
        states.remove(player);
    }

    private static void forceOutOfBrokenFlower(Player player, Map<Player, FlowerFlightState> states,
                                               FlowerFlightState state) {
        restoreVisibility(player, state);
        player.setPos(player.getX(), Math.max(player.getY(), state.flowerPos.getY() + 0.2D), player.getZ());
        player.setDeltaMovement(0.0D, 0.25D, 0.0D);
        player.hasImpulse = true;
        if (!player.level().isClientSide) {
            player.hurtMarked = true;
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.GRASS_BREAK, SoundSource.PLAYERS, 0.9F, 1.1F);
        }
        states.remove(player);
    }

    private static void finish(Player player, Map<Player, FlowerFlightState> states,
                               FlowerFlightState state, boolean eject) {
        restoreVisibility(player, state);
        if (eject) {
            player.setDeltaMovement(0.0D, 0.2D, 0.0D);
        }
        player.fallDistance = 0.0F;
        states.remove(player);
    }

    private static void finishFlight(Player player, Map<Player, FlowerFlightState> states,
                                     FlowerFlightState state) {
        if (!player.level().isClientSide && !player.onGround()) {
            FALL_PROTECTION.put(player.getUUID(), new FlowerFallProtection(
                    state.flowerPos.getY() - SAFE_LANDING_DEPTH,
                    FALL_PROTECTION_TICKS
            ));
        }
        if (!player.level().isClientSide) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundInit.DEKU_LINK_DROP.get(), SoundSource.PLAYERS, 0.9F, 1.0F);
        }
        finish(player, states, state, false);
    }

    private static void tickFallProtection(Player player) {
        if (player.level().isClientSide) {
            return;
        }

        FlowerFallProtection protection = FALL_PROTECTION.get(player.getUUID());
        if (protection == null) {
            return;
        }

        if (!player.isAlive() || protection.ticksRemaining <= 1) {
            FALL_PROTECTION.remove(player.getUUID());
        } else {
            protection.ticksRemaining--;
        }
    }

    private static boolean consumeFallProtection(Player player) {
        FlowerFallProtection protection = FALL_PROTECTION.remove(player.getUUID());
        return protection != null && player.getY() >= protection.minimumSafeY;
    }

    private static boolean hasSafeActiveFlightLanding(Player player) {
        FlowerFlightState state = SERVER_STATES.get(player);
        return state != null
                && state.phase != FlightPhase.BURIED
                && player.getY() >= state.flowerPos.getY() - SAFE_LANDING_DEPTH;
    }

    private static void removeState(Player player, boolean eject) {
        Map<Player, FlowerFlightState> states = getStates(player);
        FlowerFlightState state = states.get(player);
        if (state != null) {
            finish(player, states, state, eject);
        }
    }

    private static void restoreVisibility(Player player, FlowerFlightState state) {
        if (!state.visibilityRestored && !player.level().isClientSide) {
            player.setInvisible(state.wasInvisible);
            state.visibilityRestored = true;
        }
    }

    private static BlockPos findFlowerUnderPlayer(Player player) {
        BlockPos pos = BlockPos.containing(
                player.getX(),
                player.getBoundingBox().minY - 0.05D,
                player.getZ()
        );
        return isDekuFlower(player.level().getBlockState(pos).getBlock()) ? pos : null;
    }

    private static boolean isDekuFlower(Block block) {
        return block == BlockInit.DEKU_FLOWER_BLOCK.get()
                || block == BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get();
    }

    private static boolean isDekuLink(Player player) {
        ItemStack dekuMask = CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), player)
                .map(ImmutableTriple::getRight)
                .orElse(ItemStack.EMPTY);
        return !dekuMask.isEmpty();
    }

    private static Map<Player, FlowerFlightState> getStates(Player player) {
        return player.level().isClientSide ? CLIENT_STATES : SERVER_STATES;
    }

    private static void sendFlowerParticles(Player player, FlowerFlightState state,
                                            ParticleBurst burst) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        int count;
        double spread;
        double speed;
        double y;
        switch (burst) {
            case BURROW -> {
                count = 18;
                spread = 0.35D;
                speed = 0.06D;
                y = state.flowerPos.getY() + 0.25D;
            }
            case CHARGING -> {
                count = 3;
                spread = 0.25D;
                speed = 0.025D;
                y = state.flowerPos.getY() + 0.45D;
            }
            case CHARGED -> {
                count = 14;
                spread = 0.4D;
                speed = 0.09D;
                y = state.flowerPos.getY() + 0.45D;
            }
            case LAUNCH -> {
                count = 28;
                spread = 0.5D;
                speed = 0.12D;
                y = player.getY() + 0.25D;
            }
            case INCOMPLETE_LAUNCH -> {
                count = 10;
                spread = 0.3D;
                speed = 0.06D;
                y = player.getY() + 0.2D;
            }
            case ASCENDING -> {
                count = 3;
                spread = 0.2D;
                speed = 0.02D;
                y = player.getY();
            }
            case GLIDING -> {
                count = 2;
                spread = 0.3D;
                speed = 0.015D;
                y = player.getY() + 0.25D;
            }
            default -> throw new IllegalStateException("Unexpected particle burst: " + burst);
        }

        ParticleOptions particle = switch (burst) {
            case BURROW, CHARGING, CHARGED -> ParticleTypes.SPORE_BLOSSOM_AIR;
            case LAUNCH, INCOMPLETE_LAUNCH -> ParticleTypes.POOF;
            case ASCENDING, GLIDING -> ParticleTypes.FALLING_SPORE_BLOSSOM;
        };
        serverLevel.sendParticles(
                particle,
                player.getX(), y, player.getZ(), count, spread, spread * 0.5D, spread, speed
        );
        if (burst == ParticleBurst.CHARGED || burst == ParticleBurst.LAUNCH) {
            serverLevel.sendParticles(ParticleTypes.POOF, player.getX(), y, player.getZ(),
                    count / 2, spread, spread, spread, speed);
        }
    }

    private enum FlightPhase {
        BURIED,
        ASCENDING,
        GLIDING
    }

    private enum ParticleBurst {
        BURROW,
        CHARGING,
        CHARGED,
        LAUNCH,
        INCOMPLETE_LAUNCH,
        ASCENDING,
        GLIDING
    }

    private static final class FlowerFlightState {
        private final BlockPos flowerPos;
        private final ResourceKey<Level> dimension;
        private final BlockState flowerState;
        private final boolean yellow;
        private final boolean wasInvisible;
        private final double ascentHeight;
        private final double maximumGlideDistance;
        private FlightPhase phase = FlightPhase.BURIED;
        private int chargeTicks;
        private boolean charged;
        private boolean visibilityRestored;
        private double launchY;
        private float strafeInput;
        private float forwardInput;
        private float inputYaw;
        private int inputTicks;
        private boolean cancelRequested;

        private FlowerFlightState(BlockPos flowerPos, ResourceKey<Level> dimension,
                                  BlockState flowerState, boolean yellow, boolean wasInvisible) {
            this.flowerPos = flowerPos;
            this.dimension = dimension;
            this.flowerState = flowerState;
            this.yellow = yellow;
            this.wasInvisible = wasInvisible;
            this.ascentHeight = yellow ? YELLOW_ASCENT_HEIGHT : NORMAL_ASCENT_HEIGHT;
            this.maximumGlideDistance = yellow ? YELLOW_GLIDE_DISTANCE : NORMAL_GLIDE_DISTANCE;
        }
    }

    private static final class FlowerFallProtection {
        private final double minimumSafeY;
        private int ticksRemaining;

        private FlowerFallProtection(double minimumSafeY, int ticksRemaining) {
            this.minimumSafeY = minimumSafeY;
            this.ticksRemaining = ticksRemaining;
        }
    }
}
