package com.superworldsun.superslegend.items.customclass;

import net.minecraft.world.item.*;

public class ItemCustomBow extends BowItem {
    public ItemCustomBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}