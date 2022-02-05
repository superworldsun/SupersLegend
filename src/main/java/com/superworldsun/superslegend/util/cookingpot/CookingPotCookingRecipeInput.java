package com.superworldsun.superslegend.util.cookingpot;

import net.minecraft.item.ItemStack;

import java.util.List;

public final class CookingPotCookingRecipeInput {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    public final FoodValues mergedFoodValues;
    public final List<ItemStack> stacks;

    public CookingPotCookingRecipeInput(FoodValues mergedFoodValues, List<ItemStack> stacks) {
        this.mergedFoodValues = mergedFoodValues;
        this.stacks = stacks;
    }
}
