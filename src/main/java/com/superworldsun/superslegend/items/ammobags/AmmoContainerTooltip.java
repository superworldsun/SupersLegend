package com.superworldsun.superslegend.items.ammobags;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AmmoContainerTooltip implements TooltipComponent {
    private final List<ItemStack> contents;
    private final int slots;
    private final int selectedIndex;

    public AmmoContainerTooltip(List<ItemStack> contents, int slots, int selectedIndex) {
        this.contents = contents.stream().map(ItemStack::copy).toList();
        this.slots = slots;
        this.selectedIndex = selectedIndex;
    }

    public List<ItemStack> getContents() {
        return contents;
    }

    public int getSlots() {
        return slots;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
