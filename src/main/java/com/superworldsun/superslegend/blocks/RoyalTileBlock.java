package com.superworldsun.superslegend.blocks;

import static net.minecraft.state.properties.BlockStateProperties.FACING;
import static net.minecraft.state.properties.BlockStateProperties.POWERED;

import java.util.Random;

import com.superworldsun.superslegend.util.BlockShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RoyalTileBlock extends Block
{
	private static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 16D, 0.5D);
	// only for model rotation
	public static final DirectionProperty ROTATION = DirectionProperty.create("rotation", Direction.Plane.HORIZONTAL);
	
	public RoyalTileBlock(Properties properties)
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(POWERED, false).setValue(FACING, Direction.SOUTH).setValue(ROTATION, Direction.SOUTH));
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
		return BlockShapeHelper.rotateShape(Direction.SOUTH, blockState.getValue(FACING), SHAPE);
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
	
	@Override
	public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState newBlockState, boolean flag)
	{
		if (!flag && !blockState.is(newBlockState.getBlock()))
		{
			if (getSignalForState(blockState) > 0)
			{
				updateNeighbours(world, blockPos);
			}
			
			if (blockState.hasTileEntity() && (!blockState.is(newBlockState.getBlock()) || !newBlockState.hasTileEntity()))
			{
				world.removeBlockEntity(blockPos);
			}
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
		return direction == Direction.UP ? getSignalForState(blockState) : 0;
	}
	
	@Override
	public boolean isSignalSource(BlockState blockState)
	{
		return true;
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(POWERED).add(FACING).add(ROTATION);
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState blockState)
	{
		return PushReaction.DESTROY;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(ROTATION, context.getHorizontalDirection());
	}
}
