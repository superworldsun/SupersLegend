package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class TorchTower extends Block

{
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);

	   public TorchTower(Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }


	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return facing == Direction.DOWN && !canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean flag)
	{
		world.setBlockAndUpdate(pos.above(), BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState());
	}

	/*@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		if (pos.above() )
		{
			getBlockState().getBlock().onNeighborChange(getBlockState(), world, pos, neighbor);
		}
	}*/

	/*@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		if(world.getBlockState(neighbor) == this.getDefaultState().with(LIT, true))
		{
			action(state,world,pos);
		}
	}*/

	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor)
	{
		if(world.getBlockState(pos.above()) == BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState())
		{
			//state
		}
	}

	/*public boolean isSignalSource(BlockState p_149744_1_) {
		return false;
	}*/

	/*public int getSignal(BlockState p_180656_1_, IBlockReader p_180656_2_, BlockPos p_180656_3_, Direction p_180656_4_) {
		return 15;
	}*/

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos)
	{
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.TORCH_TOWER_TOP_UNLIT.get())
				|| (world.getBlockState(pos.above()).is(BlockInit.TORCH_TOWER_TOP_LIT.get()) || world.isEmptyBlock(pos.above())));
	}
}
	