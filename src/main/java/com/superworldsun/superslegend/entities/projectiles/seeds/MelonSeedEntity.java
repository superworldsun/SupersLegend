package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class MelonSeedEntity extends SeedEntity {
	private static final float HITBOX_HEIGHT = 0.5F;
	private static final float HITBOX_WIDTH = 0.5F;

	public MelonSeedEntity(EntityType<? extends MelonSeedEntity> type, World world) {
		super(type, world);
	}

	public MelonSeedEntity(World worldIn) {
		super(EntityTypeInit.MELON_SEED.get(), worldIn);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.MELON_SEEDS);
	}

	public static EntityType<MelonSeedEntity> createEntityType() {
		return EntityType.Builder.<MelonSeedEntity>of(MelonSeedEntity::new, EntityClassification.MISC)
				.sized(HITBOX_WIDTH, HITBOX_HEIGHT)
				.build(SupersLegendMain.MOD_ID + ":melon_seed");
	}
}