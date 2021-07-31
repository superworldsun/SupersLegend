package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class ChainLinkFenceBlock extends Block {
public ChainLinkFenceBlock(Properties properties) {
    super(properties);
 }


@Override
public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, net.minecraft.entity.LivingEntity entity)
   {
      return true;
   }

   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 16.0D, 15.99D);

static
   {
      VoxelShape voxelshape = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShape voxelshape1 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShape voxelshape2 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShape voxelshape3 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShape voxelshape4 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShapes.or(voxelshape, voxelshape1, voxelshape2, voxelshape3, voxelshape4);
      VoxelShape voxelshape5 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShape voxelshape6 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShape voxelshape7 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShape voxelshape8 = Block.box(0.0D, 0.0D, 0.0D, 17.0D, 17.0D, 17.0D);
      VoxelShapes.or(voxelshape6, voxelshape5, voxelshape8, voxelshape7);
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }


 /*public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
 }*/
}