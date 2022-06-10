package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class PumpkinSeedEntity extends SeedEntity
{
	public PumpkinSeedEntity(EntityType<? extends PumpkinSeedEntity> type, World world)
	{
		super(type, world);
	}
	
	public PumpkinSeedEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.PUMPKIN_SEED.get(), shooter, worldIn);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(Items.PUMPKIN_SEEDS);
	}
	
	public static EntityType<PumpkinSeedEntity> createEntityType()
	{
		return EntityType.Builder.<PumpkinSeedEntity>of(PumpkinSeedEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":pumpkin_seed");
	}
}