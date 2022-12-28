package com.superworldsun.superslegend.items.shields;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class HylianShield extends ShieldItem
{
	public HylianShield(Properties builder) {
	      super(builder);
	}
	      
	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}


	      @Override
	      public boolean isBookEnchantable(ItemStack stack, ItemStack book)
		  {
	    	  return false;
		  }
}
