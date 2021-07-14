package com.superworldsun.superslegend.items.weapons;

import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;

public class HerosBow extends BowItem {
    public HerosBow(Properties builder) {
        super(builder);
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
