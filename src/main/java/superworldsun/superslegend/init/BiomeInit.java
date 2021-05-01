package superworldsun.superslegend.init;

import net.minecraft.block.Block;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.world.biome.LostWoodsBiome;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
//import net.minecraft.block.material.Material;
//import superworldsun.superslegend.objects.blocks.BlockSpikes;

public class BiomeInit {

	/*public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, SupersLegend.modid);

	//Test
	public static final RegistryObject<Biome> VOLCANO = createBiome("volcano", LostWoodsBiome::makeAVolcanoBiome);

	public static final RegistryKey<Biome> VOLCANO_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "volcano"));



	//Biomas
	public static final RegistryObject<Biome> ARCTIC = createBiome("arctic", ModBiomeMaker::makeAArcticBiome);
	public static final RegistryObject<Biome> BADLANDS = createBiome("badlands", ModBiomeMaker::makeABadlandsBiome);
	public static final RegistryObject<Biome> BEACH = createBiome("beach", ModBiomeMaker::makeABeachBiome);
	public static final RegistryObject<Biome> SWAMP = createBiome("swamp", ModBiomeMaker::makeASwampBiome);
	public static final RegistryObject<Biome> BOREAL_FOREST = createBiome("boreal_forest", ModBiomeMaker::makeABorealForestBiome);
	public static final RegistryObject<Biome> CANYON = createBiome("canyon", ModBiomeMaker::makeACanyonBiome);
	public static final RegistryObject<Biome> DUNES = createBiome("dunes", ModBiomeMaker::makeADunesBiome);
	public static final RegistryObject<Biome> GRASSLAND = createBiome("grassland", ModBiomeMaker::makeAGrasslandBiome);
	public static final RegistryObject<Biome> HIGH_DESERT = createBiome("high_desert", ModBiomeMaker::makeAHighDesertBiome);
	public static final RegistryObject<Biome> ISLAND = createBiome("island", ModBiomeMaker::makeAIslandBiome);
	public static final RegistryObject<Biome> JUNGLE = createBiome("jungle", ModBiomeMaker::makeAJungleBiome);
	public static final RegistryObject<Biome> LOW_DESERT = createBiome("low_desert", ModBiomeMaker::makeALowDesertBiome);
	public static final RegistryObject<Biome> MOUNTAINS = createBiome("mountains", ModBiomeMaker::makeAMountainsBiome);
	public static final RegistryObject<Biome> DESERT = createBiome("desert", ModBiomeMaker::makeADesertBiome);
	public static final RegistryObject<Biome> OASIS = createBiome("oasis", ModBiomeMaker::makeAOasisBiome);
	public static final RegistryObject<Biome> REDWOOD_FOREST = createBiome("redwood_forest", ModBiomeMaker::makeARedwoodForestBiome);
	public static final RegistryObject<Biome> RIVER = createBiome("river", ModBiomeMaker::makeARiverBiome);
	public static final RegistryObject<Biome> SNOW = createBiome("snow", ModBiomeMaker::makeASnowBiome);
	public static final RegistryObject<Biome> TUNDRA = createBiome("tundra", ModBiomeMaker::makeATundraBiome);
	public static final RegistryObject<Biome> VOLCAN = createBiome("volcan", ModBiomeMaker::makeAVolcanBiome);
	public static final RegistryObject<Biome> OCEAN = createBiome("ocean", ModBiomeMaker::makeAOceanBiome);

	//Key
	public static final RegistryKey<Biome> ARCTIC_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "arctic"));
	public static final RegistryKey<Biome> BADLANDS_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "badlands"));
	public static final RegistryKey<Biome> BEACH_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "beach"));
	public static final RegistryKey<Biome> SWAMP_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "swamp"));
	public static final RegistryKey<Biome> BOREAL_FOREST_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "boreal_forest"));
	public static final RegistryKey<Biome> CANYON_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "canyon"));
	public static final RegistryKey<Biome> DUNES_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "dunes"));
	public static final RegistryKey<Biome> GRASSLAND_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "grassland"));
	public static final RegistryKey<Biome> HIGH_DESERT_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "high_desert"));
	public static final RegistryKey<Biome> ISLAND_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "island"));
	public static final RegistryKey<Biome> JUNGLE_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "jungle"));
	public static final RegistryKey<Biome> LOW_DESERT_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "low_desert"));
	public static final RegistryKey<Biome> MOUNTAINS_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "mountains"));
	public static final RegistryKey<Biome> DESERT_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "desert"));
	public static final RegistryKey<Biome> OASIS_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "oasis"));
	public static final RegistryKey<Biome> REDWOOD_FOREST_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "redwood_forest"));
	public static final RegistryKey<Biome> RIVER_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "river"));
	public static final RegistryKey<Biome> SNOW_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "snow"));
	public static final RegistryKey<Biome> TUNDRA_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "tundra"));
	public static final RegistryKey<Biome> VOLCAN_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "volcan"));
	public static final RegistryKey<Biome> OCEAN_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DinosExpansion.MOD_ID, "ocean"));




	public static RegistryObject<Biome> createBiome(String name, Supplier<Biome> biome)
	{
		return BIOMES.register(name, biome);
	}

	//Generation
	public static void addBiomesToOverworld(BiomeLoadingEvent event)
	{
		if (event.getName().equals(VOLCANO.get().getRegistryName())) {
			BiomeManager.addBiome(BiomeManager.BiomeType.ICY, new BiomeManager.BiomeEntry(VOLCANO_KEY, 1));
			BiomeDictionary.addTypes(VOLCANO_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		}

		if(event.getName().equals(ARCTIC.get().getRegistryName())){
			BiomeManager.addBiome(BiomeManager.BiomeType.ICY, new BiomeManager.BiomeEntry(ARCTIC_KEY, 1));
			BiomeDictionary.addTypes(ARCTIC_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		}

		if(event.getName().equals(BADLANDS.get().getRegistryName())){
			BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(BADLANDS_KEY, 1));
			BiomeDictionary.addTypes(BADLANDS_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);

		}

		if(event.getName().equals(BEACH.get().getRegistryName())){
			BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BEACH_KEY, 1));
			BiomeDictionary.addTypes(BEACH_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.RARE);

		}


		BiomeDictionary.addTypes(SWAMP_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(BOREAL_FOREST_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(CANYON_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(DUNES_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(GRASSLAND_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(HIGH_DESERT_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(ISLAND_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(JUNGLE_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(LOW_DESERT_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(MOUNTAINS_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(DESERT_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(OASIS_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(REDWOOD_FOREST_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(RIVER_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(SNOW_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(TUNDRA_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(VOLCAN_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);
		BiomeDictionary.addTypes(OCEAN_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY);


	}*/

}