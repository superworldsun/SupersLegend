package com.superworldsun.superslegend.entities.projectiles.arrows;

import java.util.List;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.BuildingHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MagicFireArrowEntity extends FireArrowEntity
{
	public MagicFireArrowEntity(EntityType<? extends MagicFireArrowEntity> type, World world)
	{
		super(type, world);
	}
	
	public MagicFireArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.MAGIC_FIRE_ARROW.get(), worldIn, shooter);
	}
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		setBaseDamage(6.0D);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.MAGIC_FIRE_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResult)
	{
		super.onHitEntity(rayTraceResult);
		// There is a small time frame after an entity is hurt that gives
		// immunity to damage. And we already damaged it with common damage from
		// an arrow. To deal damage 2 times in a row, we have to reset it.
		rayTraceResult.getEntity().invulnerableTime = 0;
		rayTraceResult.getEntity().hurt(DamageSource.MAGIC, 5.0F);
	}
	
	@Override
	protected void onHitBlock(BlockRayTraceResult rayTraceResult)
	{
		BlockState blockHit = level.getBlockState(rayTraceResult.getBlockPos());
		
		if (blockHit.getMaterial() == Material.ICE || blockHit.getMaterial() == Material.ICE_SOLID)
		{
			List<BlockPos> platformShape = BuildingHelper.createRoundPlatformShape(rayTraceResult.getBlockPos(), 3);
			// We want to replace only ice
			platformShape.removeIf(pos -> level.getBlockState(pos).getMaterial() != Material.ICE && level.getBlockState(pos).getMaterial() != Material.ICE_SOLID);
			platformShape.forEach(pos -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()));
			remove();
		}
		
		playSound(SoundInit.ARROW_HIT_FIRE.get(), 1f, 1f);
		super.onHitBlock(rayTraceResult);
	}
}
