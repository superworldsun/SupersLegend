package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.EntityPoisonArrow;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SupersLegendMain.MOD_ID);

    public static final RegistryObject<EntityType<EntityPoisonArrow>> POISON_ARROW = ENTITIES.register("poison_arrow",
            () -> EntityType.Builder.<EntityPoisonArrow>of(EntityPoisonArrow::new, EntityClassification.MISC).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));

    public static final RegistryObject<EntityType<EntityPoisonArrow>> FIRE_ARROW = ENTITIES.register("fire_arrow",
            () -> EntityType.Builder.<EntityPoisonArrow>of(EntityPoisonArrow::new, EntityClassification.MISC).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));

    public static final RegistryObject<EntityType<EntityPoisonArrow>> ICE_ARROW = ENTITIES.register("ice_arrow",
            () -> EntityType.Builder.<EntityPoisonArrow>of(EntityPoisonArrow::new, EntityClassification.MISC).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));

    public static final RegistryObject<EntityType<EntityPoisonArrow>> MAGIC_FIRE_ARROW = ENTITIES.register("magic_fire_arrow",
            () -> EntityType.Builder.<EntityPoisonArrow>of(EntityPoisonArrow::new, EntityClassification.MISC).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));

    public static final RegistryObject<EntityType<EntityPoisonArrow>> MAGIC_ICE_ARROW = ENTITIES.register("magic_ice_arrow",
            () -> EntityType.Builder.<EntityPoisonArrow>of(EntityPoisonArrow::new, EntityClassification.MISC).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));

    public static final RegistryObject<EntityType<EntityPoisonArrow>> LIGHT_ARROW = ENTITIES.register("light_arrow",
            () -> EntityType.Builder.<EntityPoisonArrow>of(EntityPoisonArrow::new, EntityClassification.MISC).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
}
