package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.math.AxisAlignedBB;

public class OwlStatueTileEntity extends TileEntity {
	public OwlStatueTileEntity() {
		super(TileEntityInit.OWL_STATUE.get());
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(worldPosition, worldPosition.offset(1.0D, 1.25D, 1.0D));
	}

	public static TileEntityType<OwlStatueTileEntity> createType() {
		return Builder.of(OwlStatueTileEntity::new, BlockInit.OWL_STATUE.get()).build(null);
	}
}
