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

import javax.annotation.Nonnull;

public class BlueRupee extends Item{

	public BlueRupee(Properties properties)
	{
		super(properties);
	}

	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity player,@Nonnull Hand hand)
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

	public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "5 rupee"));
	}   
} 