package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DeliveryBagItem extends BagItem
{
	public DeliveryBagItem()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == Items.PAPER || item == Items.BOOK || item == Items.ENCHANTED_BOOK || item == Items.KNOWLEDGE_BOOK
			|| item == Items.WRITABLE_BOOK || item == Items.WRITTEN_BOOK || item == Items.MAP
			|| item == Items.FILLED_MAP;
	}
}
