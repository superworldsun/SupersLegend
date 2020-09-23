package superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IWorldReader;

public class ChainLinkFenceBlock extends Block {
public ChainLinkFenceBlock(Block.Properties properties) {
    super(properties);
 }


@Override public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, net.minecraft.entity.LivingEntity entity) { return true; }

static {
   VoxelShape voxelshape = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShape voxelshape1 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShape voxelshape2 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShape voxelshape4 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShapes.or(voxelshape, voxelshape1, voxelshape2, voxelshape3, voxelshape4);
   VoxelShape voxelshape5 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShape voxelshape6 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShape voxelshape7 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShape voxelshape8 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
   VoxelShapes.or(voxelshape6, voxelshape5, voxelshape8, voxelshape7);
}


 /*public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
 }*/
}