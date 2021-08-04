package com.superworldsun.superslegend.entities.ai;

import java.util.EnumSet;

import com.superworldsun.superslegend.interfaces.ITameableSkeleton;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.monster.MonsterEntity;

public class SkeletonOwnerHurtTargetGoal<T extends MonsterEntity & ITameableSkeleton> extends TargetGoal
{
	private final T skeleton;
	private LivingEntity ownerLastHurt;
	private int timestamp;
	
	public SkeletonOwnerHurtTargetGoal(T skeleton)
	{
		super(skeleton, false);
		this.skeleton = skeleton;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}
	
	public boolean canUse()
	{
		if (this.skeleton.hasOwner())
		{
			LivingEntity livingentity = this.skeleton.getOwner();
			if (livingentity == null)
			{
				return false;
			}
			else
			{
				this.ownerLastHurt = livingentity.getLastHurtMob();
				int i = livingentity.getLastHurtMobTimestamp();
				return i != this.timestamp && this.canAttack(this.ownerLastHurt, EntityPredicate.DEFAULT);
			}
		}
		else
		{
			return false;
		}
	}
	
	public void start()
	{
		this.mob.setTarget(this.ownerLastHurt);
		LivingEntity livingentity = this.skeleton.getOwner();
		if (livingentity != null)
		{
			this.timestamp = livingentity.getLastHurtMobTimestamp();
		}
		
		super.start();
	}
}
