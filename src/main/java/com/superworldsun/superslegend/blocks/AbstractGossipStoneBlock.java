package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
//import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractGossipStoneBlock extends Block
{

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 21.0D, 14.0D);
    
    public AbstractGossipStoneBlock(Properties builder)
    {
        
        super(builder);
        
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        
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
    
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
    
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        
    }

     public BlockRenderType getRenderShape(BlockState state)
     {
          return BlockRenderType.MODEL;
     }
    
     public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
     {
          builder.add(FACING);
     }
     
     /*public BlockRenderLayer getRenderLayer() {
		    return BlockRenderLayer.CUTOUT;
	   }*/
    
}