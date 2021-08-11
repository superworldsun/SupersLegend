package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BaitBagItem extends BagItem
{
	public BaitBagItem()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == Items.WHEAT || item == Items.POTATO || item == Items.BEETROOT || item == Items.WHEAT_SEEDS
			|| item == Items.PUMPKIN_SEEDS || item == Items.MELON_SEEDS || item == Items.BEETROOT_SEEDS
			|| item == Items.GOLDEN_APPLE || item == Items.APPLE || item == Items.GOLDEN_CARROT
			|| item == Items.BEEF || item == Items.COOKED_BEEF || item == Items.CHICKEN || item == Items.COOKED_CHICKEN
			|| item == ItemInit.RED_JELLY.get();
	}
}
