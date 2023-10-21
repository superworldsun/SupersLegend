package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Rupee extends Item {
    public Rupee(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getCount() < 5) {
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
            ItemStack newItem = new ItemStack(ItemInit.BLUE_RUPEE.get(), 1);

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
                itemstack.shrink(5);
                player.playSound(SoundInit.RUPEE_GREEN.get(), 1.0f, 1.0f);

                return InteractionResultHolder.success(itemstack);
            }
        }

        return InteractionResultHolder.pass(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("1 Rupee").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Holding 5 in a single stack and then Right-click will convert the " +
                    "rupees into a single Blue Rupee. Rupees can be used for ammo, fuel, or trading").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.literal("Rupees can be found by slaying monsters").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
