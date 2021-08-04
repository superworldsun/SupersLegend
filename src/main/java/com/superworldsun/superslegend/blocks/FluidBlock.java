package com.superworldsun.superslegend.blocks;

import java.util.function.Supplier;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;

public class FluidBlock extends FlowingFluidBlock
{
	public FluidBlock(Supplier<FlowingFluid> fluidSupplier)
	{
		super(fluidSupplier, Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops());
	}
}