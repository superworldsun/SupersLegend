package com.superworldsun.superslegend.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemContainerSlot extends Slot {
    public ItemContainerSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack) {
        return container.canPlaceItem(getSlotIndex(), itemStack);
    }
}
