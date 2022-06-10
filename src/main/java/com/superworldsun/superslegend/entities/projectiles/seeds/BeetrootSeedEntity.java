package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class BeetrootSeedEntity extends SeedEntity
{
	public BeetrootSeedEntity(EntityType<? extends BeetrootSeedEntity> type, World world)
	{
		super(type, world);
	}
	
	public BeetrootSeedEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.BEETROOT_SEED.get(), shooter, worldIn);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(Items.BEETROOT_SEEDS);
	}
	
	public static EntityType<BeetrootSeedEntity> createEntityType()
	{
		return EntityType.Builder.<BeetrootSeedEntity>of(BeetrootSeedEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":beetroot_seed");
	}
}