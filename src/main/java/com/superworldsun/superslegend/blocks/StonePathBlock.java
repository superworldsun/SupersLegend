package com.superworldsun.superslegend.blocks;

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

import net.minecraft.block.AbstractBlock.Properties;

public class StonePathBlock extends Block

{

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 0.3D, 15.99D);

	public StonePathBlock(Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
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


	   /*public BlockRenderLayer getRenderLayer() {
		    return BlockRenderLayer.CUTOUT;
	   }*/
}
