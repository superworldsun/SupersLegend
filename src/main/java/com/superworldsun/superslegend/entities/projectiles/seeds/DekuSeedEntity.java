package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DekuSeedEntity extends SeedEntity
{
	public DekuSeedEntity(EntityType<? extends DekuSeedEntity> type, World world)
	{
		super(type, world);
	}
	
	public DekuSeedEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.PELLET.get(), shooter, worldIn);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.DEKU_SEEDS.get());
	}
	
	public static EntityType<DekuSeedEntity> createEntityType()
	{
		return EntityType.Builder.<DekuSeedEntity>of(DekuSeedEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":pellet");
	}
}