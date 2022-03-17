package com.superworldsun.superslegend.entities.ai;

import java.util.EnumSet;

import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

public class FollowBremenMaskGoal extends Goal
{
	private static final EntityPredicate TEMP_TARGETING = (new EntityPredicate()).range(10.0D).allowInvulnerable().allowSameTeam().allowNonAttackable()
			.allowUnseeable();
	protected final CreatureEntity mob;
	private final double speedModifier;
	private double px;
	private double py;
	private double pz;
	private double pRotX;
	private double pRotY;
	protected PlayerEntity player;
	private int calmDown;
	private boolean isRunning;
	private final boolean canScare;
	
	public FollowBremenMaskGoal(CreatureEntity mob, double speed, boolean canScare)
	{
		this.mob = mob;
		this.speedModifier = speed;
		this.canScare = canScare;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		
		if (!(mob.getNavigation() instanceof GroundPathNavigator) && !(mob.getNavigation() instanceof FlyingPathNavigator))
		{
			throw new IllegalArgumentException("Unsupported mob type '" + mob.getType().toString() + "'");
		}
	}
	
	public boolean canUse()
	{
		if (this.calmDown > 0)
		{
			--this.calmDown;
			return false;
		}
		else
		{
			this.player = this.mob.level.getNearestPlayer(TEMP_TARGETING, this.mob);
			if (this.player == null)
			{
				return false;
			}
			else
			{
				return shouldFollow(player);
			}
		}
	}
	
	private boolean shouldFollow(PlayerEntity player)
	{
		ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_BREMANMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		if (!stack0.isEmpty())
		return stack0.getItem() == ItemInit.MASK_BREMANMASK.get() && ((IMaskAbility) stack0.getItem()).isPlayerUsingAbility(player);
		return false;
	}
	
	public boolean canContinueToUse()
	{
		if (this.canScare())
		{
			if (this.mob.distanceToSqr(this.player) < 36.0D)
			{
				if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D)
				{
					return false;
				}
				
				if (Math.abs((double) this.player.xRot - this.pRotX) > 5.0D || Math.abs((double) this.player.yRot - this.pRotY) > 5.0D)
				{
					return false;
				}
			}
			else
			{
				this.px = this.player.getX();
				this.py = this.player.getY();
				this.pz = this.player.getZ();
			}
			
			this.pRotX = (double) this.player.xRot;
			this.pRotY = (double) this.player.yRot;
		}
		
		return this.canUse();
	}
	
	protected boolean canScare()
	{
		return this.canScare;
	}
	
	public void start()
	{
		this.px = this.player.getX();
		this.py = this.player.getY();
		this.pz = this.player.getZ();
		this.isRunning = true;
	}
	
	public void stop()
	{
		this.player = null;
		this.mob.getNavigation().stop();
		this.calmDown = 100;
		this.isRunning = false;
	}
	
	public void tick()
	{
		this.mob.getLookControl().setLookAt(this.player, (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());
		if (this.mob.distanceToSqr(this.player) < 6.25D)
		{
			this.mob.getNavigation().stop();
		}
		else
		{
			this.mob.getNavigation().moveTo(this.player, this.speedModifier);
		}
		
	}
	
	public boolean isRunning()
	{
		return this.isRunning;
	}
}
