package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class MoonPearl extends Item
{

	public MoonPearl(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand)
	{
		ItemStack stack = entity.getHeldItem(hand);
		
		int currentDim = entity.dimension.getId();
		
		if(!world.isRemote)
		{
			if(!(currentDim == 1))
			{
				entity.changeDimension(DimensionType.THE_NETHER);
		    	return ActionResult.newResult(ActionResultType.SUCCESS, stack);
			}
			else if(((currentDim == 1)) && (entity.isSneaking()))
			{
				entity.changeDimension(DimensionType.OVERWORLD);
		    	return ActionResult.newResult(ActionResultType.SUCCESS, stack);
			}
		}
	    
	    return ActionResult.newResult(ActionResultType.FAIL, stack);		 
	}
	
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Teleports the player to the Nether"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "when used in the Nether it will Return player to Portal"));
	}  
}