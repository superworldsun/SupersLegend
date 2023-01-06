package com.superworldsun.superslegend.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class BagSlot extends Slot {
	public BagSlot(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack itemStack) {
		return container.canPlaceItem(getSlotIndex(), itemStack);
	}
}
