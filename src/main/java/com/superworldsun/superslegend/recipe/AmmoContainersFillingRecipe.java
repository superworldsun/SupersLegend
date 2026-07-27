package com.superworldsun.superslegend.recipe;

import com.superworldsun.superslegend.items.ammobags.AmmoContainerItem;
import com.superworldsun.superslegend.registries.RecipeSerializerInit;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class AmmoContainersFillingRecipe extends CustomRecipe {
    public AmmoContainersFillingRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inventory, Level level) {
        ItemStack result = ItemStack.EMPTY;
        AmmoContainerItem containerItem = null;
        int containerSlot = -1;

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (!(stack.getItem() instanceof AmmoContainerItem foundContainer)) {
                continue;
            }
            if (containerItem != null) {
                return false;
            }

            containerItem = foundContainer;
            containerSlot = slot;
            result = stack.copy();
        }

        if (containerItem == null) {
            return false;
        }

        boolean foundAmmo = false;
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            if (slot == containerSlot || inventory.getItem(slot).isEmpty()) {
                continue;
            }

            ItemStack ammo = inventory.getItem(slot);
            if (!containerItem.canHoldItem(ammo) || containerItem.insert(result, ammo) <= 0) {
                return false;
            }
            foundAmmo = true;
        }
        return foundAmmo;
    }

    @Override
    public ItemStack assemble(CraftingContainer inventory, RegistryAccess registryAccess) {
        ItemStack result = ItemStack.EMPTY;
        AmmoContainerItem containerItem = null;
        int containerSlot = -1;

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.getItem() instanceof AmmoContainerItem foundContainer) {
                containerItem = foundContainer;
                containerSlot = slot;
                result = stack.copy();
                break;
            }
        }

        if (containerItem == null) {
            return ItemStack.EMPTY;
        }

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            if (slot != containerSlot && !inventory.getItem(slot).isEmpty()) {
                containerItem.insert(result, inventory.getItem(slot));
            }
        }
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inventory) {
        NonNullList<ItemStack> remainingItems = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        ItemStack simulatedContainer = ItemStack.EMPTY;
        AmmoContainerItem containerItem = null;
        int containerSlot = -1;

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.getItem() instanceof AmmoContainerItem foundContainer) {
                containerItem = foundContainer;
                containerSlot = slot;
                simulatedContainer = stack.copy();
                break;
            }
        }

        if (containerItem == null) {
            return remainingItems;
        }

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            if (slot == containerSlot || inventory.getItem(slot).isEmpty()) {
                continue;
            }

            ItemStack ammo = inventory.getItem(slot);
            int inserted = containerItem.insert(simulatedContainer, ammo);
            if (inserted < ammo.getCount()) {
                remainingItems.set(slot, ammo.copyWithCount(ammo.getCount() - inserted));
            }
            inventory.setItem(slot, ItemStack.EMPTY);
        }
        return remainingItems;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.AMMO_CONTAINERS_FILLING.get();
    }
}
