package com.superworldsun.superslegend.items.ammobags;

import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.world.item.ItemStack;

public class BulletBagItem extends AmmoContainerItem {
    public BulletBagItem(int capacity, int tooltipSlots) {
        super(capacity, tooltipSlots);
    }

    @Override
    public boolean canHoldItem(ItemStack itemStack) {
        return itemStack.is(TagInit.PELLETS);
    }
}
