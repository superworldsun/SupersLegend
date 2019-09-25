package superworldsun.superslegend.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.lists.BlockList;

public class OreGeneration{
	private static CountRangeConfig copper_ore_placement = new CountRangeConfig(10, 20, 0, 100);


	public static void setupOreWorldGen() {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			if (  biome.getCategory() == Biome.Category.THEEND || biome.getCategory() == Biome.Category.NETHER)
            {
                continue;
            }
			
			biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, BlockList.master_ore_block.getDefaultState(), 10), Placement.COUNT_RANGE, copper_ore_placement));
			
		}
	}
}