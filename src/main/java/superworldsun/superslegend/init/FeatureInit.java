package superworldsun.superslegend.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.SupersLegend;
//import superworldsun.superslegend.structures.GraveyardStructure;

import java.util.function.Supplier;

public class FeatureInit {

    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, SupersLegend.modid);


    //public static final RegistryObject<Structure<NoFeatureConfig>> GRAVEYARD = registerStructure("graveyard", () -> (new GraveyardStructure(NoFeatureConfig.field_236558_a_)));


    private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
        return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
    }


    /*public static void setupStructures() {
        setupMapSpacingAndLand(
                GRAVEYARD.get(),
                new StructureSeparationSettings(20 , /* maximum distance apart in chunks between spawn attempts */
    //                    10 , /* minimum distance apart in chunks between spawn attempts */
  //                      987654321 ), /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */
 //               true);
 //   }


    /*public static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {

        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);


        if(transformSurroundingLand){
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }


        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }*/
}
