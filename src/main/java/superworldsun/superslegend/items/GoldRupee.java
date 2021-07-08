package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class GoldRupee extends Item{

	public GoldRupee(Properties properties)
	{
		super(properties);
	}
	
	/*public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		ItemStack stack = player.getHeldItem(hand);
		
		if(stack.getCount() >= 3)
		 {
			
			 stack.shrink(3);
			
			 player.addItemStackToInventory(new ItemStack(ItemList.gold_rupee));
			 
			 BlockPos currentPos = player.getPosition();
 	         player.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.RUPEE_SILVER, SoundCategory.PLAYERS, 1f, 1f);
		 }
		        
	return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
		
	}*/
	
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GOLD + "300 rupee"));
	}   
} 