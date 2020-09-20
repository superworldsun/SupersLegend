package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class BlueRupee extends Item{

	public BlueRupee(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		ItemStack stack = player.getHeldItem(hand);
		
		if(stack.getCount() >= 4)
		 {
			
			 stack.shrink(4);
			
			 player.addItemStackToInventory(new ItemStack(ItemList.red_rupee));
			 
			 BlockPos currentPos = player.getPosition();
 	         player.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.RUPEE_BLUE, SoundCategory.PLAYERS, 1f, 1f);
		 }
		        
	return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
		
	}
	
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "5 rupee"));
	}   
} 