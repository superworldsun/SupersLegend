package com.superworldsun.superslegend.entities.projectiles.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DekuSeedEntity extends SeedEntity {
    private static final float HITBOX_HEIGHT = 0.5f;
    private static final float HITBOX_WIDTH = 0.5f;
    private static final float WATER_INERTIA = 0f;
    private static final float FLIGHT_SPEED = 2.55f;

    public DekuSeedEntity(EntityType<? extends DekuSeedEntity> type, Level level) {
        super(type, level);
    }

    public DekuSeedEntity(Level worldIn) {
        super(EntityTypeInit.DEKU_SEED.get(), worldIn);
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundInit.DEKU_SEED_HIT.get();
    }

    @Override
    protected float getWaterInertia() {
        return WATER_INERTIA;
    }

    @Override
    protected float getMass() {
        return 0f;
    }

    @Override
    protected float getFlightSpeed() {
        return FLIGHT_SPEED;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (tickCount > 80)
        {
            this.discard();
        }
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(ItemInit.DEKU_SEEDS.get());
    }

    public static EntityType<DekuSeedEntity> createEntityType() {
        return EntityType.Builder.<DekuSeedEntity>of(DekuSeedEntity::new, MobCategory.MISC)
                .sized(HITBOX_WIDTH, HITBOX_HEIGHT)
                .updateInterval(1)
                .build(SupersLegendMain.MOD_ID + ":deku_seed");
    }
}
