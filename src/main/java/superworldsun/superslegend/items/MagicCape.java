package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MagicCape extends Item
{

	public MagicCape(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(!world.isRemote)
	     {
			 player.addPotionEffect(new EffectInstance(Effect.get(18), 5, 3, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(17), 5, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(15), 60, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(14), 5, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(11), 5, 99, false, false));
				player.isSneaking();
				player.addExhaustion(1);
				player.isInvisible();

				
	     }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }
	
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "Allows you to slip through many obsticals seamlessly"));
	}  
	
}
