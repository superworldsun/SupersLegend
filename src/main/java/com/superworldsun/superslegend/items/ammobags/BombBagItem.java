package com.superworldsun.superslegend.items.ammobags;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.ItemStack;

public class BombBagItem extends AmmoContainerItem {
    public BombBagItem(int capacity) {
        super(capacity, 1);
    }

    @Override
    public boolean canHoldItem(ItemStack itemStack) {
        return itemStack.getItem() == ItemInit.BOMB.get() || itemStack.getItem() == ItemInit.WATER_BOMB.get();
    }

    @Override
    public int getCapacityCost(ItemStack itemStack) {
        return itemStack.getItem() == ItemInit.WATER_BOMB.get() ? 2 : 1;
    }
}
