package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TagInit {
    public static final TagKey<EntityType<?>> WEAK_TO_FIRE = registerEntityTag("weak_to_fire");
    public static final TagKey<EntityType<?>> WEAK_TO_ICE = registerEntityTag("weak_to_ice");
    public static final TagKey<EntityType<?>> WEAK_TO_LIGHT = registerEntityTag("weak_to_light");
    public static final TagKey<EntityType<?>> RESISTANT_TO_FIRE = registerEntityTag("resistant_to_fire");
    public static final TagKey<EntityType<?>> RESISTANT_TO_ICE = registerEntityTag("resistant_to_ice");
    public static final TagKey<EntityType<?>> RESISTANT_TO_LIGHT = registerEntityTag("resistant_to_light");

    public static final TagKey<Item> PELLETS = registerItemTag("pellets");
    public static final TagKey<Item> APPRAISAL_LIST = registerItemTag("appraisal_list");

    public static final TagKey<Block> CAN_MELT = registerBlockTag("can_melt");
    public static final TagKey<Block> FRAGILE = registerBlockTag("fragile");

    //public static final TagKey<Biome> SPAWNS_THING = registerBiomeTag("spawns_thing");
    //public static final TagKey<Structure> SPAWNS_THING2 = registerStructureTag("spawns_thing2");


    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    private static TagKey<Biome> registerBiomeTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }

    private static TagKey<Structure> registerStructureTag(String name) {
        return TagKey.create(Registries.STRUCTURE, new ResourceLocation(SupersLegendMain.MOD_ID, name));
    }
}
