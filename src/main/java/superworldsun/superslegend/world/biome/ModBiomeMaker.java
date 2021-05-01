package superworldsun.superslegend.world.biome;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

/*public class ModBiomeMaker {

    private static <C extends ISurfaceBuilderConfig> BiomeGenerationSettings.Builder genSettings(SurfaceBuilder<C> surfaceBuilder, C config)
    {
        return new BiomeGenerationSettings.Builder().surfaceBuilder(surfaceBuilder.configured(config));
    }

    public static Biome makeAVolcanoBiome()
    {
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);
        DefaultBiomeFeatures.snowySpawns(mobspawninfo$builder);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.IGLOO);
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultCarvers(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultLakes(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_SPIKE);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultFlowers(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultGrass(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultExtraVegetation(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultSprings(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addSurfaceFreezing(biomegenerationsettings$builder);


        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();
    }

    public static Biome makeAArcticBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();

    }

    public static Biome makeABadlandsBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeABeachBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_BEACH_SURFACE_BUILDER, ModSurfaceBuilders.BEACH_SURFACE_BUILDER);



        DefaultBiomeFeatures.commonSpawns(mobspawninfo$builder);
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomegenerationsettings$builder);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.MINESHAFT);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.BURIED_TREASURE);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.SHIPWRECH_BEACHED);
        DefaultBiomeFeatures.addDefaultCarvers(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultLakes(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultFlowers(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultGrass(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultExtraVegetation(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultSprings(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addSurfaceFreezing(biomegenerationsettings$builder);

        return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.BEACH)
                .depth(0.0F)
                .scale(0.025F)
                .temperature(0.95F)
                .downfall(1.0F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4445678).waterFogColor(270131).fogColor(0xB2EDFF).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeASwampBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeABorealForestBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeACanyonBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeADunesBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAGrasslandBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAHighDesertBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAIslandBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAJungleBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeALowDesertBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAMountainsBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeADesertBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAOasisBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeARedwoodForestBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeARiverBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeASnowBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeATundraBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAVolcanBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }

    public static Biome makeAOceanBiome(){

        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = genSettings(SurfaceBuilderInit.NAKED_VOLCANO_SURFACE_BUILDER, ModSurfaceBuilders.VOLCANO_SURFACE_BUILDER);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.EXTREME_HILLS)
                .depth(6.0F)
                .scale(0.45F)
                .temperature(0.5F)
                .downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder()).grassColorOverride(0x1c1c1c1c).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(BiomeUtil.calcSkyColor(0.8F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomegenerationsettings$builder.build()).build();


    }*/

