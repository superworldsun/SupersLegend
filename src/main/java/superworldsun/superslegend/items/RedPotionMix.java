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

import net.minecraft.item.Item.Properties;

public class RedPotionMix extends Item
{

	public RedPotionMix(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {
		ItemStack stack = player.getItemInHand(hand);
		  
		 if(!world.isClientSide && !player.isCreative() && player.isHurt())
	     {
			 
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 	player.setHealth(player.getHealth() + 1.0F);
				stack.shrink(1);
				player.addItem(new ItemStack(Items.GLASS_BOTTLE));
				//player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
	     }
		 else if (!world.isClientSide && player.isCreative())
		 {
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 	player.setHealth(player.getHealth() + 1.0F);
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand)); 
	 }
	
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "This could probably be cooked"));
	}  
	
}
