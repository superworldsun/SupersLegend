package com.superworldsun.superslegend.entities.projectiles.bombs;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EntityBomb extends AbstractEntityBomb {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    private static final float SECONDS_TO_EXPLODE = 4.0f;
    private static final float SECONDS_TO_FLASH_RAPIDLY = 2.0f;
    private static final int EXPLOSION_POWER = 4;

    public EntityBomb(EntityType<EntityBomb> type, World world) {
        super(type, world, SECONDS_TO_EXPLODE, SECONDS_TO_FLASH_RAPIDLY, EXPLOSION_POWER);
    }

    public EntityBomb(LivingEntity shooter, World world) {
        super(EntityTypeInit.BOMB.get(), shooter, world, SECONDS_TO_EXPLODE, SECONDS_TO_FLASH_RAPIDLY, EXPLOSION_POWER);
    }
	
	public static EntityType<EntityBomb> createEntityType()
	{
		return EntityType.Builder.<EntityBomb>of(EntityBomb::new, EntityClassification.MISC).sized(0.25F, 0.25F).build(SupersLegendMain.MOD_ID + ":bomb");
	}
}
