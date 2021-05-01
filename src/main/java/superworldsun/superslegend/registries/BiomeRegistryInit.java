package superworldsun.superslegend.registries;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import superworldsun.superslegend.lists.BiomeList;
import superworldsun.superslegend.world.biome.LostWoodsBiome;

public class BiomeRegistryInit extends BiomeRegistry {


    private static final Int2ObjectMap<RegistryKey<Biome>> idToKeyMap = new Int2ObjectArrayMap<>();

    private static Biome register(int id, RegistryKey<Biome> key, Biome biome) {
        idToKeyMap.put(id, key);
        return WorldGenRegistries.register(WorldGenRegistries.BIOME, id, key, biome);
    }

    public static RegistryKey<Biome> getKeyFromID(int id) {
        return ((net.minecraftforge.registries.ForgeRegistry<Biome>)net.minecraftforge.registries.ForgeRegistries.BIOMES).getKey(id);
    }

    static {
        register(1, BiomeList.LostWoodsBiome, LostWoodsBiome.LostWoodsBiome(0.1F, 0.2F));

        //LostWoodsBiome.LostWoodsBiome(false));
    }

}