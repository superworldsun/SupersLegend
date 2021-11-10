package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BiomeInit {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, SupersLegendMain.MOD_ID);

    static {
        createBiome("dark_world_forest", BiomeMaker::theVoidBiome);
    }

    //register the biome and add it to the biome manager
    public static RegistryKey<Biome> DARK_WORLD_FOREST = registryKey("dark_world_forest");


    public static RegistryKey<Biome> registryKey(String name) {
        return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    public static RegistryObject<Biome> createBiome(String name, Supplier<Biome> biome) {
        return BIOMES.register(name, biome);
    }

    public static void registerBiomes() {
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(DARK_WORLD_FOREST, 5));
    }
}