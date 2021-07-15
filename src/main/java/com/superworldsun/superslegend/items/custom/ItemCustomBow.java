package com.superworldsun.superslegend.items.custom;

import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;

public class ItemCustomBow extends BowItem
{
	public ItemCustomBow(Properties builder) {
	      super(builder);
	}

	      @Override
	      public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
	    	  return false;

	}
}
