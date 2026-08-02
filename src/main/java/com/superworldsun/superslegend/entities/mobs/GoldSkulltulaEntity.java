package com.superworldsun.superslegend.entities.mobs;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.world.GoldSkulltulaSpawnData;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

/**
 * A collectible monster which remains motionless on a wall or ceiling for its
 * entire lifetime. Damage never causes it to detach or pursue the attacker.
 */
public class GoldSkulltulaEntity extends Monster implements GeoEntity {
    // Play the idle clip slightly faster than its original 0.615-second
    // length, then begin the next member of the burst as it finishes.
    private static final float IDLE_SOUND_PITCH = 1.1F;
    private static final int IDLE_SOUND_INTERVAL_TICKS = 5;
    private static final EntityDataAccessor<Byte> ATTACHMENT = SynchedEntityData.defineId(
            GoldSkulltulaEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> DETACHED = SynchedEntityData.defineId(
            GoldSkulltulaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DORMANT = SynchedEntityData.defineId(
            GoldSkulltulaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    private int nextLocatorSoundTick;
    private int idleSoundsRemaining;
    private boolean attachmentInitialized;
    private UUID spawnOwner;
    private long spawnNight = -1L;
    private boolean deathRecorded;

    public GoldSkulltulaEntity(EntityType<? extends GoldSkulltulaEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        setNoGravity(true);
        setGlowingTag(true);
        this.nextLocatorSoundTick = randomIdlePause();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.ARMOR, 0.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isDormant()) {
            return false;
        }
        // Normal attacks always remove one heart, making the collectible take exactly two hits.
        // Preserve lethal bypass sources such as /kill instead of reducing them to token damage.
        return super.hurt(source, source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? amount : 2.0F);
    }

    /** Marks naturally spawned collectibles so distant despawns can reuse, but not increase, their nightly slot. */
    public void setSpawnTracking(UUID playerId, long night) {
        spawnOwner = playerId;
        spawnNight = night;
    }

    public UUID getSpawnOwner() {
        return spawnOwner;
    }

    public long getSpawnNight() {
        return spawnNight;
    }

    @Override
    public void die(DamageSource source) {
        if (!deathRecorded && !level().isClientSide && spawnOwner != null
                && level() instanceof ServerLevel serverLevel) {
            deathRecorded = true;
            GoldSkulltulaSpawnData.get(serverLevel).recordKill(spawnOwner, spawnNight, blockPosition());
        }
        super.die(source);
    }

    @Override
    protected void registerGoals() {
        // Intentionally empty: this is a stationary collectible, not a roaming spider.
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACHMENT, (byte) Direction.UP.get3DDataValue());
        entityData.define(DETACHED, false);
        entityData.define(DORMANT, false);
    }

    public Direction getAttachmentDirection() {
        return Direction.from3DDataValue(entityData.get(ATTACHMENT));
    }

    public void setAttachmentDirection(Direction direction) {
        if (direction == Direction.DOWN) {
            direction = Direction.UP;
        }
        entityData.set(ATTACHMENT, (byte) direction.get3DDataValue());
        attachmentInitialized = true;
        setYRot(yawForAttachment(direction));
        yRotO = getYRot();
        yBodyRot = getYRot();
        yBodyRotO = getYRot();
        if (!level().isClientSide && !isDetached()) {
            snapAgainstAttachmentSurface(direction);
        }
    }

    public boolean isAnchored() {
        return !isDetached();
    }

    public boolean isDetached() {
        return entityData.get(DETACHED);
    }

    public boolean isDormant() {
        return entityData.get(DORMANT);
    }

    public void setDormant(boolean dormant) {
        entityData.set(DORMANT, dormant);
        setInvisible(dormant);
        setGlowingTag(!dormant);
        setNoGravity(true);
        setDeltaMovement(0.0D, 0.0D, 0.0D);
        setTarget(null);
    }

    private void detachFromSurface() {
        entityData.set(DETACHED, true);
        setNoGravity(false);
        setOnGround(false);
    }

    @Override
    public void tick() {
        // A permanent entity flag gives the testing outline without potion
        // particles and without a timed effect that could expire.
        if (!isDormant() && !isCurrentlyGlowing()) {
            setGlowingTag(true);
        }

        // Gold Skulltulas are strictly nocturnal, including manually summoned
        // ones. Discarding rather than killing prevents sunrise token drops.
        if (!level().isClientSide && isOverworldDay()) {
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                        Component.literal("Gold Skulltula despawned at "
                                        + getBlockX() + ", " + getBlockY() + ", " + getBlockZ()
                                        + " (sunrise; location remembered)")
                                .withStyle(ChatFormatting.YELLOW),
                        false);
            }
            discard();
            return;
        }

        if (isDormant()) {
            setInvisible(true);
            setGlowingTag(false);
            setNoGravity(true);
            setDeltaMovement(0.0D, 0.0D, 0.0D);
            setTarget(null);
            super.tick();
            setDeltaMovement(0.0D, 0.0D, 0.0D);
            return;
        }
        if (isInvisible()) {
            setInvisible(false);
        }

        setTarget(null);

        if (!level().isClientSide) {
            if (!isDetached()) {
                if (!attachmentInitialized) {
                    // Spawn eggs and commands do not provide an attachment
                    // direction. Resolve the adjacent wall/ceiling once before
                    // treating a missing support as a broken attachment.
                    if (isRestingOnGroundSurface()) {
                        // A spawn egg used on the ground creates the upright,
                        // detached pose even when a wall or leaf happens to be nearby.
                        detachFromSurface();
                    } else {
                        Direction attachment = findAdjacentAttachment();
                        if (attachment != null) {
                            setAttachmentDirection(attachment);
                        } else {
                            detachFromSurface();
                        }
                    }
                } else if (!hasValidSupport()) {
                    detachFromSurface();
                }
            }

            if (--nextLocatorSoundTick <= 0) {
                if (idleSoundsRemaining <= 0) {
                    idleSoundsRemaining = 3 + random.nextInt(5);
                }

                // Client attenuation scales from the playback volume, so 2.0 carries the cue about 32 blocks.
                playSound(SoundInit.SKULLTULA_IDLE.get(), 2.0F, IDLE_SOUND_PITCH);
                idleSoundsRemaining--;
                nextLocatorSoundTick = idleSoundsRemaining > 0
                        ? IDLE_SOUND_INTERVAL_TICKS
                        : randomIdlePause();
            }
        }

        if (isDetached()) {
            // A detached Skulltula falls straight down. Once it reaches the
            // ground it becomes a completely stationary collectible again.
            if (onGround()) {
                setNoGravity(true);
                setDeltaMovement(0.0D, 0.0D, 0.0D);
            } else {
                setNoGravity(false);
                setDeltaMovement(0.0D, getDeltaMovement().y, 0.0D);
            }

            lockCardinalRotation();
            super.tick();
            damageTouchingPlayers();

            if (onGround()) {
                setNoGravity(true);
                setDeltaMovement(0.0D, 0.0D, 0.0D);
                fallDistance = 0.0F;
            } else {
                setDeltaMovement(0.0D, getDeltaMovement().y, 0.0D);
            }
            lockCardinalRotation();
            return;
        }

        setNoGravity(true);
        setDeltaMovement(0.0D, 0.0D, 0.0D);
        lockCardinalRotation();
        super.tick();
        damageTouchingPlayers();
        setDeltaMovement(0.0D, 0.0D, 0.0D);
        lockCardinalRotation();
    }

    private boolean hasValidSupport() {
        Direction attachment = getAttachmentDirection();
        var supportPos = blockPosition().relative(attachment);
        var support = level().getBlockState(supportPos);
        return support.isFaceSturdy(level(), supportPos, attachment.getOpposite())
                || (attachment == Direction.UP && support.is(net.minecraft.tags.BlockTags.LEAVES));
    }

    /**
     * Places the reduced collision box directly against its supporting face.
     * Spawn positions identify the open block beside a surface, so leaving the
     * entity at the block center creates a very noticeable gap at this scale.
     */
    private void snapAgainstAttachmentSurface(Direction attachment) {
        var cell = blockPosition();
        double x = cell.getX() + 0.5D;
        double y = cell.getY();
        double z = cell.getZ() + 0.5D;
        double wallInset = Math.max(0.0D, 0.5D - getBbWidth() * 0.5D - 0.01D);

        switch (attachment) {
            case NORTH -> {
                z -= wallInset;
                y += 0.25D;
            }
            case SOUTH -> {
                z += wallInset;
                y += 0.25D;
            }
            case WEST -> {
                x -= wallInset;
                y += 0.25D;
            }
            case EAST -> {
                x += wallInset;
                y += 0.25D;
            }
            // The renderer already rotates around an elevated ceiling pivot.
            // Only a small entity offset is needed here; using the collision
            // height as a second offset pushed the reduced model into the block.
            case UP -> y += 0.28D;
            default -> {
            }
        }

        setPos(x, y, z);
    }

    /** Returns true only when the entity's feet are actually on a collision surface. */
    private boolean isRestingOnGroundSurface() {
        var below = net.minecraft.core.BlockPos.containing(getX(), getY() - 0.02D, getZ());
        VoxelShape shape = level().getBlockState(below).getCollisionShape(level(), below);
        if (shape.isEmpty()) {
            return false;
        }
        double surfaceY = below.getY() + shape.max(Direction.Axis.Y);
        return Math.abs(getY() - surfaceY) <= 0.04D;
    }

    private Direction findAdjacentAttachment() {
        Direction[] attachments = {
                Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST
        };
        for (Direction attachment : attachments) {
            var supportPos = blockPosition().relative(attachment);
            var support = level().getBlockState(supportPos);
            if (support.isFaceSturdy(level(), supportPos, attachment.getOpposite())
                    || (attachment == Direction.UP && support.is(net.minecraft.tags.BlockTags.LEAVES))) {
                return attachment;
            }
        }
        return null;
    }

    private void lockCardinalRotation() {
        float yaw = yawForAttachment(getAttachmentDirection());
        setYRot(yaw);
        yRotO = yaw;
        yBodyRot = yaw;
        yBodyRotO = yaw;
        setYHeadRot(yaw);
        yHeadRotO = yaw;
        setXRot(0.0F);
        xRotO = 0.0F;
    }

    private int randomIdlePause() {
        return 40 + random.nextInt(61); // 2-5 seconds between 3-7 sound bursts.
    }

    private boolean isOverworldDay() {
        return level() instanceof ServerLevel serverLevel
                ? serverLevel.getServer().overworld().isDay()
                : level().isDay();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SKULLTULA_DEATH.get();
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected int decreaseAirSupply(int air) {
        // Gold Skulltulas can occupy rare underwater wall and ceiling locations.
        return air;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return !isDormant() && super.isPickable();
    }

    private void damageTouchingPlayers() {
        if (level().isClientSide || isDormant()) {
            return;
        }
        for (Player player : level().getEntitiesOfClass(Player.class, getBoundingBox(),
                player -> player.isAlive() && !player.isSpectator())) {
            player.hurt(damageSources().mobAttack(this),
                    (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
    }

    @Override
    protected void doPush(net.minecraft.world.entity.Entity entity) {
        // Surface-bound collectibles cannot be displaced by entity collisions.
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        spawnAtLocation(ItemInit.GOLD_SKULLTULA_TOKEN.get());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("Attachment", (byte) getAttachmentDirection().get3DDataValue());
        tag.putBoolean("Detached", isDetached());
        tag.putBoolean("Dormant", isDormant());
        tag.putInt("NextLocatorSound", nextLocatorSoundTick);
        tag.putInt("IdleSoundsRemaining", idleSoundsRemaining);
        if (spawnOwner != null) {
            tag.putUUID("SpawnOwner", spawnOwner);
            tag.putLong("SpawnNight", spawnNight);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(DETACHED, tag.getBoolean("Detached"));
        entityData.set(DORMANT, tag.getBoolean("Dormant"));
        setInvisible(isDormant());
        setGlowingTag(!isDormant());
        setAttachmentDirection(Direction.from3DDataValue(tag.getByte("Attachment")));
        nextLocatorSoundTick = Math.max(1, tag.getInt("NextLocatorSound"));
        idleSoundsRemaining = Math.max(0, tag.getInt("IdleSoundsRemaining"));
        spawnOwner = tag.hasUUID("SpawnOwner") ? tag.getUUID("SpawnOwner") : null;
        spawnNight = tag.contains("SpawnNight") ? tag.getLong("SpawnNight") : -1L;
        setNoGravity(isDetached() && !onGround() ? false : true);
    }

    private static float yawForAttachment(Direction direction) {
        return switch (direction) {
            // LivingEntityRenderer adds its own 180-degree model-space turn.
            // These values therefore face the model's back toward its wall.
            case NORTH -> 0.0F;
            case SOUTH -> 180.0F;
            case WEST -> -90.0F;
            case EAST -> 90.0F;
            default -> 0.0F;
        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 4,
                state -> state.setAndContinue(IDLE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
