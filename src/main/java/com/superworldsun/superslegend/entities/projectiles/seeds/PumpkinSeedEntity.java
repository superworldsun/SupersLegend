package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class PumpkinSeedEntity extends SeedEntity {
	private static final float HITBOX_HEIGHT = 0.5F;
	private static final float HITBOX_WIDTH = 0.5F;

	public PumpkinSeedEntity(EntityType<? extends PumpkinSeedEntity> type, World world) {
		super(type, world);
	}

	public PumpkinSeedEntity(World worldIn) {
		super(EntityTypeInit.PUMPKIN_SEED.get(), worldIn);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.PUMPKIN_SEEDS);
	}

	public static EntityType<PumpkinSeedEntity> createEntityType() {
		return EntityType.Builder.<PumpkinSeedEntity>of(PumpkinSeedEntity::new, EntityClassification.MISC)
				.sized(HITBOX_WIDTH, HITBOX_HEIGHT)
				.build(SupersLegendMain.MOD_ID + ":pumpkin_seed");
	}
}