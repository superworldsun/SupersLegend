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
import org.apache.commons.lang3.tuple.Pair;

public class AmmoContainersFillingRecipe extends CustomRecipe {
    public AmmoContainersFillingRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inventory, Level level) {
        AmmoContainerItem ammoContainer = null;
        ItemStack result = null;
        int ammoContainerSlot = 0;
        int ammoStacksCount = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stackInSlot = inventory.getItem(i);

            if (stackInSlot.getItem() instanceof AmmoContainerItem ammoContainerItem) {
                // multiple ammo containers
                if (ammoContainer != null) {
                    return false;
                } else {
                    if (ammoContainerItem.getContents(stackInSlot) != null) {
                        // ammo container is already full
                        if (ammoContainerItem.getContents(stackInSlot).getRight() == ammoContainerItem.getCapacity()) {
                            return false;
                        }
                    }

                    ammoContainer = ammoContainerItem;
                    ammoContainerSlot = i;
                    result = stackInSlot.copy();
                }
            }
        }

        // no ammo containers found
        if (ammoContainer == null) {
            return false;
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (i == ammoContainerSlot) {
                continue;
            }

            if (inventory.getItem(i).isEmpty()) {
                continue;
            }

            // can't put this item into container
            if (!ammoContainer.canHoldItem(inventory.getItem(i)) || !ammoContainer.containsSameItem(result, inventory.getItem(i))) {
                return false;
            } else {
                if (ammoContainer.getContents(result) == null) {
                    ammoContainer.setItemStack(result, inventory.getItem(i));
                }

                ammoStacksCount++;
            }
        }

        // no ammo found
        if (ammoStacksCount == 0) {
            return false;
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer inventory, RegistryAccess registryAccess) {
        AmmoContainerItem ammoContainer = null;
        Pair<ItemStack, Integer> ammoContainerContents = null;
        ItemStack result = null;
        int ammoContainerSlot = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stackInSlot = inventory.getItem(i);

            if (stackInSlot.getItem() instanceof AmmoContainerItem ammoContainerItem) {
                ammoContainerContents = ammoContainerItem.getContents(stackInSlot);
                ammoContainer = ammoContainerItem;
                ammoContainerSlot = i;
                result = stackInSlot.copy();
                break;
            }
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (i == ammoContainerSlot) {
                continue;
            }

            if (inventory.getItem(i).isEmpty()) {
                continue;
            }

            ItemStack ammo = inventory.getItem(i);

            if (ammoContainerContents == null) {
                ammoContainer.setItemStack(result, ammo);
                ammoContainerContents = ammoContainer.getContents(result);
            } else if (ammoContainerContents.getRight() + ammo.getCount() <= ammoContainer.getCapacity()) {
                ammoContainer.setCount(result, ammoContainerContents.getRight() + ammo.getCount());
                ammoContainerContents = ammoContainer.getContents(result);
            } else {
                ammoContainer.setCount(result, ammoContainer.getCapacity());
                break;
            }
        }

        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inventory) {
        NonNullList<ItemStack> remainingItems = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        AmmoContainerItem ammoContainer = null;
        Pair<ItemStack, Integer> ammoContainerContents = null;
        ItemStack result = null;
        int ammoContainerSlot = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stackInSlot = inventory.getItem(i);

            if (stackInSlot.getItem() instanceof AmmoContainerItem ammoContainerItem) {
                ammoContainerContents = ammoContainerItem.getContents(stackInSlot);
                ammoContainer = ammoContainerItem;
                ammoContainerSlot = i;
                result = stackInSlot.copy();
                break;
            }
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (i == ammoContainerSlot) {
                continue;
            }

            if (inventory.getItem(i).isEmpty()) {
                continue;
            }

            ItemStack ammo = inventory.getItem(i);

            if (ammoContainerContents == null) {
                ammoContainer.setItemStack(result, ammo);
                ammoContainerContents = ammoContainer.getContents(result);
                inventory.setItem(i, ItemStack.EMPTY);
            } else if (ammoContainerContents.getRight() + ammo.getCount() <= ammoContainer.getCapacity()) {
                ammoContainer.setCount(result, ammoContainerContents.getRight() + ammo.getCount());
                ammoContainerContents = ammoContainer.getContents(result);
                inventory.setItem(i, ItemStack.EMPTY);
            } else {
                int extraAmmo = ammoContainerContents.getRight() + ammo.getCount() - ammoContainer.getCapacity();
                ammoContainer.setCount(result, ammoContainer.getCapacity());
                ammoContainerContents = ammoContainer.getContents(result);
                ammo.setCount(extraAmmo);
                remainingItems.set(i, ammo.copy());
                inventory.setItem(i, ItemStack.EMPTY);
            }
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
