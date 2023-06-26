package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class HookBlockList {

    /**
     * Here you write the list of blocks to which the hook can be hooked. It is easy to add blocks.
     */
    public static List<Block> hookableBlocks = new ArrayList<>();

    public static void setHookableBlocks() {
        hookableBlocks.add(BlockInit.GRAPPLE_BLOCK.get());


        // Include blocks with material wood
        for (Block block : Registry.BLOCK) {
            if (block.defaultBlockState().getMaterial() == Material.WOOD) {
                hookableBlocks.add(block);
            }
        }
    }
}
