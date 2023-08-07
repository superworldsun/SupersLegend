package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SupersLegendMain.MOD_ID);

    public static final RegistryObject<Block> RUPEE_BLOCK = registerBlock("rupee_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> BLUE_RUPEE_BLOCK = registerBlock("blue_rupee_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> RED_RUPEE_BLOCK = registerBlock("red_rupee_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> SILVER_RUPEE_BLOCK = registerBlock("silver_rupee_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> GOLD_RUPEE_BLOCK = registerBlock("gold_rupee_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.GLASS)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
