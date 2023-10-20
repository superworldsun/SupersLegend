package com.superworldsun.superslegend.datagen.loot;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(BlockInit.RUPEE_BLOCK.get());
        this.dropSelf(BlockInit.BLUE_RUPEE_BLOCK.get());
        this.dropSelf(BlockInit.RED_RUPEE_BLOCK.get());
        this.dropSelf(BlockInit.SILVER_RUPEE_BLOCK.get());
        this.dropSelf(BlockInit.GOLD_RUPEE_BLOCK.get());
        this.dropSelf(BlockInit.SPIKES_BLOCK.get());
        this.dropSelf(BlockInit.GOSSIP_STONE_BLOCK.get());
        this.dropSelf(BlockInit.BOSS_DOOR.get());
        this.dropSelf(BlockInit.CHAIN_LINK_FENCE_BLOCK.get());
        this.dropSelf(BlockInit.GRAPPLE_BLOCK.get());
        this.dropSelf(BlockInit.DEKU_FLOWER_BLOCK.get());
        this.dropSelf(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get());
        this.dropSelf(BlockInit.POSTBOX_BLOCK.get());
        this.dropSelf(BlockInit.GRATE_BLOCK.get());
        this.dropSelf(BlockInit.CRATE_BLOCK.get());
        this.dropSelf(BlockInit.PUSH_STONE.get());
        this.dropSelf(BlockInit.SILVER_PUSH_STONE.get());
        this.dropSelf(BlockInit.BLACK_PUSH_STONE.get());
        this.dropSelf(BlockInit.ODD_MUSHROOM.get());
        this.dropSelf(BlockInit.MAGIC_MUSHROOM.get());
        this.dropSelf(BlockInit.TORCH_TOWER.get());
        this.dropSelf(BlockInit.BLUE_FLOOR_SWITCH.get());
        this.dropSelf(BlockInit.YELLOW_FLOOR_SWITCH.get());
        this.dropSelf(BlockInit.RED_FLOOR_SWITCH.get());
        this.dropSelf(BlockInit.RUSTED_FLOOR_SWITCH.get());
        this.dropSelf(BlockInit.OAK_PEG_BLOCK.get());
        this.dropSelf(BlockInit.SPRUCE_PEG_BLOCK.get());
        this.dropSelf(BlockInit.BIRCH_PEG_BLOCK.get());
        this.dropSelf(BlockInit.JUNGLE_PEG_BLOCK.get());
        this.dropSelf(BlockInit.ACACIA_PEG_BLOCK.get());
        this.dropSelf(BlockInit.DARK_OAK_PEG_BLOCK.get());
        this.dropSelf(BlockInit.RUSTED_PEG_BLOCK.get());
        this.dropSelf(BlockInit.SPIKED_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_WOODEN_PEG_OAK.get(), BlockInit.OAK_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_WOODEN_PEG_SPRUCE.get(), BlockInit.SPRUCE_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_WOODEN_PEG_BIRCH.get(), BlockInit.BIRCH_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_WOODEN_PEG_JUNGLE.get(), BlockInit.JUNGLE_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_WOODEN_PEG_ACACIA.get(), BlockInit.ACACIA_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_WOODEN_PEG_DARK_OAK.get(), BlockInit.DARK_OAK_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_RUSTED_PEG_BLOCK.get(), BlockInit.RUSTED_PEG_BLOCK.get());
        this.dropOther(BlockInit.HAMMERED_SPIKED_PEG_BLOCK.get(), BlockInit.SPIKED_PEG_BLOCK.get());
        this.dropSelf(BlockInit.SHADOW_BLOCK.get());
        this.dropSelf(BlockInit.FALSE_SHADOW_BLOCK.get());
        this.dropSelf(BlockInit.HIDDEN_SHADOW_BLOCK.get());
        this.dropSelf(BlockInit.TOMBSTONE_BLOCK.get());
        this.dropSelf(BlockInit.STONE_PATH_BLOCK.get());
        this.dropSelf(BlockInit.STONE_TILE_BLOCK.get());
        this.dropSelf(BlockInit.DINS_SACRED_PEDESTAL.get());
        this.dropSelf(BlockInit.FARORES_SACRED_PEDESTAL.get());
        this.dropSelf(BlockInit.NAYRUS_SACRED_PEDESTAL.get());
        this.dropSelf(BlockInit.PEDESTAL.get());
        this.dropSelf(BlockInit.FAN.get());
        this.dropSelf(BlockInit.SWITCHABLE_FAN.get());
        //this.dropSelf(BlockInit.LIGHT_EMITTER.get());
        //this.dropSelf(BlockInit.LIGHT_PRISM.get());
        this.dropSelf(BlockInit.ROYAL_TILE.get());
        this.dropSelf(BlockInit.BLOCK_OF_TIME.get());
        this.dropSelf(BlockInit.OWL_STATUE.get());
        this.dropSelf(BlockInit.SUN_SWITCH.get());
        this.dropSelf(BlockInit.WARP_PAD.get());
        this.dropOther(BlockInit.WARP_PAD_LIGHT.get(), BlockInit.WARP_PAD.get());
        this.dropOther(BlockInit.WARP_PAD_FOREST.get(), BlockInit.WARP_PAD.get());
        this.dropOther(BlockInit.WARP_PAD_FIRE.get(), BlockInit.WARP_PAD.get());
        this.dropOther(BlockInit.WARP_PAD_WATER.get(), BlockInit.WARP_PAD.get());
        this.dropOther(BlockInit.WARP_PAD_SPIRIT.get(), BlockInit.WARP_PAD.get());
        this.dropOther(BlockInit.WARP_PAD_SHADOW.get(), BlockInit.WARP_PAD.get());

        this.add(BlockInit.DUNGEON_DOOR.get(),
                block -> createDoorTable(BlockInit.DUNGEON_DOOR.get()));


        this.add(BlockInit.MASTER_ORE_BLOCK.get(),
               block -> createCopperLikeOreDrops(BlockInit.MASTER_ORE_BLOCK.get(), ItemInit.MASTER_ORE_CHUNK.get()));
        this.add(BlockInit.DEEPSLATE_MASTER_ORE_BLOCK.get(),
                block -> createCopperLikeOreDrops(BlockInit.DEEPSLATE_MASTER_ORE_BLOCK.get(), ItemInit.MASTER_ORE_CHUNK.get()));
    }

    //TODO, look into and see if you can reduce the amount of items you get with fortune. I would like the max to be 1 without fortune, and 2 with
    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
