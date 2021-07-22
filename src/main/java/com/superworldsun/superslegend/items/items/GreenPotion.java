package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.mana.IMana;
import com.superworldsun.superslegend.mana.ManaProvider;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class GreenPotion extends Item
{
	public GreenPotion(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity)
	{
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		
		if (player instanceof ServerPlayerEntity)
		{
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
		}
		
		if (player != null)
		{
			IMana mana = ManaProvider.get(player);
			// Restore to maximum
			mana.restoreMana(mana.getMaxMana());
			player.awardStat(Stats.ITEM_USED.get(this));
			
			if (!player.abilities.instabuild)
			{
				stack.shrink(1);
			}
		}
		
		if (player == null || !player.abilities.instabuild)
		{
			if (stack.isEmpty())
			{
				return new ItemStack(Items.GLASS_BOTTLE);
			}
			
			if (player != null)
			{
				player.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		
		return stack;
	}
	
	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 32;
	}
	
	@Override
	public UseAction getUseAnimation(ItemStack stack)
	{
		return UseAction.DRINK;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		
		if (ManaProvider.get(player).getMana() == ManaProvider.get(player).getMaxMana())
		{
			return ActionResult.fail(stack);
		}
		
		return DrinkHelper.useDrink(world, player, hand);
	}

	public void appendHoverText(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GREEN + "The Medicine of Stamina"));
	}
}
