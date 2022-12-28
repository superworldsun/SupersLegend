package com.superworldsun.superslegend.worldgen.world;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.FeatureInit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class PlantGen {

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event)
    {
        ResourceLocation biome = event.getName();
        Biome.Category category = event.getCategory();
        if (biome == null) return;
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if (event.getCategory() == Biome.Category.SWAMP && event.getName().equals(Biomes.SWAMP.location()) ||
                event.getCategory() == Biome.Category.SWAMP && event.getName().equals(Biomes.SWAMP_HILLS.location()))
        {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_MAGIC_MUSHROOM);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_ODD_MUSHROOM);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_DEKU_FLOWER);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_YELLOW_DEKU_FLOWER);
        }

        if (event.getCategory() == Biome.Category.JUNGLE && event.getName().equals(Biomes.JUNGLE.location()) ||
                event.getCategory() == Biome.Category.JUNGLE && event.getName().equals(Biomes.JUNGLE_HILLS.location()) ||
                event.getCategory() == Biome.Category.JUNGLE && event.getName().equals(Biomes.JUNGLE_EDGE.location()) ||
                event.getCategory() == Biome.Category.JUNGLE && event.getName().equals(Biomes.MODIFIED_JUNGLE.location()) ||
                event.getCategory() == Biome.Category.JUNGLE && event.getName().equals(Biomes.MODIFIED_JUNGLE_EDGE.location()))
        {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_DEKU_FLOWER);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_YELLOW_DEKU_FLOWER);
        }
    }
}