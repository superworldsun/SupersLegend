package superworldsun.superslegend.items;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import superworldsun.superslegend.lists.BlockList;

public class TorchTower extends Item

{

	public TorchTower(Properties properties) {
		super(properties);
	}
	
	@SuppressWarnings({ "deprecation" })
	@Nonnull
    public ActionResultType onItemUse(ItemUseContext context) 
	{
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        ItemStack item = context.getItem();
        BlockPos pos1 = blockpos.up();
        BlockPos pos2 = blockpos.up(2);
		
        if(world.getBlockState(pos1).isAir() && world.getBlockState(pos2).isAir())
        
        {
            world.setBlockState(pos1, BlockList.torch_tower_block_bottom.getDefaultState());
            world.setBlockState(pos2, BlockList.torch_tower_block_top.getDefaultState());
        	
        item.shrink(1);
        }
        return ActionResultType.PASS;
        
	}
}
