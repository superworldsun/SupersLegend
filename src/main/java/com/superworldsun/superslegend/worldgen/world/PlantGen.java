package com.superworldsun.superslegend.worldgen.world;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.FeatureInit;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class PlantGen {
	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		if (event.getName() == null) {
			return;
		}

		Biome.Category category = event.getCategory();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();

		// TODO: replace check with tags?
		if (category == Category.SWAMP) {
			if (event.getName().equals(Biomes.SWAMP.location()) || event.getName().equals(Biomes.SWAMP_HILLS.location())) {
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_MAGIC_MUSHROOM);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_ODD_MUSHROOM);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_DEKU_FLOWER);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_YELLOW_DEKU_FLOWER);
			}
		}

		// TODO: replace check with tags?
		if (category == Category.JUNGLE) {
			if (event.getName().equals(Biomes.JUNGLE.location()) || event.getName().equals(Biomes.JUNGLE_HILLS.location())
					|| event.getName().equals(Biomes.JUNGLE_EDGE.location()) || event.getName().equals(Biomes.MODIFIED_JUNGLE.location())
					|| event.getName().equals(Biomes.MODIFIED_JUNGLE_EDGE.location())) {
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_DEKU_FLOWER);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.Configured.PATCH_YELLOW_DEKU_FLOWER);
			}
		}
	}
}