package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class EntityTypeInit
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SupersLegendMain.MOD_ID);

    public static final RegistryObject<EntityType<FireArrowEntity>> FIRE_ARROW = ENTITY_TYPES.register("fire_arrow",
            () -> EntityType.Builder.<FireArrowEntity>of(FireArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("fire_arrow"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
