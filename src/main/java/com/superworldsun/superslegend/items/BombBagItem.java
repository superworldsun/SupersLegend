package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;

public abstract class BombBagItem extends BagItem
{
	public BombBagItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == ItemInit.BOMB.get();
	}
}
