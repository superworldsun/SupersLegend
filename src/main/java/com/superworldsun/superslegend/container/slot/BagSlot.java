package com.superworldsun.superslegend.container.slot;

import com.superworldsun.superslegend.items.bags.BagItem;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class BagSlot extends Slot
{
	private final BagItem bagItem;
	
	public BagSlot(IInventory inventory, int index, int x, int y, BagItem bagItem)
	{
		super(inventory, index, x, y);
		this.bagItem = bagItem;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack)
	{
		return bagItem.canHoldItem(stack);
	}
}
