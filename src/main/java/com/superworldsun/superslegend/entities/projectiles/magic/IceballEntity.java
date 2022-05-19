package com.superworldsun.superslegend.entities.projectiles.magic;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

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
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceballEntity extends DamagingProjectileEntity
{
	// 5 seconds of max flight time
	protected int maxAge = 100;
	protected int age;
	
	public IceballEntity(Vector3d postition, Vector3d motion, World world, PlayerEntity owner)
	{
		super(EntityTypeInit.ICEBALL.get(), world);
		setPos(postition.x, postition.y, postition.z);
		setDeltaMovement(motion);
		setOwner(owner);
	}
	
	private IceballEntity(EntityType<? extends IceballEntity> entityType, World world)
	{
		super(entityType, world);
	}
	
	@Override
	protected void onHit(RayTraceResult rayTraceResult)
	{
		if (!level.isClientSide)
		{
			int particlesDensity = 40;
			// in ticks
			int slowDuration = 200;
			// 0 radius means no damage, only visual effects
			float explosionRadius = 0F;
			float particlesSpeed = 0.4F;
			float particlesSpread = 0.2F;
			float effectRadius = 4F;
			// 50% of blocks will be covered in snow
			float snowChance = 0.5F;
			float damage = 5F;
			level.explode(this, getX(), getY(), getZ(), explosionRadius, Explosion.Mode.NONE);
			
			for (int i = 0; i < particlesDensity; i++)
			{
				double particleX = getX() + (random.nextFloat() * 2 - 1) * particlesSpread;
				double particleY = getY() + (random.nextFloat() * 2 - 1) * particlesSpread;
				double particleZ = getZ() + (random.nextFloat() * 2 - 1) * particlesSpread;
				double particleMotionX = (random.nextFloat() * 2 - 1) * particlesSpeed;
				double particleMotionY = (random.nextFloat() * 2 - 1) * particlesSpeed;
				double particleMotionZ = (random.nextFloat() * 2 - 1) * particlesSpeed;
				IParticleData particle = ParticleTypes.SPIT;
				level.addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
			}
			
			// we want to only attack living entities
			Predicate<Entity> canHit = e -> e instanceof LivingEntity;
			Predicate<Entity> isInRadius = e -> distanceTo(e) <= effectRadius;
			List<Entity> entitiesInRadius = level.getEntities(this, getBoundingBox().inflate(effectRadius), canHit.and(isInRadius));
			entitiesInRadius.forEach(entity ->
			{
				entity.hurt(DamageSource.playerAttack(getOwner()), damage);
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, slowDuration));
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
							if (level.getFluidState(pos).is(FluidTags.WATER))
							{
								level.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
							}
							else if (level.getFluidState(pos).is(FluidTags.LAVA))
							{
								level.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
								level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);
							}
							else if (Blocks.SNOW.defaultBlockState().canSurvive(level, pos.above()) && level.getBlockState(pos.above()).is(Blocks.AIR) && random.nextFloat() < snowChance)
							{
								BlockState snowBlockState = Blocks.SNOW.defaultBlockState();
								level.setBlock(pos.above(), snowBlockState, 3);
							}
						}
					}
				}
			}
		}
		
		// removes iceball from the world to prevent multiple explosions
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
			IParticleData particle = ParticleTypes.SPIT;
			level.addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
		}
		
		super.tick();
	}
	
	@Override
	protected IParticleData getTrailParticle()
	{
		return ParticleTypes.SPIT;
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
	protected boolean shouldBurn()
	{
		return false;
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public static EntityType<IceballEntity> createEntityType()
	{
		return EntityType.Builder.<IceballEntity>of(IceballEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":iceball");
	}
}
