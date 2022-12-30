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
import net.minecraft.world.World;

public class WheatSeedEntity extends SeedEntity {
	private static final float HITBOX_HEIGHT = 0.5F;
	private static final float HITBOX_WIDTH = 0.5F;
	private static final float WATER_INERTIA = 0.3F;
	private static final double BASE_DAMAGE = 0.5D;

	public WheatSeedEntity(EntityType<? extends WheatSeedEntity> type, World world) {
		super(type, world);
	}

	public WheatSeedEntity(World worldIn, LivingEntity shooter) {
		super(EntityTypeInit.WHEAT_SEED.get(), shooter, worldIn);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.CROP_BREAK;
	}

	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();
		setBaseDamage(BASE_DAMAGE);
	}

	protected float getWaterInertia() {
		return WATER_INERTIA;
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.WHEAT_SEEDS);
	}

	public static EntityType<WheatSeedEntity> createEntityType() {
		return EntityType.Builder.<WheatSeedEntity>of(WheatSeedEntity::new, EntityClassification.MISC)
				.sized(HITBOX_WIDTH, HITBOX_HEIGHT)
				.build(SupersLegendMain.MOD_ID + ":wheat_seed");
	}
}