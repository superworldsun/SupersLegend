package superworldsun.superslegend.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import superworldsun.superslegend.SupersLegend;

public class ConfiguredStructures {

    //public static StructureFeature<?, ?> CONFIGURED_GRAVEYARD = FeatureInit.GRAVEYARD.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);


    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        //Registry.register(registry, new ResourceLocation(SupersLegend.modid, "configured_graveyard"), CONFIGURED_GRAVEYARD);


        //FlatGenerationSettings.STRUCTURES.put(FeatureInit.GRAVEYARD.get(), CONFIGURED_GRAVEYARD);
    }
}
