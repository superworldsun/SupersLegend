package superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;

import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class GrateBlock extends Block implements IWaterLoggable{
public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.99D, 16.0D);

public GrateBlock(Block.Properties properties) {
    super(properties);
 }

public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
   return SHAPE;
}

	   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		      builder.add(TYPE, WATERLOGGED);
		   }
	   
	   @SuppressWarnings("deprecation")
	public IFluidState getFluidState(BlockState state) {
		      return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
		   }

		   public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
		      return state.get(TYPE) != SlabType.DOUBLE ? IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn) : false;
		   }

	   
		   @SuppressWarnings("deprecation")
		public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
			      if (stateIn.get(WATERLOGGED)) {
			         worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			      }

			      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
			   }
		   
		   
	   
	   public BlockRenderLayer getRenderLayer() {
		    return BlockRenderLayer.CUTOUT;
	   }
}
	