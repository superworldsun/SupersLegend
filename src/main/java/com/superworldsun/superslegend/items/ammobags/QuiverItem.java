package com.superworldsun.superslegend.items.ammobags;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class QuiverItem extends AmmoContainerItem {
    public QuiverItem(int capacity, int tooltipSlots) {
        super(capacity, tooltipSlots);
    }

    @Override
    public boolean canHoldItem(ItemStack itemStack) {
        Item ammoItem = itemStack.getItem();

        if (ammoItem == ItemInit.MAGIC_FIRE_ARROW.get()
                || ammoItem == ItemInit.MAGIC_ICE_ARROW.get()
                || ammoItem == ItemInit.MAGIC_LIGHT_ARROW.get()) {
            return false;
        }

        return itemStack.is(ItemTags.ARROWS);
    }
}
