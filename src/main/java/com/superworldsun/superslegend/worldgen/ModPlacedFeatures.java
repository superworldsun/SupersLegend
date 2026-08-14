package com.superworldsun.superslegend.worldgen;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> MASTER_ORE_PLACED_KEY = registerKey("master_ore_placed");
    public static final ResourceKey<PlacedFeature> DARK_ORE_UPPER_PLACED_KEY = registerKey("dark_ore_upper_placed");
    public static final ResourceKey<PlacedFeature> DARK_ORE_MIDDLE_PLACED_KEY = registerKey("dark_ore_middle_placed");
    public static final ResourceKey<PlacedFeature> DARK_ORE_DEEP_PLACED_KEY = registerKey("dark_ore_deep_placed");
    public static final ResourceKey<PlacedFeature> ODD_MUSHROOM_PLACED_KEY = registerKey("odd_mushroom_placed");
    public static final ResourceKey<PlacedFeature> MAGIC_MUSHROOM_PLACED_KEY = registerKey("magic_mushroom_placed");
    public static final ResourceKey<PlacedFeature> DEKU_FLOWER_PLACED_KEY = registerKey("deku_flower_placed");
    public static final ResourceKey<PlacedFeature> YELLOW_DEKU_FLOWER_PLACED_KEY = registerKey("yellow_deku_flower_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, MASTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_MASTER_ORE_KEY),
                // Three placement attempts from the bottom of the world through Y 19.
                // The lower bound is extended to -64 so deepslate Master Ore can generate naturally.
                ModOrePlacement.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(19))));

        // Dark Ore is deliberately rarer than diamond and becomes progressively more common with depth.
        // Upper keeps two attempts per successful roll. The middle and deep bands make three
        // attempts so Dark Ore becomes moderately more common as the player mines deeper.
        // Upper: 2 attempts per 5 chunks, middle: 3 per 3 chunks, deep: 3 per 2 chunks.
        register(context, DARK_ORE_UPPER_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DARK_ORE_EXPOSED_DISCARDED_KEY),
                ModOrePlacement.rareOrePlacement(5, 2,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-3), VerticalAnchor.absolute(32))));
        register(context, DARK_ORE_MIDDLE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DARK_ORE_BURIED_KEY),
                ModOrePlacement.rareOrePlacement(3, 3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-31), VerticalAnchor.absolute(-4))));
        register(context, DARK_ORE_DEEP_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DARK_ORE_BURIED_KEY),
                ModOrePlacement.rareOrePlacement(2, 3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-32))));

        registerPlant(context, ODD_MUSHROOM_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.ODD_MUSHROOM_KEY), 8);
        registerPlant(context, MAGIC_MUSHROOM_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.MAGIC_MUSHROOM_KEY), 8);
        registerPlant(context, DEKU_FLOWER_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DEKU_FLOWER_KEY), 28);
        registerPlant(context, YELLOW_DEKU_FLOWER_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.YELLOW_DEKU_FLOWER_KEY), 12);
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void registerPlant(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                      Holder<ConfiguredFeature<?, ?>> configuration, int rarity) {
        register(context, key, configuration, List.of(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome()));
    }
}
