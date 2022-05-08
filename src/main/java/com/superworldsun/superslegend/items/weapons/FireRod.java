package com.superworldsun.superslegend.items.weapons;

import java.util.function.Predicate;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.magic.FireballEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
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
				float fireballSpeed = 4F;
				Vector3d playerLookVec = playerEntity.getLookAngle();
				Vector3d fireballPosition = playerEntity.getEyePosition(1F).add(playerLookVec);
				Vector3d fireballMotion = playerLookVec.multiply(fireballSpeed, fireballSpeed, fireballSpeed);
				FireballEntity fireballEntity = new FireballEntity(fireballPosition, fireballMotion, world, playerEntity);
				world.addFreshEntity(fireballEntity);
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
			int particlesDensity = 2;
			int secondsOnFire = 3;
			float particlesSpread = 0.1F;
			float particlesSpeed = 1F;
			float effectRange = 8F;
			float damage = 1F;
			PlayerEntity player = (PlayerEntity) livingEntity;
			Vector3d playerLookVec = player.getLookAngle();
			Vector3d effectStart = player.getEyePosition(1F).add(playerLookVec);
			Vector3d effectEnd = effectStart.add(playerLookVec.multiply(effectRange, effectRange, effectRange));
			Vector3d particlesMotionVec = playerLookVec.multiply(particlesSpeed, particlesSpeed, particlesSpeed);
			
			for (int i = 0; i < particlesDensity; i++)
			{
				double particleX = effectStart.x + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread;
				double particleY = effectStart.y + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread;
				double particleZ = effectStart.z + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread;
				double particleMotionX = particlesMotionVec.x + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread / 5F;
				double particleMotionY = particlesMotionVec.y + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread / 5F;
				double particleMotionZ = particlesMotionVec.z + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread / 5F;
				world.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
			}
			
			RayTraceResult blockRayTraceResult = world.clip(new RayTraceContext(effectStart, effectEnd, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, null));
			
			if (blockRayTraceResult.getType() != RayTraceResult.Type.MISS)
			{
				// if we hit block, area of effect ends at the hit location
				effectEnd = blockRayTraceResult.getLocation();
			}
			
			// we want to only attack living entities
			Predicate<Entity> canHit = e -> e instanceof LivingEntity;
			EntityRayTraceResult entityRayTraceResult = ProjectileHelper.getEntityHitResult(world, player, effectStart, effectEnd, new AxisAlignedBB(effectStart, effectEnd).inflate(1.0D), canHit);
			
			// if we hit entity
			if (entityRayTraceResult != null)
			{
				DamageSource damageSource = DamageSource.playerAttack(player).setIsFire();
				entityRayTraceResult.getEntity().hurt(damageSource, damage);
				entityRayTraceResult.getEntity().setSecondsOnFire(secondsOnFire);
			}
		}
	}
}
