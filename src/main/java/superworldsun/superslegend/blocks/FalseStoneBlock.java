package superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class FalseStoneBlock extends Block

{
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	   public FalseStoneBlock(Block.Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		      return SHAPE;
		   }
    
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
     }
}