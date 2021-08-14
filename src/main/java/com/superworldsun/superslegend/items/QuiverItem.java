package com.superworldsun.superslegend.items;

import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;

public abstract class QuiverItem extends BagItem
{
	public QuiverItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		return ItemTags.ARROWS.contains(stack.getItem());
	}
}
