package com.superworldsun.superslegend.entities.ai;

import java.util.EnumSet;

import com.superworldsun.superslegend.interfaces.TameableEntity;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.monster.MonsterEntity;

public class SkeletonOwnerHurtByTargetGoal<T extends MonsterEntity & TameableEntity> extends TargetGoal
{
	private final T skeleton;
	private LivingEntity ownerLastHurtBy;
	private int timestamp;
	
	public SkeletonOwnerHurtByTargetGoal(T skeleton)
	{
		super(skeleton, false);
		this.skeleton = skeleton;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}
	
	public boolean canUse()
	{
		if (this.skeleton.hasOwner())
		{
			LivingEntity livingentity = this.skeleton.getOwner().get();
			if (livingentity == null)
			{
				return false;
			}
			else
			{
				this.ownerLastHurtBy = livingentity.getLastHurtByMob();
				int i = livingentity.getLastHurtByMobTimestamp();
				return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, EntityPredicate.DEFAULT);
			}
		}
		else
		{
			return false;
		}
	}
	
	public void start()
	{
		this.mob.setTarget(this.ownerLastHurtBy);
		LivingEntity livingentity = this.skeleton.getOwner().get();
		if (livingentity != null)
		{
			this.timestamp = livingentity.getLastHurtByMobTimestamp();
		}
		
		super.start();
	}
}
