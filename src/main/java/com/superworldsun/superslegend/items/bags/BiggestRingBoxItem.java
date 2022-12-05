package com.superworldsun.superslegend.items.bags;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.RingItem;

import net.minecraft.item.ItemStack;

public class BiggestRingBoxItem extends BagItem
{
	public BiggestRingBoxItem()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		return stack.getItem() instanceof RingItem;
	}
}
