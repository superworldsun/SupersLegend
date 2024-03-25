package com.superworldsun.superslegend.entities.projectiles.bombs;

import com.superworldsun.superslegend.items.armors.KokiriArmor;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class BombEntity extends AbstractBombEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    private static final float SECONDS_TO_EXPLODE = 4.0f;
    private static final float SECONDS_TO_FLASH_RAPIDLY = 2.0f;
    private static final int EXPLOSION_POWER = 4;
    private static final double BOUNCE_DAMPENING_FACTOR = 0.05;

    public BombEntity(EntityType<BombEntity> type, Level world) {
        super(type, world, SECONDS_TO_EXPLODE, SECONDS_TO_FLASH_RAPIDLY, EXPLOSION_POWER, BOUNCE_DAMPENING_FACTOR);
    }

    public BombEntity(LivingEntity shooter, Level world) {
        super(EntityTypeInit.BOMB.get(), shooter, world, SECONDS_TO_EXPLODE, SECONDS_TO_FLASH_RAPIDLY, EXPLOSION_POWER, BOUNCE_DAMPENING_FACTOR);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    controllerRegistrar.add(new AnimationController<>(this, "controller",0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}