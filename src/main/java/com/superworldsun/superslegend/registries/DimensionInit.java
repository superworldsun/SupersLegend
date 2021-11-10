package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class DimensionInit {
    public static final RegistryKey<World> DARK_WORLD_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(SupersLegendMain.MOD_ID, "dark_world"));
}