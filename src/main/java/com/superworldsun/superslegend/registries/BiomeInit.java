package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.BiomeBuilder;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {

    public static final DeferredRegister<Item> BIOMES = DeferredRegister.create(ForgeRegistries.ITEMS,
            SupersLegendMain.MOD_ID);

    /*public static final RegistryObject<Biome> LOSTWOODS = BIOMES.register("lostwoods", () -> BiomeBuilder.makeLostWoodsBiome(
            BiomeBuilder.getSurfaceBuilder(ConfiguredSurfaceBuilders.LOSTWOODS),
            0.18f, 0.1f, true)
    );*/
}
