package com.superworldsun.superslegend.container.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

@FunctionalInterface
public interface SlotFactory {
	Slot create(IInventory inventory, int slotIndex, int slotX, int slotY);
}
