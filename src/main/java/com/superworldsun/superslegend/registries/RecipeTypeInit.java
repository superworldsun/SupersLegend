package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.cookingpot.CookingPotCookingRecipe;
import com.superworldsun.superslegend.util.cookingpot.FoodValuesDefinition;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class RecipeTypeInit
{
	// Code credited to Si_hen, this code is a modified version of their crockpot mod
	public static final IRecipeType<CookingPotCookingRecipe> COOKING_POT_COOKING_RECIPE_TYPE = register("cooking_pot_cooking");
	public static final IRecipeType<FoodValuesDefinition> FOOD_VALUES_RECIPE_TYPE = register("food_values");
	
	private static <T extends IRecipe<?>> IRecipeType<T> register(String key)
	{
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(SupersLegendMain.MOD_ID, key), new IRecipeType<T>()
		{
			@Override
			public String toString()
			{
				return SupersLegendMain.MOD_ID + ":" + key;
			}
		});
	}
}
