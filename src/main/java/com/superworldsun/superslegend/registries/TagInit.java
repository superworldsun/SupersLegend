package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagInit {

    //TODO cant register these tags, not sure how
    //public static final TagKey<EntityType<?>> WEAK_TO_FIRE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "weak_to_fire"));
    //public static final TagKey<EntityType<?>> WEAK_TO_ICE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "weak_to_ice"));
    //public static final TagKey<EntityType<?>> WEAK_TO_LIGHT = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "weak_to_light"));
    //public static final TagKey<EntityType<?>> RESISTANT_TO_FIRE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "resistant_to_fire"));
    //public static final TagKey<EntityType<?>> RESISTANT_TO_ICE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "resistant_to_ice"));
    //public static final TagKey<EntityType<?>> RESISTANT_TO_LIGHT = EntityTypeTags.create(new ResourceLocation(SupersLegendMain.MOD_ID, "resistant_to_light"));

    public static final TagKey<Block> CAN_MELT = BlockTags.create(new ResourceLocation(SupersLegendMain.MOD_ID, "can_melt"));
    public static final TagKey<Block> FRAGILE = BlockTags.create(new ResourceLocation(SupersLegendMain.MOD_ID, "fragile"));

    public static final TagKey<Item> PELLETS = ItemTags.create(new ResourceLocation(SupersLegendMain.MOD_ID, "pellets"));
}
