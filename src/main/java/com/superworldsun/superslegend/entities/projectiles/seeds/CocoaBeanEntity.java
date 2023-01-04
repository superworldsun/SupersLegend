package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class CocoaBeanEntity extends SeedEntity {
	private static final float HITBOX_HEIGHT = 0.5F;
	private static final float HITBOX_WIDTH = 0.5F;

	public CocoaBeanEntity(EntityType<? extends CocoaBeanEntity> type, World world) {
		super(type, world);
	}

	public CocoaBeanEntity(World worldIn, LivingEntity shooter) {
		super(EntityTypeInit.COCOA_BEAN.get(), shooter, worldIn);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.COCOA_BEANS);
	}
	
	@Override
	protected float getMass() {
		return 0.15F;
	}

	public static EntityType<CocoaBeanEntity> createEntityType() {
		return EntityType.Builder.<CocoaBeanEntity>of(CocoaBeanEntity::new, EntityClassification.MISC)
				.sized(HITBOX_WIDTH, HITBOX_HEIGHT)
				.build(SupersLegendMain.MOD_ID + ":cocoa_bean");
	}
}