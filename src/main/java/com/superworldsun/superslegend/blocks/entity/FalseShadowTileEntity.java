package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;

public class FalseShadowTileEntity extends ShadowTileEntity {
	public FalseShadowTileEntity() {
		super(TileEntityInit.FALSE_SHADOW.get());
	}

	public static TileEntityType<FalseShadowTileEntity> createFalseShadowType() {
		return Builder.of(FalseShadowTileEntity::new, BlockInit.FALSE_SHADOW_BLOCK.get()).build(null);
	}
}
