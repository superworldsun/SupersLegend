package com.superworldsun.superslegend.items.cookingpot;

public enum FoodUseDuration {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    SUPER_FAST(16),
    FAST(24),
    NORMAL(32),
    SLOW(40),
    SUPER_SLOW(48);

    public final int val;

    FoodUseDuration(int val) {
        this.val = val;
    }
}
