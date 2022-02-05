package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.cookingpot.CookingPotCookingRecipe;
import com.superworldsun.superslegend.util.cookingpot.FoodValuesDefinition;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeSerializerInit {
    //Cooking pot code credited to Si_hen, this code is a modified version of their crockpot mod
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SupersLegendMain.MOD_ID);

    public static final IRecipeSerializer<CookingPotCookingRecipe> COOKING_POT_COOKING = register(RECIPE_SERIALIZERS, "cooking_pot_cooking",
            new CookingPotCookingRecipe.Serializer());
    public static final IRecipeSerializer<FoodValuesDefinition> FOOD_VALUES = register(RECIPE_SERIALIZERS,
            "food_values", new FoodValuesDefinition.Serializer());

    private static <T extends IForgeRegistryEntry<T>, E extends T> E register(final DeferredRegister<T> register, final String name, final E entry) {
        register.register(name, () -> entry);
        return entry;
    }

}
