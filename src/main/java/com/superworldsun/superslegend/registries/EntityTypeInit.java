package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.HeartEntity;
import com.superworldsun.superslegend.entities.LargeMagicJarEntity;
import com.superworldsun.superslegend.entities.MagicJarEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.*;
import com.superworldsun.superslegend.entities.projectiles.bombs.BombEntity;
import com.superworldsun.superslegend.entities.projectiles.magic.MasterSwordBeamEntity;
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

    public static final RegistryObject<EntityType<SilverArrowEntity>> SILVER_ARROW = ENTITY_TYPES.register("silver_arrow",
            () -> EntityType.Builder.<SilverArrowEntity>of(SilverArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("silver_arrow"));

    public static final RegistryObject<EntityType<MagicFireArrowEntity>> MAGIC_FIRE_ARROW = ENTITY_TYPES.register("magic_fire_arrow",
            () -> EntityType.Builder.<MagicFireArrowEntity>of(MagicFireArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("magic_fire_arrow"));

    public static final RegistryObject<EntityType<MagicIceArrowEntity>> MAGIC_ICE_ARROW = ENTITY_TYPES.register("magic_ice_arrow",
            () -> EntityType.Builder.<MagicIceArrowEntity>of(MagicIceArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("magic_ice_arrow"));

    public static final RegistryObject<EntityType<MagicLightArrowEntity>> MAGIC_LIGHT_ARROW = ENTITY_TYPES.register("magic_light_arrow",
            () -> EntityType.Builder.<MagicLightArrowEntity>of(MagicLightArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("magic_light_arrow"));

    //TODO Size of the master_sword_beam dosent actually affect what it interacts with
    public static final RegistryObject<EntityType<MasterSwordBeamEntity>> MASTERSWORD_SWORD_BEAM = ENTITY_TYPES.register("master_sword_beam",
            () -> EntityType.Builder.<MasterSwordBeamEntity>of(MasterSwordBeamEntity::new, MobCategory.MISC)
                    .sized(3F, 0.5F).build("master_sword_beam"));

    /*public static final RegistryObject<EntityType<AncientArrowEntity>> ICE_BEAM = ENTITY_TYPES.register("ice_beam",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("ice_beam"));*/

    public static final RegistryObject<EntityType<HeartEntity>> HEART = ENTITY_TYPES.register("heart",
            () -> EntityType.Builder.<HeartEntity>of(HeartEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("heart"));

    public static final RegistryObject<EntityType<MagicJarEntity>> MAGIC_JAR = ENTITY_TYPES.register("magic_jar",
            () -> EntityType.Builder.<MagicJarEntity>of(MagicJarEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("magic_jar"));

    public static final RegistryObject<EntityType<LargeMagicJarEntity>> LARGE_MAGIC_JAR = ENTITY_TYPES.register("large_magic_jar",
            () -> EntityType.Builder.<LargeMagicJarEntity>of(LargeMagicJarEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("large_magic_jar"));

    public static final RegistryObject<EntityType<BombEntity>> BOMB = ENTITY_TYPES.register("bomb",
            () -> EntityType.Builder.<BombEntity>of(BombEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("bomb"));

    public static final RegistryObject<EntityType<AncientArrowEntity>> WATER_BOMB = ENTITY_TYPES.register("water_bomb",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("water_bomb"));



    public static final RegistryObject<EntityType<AncientArrowEntity>> DEKU_SEED = ENTITY_TYPES.register("deku_seed",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("deku_seed"));
    public static final RegistryObject<EntityType<AncientArrowEntity>> WHEAT_SEED = ENTITY_TYPES.register("wheat_seed",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("wheat_seed"));
    public static final RegistryObject<EntityType<AncientArrowEntity>> BEETROOT_SEED = ENTITY_TYPES.register("beetroot_seed",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("beetroot_seed"));
    public static final RegistryObject<EntityType<AncientArrowEntity>> MELON_SEED = ENTITY_TYPES.register("melon_seed",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("melon_seed"));
    public static final RegistryObject<EntityType<AncientArrowEntity>> PUMPKIN_SEED = ENTITY_TYPES.register("pumpkin_seed",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("pumpkin_seed"));
    public static final RegistryObject<EntityType<AncientArrowEntity>> COCOA_BEAN = ENTITY_TYPES.register("cocoa_bean",
            () -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, MobCategory.MISC)
                    .sized(1F, 1F).build("cocoa_bean"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
