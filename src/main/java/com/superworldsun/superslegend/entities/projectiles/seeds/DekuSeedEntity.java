package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class DekuSeedEntity extends SeedEntity {
	private static final float HITBOX_HEIGHT = 0.5F;
	private static final float HITBOX_WIDTH = 0.5F;
	private static final float WATER_INERTIA = 0.0F;

	public DekuSeedEntity(EntityType<? extends DekuSeedEntity> type, World world) {
		super(type, world);
	}

	public DekuSeedEntity(World worldIn) {
		super(EntityTypeInit.DEKU_SEED.get(), worldIn);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundInit.DEKU_SEED_HIT.get();
	}

	@Override
	protected float getWaterInertia() {
		return WATER_INERTIA;
	}

	@Override
	protected float getMass() {
		return 0.00F;
	}

	@Override
	protected float getFlightSpeed() {
		return 3F;
	}

	@Override
	public void tick()
	{
		super.tick();
		if (tickCount > 80)
		{
			this.remove();
		}
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(ItemInit.DEKU_SEEDS.get());
	}

	public static EntityType<DekuSeedEntity> createEntityType() {
		return EntityType.Builder.<DekuSeedEntity>of(DekuSeedEntity::new, EntityClassification.MISC)
				.sized(HITBOX_WIDTH, HITBOX_HEIGHT)
				.build(SupersLegendMain.MOD_ID + ":deku_seed");
	}
}