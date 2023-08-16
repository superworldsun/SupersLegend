package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagInit {

    public static final TagKey<Block> FRAGILE = BlockTags.create(new ResourceLocation(SupersLegendMain.MOD_ID, "fragile"));
}
