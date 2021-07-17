package com.superworldsun.superslegend.util;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class BiomeBuilder {

    public static ConfiguredSurfaceBuilder<?> getSurfaceBuilder(final RegistryKey<ConfiguredSurfaceBuilder<?>> key) {
        return WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(key);
    }

    public static Biome makeLostWoodsBiome(
            final ConfiguredSurfaceBuilder<?> surfaceBuilder,
            final float depth,
            final float scale,
            final boolean hasVillageAndOutpost
    ) {
        final MobSpawnInfo.Builder mobSpawnInfoBuilder = new MobSpawnInfo.Builder()
                .setPlayerCanSpawn();
                //.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), 50, 1, 4));

        //final MoodSoundAmbience ambienceSound = new MoodSoundAmbience(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 5000, 8, 2.0D);

        final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
                .surfaceBuilder(surfaceBuilder)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PLAIN_VEGETATION)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);

        if (hasVillageAndOutpost) {
            biomeGenerationSettingBuilder.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
        }

        DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomeGenerationSettingBuilder);
        DefaultBiomeFeatures.addDefaultCarvers(biomeGenerationSettingBuilder);
        DefaultBiomeFeatures.addDefaultMonsterRoom(biomeGenerationSettingBuilder);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingBuilder);
        DefaultBiomeFeatures.addDefaultGrass(biomeGenerationSettingBuilder);
        DefaultBiomeFeatures.addDefaultSprings(biomeGenerationSettingBuilder);

        return new Biome.Builder()
                .precipitation(Biome.RainType.RAIN)
                .biomeCategory(Biome.Category.PLAINS)
                .depth(depth)
                .scale(scale)
                .temperature(0.8f)
                .downfall(0.3f)
                .specialEffects(
                        new BiomeAmbience.Builder()
                                .waterColor(0x55274c)
                                .waterFogColor(0x503b72)
                                .fogColor(0x906c80)
                                .skyColor(0x746776)
                                .grassColorOverride(0x551710)
                                .foliageColorOverride(0x442e35)
                                //.ambientMoodSound(ambienceSound)
                                //.ambientLoopSound(ambienceSound)
                                .build()
                )
                .mobSpawnSettings(mobSpawnInfoBuilder.build())
                .generationSettings(biomeGenerationSettingBuilder.build())
                .build();
    }
}