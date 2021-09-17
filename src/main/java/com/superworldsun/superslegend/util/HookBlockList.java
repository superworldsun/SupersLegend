package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class HookBlockList {

    /**
     * Here you write the list of blocks to which the hook can be hooked. It is easy to add blocks.
     */
    public static List<Block> hookableBlocks = new ArrayList<>();

    public static void setHookableBlocks() {
        hookableBlocks.add(BlockInit.GRAPPLE_BLOCK.get());
        hookableBlocks.add(Blocks.OAK_WOOD);
        hookableBlocks.add(Blocks.SPRUCE_WOOD);
        hookableBlocks.add(Blocks.BIRCH_WOOD);
        hookableBlocks.add(Blocks.JUNGLE_WOOD);
        hookableBlocks.add(Blocks.ACACIA_WOOD);
        hookableBlocks.add(Blocks.DARK_OAK_WOOD);
        hookableBlocks.add(Blocks.CRIMSON_HYPHAE);
        hookableBlocks.add(Blocks.WARPED_HYPHAE);
        hookableBlocks.add(Blocks.STRIPPED_OAK_WOOD);
        hookableBlocks.add(Blocks.STRIPPED_SPRUCE_WOOD);
        hookableBlocks.add(Blocks.STRIPPED_BIRCH_WOOD);
        hookableBlocks.add(Blocks.STRIPPED_JUNGLE_WOOD);
        hookableBlocks.add(Blocks.STRIPPED_ACACIA_WOOD);
        hookableBlocks.add(Blocks.STRIPPED_DARK_OAK_WOOD);
        hookableBlocks.add(Blocks.STRIPPED_CRIMSON_HYPHAE);
        hookableBlocks.add(Blocks.STRIPPED_WARPED_HYPHAE);
        hookableBlocks.add(Blocks.OAK_LOG);
        hookableBlocks.add(Blocks.SPRUCE_LOG);
        hookableBlocks.add(Blocks.BIRCH_LOG);
        hookableBlocks.add(Blocks.JUNGLE_LOG);
        hookableBlocks.add(Blocks.ACACIA_LOG);
        hookableBlocks.add(Blocks.DARK_OAK_LOG);
        hookableBlocks.add(Blocks.CRIMSON_STEM);
        hookableBlocks.add(Blocks.WARPED_STEM);
        hookableBlocks.add(Blocks.STRIPPED_OAK_LOG);
        hookableBlocks.add(Blocks.STRIPPED_SPRUCE_LOG);
        hookableBlocks.add(Blocks.STRIPPED_BIRCH_LOG);
        hookableBlocks.add(Blocks.STRIPPED_JUNGLE_LOG);
        hookableBlocks.add(Blocks.STRIPPED_ACACIA_LOG);
        hookableBlocks.add(Blocks.STRIPPED_DARK_OAK_LOG);
        hookableBlocks.add(Blocks.STRIPPED_CRIMSON_STEM);
        hookableBlocks.add(Blocks.STRIPPED_WARPED_STEM);
        hookableBlocks.add(Blocks.OAK_PLANKS);
        hookableBlocks.add(Blocks.SPRUCE_PLANKS);
        hookableBlocks.add(Blocks.BIRCH_PLANKS);
        hookableBlocks.add(Blocks.JUNGLE_PLANKS);
        hookableBlocks.add(Blocks.ACACIA_PLANKS);
        hookableBlocks.add(Blocks.DARK_OAK_PLANKS);
        hookableBlocks.add(Blocks.CRIMSON_PLANKS);
        hookableBlocks.add(Blocks.WARPED_PLANKS);

    }
}
