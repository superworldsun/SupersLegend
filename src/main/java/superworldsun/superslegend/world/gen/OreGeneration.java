package superworldsun.superslegend.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
//import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.lists.BlockList;


public class OreGeneration{
	/*private static CountRangeConfig master_ore_placement = new CountRangeConfig(3, 1, 7, 25);
	
																				//how often ,lowest point, highest point, maximum

	public static void setupOreWorldGen() {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			if (  biome.getCategory() == Biome.Category.THEEND || biome.getCategory() == Biome.Category.NETHER)
            {
                continue;
            }
			
			biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, BlockList.master_ore_block.getDefaultState(), 3), Placement.COUNT_RANGE, master_ore_placement));
																																																	// how many in a vein, (minimum is 3)
		}
	}*/

	/*private static void setupOreGeneration() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			biome.addFeature(Decoration.UNDERGROUND_ORES,
					Feature.ORE
							.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
									BlockList.master_ore_block.getBlock().getDefaultState(), 3))
							.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 1, 7, 25))));
		}
	}

	public static void generate() {
		setupOreGeneration();
	}*/



}