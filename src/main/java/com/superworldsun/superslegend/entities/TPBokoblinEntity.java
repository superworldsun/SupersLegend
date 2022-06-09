package com.superworldsun.superslegend.entities;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD)
public class TPBokoblinEntity extends CreatureEntity
{
	public TPBokoblinEntity(EntityType<TPBokoblinEntity> type, World world)
	{
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setAttributes()
	{
		return LivingEntity.createLivingAttributes()
				.add(Attributes.FOLLOW_RANGE, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0f)
				.add(Attributes.ATTACK_KNOCKBACK, 3.0f)
				.add(Attributes.MAX_HEALTH, 20.0f)
				.add(Attributes.ATTACK_SPEED, 2.0)
				.add(Attributes.MOVEMENT_SPEED, 0.3f);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes()
	{
		return MobEntity.createMobAttributes()
				.add(Attributes.FOLLOW_RANGE, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0f)
				.add(Attributes.ATTACK_KNOCKBACK, 3.0f)
				.add(Attributes.MAX_HEALTH, 20.0f)
				.add(Attributes.ATTACK_SPEED, 2.0)
				.add(Attributes.MOVEMENT_SPEED, 0.3f);
	}
	
	protected void registerGoals()
	{
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}
	
	// protected void registerGoals()
	// {
	// super.goalSelector.addGoal(1, new LookRandomlyGoal(this));
	// super.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8f));
	// this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	// }
	
	@Override
	protected int getExperienceReward(PlayerEntity player)
	{
		return 10;
	}
	
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_)
	{
		return SoundEvents.HUSK_HURT;
	}
	
	@Nullable
	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundInit.SLINGSHOT_SHOOT.get();
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.HUSK_AMBIENT;
	}
	
	protected SoundEvent getStepSound()
	{
		return SoundEvents.HUSK_STEP;
	}
	
	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_)
	{
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}
	
	public CreatureAttribute getMobType()
	{
		return CreatureAttribute.UNDEFINED;
	}
	
	@SubscribeEvent
	public static void addEntityAttributes(EntityAttributeCreationEvent event)
	{
		event.put(EntityTypeInit.TP_BOKOBLIN.get(), TPBokoblinEntity.setCustomAttributes().build());
	}
	
	public static EntityType<TPBokoblinEntity> createEntityType()
	{
		return EntityType.Builder.<TPBokoblinEntity>of(TPBokoblinEntity::new, EntityClassification.MONSTER).sized(1.0F, 3.0F).build(SupersLegendMain.MOD_ID + ":tp_bokoblin");
	}
}
