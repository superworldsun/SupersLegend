package com.superworldsun.superslegend.util.datagen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class Tags {
    public static void init ()
    {
        Blocks.init();
        Items.init();
    }

    public static class Items
    {
        private static void init(){}

        public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Item> APPRAISAL_LIST = tag("appraisal_list");

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> tag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
    public static class Blocks
    {
        private static void init(){}
        //public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Block> ORES = tag("ores");

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Block> tag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

}
