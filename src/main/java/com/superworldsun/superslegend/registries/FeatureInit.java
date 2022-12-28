package com.superworldsun.superslegend.registries;

import com.google.common.collect.ImmutableSet;
import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FeatureInit {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, SupersLegendMain.MOD_ID);

    public static final class Configured {

        public static final ConfiguredFeature<?, ?> PATCH_MAGIC_MUSHROOM =
                Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new
                SimpleBlockStateProvider(BlockInit.MAGIC_MUSHROOM.get().defaultBlockState()),
                SimpleBlockPlacer.INSTANCE).xspread(4).yspread(1).zspread(4).tries(8).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build())
                .decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(8);

        public static final ConfiguredFeature<?, ?> PATCH_ODD_MUSHROOM =
                Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new
                SimpleBlockStateProvider(BlockInit.ODD_MUSHROOM.get().defaultBlockState()),
                SimpleBlockPlacer.INSTANCE).xspread(4).yspread(1).zspread(4).tries(8).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build())
                .decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(8);

        public static final ConfiguredFeature<?, ?> PATCH_DEKU_FLOWER =
                Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new
                SimpleBlockStateProvider(BlockInit.DEKU_FLOWER_BLOCK.get().defaultBlockState()),
                SimpleBlockPlacer.INSTANCE).xspread(6).yspread(1).zspread(4).tries(14).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build())
                .decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(14);

        public static final ConfiguredFeature<?, ?> PATCH_YELLOW_DEKU_FLOWER =
                Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new
                SimpleBlockStateProvider(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get().defaultBlockState()),
                SimpleBlockPlacer.INSTANCE).xspread(6).yspread(1).zspread(6).tries(3).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build())
                .decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(3);

        private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(SupersLegendMain.MOD_ID, name), configuredFeature);
        }

        public static void registerConfiguredFeatures()
        {
            register("patch_magic_mushroom", PATCH_MAGIC_MUSHROOM);
            register("patch_odd_mushroom", PATCH_ODD_MUSHROOM);
            register("patch_deku_flower", PATCH_DEKU_FLOWER);
            register("patch_yellow_deku_flower", PATCH_YELLOW_DEKU_FLOWER);
        }
    }
}