package superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class TorchTowerBlockBottom extends Block

{
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);

	   public TorchTowerBlockBottom(Block.Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }
	   
	   @SuppressWarnings("deprecation")
		public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
			      return facing == Direction.UP && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
			   }

			   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
			      return func_220055_a(worldIn, pos.up(), Direction.DOWN);
			   }
	   
	   public BlockRenderLayer getRenderLayer() {
		    return BlockRenderLayer.CUTOUT;
	   }
	   
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
	    }
}
	