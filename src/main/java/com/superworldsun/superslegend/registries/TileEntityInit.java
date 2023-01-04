package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.entity.*;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit
{
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<TileEntityType<PedestalTileEntity>> PEDESTAL = TILES.register("pedestal", PedestalTileEntity::createType);
	public static final RegistryObject<TileEntityType<FanTileEntity>> FAN = TILES.register("fan", FanTileEntity::createType);
	public static final RegistryObject<TileEntityType<SwitchableFanTileEntity>> SWITCHABLE_FAN = TILES.register("switchable_fan", FanTileEntity::createSwitchableFanType);
	public static final RegistryObject<TileEntityType<GossipStoneTileEntity>> GOSSIP_STONE = TILES.register("gossip_stone", GossipStoneTileEntity::createType);
	public static final RegistryObject<TileEntityType<FalseShadowTileEntity>> FALSE_SHADOW = TILES.register("false_shadow", FalseShadowTileEntity::createFalseShadowType);
	public static final RegistryObject<TileEntityType<HiddenShadowTileEntity>> HIDDEN_SHADOW = TILES.register("hidden_shadow", HiddenShadowTileEntity::createHiddenShadowType);
	public static final RegistryObject<TileEntityType<PostboxTileEntity>> POSTBOX = TILES.register("postbox", PostboxTileEntity::createType);
	public static final RegistryObject<TileEntityType<LightEmitterTileEntity>> LIGHT_EMITTER = TILES.register("light_emitter", LightEmitterTileEntity::createType);
	public static final RegistryObject<TileEntityType<LightPrismTileEntity>> LIGHT_PRISM = TILES.register("light_prism", LightPrismTileEntity::createType);
	public static final RegistryObject<TileEntityType<OwlStatueTileEntity>> OWL_STATUE = TILES.register("owl_statue", OwlStatueTileEntity::createType);
	public static final RegistryObject<TileEntityType<CookingPotTileEntity>> COOKING_POT = TILES.register("cooking_pot", CookingPotTileEntity::createType);
	public static final RegistryObject<TileEntityType<ShadowTileEntity>> SHADOW = TILES.register("shadow", ShadowTileEntity::createType);
	public static final RegistryObject<TileEntityType<SunSwitchTileEntity>> SUN_SWITCH = TILES.register("sun_switch", SunSwitchTileEntity::createType);
}
