package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SpoilsBagItem extends BagItem
{
	public SpoilsBagItem()
	{
		super(new Item.Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == Items.GUNPOWDER || item == Items.ROTTEN_FLESH || item == Items.BONE;
	}
}
