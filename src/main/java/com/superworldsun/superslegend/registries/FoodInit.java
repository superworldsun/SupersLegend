package com.superworldsun.superslegend.registries;

import net.minecraft.world.food.FoodProperties;

public class FoodInit {
    public static final FoodProperties HYRULE_BASS = new FoodProperties.Builder().meat().saturationMod(0.3f).nutrition(1).build();
    public static final FoodProperties COOKED_HYRULE_BASS = new FoodProperties.Builder().meat().saturationMod(0.8f).nutrition(7).build();
    public static final FoodProperties HYLIAN_LOACH = new FoodProperties.Builder().meat().saturationMod(0.4f).nutrition(2).build();
    public static final FoodProperties COOKED_HYLIAN_LOACH = new FoodProperties.Builder().meat().saturationMod(0.9f).nutrition(9).build();
}