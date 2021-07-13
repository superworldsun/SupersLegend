package com.superworldsun.superslegend.registries;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
            .requiresCorrectToolForDrops();

}

