package com.superworldsun.superslegend.items.food;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class HyruleBass extends Item
{
	public HyruleBass()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES).food(new Food.Builder().saturationMod(0.3f).nutrition(1).meat().build()));
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack)
	{
		return UseAction.EAT;
	}

	@Override
	public int getUseDuration(ItemStack p_77626_1_)
	{
		return 32;
	}
}
