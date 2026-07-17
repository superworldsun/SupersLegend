package com.superworldsun.superslegend.entities.projectiles.magic;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class MasterSwordBeamEntity extends ThrowableProjectile implements GeoEntity
{
    private static final SoundEvent SPAWN_SOUND = SoundInit.SWORD_BEAM_LOOP.get(); // Your custom sound event
    private static final int LIFESPAN_TICKS = 40;
    private static final int SOUND_INTERVAL_TICKS = 5;
    private static final float DAMAGE_AMOUNT = 4.0f;
    private int ticksSinceLastSound = 0;
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    //TODO, currently the size of the projectile cant be higher than the default (1f, 1f), find a way to make it wider and shorter.
    // Will probably have to change the type of Entity type it is, low prio
    public MasterSwordBeamEntity(EntityType<? extends Projectile> type, Level worldIn, LivingEntity player) {
        super((EntityType<? extends ThrowableProjectile>) type, worldIn);
        playSpawnSound();
        this.isNoGravity();
        this.setOwner(player); // Set the entity's owner for ownership tracking
        this.setPos(player.getX(), player.getEyeY(), player.getZ()); // Set the initial position
        this.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5F, 1F); // Set the initial motion

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
        super.tick();
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