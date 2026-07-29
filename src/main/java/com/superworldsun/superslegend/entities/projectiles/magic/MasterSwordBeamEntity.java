package com.superworldsun.superslegend.entities.projectiles.magic;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.Comparator;

public class MasterSwordBeamEntity extends ThrowableProjectile implements GeoEntity
{
    private static final double AIR_INERTIA = 0.99D;
    private static final SoundEvent SPAWN_SOUND = SoundInit.SWORD_BEAM_LOOP.get(); // Your custom sound event
    private static final int LIFESPAN_TICKS = 40;
    private static final int SOUND_INTERVAL_TICKS = 5;
    private static final float DAMAGE_AMOUNT = 4.0f;
    private int ticksSinceLastSound = 0;
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public MasterSwordBeamEntity(EntityType<? extends Projectile> type, Level worldIn, LivingEntity player) {
        super((EntityType<? extends ThrowableProjectile>) type, worldIn);
        playSpawnSound();
        this.setOwner(player); // Set the entity's owner for ownership tracking
        this.setPos(player.getX(), player.getEyeY(), player.getZ()); // Set the initial position
        this.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5F, 1F); // Set the initial motion
        this.setYRot(player.getYRot());
        this.setXRot(player.getXRot());
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();

        /*// Apply scale
        ScaleData scaleData = ScaleTypes.BASE.getScaleData(this);
        scaleData.setScale(10.0f); // Set the scale as needed*/
    }

    public MasterSwordBeamEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super((EntityType<? extends ThrowableProjectile>) pEntityType, pLevel);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        playSpawnSound();
    }

    private void playSpawnSound() {
        if (!level().isClientSide) {
            level().playSound(null, this.getX(), this.getY(), this.getZ(), SPAWN_SOUND, SoundSource.PLAYERS, 0.4f, 1.0f);
        }
    }

    @Override
    protected float getGravity() {
        return 0f;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            DamageSource damageSource = getOwner() instanceof LivingEntity owner
                    ? level().damageSources().mobProjectile(this, owner)
                    : level().damageSources().generic();

            if (entity instanceof Mob mob) {
                boolean isWeakToLight = mob.getType().is(TagInit.WEAK_TO_LIGHT);
                if (isWeakToLight || mob.getMobType() == MobType.UNDEAD) {
                    mob.hurt(damageSource, DAMAGE_AMOUNT * 2);
                } else {
                    mob.hurt(damageSource, DAMAGE_AMOUNT);
                }
            } else {
                // Apply normal damage to non-Mob entities like Carts, Boats, etc.
                entity.hurt(damageSource, DAMAGE_AMOUNT);
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.discard();
    }

    @Override
    public void tick() {
        double speedBeforeMovement = getDeltaMovement().length();

        if (!level().isClientSide) {
            EntityHitResult wideHit = findWideEntityHit();
            if (wideHit != null && !ForgeEventFactory.onProjectileImpact(this, wideHit)) {
                onHit(wideHit);
                if (isRemoved()) {
                    return;
                }
            }
        }

        super.tick();

        // ThrowableProjectile applies 20% water drag instead of its normal 1% air drag.
        // Restore the speed it would have had in air while retaining its current direction.
        if (!isRemoved() && isTouchingWater() && speedBeforeMovement > 1.0E-7D) {
            Vec3 movement = getDeltaMovement();
            double currentSpeed = movement.length();
            if (currentSpeed > 1.0E-7D) {
                setDeltaMovement(movement.scale((speedBeforeMovement * AIR_INERTIA) / currentSpeed));
            }
        }

        updateTravelRotation();
        if (!this.level().isClientSide) {
            ticksSinceLastSound++;
            if (ticksSinceLastSound >= SOUND_INTERVAL_TICKS) {
                this.playSound(SoundInit.SWORD_BEAM_LOOP.get(), 0.4f, 1.0f);
                ticksSinceLastSound = 0;
            }
            if (this.tickCount >= LIFESPAN_TICKS) {
                this.discard();
            }
        }
    }

    private boolean isTouchingWater() {
        return isInWater() || level().getFluidState(blockPosition()).is(FluidTags.WATER);
    }

    private void updateTravelRotation() {
        Vec3 direction = getDeltaMovement();
        if (direction.lengthSqr() < 1.0E-7D) {
            return;
        }

        double horizontalDistance = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        float targetYaw = (float) (Mth.atan2(-direction.x, direction.z) * Mth.RAD_TO_DEG);
        float targetPitch = (float) (Mth.atan2(-direction.y, horizontalDistance) * Mth.RAD_TO_DEG);
        setYRot(targetYaw);
        setXRot(targetPitch);
    }

    /**
     * Vanilla throwable projectiles only use their bounding box to collect possible targets, then perform the
     * actual hit test with a thin ray through the projectile's center. Sweeping the complete bounding box here
     * makes the wide, short box shown by F3+B function as the beam's real entity hurtbox.
     */
    @Nullable
    private EntityHitResult findWideEntityHit() {
        Vec3 movement = getDeltaMovement();
        if (movement.lengthSqr() < 1.0E-7D) {
            return null;
        }

        AABB sweptHurtbox = getBoundingBox().expandTowards(movement);
        Vec3 start = position();
        Entity closestEntity = level().getEntities(this, sweptHurtbox, this::canHitEntity)
                .stream()
                .min(Comparator.comparingDouble(entity -> getTravelOrder(entity, start, movement)))
                .orElse(null);
        return closestEntity == null ? null : new EntityHitResult(closestEntity);
    }

    private static double getTravelOrder(Entity entity, Vec3 start, Vec3 movement) {
        Vec3 relativePosition = entity.getBoundingBox().getCenter().subtract(start);
        double progress = Math.max(0.0D, Math.min(1.0D, relativePosition.dot(movement) / movement.lengthSqr()));
        Vec3 closestPoint = start.add(movement.scale(progress));
        double perpendicularDistance = entity.getBoundingBox().getCenter().distanceToSqr(closestPoint);
        return progress + perpendicularDistance * 1.0E-6D;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller",0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("animation.master_sword_beam.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void defineSynchedData() {}
}
