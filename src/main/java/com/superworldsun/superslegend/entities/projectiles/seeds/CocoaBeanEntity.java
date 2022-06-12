package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class CocoaBeanEntity extends SeedEntity
{
	public CocoaBeanEntity(EntityType<? extends CocoaBeanEntity> type, World world)
	{
		super(type, world);
	}
	
	public CocoaBeanEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.COCOA_BEAN.get(), shooter, worldIn);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(Items.COCOA_BEANS);
	}
	
	public static EntityType<CocoaBeanEntity> createEntityType()
	{
		return EntityType.Builder.<CocoaBeanEntity>of(CocoaBeanEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":cocoa_bean");
	}
}