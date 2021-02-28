package superworldsun.superslegend.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.lists.BlockList;

public class TorchTower extends Item

{

	public TorchTower(Properties properties) {
		super(properties);
	}
	

	@Nonnull
    public ActionResultType onItemUse(ItemUseContext context) 
	{
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        ItemStack item = context.getItem();
        BlockPos pos1 = blockpos.up();
        BlockPos pos2 = blockpos.up(2);
        
		 world.playSound(null, pos1.getX(), pos1.getY(), pos1.getZ(), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.PLAYERS, 1f, 1f);
		
        if(world.getBlockState(pos1).isAir() && world.getBlockState(pos2).isAir())
        
        {
            world.setBlockState(pos1, BlockList.torch_tower_block_bottom.getDefaultState());
            world.setBlockState(pos2, BlockList.torch_tower_block_top.getDefaultState());
        	
        item.shrink(1);
        }
        return ActionResultType.PASS;
        
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "[WIP]"));
	}  
}
