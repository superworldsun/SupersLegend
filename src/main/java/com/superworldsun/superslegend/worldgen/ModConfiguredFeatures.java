package com.superworldsun.superslegend.worldgen;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_MASTER_ORE_KEY = registerKey("master_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_ORE_BURIED_KEY = registerKey("dark_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_ORE_EXPOSED_DISCARDED_KEY = registerKey("dark_ore_exposed_discarded");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ODD_MUSHROOM_KEY = registerKey("odd_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGIC_MUSHROOM_KEY = registerKey("magic_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEKU_FLOWER_KEY = registerKey("deku_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_DEKU_FLOWER_KEY = registerKey("yellow_deku_flower");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
//        RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
//        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldSapphireOres = List.of(OreConfiguration.target(stoneReplaceable,
                BlockInit.MASTER_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                BlockInit.DEEPSLATE_MASTER_ORE_BLOCK.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworldDarkOres = List.of(OreConfiguration.target(stoneReplaceable,
                BlockInit.DARK_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                BlockInit.DEEPSLATE_DARK_ORE_BLOCK.get().defaultBlockState()));

        // Scattered ore supports a size of one correctly; the normal ore-vein feature's tiny sphere can place nothing.
        register(context, OVERWORLD_MASTER_ORE_KEY, Feature.SCATTERED_ORE, new OreConfiguration(overworldSapphireOres, 1));

        // Deep and middle Dark Ore may generate beside caves. The upper band uses the second configuration,
        // whose 100% air-exposure discard chance keeps every exposed ore block from generating.
        register(context, DARK_ORE_BURIED_KEY, Feature.ORE, new OreConfiguration(overworldDarkOres, 4));
        register(context, DARK_ORE_EXPOSED_DISCARDED_KEY, Feature.ORE, new OreConfiguration(overworldDarkOres, 4, 1.0F));

        registerPatch(context, ODD_MUSHROOM_KEY, BlockInit.ODD_MUSHROOM.get().defaultBlockState(), 8, 4, 1);
        registerPatch(context, MAGIC_MUSHROOM_KEY, BlockInit.MAGIC_MUSHROOM.get().defaultBlockState(), 8, 4, 1);
        registerPatch(context, DEKU_FLOWER_KEY, BlockInit.DEKU_FLOWER_BLOCK.get().defaultBlockState(), 14, 6, 1);
        registerPatch(context, YELLOW_DEKU_FLOWER_KEY, BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get().defaultBlockState(), 3, 6, 1);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static void registerPatch(BootstapContext<ConfiguredFeature<?, ?>> context,
                                      ResourceKey<ConfiguredFeature<?, ?>> key,
                                      net.minecraft.world.level.block.state.BlockState state,
                                      int tries, int horizontalSpread, int verticalSpread) {
        BlockPredicate placement = BlockPredicate.allOf(
                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), Blocks.GRASS_BLOCK));
        RandomPatchConfiguration configuration = new RandomPatchConfiguration(
                tries, horizontalSpread, verticalSpread,
                PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(state)), placement));
        register(context, key, Feature.FLOWER, configuration);
    }
}
