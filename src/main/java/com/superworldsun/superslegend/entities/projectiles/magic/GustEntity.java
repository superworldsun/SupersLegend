package com.superworldsun.superslegend.entities.projectiles.magic;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class GustEntity extends DamagingProjectileEntity
{
	// 1.5 seconds of max flight time
	protected int maxAge = 30;
	protected int age;
	
	public GustEntity(Vector3d postition, Vector3d motion, World world, PlayerEntity owner)
	{
		super(EntityTypeInit.GUST.get(), world);
		setPos(postition.x, postition.y, postition.z);
		setDeltaMovement(motion);
		setOwner(owner);
	}
	
	private GustEntity(EntityType<? extends GustEntity> entityType, World world)
	{
		super(entityType, world);
	}
	
	@Override
	protected void onHitBlock(BlockRayTraceResult rayTraceResult)
	{
		super.onHitBlock(rayTraceResult);
		remove();
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResult)
	{
		Entity hitEntity = rayTraceResult.getEntity();
		hitEntity.setDeltaMovement(hitEntity.getDeltaMovement().add(getDeltaMovement().multiply(1.1D, 1.1D, 1.1D)));
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
			IParticleData particle = ParticleTypes.CLOUD;
			level.addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
		}
		
		super.tick();
	}
	
	@Override
	protected IParticleData getTrailParticle()
	{
		return ParticleTypes.CLOUD;
	}
	
	@Override
	public boolean hurt(DamageSource damageSource, float damage)
	{
		return false;
	}
	
	@Override
	protected boolean shouldBurn()
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
	
	public static EntityType<GustEntity> createEntityType()
	{
		return EntityType.Builder.<GustEntity>of(GustEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":gust");
	}
}
