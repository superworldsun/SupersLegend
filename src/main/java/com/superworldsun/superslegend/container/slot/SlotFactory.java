package com.superworldsun.superslegend.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

@FunctionalInterface
public interface SlotFactory {
    Slot create(Container container, int slotIndex, int slotX, int slotY);
}