package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.LetterItem;

import net.minecraft.item.ItemStack;

public class Letter extends LetterItem
{
	public Letter()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		return true;
	}
}
