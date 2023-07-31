package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlueRupee extends Item {
    public BlueRupee(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getCount() < 4) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            // Check if the player has enough space in their inventory
            int emptySlots = 0;
            NonNullList<ItemStack> inventory = player.getInventory().items;
            for (ItemStack stack : inventory) {
                if (stack.isEmpty()) {
                    emptySlots++;
                }
            }
            ItemStack newItem = new ItemStack(ItemInit.RED_RUPEE.get(), 1);

            int remainingToAdd = 1;
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack slotStack = inventory.get(i);
                if (!slotStack.isEmpty() && slotStack.getItem() == newItem.getItem() && slotStack.getCount() < slotStack.getMaxStackSize()) {
                    int spaceAvailable = slotStack.getMaxStackSize() - slotStack.getCount();
                    if (remainingToAdd <= spaceAvailable) {
                        slotStack.grow(remainingToAdd);
                        remainingToAdd = 0;
                        break;
                    } else {
                        slotStack.setCount(slotStack.getMaxStackSize());
                        remainingToAdd -= spaceAvailable;
                    }
                }
            }

            while (emptySlots > 0 && remainingToAdd > 0) {
                ItemStack newStack = newItem.copy();
                int stackSize = Math.min(remainingToAdd, newItem.getMaxStackSize());
                newStack.setCount(stackSize);
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack slotStack = inventory.get(i);
                    if (slotStack.isEmpty()) {
                        inventory.set(i, newStack);
                        remainingToAdd -= stackSize;
                        emptySlots--;
                        break;
                    }
                }
            }

            if (remainingToAdd == 0) {
                itemstack.shrink(4);

                // Play a sound when the new item is created
                //TODO add rupee sound effect
                player.playSound(SoundEvents.ITEM_PICKUP, 1.0f, 1.0f);

                return InteractionResultHolder.success(itemstack);
            }
        }

        return InteractionResultHolder.pass(itemstack);
    }
}