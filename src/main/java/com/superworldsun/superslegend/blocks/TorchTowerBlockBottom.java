package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class TorchTowerBlockBottom extends Block

{
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);

	   public TorchTowerBlockBottom(Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }
	   
	   @SuppressWarnings("deprecation")
		public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
			      return facing == Direction.UP && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
			   }

			   /*public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
			      return canSupportCenter(worldIn, pos.up(), Direction.DOWN);
			   }*/

	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return !worldIn.isEmptyBlock(pos.above());
	}
	   
	   /*public BlockRenderLayer getRenderLayer() {
		    return BlockRenderLayer.CUTOUT;
	   }*/
	   
	   public enum EnumTorchTowerBlock implements IStringSerializable {
	        UPPER,
	        LOWER;

	        public String toString()
	        {
	            return this.getName();
	        }

	        public String getName() {
	            return this.name().toLowerCase();
	        }

		   @Override
		   public String getSerializedName() {
			   return null;
		   }
	   }
}
	