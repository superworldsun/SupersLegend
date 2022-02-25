package com.superworldsun.superslegend.entities;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TPBokoblinEntity<T extends MobEntity> extends MobEntity {
    public TPBokoblinEntity(EntityType<? extends MobEntity> Type, World worldIn) {
        super(Type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0f)
                .add(Attributes.MAX_HEALTH, 20.0f)
                .add(Attributes.ATTACK_SPEED, 2.0)
                .add(Attributes.MOVEMENT_SPEED, 5.0f);
    }

    protected void registerGoals()
    {
        super.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        super.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8f));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected int getExperienceReward(PlayerEntity player) {
        return 10;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundInit.RUPEE_GREEN.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SLINGSHOT_SHOOT.get();
    }
}
