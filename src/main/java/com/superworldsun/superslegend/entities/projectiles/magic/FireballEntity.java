package com.superworldsun.superslegend.entities.projectiles.magic;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireballEntity extends DamagingProjectileEntity
{
	// 5 seconds of max flight time
	protected int maxAge = 100;
	protected int age;
	
	public FireballEntity(Vector3d postition, Vector3d motion, World world, PlayerEntity owner)
	{
		super(EntityTypeInit.FIREBALL.get(), world);
		setPos(postition.x, postition.y, postition.z);
		setDeltaMovement(motion);
		setOwner(owner);
	}
	
	private FireballEntity(EntityType<? extends FireballEntity> entityType, World world)
	{
		super(entityType, world);
	}
	
	@Override
	protected void onHit(RayTraceResult rayTraceResult)
	{
		if (!level.isClientSide)
		{
			int particlesDensity = 40;
			int secondsOnFire = 3;
			// 0 radius means no damage, only visual effects
			float explosionRadius = 0F;
			float particlesSpeed = 0.4F;
			float particlesSpread = 0.2F;
			float effectRadius = 4F;
			// 50% of blocks will be set on fire
			float fireChance = 0.5F;
			level.explode(this, getX(), getY(), getZ(), explosionRadius, Explosion.Mode.NONE);
			
			for (int i = 0; i < particlesDensity; i++)
			{
				double particleX = getX() + (random.nextFloat() * 2 - 1) * particlesSpread;
				double particleY = getY() + (random.nextFloat() * 2 - 1) * particlesSpread;
				double particleZ = getZ() + (random.nextFloat() * 2 - 1) * particlesSpread;
				double particleMotionX = (random.nextFloat() * 2 - 1) * particlesSpeed;
				double particleMotionY = (random.nextFloat() * 2 - 1) * particlesSpeed;
				double particleMotionZ = (random.nextFloat() * 2 - 1) * particlesSpeed;
				IParticleData particle = random.nextBoolean() ? ParticleTypes.FLAME : ParticleTypes.SMOKE;
				level.addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
			}
			
			// we want to only attack living entities
			Predicate<Entity> canHit = e -> e instanceof LivingEntity;
			Predicate<Entity> isInRadius = e -> distanceTo(e) <= effectRadius;
			List<Entity> entitiesInRadius = level.getEntities(this, getBoundingBox().inflate(effectRadius), canHit.and(isInRadius));
			entitiesInRadius.forEach(entity ->
			{
				entity.setSecondsOnFire(secondsOnFire);
			});
			
			// here we are searching for blocks in radius
			for (int x = (int) -effectRadius; x <= effectRadius; x++)
			{
				for (int y = (int) -effectRadius; y <= effectRadius; y++)
				{
					for (int z = (int) -effectRadius; z <= effectRadius; z++)
					{
						BlockPos pos = blockPosition().north(x).above(y).east(z);
						
						// if the block is in radius
						if (blockPosition().distSqr(pos) <= effectRadius * effectRadius)
						{
							if (level.getBlockState(pos).is(TagInit.CAN_MELT))
							{
								// replaces meltable blocks with air
								level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
							}
							else if (level.getBlockState(pos).isCollisionShapeFullBlock(level, pos) && level.getBlockState(pos.above()).is(Blocks.AIR) && random.nextFloat() < fireChance)
							{
								// sets other blocks on fire
								if (AbstractFireBlock.canBePlacedAt(level, pos.above(), Direction.UP))
								{
									BlockState fireBlockState = AbstractFireBlock.getState(level, pos.above());
									level.setBlock(pos.above(), fireBlockState, 11);
								}
							}
						}
					}
				}
			}
		}
		
		// removes fireball from the world to prevent multiple explosions
		remove();
	}
	
	@Override
	protected float getInertia()
	{
		return 1F;
	}
	
	@Override
	public void tick()
	{
		if (age > maxAge)
		{
			remove();
			return;
		}
		
		age++;
		
		int particlesDensity = 3;
		float particlesSpeed = 0.1F;
		float particlesSpread = 0.2F;
		
		for (int i = 0; i < particlesDensity; i++)
		{
			double particleX = getX() + (random.nextFloat() * 2 - 1) * particlesSpread;
			double particleY = getY() + (random.nextFloat() * 2 - 1) * particlesSpread;
			double particleZ = getZ() + (random.nextFloat() * 2 - 1) * particlesSpread;
			double particleMotionX = (random.nextFloat() * 2 - 1) * particlesSpeed;
			double particleMotionY = (random.nextFloat() * 2 - 1) * particlesSpeed;
			double particleMotionZ = (random.nextFloat() * 2 - 1) * particlesSpeed;
			IParticleData particle = random.nextBoolean() ? ParticleTypes.FLAME : ParticleTypes.SMOKE;
			level.addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
		}
		
		super.tick();
	}
	
	@Override
	public boolean hurt(DamageSource damageSource, float damage)
	{
		return false;
	}
	
	@Nullable
	@Override
	public PlayerEntity getOwner()
	{
		return (PlayerEntity) super.getOwner();
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public static EntityType<FireballEntity> createEntityType()
	{
		return EntityType.Builder.<FireballEntity>of(FireballEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":fireball");
	}
}
