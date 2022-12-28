package com.superworldsun.superslegend.items.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NonEnchantItem extends Item
{
    public NonEnchantItem(Properties properties) {
        super(properties);
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
