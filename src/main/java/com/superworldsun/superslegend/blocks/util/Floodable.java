package com.superworldsun.superslegend.blocks.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.IntegerProperty;

public interface Floodable extends ILiquidContainer, IBucketPickupHandler{
	public static final IntegerProperty FLUID_STATE_PROPERTY = IntegerProperty.create("fluid", 0, 1024);
	
	public BlockState getBlockState(FluidState fluidState);
}
