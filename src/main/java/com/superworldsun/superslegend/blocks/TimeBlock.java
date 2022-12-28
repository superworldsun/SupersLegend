package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TimeBlock extends Block
{
	public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");
	protected static final VoxelShape SHAPE_FULL = Block.box(0D, 0D, 0D, 16D, 16D, 16D);
	protected static final VoxelShape SHAPE_HIDDEN = Block.box(0D, 0D, 0D, 0D, 0D, 0D);
	
	public TimeBlock(Properties properties)
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(HIDDEN, false));
	}
	
	public void toggle(World level, BlockState blockState, BlockPos blockPos)
	{
		BlockState newBlockState = blockState.setValue(HIDDEN, !blockState.getValue(HIDDEN));
		level.setBlock(blockPos, newBlockState, 2);
		level.setBlocksDirty(blockPos, blockState, newBlockState);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState blockState, IBlockReader level, BlockPos blockPos, ISelectionContext context)
	{
		return blockState.getValue(HIDDEN) ? VoxelShapes.empty() : VoxelShapes.block();
	}
	
	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader world, BlockPos blockPos, ISelectionContext context)
	{
		return blockState.getValue(HIDDEN) ? SHAPE_HIDDEN : SHAPE_FULL;
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HIDDEN);
	}
}
