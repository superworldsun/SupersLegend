package com.superworldsun.superslegend.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class OddMushroomBlock extends MushroomBlock implements IGrowable

{
	public OddMushroomBlock(Properties p_i48363_1_) {
		super(p_i48363_1_);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_)
	{
		return VoxelShapes.empty();
	}

}
	