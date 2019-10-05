package superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public abstract class GossipStoneBlock extends Block
{

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    
    public GossipStoneBlock(Properties builder)
    {
        
        super(builder);
        
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
        
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
    
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
        
    }

     public BlockRenderType getRenderType(BlockState state)
     {
          return BlockRenderType.MODEL;
     }
    
     public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
     {
          builder.add(FACING);
     }
    
}