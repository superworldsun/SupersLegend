package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class HiddenShadowBlock extends Block

{
	   public HiddenShadowBlock(Properties properties) {
	      super(properties);
	   }

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 15.99D, 15.99D);

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1F;
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}
}