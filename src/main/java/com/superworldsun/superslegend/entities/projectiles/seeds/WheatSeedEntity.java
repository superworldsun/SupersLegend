package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WheatSeedEntity extends SeedEntity
{
	public WheatSeedEntity(EntityType<? extends WheatSeedEntity> type, World world)
	{
		super(type, world);
	}
	
	public WheatSeedEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.WHEAT_SEED.get(), shooter, worldIn);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent()
	{
		return SoundEvents.CROP_BREAK;
	}

	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		setBaseDamage(0.5D);
	}

	protected float getWaterInertia() {
		return 0.3F;
	}

	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(Items.WHEAT_SEEDS);
	}
	
	public static EntityType<WheatSeedEntity> createEntityType()
	{
		return EntityType.Builder.<WheatSeedEntity>of(WheatSeedEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":wheat_seed");
	}
}