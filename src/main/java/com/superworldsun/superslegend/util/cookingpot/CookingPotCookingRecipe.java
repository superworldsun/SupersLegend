package com.superworldsun.superslegend.util.cookingpot;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.superworldsun.superslegend.registries.RecipeTypeInit;
import com.superworldsun.superslegend.registries.RecipeSerializerInit;
import com.superworldsun.superslegend.util.cookingpot.requirement.IRequirement;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CookingPotCookingRecipe extends AbstractCookingPotRecipe {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    private final List<IRequirement> requirements;
    private final ItemStack result;
    private final int priority, weight, cookingTime;

    public CookingPotCookingRecipe(ResourceLocation id, List<IRequirement> requirements, ItemStack result, int priority, int weight, int cookingTime) {
        super(id);
        this.requirements = ImmutableList.copyOf(requirements);
        this.result = result;
        this.priority = priority;
        this.weight = Math.max(weight, 1);
        this.cookingTime = cookingTime;
    }

    public List<IRequirement> getRequirements() {
        return requirements;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getPriority() {
        return priority;
    }

    public int getWeight() {
        return weight;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public boolean matches(CookingPotCookingRecipeInput input) {
        return this.requirements.stream().allMatch(r -> r.test(input));
    }

    @Nullable
    public static CookingPotCookingRecipe getRecipeFor(CookingPotCookingRecipeInput input, Random random, RecipeManager recipeManager) {
        List<CookingPotCookingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeTypeInit.COOKING_POT_COOKING_RECIPE_TYPE);
        recipes.sort(Comparator.comparing(CookingPotCookingRecipe::getPriority).reversed());
        List<CookingPotCookingRecipe> matchedRecipes = new ArrayList<>();
        boolean isFirst = true;
        int priority = 0;
        for (CookingPotCookingRecipe recipe : recipes) {
            if (isFirst) {
                if (recipe.matches(input)) {
                    priority = recipe.getPriority();
                    matchedRecipes.add(recipe);
                    isFirst = false;
                }
            } else {
                if (recipe.getPriority() != priority) {
                    break;
                } else {
                    if (recipe.matches(input)) {
                        matchedRecipes.add(recipe);
                    }
                }
            }
        }
        if (matchedRecipes.isEmpty()) {
            return null;
        }
        int weightSum = matchedRecipes.stream().mapToInt(CookingPotCookingRecipe::getWeight).sum();
        int rand = random.nextInt(weightSum) + 1;
        for (CookingPotCookingRecipe matchedRecipe : matchedRecipes) {
            rand -= matchedRecipe.getWeight();
            if (rand <= 0) {
                return matchedRecipe;
            }
        }
        return null;
    }

    public ItemStack assemble() {
        return this.result.copy();
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.COOKING_POT_COOKING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeTypeInit.COOKING_POT_COOKING_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CookingPotCookingRecipe> {
        @Override
        public CookingPotCookingRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            List<IRequirement> requirements = new ArrayList<>();
            JSONUtils.getAsJsonArray(serializedRecipe, "requirements").forEach(requirement -> requirements.add(IRequirement.fromJson(requirement)));
            ItemStack result = JsonUtils.getAsItemStack(serializedRecipe, "result");
            int priority = JSONUtils.getAsInt(serializedRecipe, "priority");
            int weight = JSONUtils.getAsInt(serializedRecipe, "weight", 1);
            int cookingTime = JSONUtils.getAsInt(serializedRecipe, "cookingtime");
            return new CookingPotCookingRecipe(recipeId, requirements, result, priority, weight, cookingTime);
        }

        @Nullable
        @Override
        public CookingPotCookingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            List<IRequirement> requirements = new ArrayList<>();
            int length = buffer.readVarInt();
            for (int i = 0; i < length; i++) {
                requirements.add(IRequirement.fromNetwork(buffer));
            }
            ItemStack result = buffer.readItem();
            int priority = buffer.readVarInt();
            int weight = buffer.readVarInt();
            int cookingTime = buffer.readVarInt();
            return new CookingPotCookingRecipe(recipeId, requirements, result, priority, weight, cookingTime);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, CookingPotCookingRecipe recipe) {
            buffer.writeVarInt(recipe.getRequirements().size());
            recipe.getRequirements().forEach(requirement -> requirement.toNetwork(buffer));
            buffer.writeItem(recipe.getResult());
            buffer.writeVarInt(recipe.getPriority());
            buffer.writeVarInt(recipe.getWeight());
            buffer.writeVarInt(recipe.getCookingTime());
        }
    }
}
