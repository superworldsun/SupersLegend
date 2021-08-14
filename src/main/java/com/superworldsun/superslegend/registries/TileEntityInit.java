package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.*;

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
	public static final RegistryObject<TileEntityType<FalseShadowTileEntity>> FALSE_SHADOW = TILES.register("false_shadow", FalseShadowTileEntity::createType);
	public static final RegistryObject<TileEntityType<HiddenShadowTileEntity>> HIDDEN_SHADOW = TILES.register("hidden_shadow", HiddenShadowTileEntity::createType);
	public static final RegistryObject<TileEntityType<PostboxTileEntity>> POSTBOX = TILES.register("postbox", PostboxTileEntity::createType);
}
