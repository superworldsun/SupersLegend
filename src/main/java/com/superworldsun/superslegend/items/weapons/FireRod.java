package com.superworldsun.superslegend.items.weapons;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireRod extends Item
{
	public FireRod()
	{
		super(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		if (playerEntity.isCrouching())
		{
			if (!world.isClientSide)
			{
				// spawn fireball
			}
		}
		else
		{
			playerEntity.startUsingItem(hand);
		}
		
		return ActionResult.consume(playerEntity.getItemInHand(hand));
	}
	
	@Override
	public UseAction getUseAnimation(ItemStack itemStack)
	{
		return UseAction.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack itemStack)
	{
		return 72000;
	}
	
	@Override
	public void onUseTick(World world, LivingEntity livingEntity, ItemStack itemStack, int timeInUse)
	{
		if (livingEntity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) livingEntity;			
			Vector3d playerLookVec = player.getLookAngle();
			Vector3d effectCenter = player.getEyePosition(1F).add(playerLookVec);
			AxisAlignedBB areaOfEffect = AxisAlignedBB.ofSize(0D, 0D, 0D).move(effectCenter).inflate(1D);
			float particlesSpeed = 0.2F;
			Vector3d particlesMotionVec = playerLookVec.multiply(particlesSpeed, particlesSpeed, particlesSpeed);
			int particlesDensity = 7;
			
			for (int i = 0; i < particlesDensity; i++)
			{
				double particleX = areaOfEffect.minX + player.getRandom().nextFloat() * areaOfEffect.getXsize();
				double particleY = areaOfEffect.minY + player.getRandom().nextFloat() * areaOfEffect.getYsize();
				double particleZ = areaOfEffect.minZ + player.getRandom().nextFloat() * areaOfEffect.getZsize();
				double particleMotionX = particlesMotionVec.x + player.getRandom().nextFloat() / 100F;
				double particleMotionY = particlesMotionVec.y + player.getRandom().nextFloat() / 100F;
				double particleMotionZ = particlesMotionVec.z + player.getRandom().nextFloat() / 100F;
				world.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
			}
			
			float range = 4F;
			areaOfEffect = areaOfEffect.expandTowards(playerLookVec.multiply(range, range, range));
			List<LivingEntity> affectedEntities = world.getEntitiesOfClass(LivingEntity.class, areaOfEffect, entity -> entity != player);
			
			affectedEntities.forEach(entity ->
			{
				DamageSource damageSource = DamageSource.playerAttack(player).setIsFire();
				entity.hurt(damageSource, 1F);
				entity.setSecondsOnFire(1);
			});
		}
	}
}
