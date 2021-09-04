package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class AncientTablet extends Block implements IWaterLoggable

{

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	//protected static final VoxelShape FACE_EAST_AABB = Block.box(	0.0D, 0.0D, 0.0D, 		3.0D, 19.0D, 16.0D);
	//protected static final VoxelShape FACE_WEST_AABB = Block.box(	13.0D, 0.0D, 0.0D, 		16.0D, 19.0D,16.0D);
	//protected static final VoxelShape FACE_SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 		16.0D, 19.0D,3.0D);
	//protected static final VoxelShape FACE_NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 		16.0D, 19.0D,16.0D);

	protected static final VoxelShape FACE_EAST_AABB = Block.box(2.0D, 0.0D, 0.0D, 5.0D, 4.0D, 16.0D);
	protected static final VoxelShape FACE_WEST_AABB = Block.box(3.0D, 0.0D, 0.0D, 13.0D, 10.0D, 12.0D);
	protected static final VoxelShape FACE_SOUTH_AABB = Block.box(0.0D, 0.0D, 3.0D, 13.0D, 16.0D, 12.0D);
	protected static final VoxelShape FACE_NORTH_AABB = Block.box(2.0D, 0.0D, 13.0D, 5.0D, 22.0D, 16.0D);

	public AncientTablet(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
		this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch((Direction)state.getValue(FACING)) {
			case EAST:
			default:
				return FACE_EAST_AABB;
			case WEST:
				return FACE_WEST_AABB;
			case SOUTH:
				return FACE_SOUTH_AABB;
			case NORTH:
				return FACE_NORTH_AABB;
		}
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	/*public BlockState rotate(BlockState state, Rotation rot) {
		return BlockList.tombstone_block.rotate(state, rot);
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return BlockList.tombstone_block.mirror(state, mirrorIn);
	}*/

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, TYPE, WATERLOGGED);
	}

	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return !worldIn.isEmptyBlock(pos.below());
	}
}