package com.superworldsun.superslegend.items.containers;

import com.superworldsun.superslegend.registries.MenuTypeInit;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class LetterItem extends ContainerItem {
    public LetterItem(Properties properties) {
        super(properties);
    }

    @Override
    protected MenuType<?> getMenuType() {
        return MenuTypeInit.LETTER.get();
    }

    @Override
    public boolean canContainItem(ItemStack stack) {
        return true;
    }
}
