package com.superworldsun.superslegend.worldgen;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_MASTER_ORE = registerKey("add_master_ore");
    public static final ResourceKey<BiomeModifier> ADD_DARK_ORE = registerKey("add_dark_ore");
    public static final ResourceKey<BiomeModifier> ADD_ODD_MUSHROOM = registerKey("add_odd_mushroom");
    public static final ResourceKey<BiomeModifier> ADD_MAGIC_MUSHROOM = registerKey("add_magic_mushroom");
    public static final ResourceKey<BiomeModifier> ADD_DEKU_FLOWER_SWAMP = registerKey("add_deku_flower_swamp");
    public static final ResourceKey<BiomeModifier> ADD_DEKU_FLOWER_JUNGLE = registerKey("add_deku_flower_jungle");
    public static final ResourceKey<BiomeModifier> ADD_YELLOW_DEKU_FLOWER_SWAMP = registerKey("add_yellow_deku_flower_swamp");
    public static final ResourceKey<BiomeModifier> ADD_YELLOW_DEKU_FLOWER_JUNGLE = registerKey("add_yellow_deku_flower_jungle");
//    public static final ResourceKey<BiomeModifier> ADD_NETHER__ORE = registerKey("add_nether__ore");
//    public static final ResourceKey<BiomeModifier> ADD_END__ORE = registerKey("add_end__ore");


    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_MASTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MASTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DARK_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(ModPlacedFeatures.DARK_ORE_UPPER_PLACED_KEY),
                        placedFeatures.getOrThrow(ModPlacedFeatures.DARK_ORE_MIDDLE_PLACED_KEY),
                        placedFeatures.getOrThrow(ModPlacedFeatures.DARK_ORE_DEEP_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        addPlant(context, ADD_ODD_MUSHROOM, biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                placedFeatures.getOrThrow(ModPlacedFeatures.ODD_MUSHROOM_PLACED_KEY));
        addPlant(context, ADD_MAGIC_MUSHROOM, biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                placedFeatures.getOrThrow(ModPlacedFeatures.MAGIC_MUSHROOM_PLACED_KEY));
        addPlant(context, ADD_DEKU_FLOWER_SWAMP, biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                placedFeatures.getOrThrow(ModPlacedFeatures.DEKU_FLOWER_PLACED_KEY));
        addPlant(context, ADD_DEKU_FLOWER_JUNGLE, biomes.getOrThrow(BiomeTags.IS_JUNGLE),
                placedFeatures.getOrThrow(ModPlacedFeatures.DEKU_FLOWER_PLACED_KEY));
        addPlant(context, ADD_YELLOW_DEKU_FLOWER_SWAMP, biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                placedFeatures.getOrThrow(ModPlacedFeatures.YELLOW_DEKU_FLOWER_PLACED_KEY));
        addPlant(context, ADD_YELLOW_DEKU_FLOWER_JUNGLE, biomes.getOrThrow(BiomeTags.IS_JUNGLE),
                placedFeatures.getOrThrow(ModPlacedFeatures.YELLOW_DEKU_FLOWER_PLACED_KEY));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    private static void addPlant(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> key,
                                 HolderSet<net.minecraft.world.level.biome.Biome> biomes,
                                 net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> feature) {
        context.register(key, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes, HolderSet.direct(feature), GenerationStep.Decoration.VEGETAL_DECORATION));
    }
}
