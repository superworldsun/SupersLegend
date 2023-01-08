package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class BeetrootSeedEntity extends SeedEntity {
	private static final float HITBOX_HEIGHT = 0.5F;
	private static final float HITBOX_WIDTH = 0.5F;

	public BeetrootSeedEntity(EntityType<? extends BeetrootSeedEntity> type, World world) {
		super(type, world);
	}

	public BeetrootSeedEntity(World worldIn) {
		super(EntityTypeInit.BEETROOT_SEED.get(), worldIn);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.BEETROOT_SEEDS);
	}

	public static EntityType<BeetrootSeedEntity> createEntityType() {
		return EntityType.Builder.<BeetrootSeedEntity>of(BeetrootSeedEntity::new, EntityClassification.MISC)
				.sized(HITBOX_WIDTH, HITBOX_HEIGHT)
				.build(SupersLegendMain.MOD_ID + ":beetroot_seed");
	}
}