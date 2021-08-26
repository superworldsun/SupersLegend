package com.superworldsun.superslegend.api;

import net.minecraft.item.ItemStack;

public interface IPelletAmo {
    default boolean isAmmo(ItemStack stack) {
        return true;
    }
}