package com.superworldsun.superslegend.blocks.tile;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;

public class HiddenShadowTileEntity extends TileEntity
{
	public HiddenShadowTileEntity()
	{
		super(TileEntityInit.HIDDEN_SHADOW.get());
	}
	
	public static TileEntityType<HiddenShadowTileEntity> createType()
	{
		return Builder.of(HiddenShadowTileEntity::new, BlockInit.HIDDEN_SHADOW_BLOCK.get()).build(null);
	}
}
