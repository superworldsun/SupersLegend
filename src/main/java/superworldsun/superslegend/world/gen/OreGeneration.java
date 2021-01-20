package superworldsun.superslegend.world.gen;

import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.BlockList;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.ArrayList;



@Mod.EventBusSubscriber
public class OreGeneration {

	private static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<>();

	public static void registerOres(){

		//Overworld Ore Register
		overworldOres.add(register("master_ore_block", Feature.ORE.withConfiguration(new OreFeatureConfig(
				OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockList.master_ore_block.getDefaultState(), 4)) //Vein Size
				.range(19).square() //Spawn height start
				.func_242731_b(3))); //Chunk spawn frequency
		overworldOres.add(register("earthy_deposit", Feature.ORE.withConfiguration(new OreFeatureConfig(
				new BlockMatchRuleTest(Blocks.DIRT), BlockList.master_ore_block.getDefaultState(), 4)) //Vein Size
				.range(19).square() //Spawn height start
				.func_242731_b(3))); //Chunk spawn frequency
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void gen(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		for(ConfiguredFeature<?, ?> ore : overworldOres){
			if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
		}
	}

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, SupersLegend.modid + ":" + name, configuredFeature);
	}

}