package com.superworldsun.superslegend.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TorchTowerBlockTop extends Block

{
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 9.0D, 13.0D);

	   public TorchTowerBlockTop(Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }
	   
	   @OnlyIn(Dist.CLIENT)
	   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
	      double d0 = (double)pos.getX() + 0.5D;
	      double d1 = (double)pos.getY() + 0.5D;
	      double d2 = (double)pos.getZ() + 0.5D;
	      worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	      worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
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
	