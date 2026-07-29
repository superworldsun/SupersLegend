package com.superworldsun.superslegend.recipe;

import com.superworldsun.superslegend.registries.RecipeSerializerInit;
import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Value-preserving shapeless upgrades for rupees. Every occupied crafting slot
 * contributes one rupee, and mixed denominations are supported. The recipe
 * only succeeds when the exact total is another registered denomination.
 */
public class RupeeCombiningRecipe extends CustomRecipe {
    public RupeeCombiningRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(@NotNull CraftingContainer inventory, @NotNull Level level) {
        return findResult(inventory) != null;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer inventory,
                                       @NotNull RegistryAccess registryAccess) {
        RupeeValue result = findResult(inventory);
        return result == null ? ItemStack.EMPTY : new ItemStack(result.item());
    }

    private static RupeeValue findResult(CraftingContainer inventory) {
        int totalValue = 0;
        int ingredientCount = 0;

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.isEmpty()) {
                continue;
            }

            RupeeValue rupee = RupeeValue.from(stack);
            if (rupee == null) {
                return null;
            }

            // Vanilla crafting consumes one item from each occupied slot.
            totalValue += rupee.value();
            ingredientCount++;
        }

        // A single rupee would merely craft into itself rather than upgrading.
        if (ingredientCount < 2) {
            return null;
        }

        RupeeValue result = RupeeValue.fromValue(totalValue);
        return result == RupeeValue.GREEN ? null : result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.RUPEE_COMBINING.get();
    }
}
