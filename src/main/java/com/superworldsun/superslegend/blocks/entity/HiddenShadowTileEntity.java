package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;

public class HiddenShadowTileEntity extends ShadowTileEntity {
	public HiddenShadowTileEntity() {
		super(TileEntityInit.HIDDEN_SHADOW.get());
	}

	public static TileEntityType<HiddenShadowTileEntity> createHiddenShadowType() {
		return Builder.of(HiddenShadowTileEntity::new, BlockInit.HIDDEN_SHADOW_BLOCK.get()).build(null);
	}
}
