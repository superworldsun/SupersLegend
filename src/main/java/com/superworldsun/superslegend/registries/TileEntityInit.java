package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.PedestalTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit
{
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<TileEntityType<PedestalTileEntity>> PEDESTAL = TILES.register("pedestal",
			() -> Builder.<PedestalTileEntity>of(PedestalTileEntity::new, BlockInit.PEDESTAL.get()).build(null));
}
