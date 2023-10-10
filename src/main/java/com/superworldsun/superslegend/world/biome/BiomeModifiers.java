package com.superworldsun.superslegend.world.biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BiomeModifiers {
    //Biome modifiers is a current work-around for how world gen works compared to 1.18, will add tree/ore ones later
    //See 24:05 at https://www.youtube.com/watch?v=tYAC5zRh12A for more info
    public static final DeferredRegister<Codec<? extends net.minecraftforge.common.world.BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, SupersLegendMain.MOD_ID);


    public static RegistryObject<Codec<VegetalBiomeModifier>> VEGETAL_MODIFIER = BIOME_MODIFIERS.register("vegetal", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(VegetalBiomeModifier::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(VegetalBiomeModifier::feature)
            ).apply(builder, VegetalBiomeModifier::new)));

    public static void register(IEventBus eventBus) {
        BIOME_MODIFIERS.register(eventBus);
    }
}
