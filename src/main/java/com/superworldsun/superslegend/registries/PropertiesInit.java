package com.superworldsun.superslegend.registries;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
public class PropertiesInit {
    public static final Block.Properties WRECKAGE = Block.Properties.of(Material.METAL)
            .strength(4.0F, 3.25F)
            .sound(SoundType.METAL)
            .harvestLevel(1)
            .harvestTool(ToolType.PICKAXE)
            .requiresCorrectToolForDrops();

    public static final Block.Properties RUST = Block.Properties.of(Material.METAL)
            .strength(2.0F, 2.0F)
            .sound(SoundType.METAL)
            .harvestLevel(1)
            .harvestTool(ToolType.PICKAXE)
            .requiresCorrectToolForDrops();

    public static final Block.Properties RUST_PLATE = Block.Properties.of(Material.METAL)
            .strength(2.0F, 2.0F)
            .sound(SoundType.METAL)
            .harvestLevel(1)
            .harvestTool(ToolType.PICKAXE)
            .friction(1f)
            .requiresCorrectToolForDrops();

    public static final Block.Properties HAMMERED_WOODEN_PEG = Block.Properties.of(Material.WOOD)
            .strength(1.0F, 1.0F)
            .sound(SoundType.WOOD)
            .harvestLevel(1)
            .harvestTool(ToolType.AXE)
            .randomTicks()
            .requiresCorrectToolForDrops();

    public static final Block.Properties HAMMERED_RUSTED_PEG = Block.Properties.of(Material.WOOD)
            .strength(1.0F, 1.0F)
            .sound(SoundType.ANCIENT_DEBRIS)
            .harvestLevel(1)
            .harvestTool(ToolType.PICKAXE)
            .randomTicks()
            .requiresCorrectToolForDrops();

    public static final Block.Properties QUICK_SAND = Block.Properties.of(Material.SAND)
            .strength(1.0F, 1.0F)
            .sound(SoundType.SAND)
            .harvestLevel(1)
            .harvestTool(ToolType.SHOVEL)
            .noCollission()
            .isSuffocating(PropertiesInit::always);

    public static final Block.Properties TORCH_TOWER = Block.Properties.of(Material.WOOD)
            .strength(1.0F, 1.0F)
            .sound(SoundType.WOOD)
            .harvestLevel(1)
            .harvestTool(ToolType.AXE);

    public static final Block.Properties TORCH_TOWER_TOP_UNLIT = Block.Properties.of(Material.WOOD)
            .strength(1.0F, 1.0F)
            .sound(SoundType.WOOD)
            .harvestLevel(1)
            .harvestTool(ToolType.AXE)
            .noCollission();

    public static final Block.Properties TORCH_TOWER_TOP_LIT = Block.Properties.of(Material.WOOD)
            .strength(1.0F, 1.0F)
            .sound(SoundType.WOOD)
            .harvestLevel(1)
            .harvestTool(ToolType.AXE)
            .noCollission()
            .lightLevel(value -> 15);

    private static boolean always(BlockState p_235426_0_, IBlockReader p_235426_1_, BlockPos p_235426_2_) {
        return true;
    }

    private static boolean never(BlockState p_235436_0_, IBlockReader p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }
}

