package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.recipe.AmmoContainersFillingRecipe;
import com.superworldsun.superslegend.recipe.RupeeCombiningRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SupersLegendMain.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> AMMO_CONTAINERS_FILLING = RECIPE_SERIALIZERS.register("ammo_containers_filling",
            () -> new SimpleCraftingRecipeSerializer<>(AmmoContainersFillingRecipe::new));

    public static final RegistryObject<RecipeSerializer<?>> RUPEE_COMBINING = RECIPE_SERIALIZERS.register("rupee_combining",
            () -> new SimpleCraftingRecipeSerializer<>(RupeeCombiningRecipe::new));
}
