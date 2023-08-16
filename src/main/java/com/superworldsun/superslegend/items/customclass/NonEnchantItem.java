package com.superworldsun.superslegend.items.customclass;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NonEnchantItem extends Item {
    public NonEnchantItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
