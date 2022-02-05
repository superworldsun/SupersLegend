package com.superworldsun.superslegend.util.cookingpot;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Color;

public enum FoodCategory {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    MEAT("#FFABC7"),
    MUSHROOM("#BF8778"),
    FISH("#006BFF"),
    SEAFOOD("#59A3FF"),
    FRUIT("#FF6B00"),
    HERB("#3F7F3F"),
    VEGETABLE("#00FF00"),
    HONEY("#D96E1A"),
    CRITTER("#007F7F"),
    MONSTERPART("#994741"),
    FAIRY("#A552A5"),
    HEARTY("#CCC928"),
    INEDIBLE("#9B9B9B");
    //Todo: add some effect modifier categories

    public final Color color;

    FoodCategory(String colorHex) {
        this.color = Color.parseColor(colorHex);
    }

    public static ItemStack getItemStack(FoodCategory category) {
        return ItemInit.foodCategoryItems.get(category).getDefaultInstance();
    }
}