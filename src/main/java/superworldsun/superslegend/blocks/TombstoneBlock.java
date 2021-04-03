package superworldsun.superslegend.blocks;

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
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import superworldsun.superslegend.lists.BlockList;

public class TombstoneBlock extends Block implements IWaterLoggable

{

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	protected static final VoxelShape FACE_EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 19.0D, 16.0D);
	protected static final VoxelShape FACE_WEST_AABB = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 19.0D, 16.0D);
	protected static final VoxelShape FACE_SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 19.0D, 3.0D);
	protected static final VoxelShape FACE_NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 19.0D, 16.0D);

	   public TombstoneBlock(Properties properties) {
	      super(properties);
		   this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
		   this.setDefaultState(getDefaultState().with(WATERLOGGED, false));
	   }

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch((Direction)state.get(FACING)) {
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
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	/*public BlockState rotate(BlockState state, Rotation rot) {
		return BlockList.tombstone_block.rotate(state, rot);
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return BlockList.tombstone_block.mirror(state, mirrorIn);
	}*/

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, TYPE, WATERLOGGED);
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return facing == Direction.DOWN && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return !worldIn.isAirBlock(pos.down());
	}
}
	