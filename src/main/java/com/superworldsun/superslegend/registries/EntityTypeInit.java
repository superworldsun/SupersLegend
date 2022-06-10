package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.*;
import com.superworldsun.superslegend.entities.projectiles.arrows.*;
import com.superworldsun.superslegend.entities.projectiles.bombs.*;
import com.superworldsun.superslegend.entities.projectiles.boomerang.*;
import com.superworldsun.superslegend.entities.projectiles.hooks.*;
import com.superworldsun.superslegend.entities.projectiles.magic.*;
import com.superworldsun.superslegend.entities.projectiles.mastersword.*;
import com.superworldsun.superslegend.entities.projectiles.seeds.*;

import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit
{
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<EntityType<FireArrowEntity>> FIRE_ARROW = ENTITIES.register("fire_arrow", FireArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<IceArrowEntity>> ICE_ARROW = ENTITIES.register("ice_arrow", IceArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<ShockArrowEntity>> SHOCK_ARROW = ENTITIES.register("shock_arrow", ShockArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<BombArrowEntity>> BOMB_ARROW = ENTITIES.register("bomb_arrow", BombArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<AncientArrowEntity>> ANCIENT_ARROW = ENTITIES.register("ancient_arrow", AncientArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<SilverArrowEntity>> SILVER_ARROW = ENTITIES.register("silver_arrow", SilverArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<MagicFireArrowEntity>> MAGIC_FIRE_ARROW = ENTITIES.register("magic_fire_arrow", MagicFireArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<MagicIceArrowEntity>> MAGIC_ICE_ARROW = ENTITIES.register("magic_ice_arrow", MagicIceArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<MagicLightArrowEntity>> MAGIC_LIGHT_ARROW = ENTITIES.register("magic_light_arrow", MagicLightArrowEntity::createEntityType);
	public static final RegistryObject<EntityType<DekuSeedEntity>> DEKU_SEED = ENTITIES.register("deku_seed", DekuSeedEntity::createEntityType);
	public static final RegistryObject<EntityType<IceBeamEntity>> ICE_BEAM = ENTITIES.register("ice_beam", IceBeamEntity::createEntityType);
	public static final RegistryObject<EntityType<HookshotEntity>> HOOKSHOT_ENTITY = ENTITIES.register("hookshot", HookshotEntity::createEntityType);
	public static final RegistryObject<EntityType<BoomerangEntity>> REGULAR_BOOMERANG = ENTITIES.register("boomerang", RegularBoomerang::createEntityType);
	public static final RegistryObject<EntityType<MagicBoomerangEntity>> MAGIC_BOOMERANG = ENTITIES.register("magic_boomerang", MagicBoomerang::createEntityType);
	public static final RegistryObject<EntityType<WWBoomerangEntity>> WW_BOOMERANG = ENTITIES.register("ww_boomerang", WWBoomerang::createEntityType);
	public static final RegistryObject<EntityType<GaleBoomerangEntity>> GALE_BOOMERANG = ENTITIES.register("gale_boomerang", GaleBoomerang::createEntityType);
	public static final RegistryObject<EntityType<MasterSwordSwordEntity>> MASTERSWORD_SWORD_ENTITY = ENTITIES.register("mastersword_sword", MasterSwordSwordEntity::createEntityType);
	public static final RegistryObject<EntityType<LongshotEntity>> LONGSHOT_ENTITY = ENTITIES.register("longshot", LongshotEntity::createEntityType);
	public static final RegistryObject<EntityType<ClawshotEntity>> CLAWSHOT_ENTITY = ENTITIES.register("clawshot", ClawshotEntity::createEntityType);
	public static final RegistryObject<EntityType<SpinnerEntity>> SPINNER = ENTITIES.register("spinner", SpinnerEntity::createEntityType);
	public static final RegistryObject<EntityType<HeartEntity>> HEART = ENTITIES.register("heart", HeartEntity::createEntityType);
	public static final RegistryObject<EntityType<MagicJarEntity>> MAGIC_JAR = ENTITIES.register("magic_jar", MagicJarEntity::createEntityType);
	public static final RegistryObject<EntityType<LargeMagicJarEntity>> LARGE_MAGIC_JAR = ENTITIES.register("large_magic_jar", LargeMagicJarEntity::createEntityType);
	public static final RegistryObject<EntityType<EntityBomb>> BOMB = ENTITIES.register("bomb", EntityBomb::createEntityType);
	public static final RegistryObject<EntityType<FaroresWindEntity>> FARORES_WIND = ENTITIES.register("farores_wind", FaroresWindEntity::createEntityType);
	public static final RegistryObject<EntityType<FireballEntity>> FIREBALL = ENTITIES.register("fireball", FireballEntity::createEntityType);
	public static final RegistryObject<EntityType<IceballEntity>> ICEBALL = ENTITIES.register("iceball", IceballEntity::createEntityType);
	
	// MOBS
	
	public static final RegistryObject<EntityType<TPBokoblinEntity>> TP_BOKOBLIN = ENTITIES.register("tp_bokoblin", TPBokoblinEntity::createEntityType);
}
