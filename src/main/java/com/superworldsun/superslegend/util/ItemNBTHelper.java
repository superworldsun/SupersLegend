package com.superworldsun.superslegend.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class ItemNBTHelper {
    public static boolean detectNBT(ItemStack stack) {
        return stack.hasTag();
    }

    public static void initNBT(ItemStack stack) {
        if (!detectNBT(stack)) {
            injectNBT(stack, new CompoundTag());
        }
    }

    public static void injectNBT(ItemStack stack, CompoundTag nbt) {
        stack.setTag(nbt);
    }

    public static CompoundTag getNBT(ItemStack stack) {
        initNBT(stack);
        return stack.getTag();
    }

    public static void setInt(ItemStack stack, String tag, int i) {
        getNBT(stack).putInt(tag, i);
    }

    public static void setCompound(ItemStack stack, String tag, CompoundTag cmp) {
        if (!tag.equalsIgnoreCase("ench")) {
            getNBT(stack).put(tag, cmp);
        }
    }

    public static boolean verifyExistence(ItemStack stack, String tag) {
        return !stack.isEmpty() && detectNBT(stack) && getNBT(stack).contains(tag);
    }

    public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).getBoolean(tag) : defaultExpected;
    }

    public static int getInt(ItemStack stack, String tag, int defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).getInt(tag) : defaultExpected;
    }

    public static CompoundTag getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
        return verifyExistence(stack, tag) ? getNBT(stack).getCompound(tag) : (nullifyOnFail ? null : new CompoundTag());
    }
}
