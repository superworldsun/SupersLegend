package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum RupeeValue {
    GOLD(300),
    SILVER(100),
    PURPLE(50),
    RED(20),
    YELLOW(10),
    BLUE(5),
    GREEN(1);

    private final int value;

    RupeeValue(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public Item item() {
        return switch (this) {
            case GOLD -> ItemInit.GOLD_RUPEE.get();
            case SILVER -> ItemInit.SILVER_RUPEE.get();
            case PURPLE -> ItemInit.PURPLE_RUPEE.get();
            case RED -> ItemInit.RED_RUPEE.get();
            case YELLOW -> ItemInit.YELLOW_RUPEE.get();
            case BLUE -> ItemInit.BLUE_RUPEE.get();
            case GREEN -> ItemInit.RUPEE.get();
        };
    }

    /** The same denomination-specific sound used when this rupee is collected. */
    public SoundEvent pickupSound() {
        return switch (this) {
            case GREEN -> SoundInit.RUPEE_GREEN.get();
            case BLUE -> SoundInit.RUPEE_BLUE.get();
            case YELLOW -> SoundInit.RUPEE_YELLOW.get();
            case RED -> SoundInit.RUPEE_RED.get();
            case PURPLE -> SoundInit.RUPEE_PURPLE.get();
            case SILVER -> SoundInit.RUPEE_SILVER.get();
            case GOLD -> SoundInit.RUPEE_GOLD.get();
        };
    }

    public static RupeeValue from(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Item item = stack.getItem();
        for (RupeeValue rupee : values()) {
            if (item == rupee.item()) {
                return rupee;
            }
        }
        return null;
    }

    /** Returns the denomination with exactly this value, if one exists. */
    public static RupeeValue fromValue(int value) {
        for (RupeeValue rupee : values()) {
            if (rupee.value == value) {
                return rupee;
            }
        }
        return null;
    }

    public static int totalValue(ItemStack stack) {
        RupeeValue rupee = from(stack);
        return rupee == null ? 0 : rupee.value * stack.getCount();
    }

    /**
     * Converts an exact rupee value into the fewest item stacks possible.
     * Stack sizes follow the registered rupee items, so this remains correct if
     * their maximum stack size changes later.
     */
    public static List<ItemStack> splitIntoStacks(int amount) {
        List<ItemStack> stacks = new ArrayList<>();
        int remaining = Math.max(0, amount);

        for (RupeeValue rupee : values()) {
            int count = remaining / rupee.value;
            remaining %= rupee.value;
            while (count > 0) {
                int stackCount = Math.min(rupee.item().getMaxStackSize(), count);
                stacks.add(new ItemStack(rupee.item(), stackCount));
                count -= stackCount;
            }
        }
        return stacks;
    }
}
