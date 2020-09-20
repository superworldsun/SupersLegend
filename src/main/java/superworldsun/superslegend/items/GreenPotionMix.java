package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GreenPotionMix extends Item
{

	public GreenPotionMix(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(!world.isRemote && player.getFoodStats().needFood() && !player.isCreative())
	     {
			 
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 	player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 1);
				stack.shrink(1);
				player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
	     }
		 else if(player.isCreative() && player.getFoodStats().needFood())
		 {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 	player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 1);
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }
	
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "This could probably be cooked"));
	}  
	
}
