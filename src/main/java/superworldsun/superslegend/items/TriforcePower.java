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

public class TriforcePower extends Item
{

	public TriforcePower(Properties properties)
	{
		super(properties);
	}
	
	 public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);
		 {
					player.addPotionEffect(new EffectInstance(Effect.get(20), 44, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(19), 400, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(5), 600, 0, false, true));
					player.addPotionEffect(new EffectInstance(Effect.get(3), 200, 0, false, false));
		 }
	 			return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
			
		}
	 @Override
		public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
		{
			super.addInformation(stack, world, list, flag);				
			list.add(new StringTextComponent(TextFormatting.RED + "This gives the you Power, but at what cost?"));
		}   
}
