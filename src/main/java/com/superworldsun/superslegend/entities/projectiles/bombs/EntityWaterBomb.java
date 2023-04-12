package com.superworldsun.superslegend.entities.projectiles.bombs;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityWaterBomb extends AbstractEntityWaterBomb {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    private static final float SECONDS_TO_EXPLODE = 4.0f;
    private static final float SECONDS_TO_FLASH_RAPIDLY = 2.0f;
    private static final int EXPLOSION_POWER = 4;
    private static final double BOUNCE_DAMPENING_FACTOR = 0.05;

    public EntityWaterBomb(EntityType<EntityWaterBomb> type, World world) {
        super(type, world, SECONDS_TO_EXPLODE, SECONDS_TO_FLASH_RAPIDLY, EXPLOSION_POWER, BOUNCE_DAMPENING_FACTOR);
    }

    public EntityWaterBomb(LivingEntity shooter, World world) {
        super(EntityTypeInit.WATER_BOMB.get(), shooter, world, SECONDS_TO_EXPLODE, SECONDS_TO_FLASH_RAPIDLY, EXPLOSION_POWER, BOUNCE_DAMPENING_FACTOR);
    }
	
	public static EntityType<EntityWaterBomb> createEntityType()
	{
		return EntityType.Builder.<EntityWaterBomb>of(EntityWaterBomb::new, EntityClassification.MISC).sized(0.25F, 0.25F).build(SupersLegendMain.MOD_ID + ":water_bomb");
	}

    //TODO Turn this back on when item is put back in
    @Override
    protected Item getDefaultItem() {
        return ItemInit.WATER_BOMB.get();
    }
}
