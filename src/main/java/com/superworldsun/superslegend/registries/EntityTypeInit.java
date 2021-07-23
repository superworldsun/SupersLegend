package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.AncientArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.BombArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.IceArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.IceBeamEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicFireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicIceArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicLightArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.PoisonArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.ShockArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.SilverArrowEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit
{
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<EntityType<PoisonArrowEntity>> POISON_ARROW = ENTITIES.register("poison_arrow",
			() -> EntityType.Builder.<PoisonArrowEntity>of(PoisonArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<FireArrowEntity>> FIRE_ARROW = ENTITIES.register("fire_arrow",
			() -> EntityType.Builder.<FireArrowEntity>of(FireArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<IceArrowEntity>> ICE_ARROW = ENTITIES.register("ice_arrow",
			() -> EntityType.Builder.<IceArrowEntity>of(IceArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<ShockArrowEntity>> SHOCK_ARROW = ENTITIES.register("shock_arrow",
			() -> EntityType.Builder.<ShockArrowEntity>of(ShockArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<BombArrowEntity>> BOMB_ARROW = ENTITIES.register("bomb_arrow",
			() -> EntityType.Builder.<BombArrowEntity>of(BombArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<AncientArrowEntity>> ANCIENT_ARROW = ENTITIES.register("ancient_arrow",
			() -> EntityType.Builder.<AncientArrowEntity>of(AncientArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<SilverArrowEntity>> SILVER_ARROW = ENTITIES.register("silver_arrow",
			() -> EntityType.Builder.<SilverArrowEntity>of(SilverArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<IceBeamEntity>> ICE_BEAM = ENTITIES.register("ice_beam",
			() -> EntityType.Builder.<IceBeamEntity>of(IceBeamEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<MagicFireArrowEntity>> MAGIC_FIRE_ARROW = ENTITIES.register("magic_fire_arrow",
			() -> EntityType.Builder.<MagicFireArrowEntity>of(MagicFireArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<MagicIceArrowEntity>> MAGIC_ICE_ARROW = ENTITIES.register("magic_ice_arrow",
			() -> EntityType.Builder.<MagicIceArrowEntity>of(MagicIceArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
	
	public static final RegistryObject<EntityType<MagicLightArrowEntity>> MAGIC_LIGHT_ARROW = ENTITIES.register("magic_light_arrow",
			() -> EntityType.Builder.<MagicLightArrowEntity>of(MagicLightArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F)
					.build(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/projectiles/arrows").toString()));
}
