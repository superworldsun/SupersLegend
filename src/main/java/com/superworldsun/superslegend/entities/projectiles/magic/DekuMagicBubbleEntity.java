package com.superworldsun.superslegend.entities.projectiles.magic;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class DekuMagicBubbleEntity extends AbstractHurtingProjectile implements IEntityAdditionalSpawnData {
    // Bubble/model and collision-box size at zero charge. This is measured in blocks.
    public static final float MIN_VISUAL_SIZE = 0.25F;

    // Bubble/model and collision-box size at full charge. This is measured in blocks.
    public static final float MAX_VISUAL_SIZE = 0.55F;

    // Raises the collision box relative to the rendered model. Increasing this makes the
    // hitbox sit higher while the model appears lower inside it.
    private static final double HITBOX_UPWARD_OFFSET = 0.04D;
    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(
            DekuMagicBubbleEntity.class,
            EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Float> FLIGHT_X = SynchedEntityData.defineId(
            DekuMagicBubbleEntity.class,
            EntityDataSerializers.FLOAT
    );
    private static final EntityDataAccessor<Float> FLIGHT_Y = SynchedEntityData.defineId(
            DekuMagicBubbleEntity.class,
            EntityDataSerializers.FLOAT
    );
    private static final EntityDataAccessor<Float> FLIGHT_Z = SynchedEntityData.defineId(
            DekuMagicBubbleEntity.class,
            EntityDataSerializers.FLOAT
    );
    private static final EntityDataAccessor<Integer> RELEASE_TICK = SynchedEntityData.defineId(
            DekuMagicBubbleEntity.class,
            EntityDataSerializers.INT
    );
    private static final EntityDataAccessor<Float> CHARGE_STRENGTH = SynchedEntityData.defineId(
            DekuMagicBubbleEntity.class,
            EntityDataSerializers.FLOAT
    );
    private static final EntityDataAccessor<Boolean> RANDOMIZED_FLIGHT_PATH = SynchedEntityData.defineId(
            DekuMagicBubbleEntity.class,
            EntityDataSerializers.BOOLEAN
    );

    // Time required to reach full strength and maximum size. Minecraft runs at 20 ticks per second.
    public static final int FULL_CHARGE_TICKS = 32;

    // Total time the button may be held before the bubble overcharges and pops (70 ticks = 3.5 seconds).
    public static final int MAX_CHARGE_TICKS = 70;

    // Airborne lifetime of a bubble released with little or no charge.
    private static final int MIN_FLIGHT_TICKS = 12;

    // Maximum airborne lifetime of a fully charged bubble (40 ticks = 2 seconds).
    private static final int MAX_FLIGHT_TICKS = 40;

    // Damage caused by a bubble released with little or no charge.
    private static final float MIN_DAMAGE = 1.0F;

    // Damage caused by a fully charged bubble.
    private static final float MAX_DAMAGE = 4.0F;

    // Forward movement in blocks per tick at minimum charge. Raising this increases minimum range.
    private static final double MIN_FLIGHT_SPEED = 0.20D;

    // Forward movement in blocks per tick at full charge. Raising this increases maximum range.
    private static final double MAX_FLIGHT_SPEED = 0.45D;

    // Sideways/upward spiral movement at minimum charge. Higher values make the small spiral wider.
    private static final double MIN_SPIRAL_SPEED = 0.02D;

    // Sideways/upward spiral movement at full charge. Higher values make the full bubble spiral wider.
    private static final double MAX_SPIRAL_SPEED = 0.055D;

    // Rotation around the forward axis per tick. Higher values make the spiral complete turns faster.
    private static final double SPIRAL_RADIANS_PER_TICK = 0.45D;

    // Size in radians of the slower angle wobble used by randomized flight. Higher values bend the
    // spiral timing farther away from a perfect circle.
    private static final double RANDOM_PATH_PRIMARY_ANGLE_SWING = 0.62D;

    // Size in radians of a quicker secondary angle wobble that prevents the random path from repeating evenly.
    private static final double RANDOM_PATH_SECONDARY_ANGLE_SWING = 0.30D;

    // Fraction by which randomized flight expands/contracts each side of the spiral.
    // For example, 0.42 varies its radius by up to 42 percent.
    private static final double RANDOM_PATH_RADIUS_VARIATION = 0.42D;

    // Distance in blocks from the owner's eyes to the center of the charging bubble.
    private static final double HELD_DISTANCE = 0.6D;

    // World-space vertical adjustment for the charging bubble. Negative values move it downward.
    private static final double HELD_MODEL_VERTICAL_OFFSET = -0.18D;

    // Smallest permitted model/hitbox size immediately before the bubble disappears.
    private static final float MINIMUM_FLIGHT_SIZE = 0.01F;

    public DekuMagicBubbleEntity(EntityType<? extends DekuMagicBubbleEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public DekuMagicBubbleEntity(Level level, LivingEntity owner) {
        this(EntityTypeInit.DEKU_MAGIC_BUBBLE.get(), level);
        setOwner(owner);
        entityData.set(RANDOMIZED_FLIGHT_PATH, Config.randomizeDekuBubbleFlightPath());
        refreshDimensions();
        moveToHeldPosition(owner);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CHARGING, true);
        entityData.define(FLIGHT_X, 0.0F);
        entityData.define(FLIGHT_Y, 0.0F);
        entityData.define(FLIGHT_Z, 0.0F);
        entityData.define(RELEASE_TICK, 0);
        entityData.define(CHARGE_STRENGTH, 0.0F);
        entityData.define(RANDOMIZED_FLIGHT_PATH, false);
    }

    public boolean isCharging() {
        return entityData.get(CHARGING);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (CHARGING.equals(accessor) || CHARGE_STRENGTH.equals(accessor)) {
            refreshDimensions();
        }
    }

    public void fire(Vec3 direction) {
        if (!isCharging() || direction.lengthSqr() < 1.0E-6D) {
            return;
        }

        Vec3 flightDirection = direction.normalize();
        float chargeStrength = Math.min(1.0F, tickCount / (float) FULL_CHARGE_TICKS);
        entityData.set(FLIGHT_X, (float) flightDirection.x);
        entityData.set(FLIGHT_Y, (float) flightDirection.y);
        entityData.set(FLIGHT_Z, (float) flightDirection.z);
        entityData.set(RELEASE_TICK, tickCount);
        entityData.set(CHARGE_STRENGTH, chargeStrength);
        entityData.set(CHARGING, false);
        noPhysics = false;
        setDeltaMovement(flightDirection.scale(getFlightSpeed()));
        refreshDimensions();
        hasImpulse = true;
    }

    @Override
    public void tick() {
        if (isCharging()) {
            tickWhileCharging();
            return;
        }

        noPhysics = false;
        int flightTicks = tickCount - entityData.get(RELEASE_TICK);
        if (flightTicks >= getMaximumFlightTicks()) {
            pop(false);
            return;
        }
        if (isTouchingLava()) {
            pop(false);
            return;
        }

        Vec3 forward = getFlightDirection();
        if (forward.lengthSqr() < 1.0E-6D) {
            discard();
            return;
        }

        refreshDimensions();
        Vec3 referenceAxis = Math.abs(forward.y) > 0.9D
                ? new Vec3(1.0D, 0.0D, 0.0D)
                : new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 side = forward.cross(referenceAxis).normalize();
        Vec3 spiralUp = side.cross(forward).normalize();
        double spiralAngle = flightTicks * SPIRAL_RADIANS_PER_TICK;
        double spiralSpeed = getSpiralSpeed();
        double sideScale = 1.0D;
        double verticalScale = 1.0D;
        if (usesRandomizedFlightPath()) {
            double phase = getId() * 0.754877666D;
            spiralAngle += Math.sin(flightTicks * 0.24D + phase) * RANDOM_PATH_PRIMARY_ANGLE_SWING
                    + Math.sin(flightTicks * 0.43D + phase * 1.7D) * RANDOM_PATH_SECONDARY_ANGLE_SWING;
            sideScale += Math.sin(flightTicks * 0.17D + phase * 0.7D)
                    * RANDOM_PATH_RADIUS_VARIATION;
            verticalScale += Math.sin(flightTicks * 0.29D + phase * 1.3D)
                    * RANDOM_PATH_RADIUS_VARIATION;
        }
        Vec3 spiralMotion = side.scale(Math.cos(spiralAngle) * spiralSpeed * sideScale)
                .add(spiralUp.scale(Math.sin(spiralAngle) * spiralSpeed * verticalScale));

        setDeltaMovement(forward.scale(getFlightSpeed()).add(spiralMotion));
        super.tick();
    }

    private void tickWhileCharging() {
        baseTick();
        if (tickCount >= MAX_CHARGE_TICKS) {
            pop(false);
            return;
        }

        Entity owner = getOwner();
        if (!(owner instanceof LivingEntity livingOwner) || !livingOwner.isAlive()) {
            if (!level().isClientSide || tickCount > 10) {
                discard();
            }
            return;
        }

        noPhysics = true;
        setDeltaMovement(Vec3.ZERO);
        refreshDimensions();
        moveToHeldPosition(livingOwner);
    }

    private void moveToHeldPosition(LivingEntity owner) {
        Vec3 heldPosition = owner.getEyePosition()
                .add(owner.getLookAngle().scale(HELD_DISTANCE))
                .add(0.0D, HELD_MODEL_VERTICAL_OFFSET - getModelCenterYOffset(0.0F), 0.0D);
        setPos(heldPosition.x, heldPosition.y, heldPosition.z);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        float size = getVisualScale(0.0F);
        return EntityDimensions.scalable(size, size);
    }

    public double getModelCenterYOffset(float partialTick) {
        double hitboxOffset = isCharging()
                ? HITBOX_UPWARD_OFFSET
                : HITBOX_UPWARD_OFFSET * getRemainingFlightScale(partialTick);
        return getVisualScale(partialTick) * 0.5D - hitboxOffset;
    }

    public Vec3 getFlightDirection() {
        return new Vec3(
                entityData.get(FLIGHT_X),
                entityData.get(FLIGHT_Y),
                entityData.get(FLIGHT_Z)
        ).normalize();
    }

    private float getChargeStrength() {
        return entityData.get(CHARGE_STRENGTH);
    }

    private double getFlightSpeed() {
        return MIN_FLIGHT_SPEED + (MAX_FLIGHT_SPEED - MIN_FLIGHT_SPEED) * getChargeStrength();
    }

    private double getSpiralSpeed() {
        return MIN_SPIRAL_SPEED + (MAX_SPIRAL_SPEED - MIN_SPIRAL_SPEED) * getChargeStrength();
    }

    private boolean usesRandomizedFlightPath() {
        return entityData.get(RANDOMIZED_FLIGHT_PATH);
    }

    private int getMaximumFlightTicks() {
        return (int) (MIN_FLIGHT_TICKS
                + (MAX_FLIGHT_TICKS - MIN_FLIGHT_TICKS) * getChargeStrength());
    }

    private float getDamage() {
        return MIN_DAMAGE + (MAX_DAMAGE - MIN_DAMAGE) * getChargeStrength();
    }

    public float getVisualScale(float partialTick) {
        float chargeStrength = isCharging()
                ? Math.min(1.0F, (tickCount + partialTick) / FULL_CHARGE_TICKS)
                : getChargeStrength();
        float chargedSize = MIN_VISUAL_SIZE + chargeStrength * (MAX_VISUAL_SIZE - MIN_VISUAL_SIZE);
        if (isCharging()) {
            return chargedSize;
        }
        return Math.max(MINIMUM_FLIGHT_SIZE, chargedSize * getRemainingFlightScale(partialTick));
    }

    private float getRemainingFlightScale(float partialTick) {
        int maximumFlightTicks = getMaximumFlightTicks();
        if (isCharging() || maximumFlightTicks <= 0) {
            return 1.0F;
        }

        float elapsedFlightTicks = tickCount - entityData.get(RELEASE_TICK) + partialTick;
        return Math.max(0.0F, 1.0F - elapsedFlightTicks / maximumFlightTicks);
    }

    private boolean isTouchingLava() {
        if (isInLava()) {
            return true;
        }

        BlockPos position = blockPosition();
        return level().getFluidState(position).is(FluidTags.LAVA);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide) {
            Entity owner = getOwner();
            DamageSource damageSource = owner instanceof LivingEntity livingOwner
                    ? level().damageSources().mobProjectile(this, livingOwner)
                    : level().damageSources().magic();
            result.getEntity().hurt(damageSource, getDamage());
        }
        pop(true);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        activateHitBlock(result);
        super.onHitBlock(result);
        pop(true);
    }

    private void activateHitBlock(BlockHitResult result) {
        if (level().isClientSide || !(getOwner() instanceof Player player)) {
            return;
        }

        BlockPos interactionPos = result.getBlockPos();
        BlockState interactionState = level().getBlockState(interactionPos);
        BlockHitResult interactionHit = result;

        // Buttons and levers do not have a normal solid collision box, so projectile
        // collision commonly reports the supporting block behind them instead.
        if (!canBubbleActivate(interactionState)) {
            BlockPos mountedControlPos = interactionPos.relative(result.getDirection());
            BlockState mountedControlState = level().getBlockState(mountedControlPos);
            if (canBubbleActivate(mountedControlState)) {
                interactionPos = mountedControlPos;
                interactionState = mountedControlState;
                interactionHit = new BlockHitResult(
                        result.getLocation(),
                        result.getDirection(),
                        interactionPos,
                        result.isInside()
                );
            }
        }

        if (canBubbleActivate(interactionState)) {
            interactionState.use(level(), player, InteractionHand.MAIN_HAND, interactionHit);
        }
    }

    private static boolean canBubbleActivate(BlockState state) {
        return state.is(BlockTags.WOODEN_BUTTONS) || state.getBlock() instanceof LeverBlock;
    }

    private void pop(boolean hitSomething) {
        if (!level().isClientSide) {
            level().playSound(
                    null,
                    getX(),
                    getY(),
                    getZ(),
                    SoundInit.DEKU_LINK_BUBBLE_POP.get(),
                    SoundSource.PLAYERS,
                    0.8F,
                    1.2F
            );
            if (level() instanceof ServerLevel serverLevel) {
                if (hitSomething) {
                    serverLevel.sendParticles(
                            ParticleTypes.SNEEZE,
                            getX(),
                            getY(),
                            getZ(),
                            14,
                            0.22D,
                            0.22D,
                            0.22D,
                            0.025D
                    );
                } else {
                    serverLevel.sendParticles(
                            ParticleTypes.POOF,
                            getX(),
                            getY(),
                            getZ(),
                            8,
                            0.2D,
                            0.2D,
                            0.2D,
                            0.02D
                    );
                }
            }
        }
        discard();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected float getInertia() {
        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Charging", isCharging());
        tag.putFloat("FlightX", entityData.get(FLIGHT_X));
        tag.putFloat("FlightY", entityData.get(FLIGHT_Y));
        tag.putFloat("FlightZ", entityData.get(FLIGHT_Z));
        tag.putInt("ReleaseTick", entityData.get(RELEASE_TICK));
        tag.putFloat("ChargeStrength", getChargeStrength());
        tag.putBoolean("RandomizedFlightPath", usesRandomizedFlightPath());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(CHARGING, tag.getBoolean("Charging"));
        entityData.set(FLIGHT_X, tag.getFloat("FlightX"));
        entityData.set(FLIGHT_Y, tag.getFloat("FlightY"));
        entityData.set(FLIGHT_Z, tag.getFloat("FlightZ"));
        entityData.set(RELEASE_TICK, tag.getInt("ReleaseTick"));
        entityData.set(CHARGE_STRENGTH, tag.getFloat("ChargeStrength"));
        entityData.set(RANDOMIZED_FLIGHT_PATH, tag.getBoolean("RandomizedFlightPath"));
        noPhysics = isCharging();
        refreshDimensions();
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        Entity owner = getOwner();
        buffer.writeVarInt(owner == null ? 0 : owner.getId());
        buffer.writeBoolean(usesRandomizedFlightPath());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        Entity owner = level().getEntity(additionalData.readVarInt());
        if (owner != null) {
            setOwner(owner);
        }
        entityData.set(RANDOMIZED_FLIGHT_PATH, additionalData.readBoolean());
        refreshDimensions();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
