package com.superworldsun.superslegend.mixin;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.superworldsun.superslegend.entities.ai.FollowSkeletonOwnerGoal;
import com.superworldsun.superslegend.entities.ai.SkeletonOwnerHurtByTargetGoal;
import com.superworldsun.superslegend.entities.ai.SkeletonOwnerHurtTargetGoal;
import com.superworldsun.superslegend.interfaces.ITameableSkeleton;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;

@Mixin(AbstractSkeletonEntity.class)
public class MixinAbstractSkeletonEntity extends MonsterEntity implements ITameableSkeleton
{
	private static final DataParameter<Boolean> TAMED = EntityDataManager.defineId(AbstractSkeletonEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Optional<UUID>> OWNERUUID = EntityDataManager.defineId(AbstractSkeletonEntity.class, DataSerializers.OPTIONAL_UUID);
	
	// This constructor is fake and never used
	protected MixinAbstractSkeletonEntity()
	{
		super(null, null);
	}
	
	@Inject(method = "registerGoals", at = @At("HEAD"))
	private void injectRegisterGoals(CallbackInfo ci)
	{
		goalSelector.addGoal(4, new FollowSkeletonOwnerGoal<>(this, 1.0D, 10.0F, 2.0F));
		targetSelector.addGoal(2, new SkeletonOwnerHurtByTargetGoal<>(this));
		targetSelector.addGoal(3, new SkeletonOwnerHurtTargetGoal<>(this));
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		entityData.define(TAMED, false);
		entityData.define(OWNERUUID, Optional.empty());
	}
	
	@Override
	public boolean canAttack(LivingEntity entity)
	{
		return entity == getOwner() ? false : super.canAttack(entity);
	}
	
	@Override
	public Team getTeam()
	{
		if (hasOwner())
		{
			return getOwner().getTeam();
		}
		
		return super.getTeam();
	}
	
	@Override
	public boolean isAlliedTo(Entity entity)
	{
		if (hasOwner())
		{
			LivingEntity owner = getOwner();
			return entity == owner || entity.isAlliedTo(owner);
		}
		
		return super.isAlliedTo(entity);
	}
	
	@Override
	public LivingEntity getOwner()
	{
		UUID uuid = getOwnerUniqueId();
		return uuid == null ? null : level.getPlayerByUUID(uuid);
	}
	
	@Override
	public void setOwner(LivingEntity owner)
	{
		setOwnerUUID(owner == null ? null : owner.getUUID());
	}
	
	@Override
	public UUID getOwnerUniqueId()
	{
		return entityData.get(OWNERUUID).orElse(null);
	}
	
	@Override
	public boolean hasOwner()
	{
		return getOwner() != null;
	}
	
	private void setOwnerUUID(@Nullable UUID id)
	{
		entityData.set(OWNERUUID, Optional.ofNullable(id));
	}
}
