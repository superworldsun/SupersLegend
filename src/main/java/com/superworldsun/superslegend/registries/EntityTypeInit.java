package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.*;
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

    public static final RegistryObject<EntityType<IceArrowEntity>> ICE_ARROW = ENTITY_TYPES.register("ice_arrow",
            () -> EntityType.Builder.<IceArrowEntity>of(IceArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("ice_arrow"));

    public static final RegistryObject<EntityType<ShockArrowEntity>> SHOCK_ARROW = ENTITY_TYPES.register("shock_arrow",
            () -> EntityType.Builder.<ShockArrowEntity>of(ShockArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("shock_arrow"));

    public static final RegistryObject<EntityType<BombArrowEntity>> BOMB_ARROW = ENTITY_TYPES.register("bomb_arrow",
            () -> EntityType.Builder.<BombArrowEntity>of(BombArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("bomb_arrow"));

    public static final RegistryObject<EntityType<AncientArrowEntity>> ANCIENT_ARROW = ENTITY_TYPES.register("ancient_arrow",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("ancient_arrow"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
