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

public class Triforce extends Item
{

	public Triforce(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);

	
		        {
					player.addPotionEffect(new EffectInstance(Effect.get(1), 300, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(11), 300, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(5), 300, 0, false, true));
					player.addPotionEffect(new EffectInstance(Effect.get(3), 300, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(16), 900, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(13), 900, 0, false, false));
		        }
				return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
		}
	
	@Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
			
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GOLD + "You feel a godly power holding this"));
	}   
		
	
}
