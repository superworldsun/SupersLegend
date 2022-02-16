package com.superworldsun.superslegend.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class OwlStatue extends Block

{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 20.0D, 16D);

	public OwlStatue(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{

		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());

	}

	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

		   /*public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		      return canSupportCenter(worldIn, pos.down(), Direction.UP);
		   }*/

	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return !worldIn.isEmptyBlock(pos.below());
	}
	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
		return VoxelShapes.box(0,0,0,1,1.25,1);
	}

}
