package com.superworldsun.superslegend.entities.statue;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.world.ElegyStatueManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * A stationary, gravity-affected Elegy statue. Its physical entity is only the
 * low wooden platform; the rendered body deliberately has no collision so
 * players can pass through it.
 */
public class ElegyStatueEntity extends Mob implements GeoEntity {
    public static final long LIFETIME_TICKS = 24_000L;
    public static final int BREAK_TIME_TICKS = 20;

    private static final EntityDataAccessor<Optional<UUID>> OWNER_ID = SynchedEntityData.defineId(
            ElegyStatueEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(
            ElegyStatueEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BREAK_STAGE = SynchedEntityData.defineId(
            ElegyStatueEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    private long createdGameTime;
    private double anchoredX;
    private double anchoredZ;
    private double fallVelocity;
    private boolean intentionallyDespawning;
    private UUID miningPlayerId;
    private long lastMiningSignalTime = Long.MIN_VALUE;
    private int miningTicks;

    public ElegyStatueEntity(EntityType<? extends ElegyStatueEntity> type, Level level) {
        super(type, level);
        setNoAi(true);
        setPersistenceRequired();
    }

    public ElegyStatueEntity(ServerLevel level, Player owner, ElegyStatueVariant variant,
                             double x, double y, double z, float yaw) {
        this(EntityTypeInit.ELEGY_STATUE.get(), level);
        entityData.set(OWNER_ID, Optional.of(owner.getUUID()));
        entityData.set(VARIANT, variant.networkId());
        createdGameTime = level.getServer().overworld().getGameTime();
        anchoredX = x;
        anchoredZ = z;
        moveTo(x, y, z, yaw, 0.0F);
        setYHeadRot(yaw);
        setYBodyRot(yaw);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        // Statues never act or navigate.
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(OWNER_ID, Optional.empty());
        entityData.define(VARIANT, ElegyStatueVariant.PLAYER.networkId());
        entityData.define(BREAK_STAGE, -1);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
            // This is the statue's only permitted movement. Entity movement, velocity,
            // fans, fluids, hooks and knockback are all ignored by the overrides below.
            if (!isNoGravity()) {
                fallVelocity = Math.max(-3.92D, (fallVelocity - 0.08D) * 0.98D);
                super.move(MoverType.SELF, new Vec3(0.0D, fallVelocity, 0.0D));
                if (onGround()) {
                    fallVelocity = 0.0D;
                }
            }
            super.setDeltaMovement(Vec3.ZERO);

            // Undo every horizontal displacement after vanilla gravity/collision has
            // run. This retains falling while making water, entities, explosions,
            // pistons and other velocity sources unable to relocate the statue.
            // The client follows normal server position packets; anchor data is not
            // needed in the spawn packet and must therefore stay server-authoritative.
            if (getX() != anchoredX || getZ() != anchoredZ) {
                setPos(anchoredX, getY(), anchoredZ);
            }
            long now = serverLevel.getServer().overworld().getGameTime();
            tickMining(serverLevel, now);
            if (isRemoved()) {
                return;
            }
            if (now - createdGameTime >= LIFETIME_TICKS || !ElegyStatueManager.isCurrent(this)) {
                despawnWithSmoke();
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide && source.getEntity() instanceof Player player) {
            continueMining(player);
            return true;
        }
        return false;
    }

    public void continueMining(Player player) {
        if (level().isClientSide || !isAlive() || player.isSpectator()
                || player.distanceToSqr(this) > 36.0D) {
            return;
        }

        long now = level().getServer().overworld().getGameTime();
        if (!player.getUUID().equals(miningPlayerId)) {
            resetMining();
            miningPlayerId = player.getUUID();
        }
        lastMiningSignalTime = now;
    }

    public void stopMining(Player player) {
        if (!level().isClientSide && player.getUUID().equals(miningPlayerId)) {
            resetMining();
        }
    }

    private void tickMining(ServerLevel serverLevel, long now) {
        if (miningPlayerId == null) {
            return;
        }

        Player miner = serverLevel.getPlayerByUUID(miningPlayerId);
        if (miner == null || !miner.isAlive() || miner.isSpectator()
                || miner.distanceToSqr(this) > 36.0D || now - lastMiningSignalTime > 2L) {
            resetMining();
            return;
        }

        miningTicks++;
        entityData.set(BREAK_STAGE, Math.min(9, miningTicks * 10 / BREAK_TIME_TICKS));
        if (miningTicks == 1 || miningTicks % 4 == 0) {
            SoundType soundType = Blocks.OAK_PLANKS.defaultBlockState().getSoundType();
            serverLevel.playSound(null, getX(), getY(), getZ(), soundType.getHitSound(),
                    SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 8.0F,
                    soundType.getPitch() * 0.5F);
        }
        if (miningTicks >= BREAK_TIME_TICKS) {
            breakLikeBlock(serverLevel);
        }
    }

    private void resetMining() {
        miningPlayerId = null;
        miningTicks = 0;
        lastMiningSignalTime = Long.MIN_VALUE;
        entityData.set(BREAK_STAGE, -1);
    }

    private void breakLikeBlock(ServerLevel serverLevel) {
        if (intentionallyDespawning) {
            return;
        }
        intentionallyDespawning = true;
        BlockState woodState = Blocks.OAK_PLANKS.defaultBlockState();
        SoundType soundType = woodState.getSoundType();
        serverLevel.levelEvent(2001, blockPosition(), Block.getId(woodState));
        serverLevel.playSound(null, getX(), getY(), getZ(), soundType.getBreakSound(),
                SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F,
                soundType.getPitch() * 0.8F);
        spawnSmokeParticles(serverLevel);
        ElegyStatueManager.clearIfCurrent(this);
        discard();
    }

    public void despawnWithSmoke() {
        if (intentionallyDespawning || level().isClientSide) {
            return;
        }
        intentionallyDespawning = true;
        if (level() instanceof ServerLevel serverLevel) {
            spawnSmokeParticles(serverLevel);
            ElegyStatueManager.clearIfCurrent(this);
        }
        discard();
    }

    private void spawnSmokeParticles(ServerLevel serverLevel) {
        serverLevel.sendParticles(ParticleTypes.POOF, getX(), getY() + 0.45D, getZ(),
                18, 0.35D, 0.45D, 0.35D, 0.03D);
        serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), getY() + 0.35D, getZ(),
                12, 0.28D, 0.3D, 0.28D, 0.02D);
    }

    public Optional<UUID> getOwnerId() {
        return entityData.get(OWNER_ID);
    }

    public ElegyStatueVariant getStatueVariant() {
        return ElegyStatueVariant.byNetworkId(entityData.get(VARIANT));
    }

    public int getBreakStage() {
        return entityData.get(BREAK_STAGE);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void move(MoverType type, Vec3 movement) {
        // Gravity calls Entity#move directly from tick(); all outside movement is ignored.
    }

    @Override
    public void setDeltaMovement(Vec3 movement) {
        // Prevent direct velocity manipulation. Gravity uses its own fallVelocity field.
    }

    @Override
    public void setDeltaMovement(double x, double y, double z) {
        // Prevent direct velocity manipulation. Gravity uses its own fallVelocity field.
    }

    @Override
    public void push(Entity entity) {
        // Immovable, while still exposing its platform collision box.
    }

    @Override
    protected void doPush(Entity entity) {
        if (entity instanceof Player && entity.getBoundingBox().intersects(getBoundingBox())) {
            // Replace vanilla's horizontal entity separation with a small lift so
            // players step onto the solid platform instead of being shoved aside.
            double upwardImpulse = Math.max(0.0D, 0.08D - entity.getDeltaMovement().y);
            if (upwardImpulse > 0.0D) {
                entity.push(0.0D, upwardImpulse, 0.0D);
            }
        }
    }

    @Override
    public void push(double x, double y, double z) {
        // Immovable, while gravity remains handled by normal entity ticking.
    }

    @Override
    public void knockback(double strength, double x, double z) {
        // Damage breaks the statue instead of knocking it away.
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.BLOCK;
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void teleportTo(double x, double y, double z) {
        // Commands and other teleport-style movement cannot relocate a statue.
    }

    @Override
    public void teleportRelative(double x, double y, double z) {
        // Commands and other teleport-style movement cannot relocate a statue.
    }

    @Override
    public boolean teleportTo(ServerLevel level, double x, double y, double z,
                              Set<RelativeMovement> relativeMovements, float yaw, float pitch) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        // The physical box is only the platform, but the model is player-height.
        return getBoundingBox().expandTowards(0.0D, 2.25D, 0.0D).inflate(0.15D);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) {
            entityData.set(OWNER_ID, Optional.of(tag.getUUID("Owner")));
        }
        entityData.set(VARIANT, tag.getInt("Variant"));
        createdGameTime = tag.getLong("CreatedGameTime");
        anchoredX = tag.contains("AnchorX") ? tag.getDouble("AnchorX") : getX();
        anchoredZ = tag.contains("AnchorZ") ? tag.getDouble("AnchorZ") : getZ();
        fallVelocity = tag.getDouble("FallVelocity");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        entityData.get(OWNER_ID).ifPresent(owner -> tag.putUUID("Owner", owner));
        tag.putInt("Variant", entityData.get(VARIANT));
        tag.putLong("CreatedGameTime", createdGameTime);
        tag.putDouble("AnchorX", anchoredX);
        tag.putDouble("AnchorZ", anchoredZ);
        tag.putDouble("FallVelocity", fallVelocity);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Static model; future variants may add their own animation controllers.
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
