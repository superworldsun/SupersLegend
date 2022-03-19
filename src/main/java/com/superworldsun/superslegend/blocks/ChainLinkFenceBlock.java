package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class ChainLinkFenceBlock extends PaneBlock {
public ChainLinkFenceBlock(Properties properties) {
    super(properties);
 }


@Override
public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, net.minecraft.entity.LivingEntity entity)
   {
      return true;
   }

   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 16.0D, 15.99D);

}