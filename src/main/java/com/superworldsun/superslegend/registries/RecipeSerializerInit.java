package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.cookingpot.CookingPotCookingRecipe;
import com.superworldsun.superslegend.util.cookingpot.FoodValuesDefinition;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerInit
{
	// Cooking pot code credited to Si_hen, this code is a modified version of their crockpot mod
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<IRecipeSerializer<?>> COOKING_POT_COOKING = RECIPE_SERIALIZERS.register("cooking_pot_cooking", CookingPotCookingRecipe.Serializer::new);
	public static final RegistryObject<IRecipeSerializer<?>> FOOD_VALUES = RECIPE_SERIALIZERS.register("food_values", FoodValuesDefinition.Serializer::new);
}
