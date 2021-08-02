package com.superworldsun.superslegend.entities.projectiles.arrows;

import java.util.List;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import com.superworldsun.superslegend.util.BuildingHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceArrowEntity extends AbstractArrowEntity
{
	public IceArrowEntity(EntityType<? extends IceArrowEntity> type, World world)
	{
		super(type, world);
	}
	
	public IceArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.ICE_ARROW.get(), shooter, worldIn);
	}

	public IceArrowEntity(EntityType<? extends IceArrowEntity> type, World worldIn, LivingEntity shooter)
	{
		super(type, shooter, worldIn);
	}
	
	public IceArrowEntity(EntityType<? extends IceArrowEntity> type, World worldIn, double x, double y, double z)
	{
		super(type, x, y, z, worldIn);
	}
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		setBaseDamage(4.0D);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.ICE_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public void tick()
	{
		super.tick();

		if (!inGround)
		{
			level.addParticle(ParticleTypes.ITEM_SNOWBALL, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
			level.addParticle(ParticleTypes.SPIT, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
		}
		
	}
	
	@Override
	protected void onHitBlock(BlockRayTraceResult rayTraceResult)
	{
		BlockState blockHit = level.getBlockState(rayTraceResult.getBlockPos());
		
		if (blockHit.getMaterial() == Material.WATER)
		{
			List<BlockPos> platformShape = BuildingHelper.createRoundPlatformShape(rayTraceResult.getBlockPos(), 4);
			// We want to replace only water
			platformShape.removeIf(pos -> !level.getBlockState(pos).is(Blocks.WATER));
			platformShape.forEach(pos -> level.setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState()));
			remove();
		}
		else if (blockHit.is(Blocks.LAVA))
		{
			// If source block
			if (blockHit.getValue(FlowingFluidBlock.LEVEL) == 0)
			{
				level.setBlockAndUpdate(rayTraceResult.getBlockPos(), Blocks.OBSIDIAN.defaultBlockState());
			}
			else
			{
				level.setBlockAndUpdate(rayTraceResult.getBlockPos(), Blocks.COBBLESTONE.defaultBlockState());
			}
			
			remove();
		}
		else
		{
			BlockPos hitPos = rayTraceResult.getBlockPos().relative(rayTraceResult.getDirection());
			
			if (level.isEmptyBlock(hitPos))
			{
				level.setBlock(hitPos, Blocks.SNOW.defaultBlockState(), 11);
			}
			
			remove();
		}
		
		playSound(SoundInit.ARROW_HIT_ICE.get(), 1f, 1f);
		super.onHitBlock(rayTraceResult);
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResult)
	{
		Entity entity = rayTraceResult.getEntity();
		
		if (TagInit.WEAK_TO_ICE.contains(entity.getType()))
		{
			setBaseDamage(getBaseDamage() * 2);
		}
		
		if (TagInit.RESISTANT_TO_ICE.contains(entity.getType()))
		{
			setBaseDamage(getBaseDamage() / 2);
		}
		
		super.onHitEntity(rayTraceResult);
	}
	
	@Override
	protected void doPostHurtEffects(LivingEntity entity)
	{
		super.doPostHurtEffects(entity);
		playSound(SoundInit.ARROW_HIT_ICE.get(), 1f, 1f);
		// TODO: create actual FREEZE effect
		entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 70, 255));
	}
	
}
