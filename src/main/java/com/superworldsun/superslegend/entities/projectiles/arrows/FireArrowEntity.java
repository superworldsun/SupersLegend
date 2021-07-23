package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireArrowEntity extends AbstractArrowEntity
{
	public FireArrowEntity(EntityType<? extends FireArrowEntity> type, World world)
	{
		super(type, world);
	}
	
	public FireArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.FIRE_ARROW.get(), shooter, worldIn);
	}
	
	public FireArrowEntity(World worldIn, double x, double y, double z)
	{
		super(EntityTypeInit.FIRE_ARROW.get(), x, y, z, worldIn);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.FIRE_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResutl)
	{
		Entity entity = rayTraceResutl.getEntity();
		
		if (entity.isAlive())
		{
			entity.setSecondsOnFire(6);
		}
		
		if (TagInit.RESISTANT_TO_FIRE.contains(entity.getType()))
		{
			setBaseDamage(getBaseDamage() / 5f);
		}
		
		if (TagInit.WEAK_TO_FIRE.contains(entity.getType()))
		{
			setBaseDamage(getBaseDamage() * 2f);
		}
		
		super.onHitEntity(rayTraceResutl);
	}
	
	@Override
	protected void doPostHurtEffects(LivingEntity entity)
	{
		super.doPostHurtEffects(entity);
		playSound(SoundInit.ARROW_HIT_FIRE.get(), 1f, 1f);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		if (!this.isInWaterOrRain() && !this.inGround)
		{
			this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
		
		if (this.inGround)
		{
			if (!this.isInWaterOrRain() || !this.isInWater())
			{
				if (level.isEmptyBlock(this.blockPosition()))
					level.setBlock(this.blockPosition(), Blocks.FIRE.defaultBlockState(), 11);
				
				BlockPos currentPos = this.blockPosition();
				this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_FIRE.get(), SoundCategory.PLAYERS, 1f,
						1f);
				
				this.remove();
			}
			if (!this.isInWaterOrRain() || !this.isInWater())
			{
				if (level.isEmptyBlock(this.blockPosition().west()))
					level.setBlock(this.blockPosition().west(), Blocks.FIRE.defaultBlockState(), 11);
				
			}
			if (!this.isInWaterOrRain() || !this.isInWater())
			{
				if (level.isEmptyBlock(this.blockPosition().east()))
					level.setBlock(this.blockPosition().east(), Blocks.FIRE.defaultBlockState(), 11);
				
			}
			if (!this.isInWaterOrRain() || !this.isInWater())
			{
				if (level.isEmptyBlock(this.blockPosition().north()))
					level.setBlock(this.blockPosition().north(), Blocks.FIRE.defaultBlockState(), 11);
				
			}
			if (!this.isInWaterOrRain() || !this.isInWater())
			{
				if (level.isEmptyBlock(this.blockPosition().south()))
					level.setBlock(this.blockPosition().south(), Blocks.FIRE.defaultBlockState(), 11);
				
			}
		}
		
		if (this.isInWater())
		{
			BlockPos currentPos = this.blockPosition();
			this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 1f);
			this.remove();
		}
	}
	
	@Override
	public double getBaseDamage()
	{
		return 4.0D;
	}
}
