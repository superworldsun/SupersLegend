package com.superworldsun.superslegend.blocks;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RoyalTileBlock extends Block
{
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	protected static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 1D, 16D);
	
	public RoyalTileBlock()
	{
		super(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().noCollission().strength(0.5F));
		registerDefaultState(stateDefinition.any().setValue(POWERED, false));
	}
	
	public void activate(World world, BlockState blockState, BlockPos blockPos)
	{
		BlockState newBlockState = setSignalForState(blockState, 15);
		world.setBlock(blockPos, newBlockState, 2);
		updateNeighbours(world, blockPos);
		world.setBlocksDirty(blockPos, blockState, newBlockState);
		// 20 is activated time, in ticks
		world.getBlockTicks().scheduleTick(new BlockPos(blockPos), this, 20);
	}
	
	protected int getSignalForState(BlockState blockState)
	{
		return blockState.getValue(POWERED) ? 15 : 0;
	}
	
	protected void updateNeighbours(World world, BlockPos blockPos)
	{
		world.updateNeighborsAt(blockPos, this);
		world.updateNeighborsAt(blockPos.below(), this);
	}
	
	protected BlockState setSignalForState(BlockState blockState, int signal)
	{
		return blockState.setValue(POWERED, Boolean.valueOf(signal > 0));
	}
	
	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader world, BlockPos blockPos, ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Override
	public void tick(BlockState blockState, ServerWorld world, BlockPos blockPos, Random random)
	{
		int signal = getSignalForState(blockState);
		
		if (signal > 0)
		{
			BlockState newBlockState = setSignalForState(blockState, 0);
			world.setBlock(blockPos, newBlockState, 2);
			updateNeighbours(world, blockPos);
			world.setBlocksDirty(blockPos, blockState, newBlockState);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState newBlockState, boolean p_196243_5_)
	{
		if (!p_196243_5_ && !blockState.is(newBlockState.getBlock()))
		{
			if (getSignalForState(blockState) > 0)
			{
				updateNeighbours(world, blockPos);
			}
			
			super.onRemove(blockState, world, blockPos, newBlockState, p_196243_5_);
		}
	}
	
	@Override
	public int getSignal(BlockState blockState, IBlockReader world, BlockPos blockPos, Direction direction)
	{
		return getSignalForState(blockState);
	}
	
	@Override
	public int getDirectSignal(BlockState blockState, IBlockReader world, BlockPos blockPos, Direction direction)
	{
		return direction == Direction.UP ? this.getSignalForState(blockState) : 0;
	}
	
	@Override
	public boolean isSignalSource(BlockState blockState)
	{
		return true;
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(POWERED);
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState blockState)
	{
		return PushReaction.DESTROY;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
		return VoxelShapes.box(0,0,0,1,0.2,1);
	}
}
