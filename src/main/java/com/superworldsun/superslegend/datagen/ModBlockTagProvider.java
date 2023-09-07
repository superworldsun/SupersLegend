package com.superworldsun.superslegend.datagen;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SupersLegendMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(Tags.Blocks.NEEDS_WOOD_TOOL)
                .add(BlockInit.CHAIN_LINK_FENCE_BLOCK.get())
                .add(BlockInit.GOSSIP_STONE_BLOCK.get())
                .add(BlockInit.GOSSIP_STONE_TOP.get())
                .add(BlockInit.DEKU_FLOWER_BLOCK.get())
                .add(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get())
                .add(BlockInit.POT_BLOCK.get())
                .add(BlockInit.JAR_BLOCK.get())
                .add(BlockInit.POSTBOX_BLOCK.get())
                .add(BlockInit.POSTBOX_TOP.get())
                .add(BlockInit.CRATE_BLOCK.get())
                .add(BlockInit.PUSH_STONE.get())
                .add(BlockInit.TORCH_TOWER.get())
                .add(BlockInit.TORCH_TOWER_TOP_UNLIT.get())
                .add(BlockInit.TORCH_TOWER_TOP_LIT.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_OAK.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_SPRUCE.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_BIRCH.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_JUNGLE.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_ACACIA.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_DARK_OAK.get())
                .add(BlockInit.HAMMERED_RUSTED_PEG_BLOCK.get())
                .add(BlockInit.HAMMERED_SPIKED_PEG_BLOCK.get())
                .add(BlockInit.TOMBSTONE_BLOCK.get())
                .add(BlockInit.STONE_PATH_BLOCK.get())
                .add(BlockInit.STONE_TILE_BLOCK.get())
                .add(BlockInit.PEDESTAL.get())
                .add(BlockInit.FAN.get())
                .add(BlockInit.SWITCHABLE_FAN.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(BlockInit.RUPEE_BLOCK.get())
                .add(BlockInit.BLUE_RUPEE_BLOCK.get())
                .add(BlockInit.RED_RUPEE_BLOCK.get())
                .add(BlockInit.SILVER_RUPEE_BLOCK.get())
                .add(BlockInit.GOLD_RUPEE_BLOCK.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(BlockInit.DUNGEON_DOOR.get())
                .add(BlockInit.BOSS_DOOR.get())
                .add(BlockInit.SPIKES_BLOCK.get())
                .add(BlockInit.GRAPPLE_BLOCK.get())
                .add(BlockInit.POT_BLOCK.get())
                .add(BlockInit.JAR_BLOCK.get())
                .add(BlockInit.GRATE_BLOCK.get())
                .add(BlockInit.PUSH_STONE.get())
                .add(BlockInit.SILVER_RUPEE_BLOCK.get())
                .add(BlockInit.BLACK_PUSH_STONE.get())
                .add(BlockInit.BLUE_FLOOR_SWITCH.get())
                .add(BlockInit.YELLOW_FLOOR_SWITCH.get())
                .add(BlockInit.RED_FLOOR_SWITCH.get())
                .add(BlockInit.RUSTED_FLOOR_SWITCH.get())
                .add(BlockInit.OAK_PEG_BLOCK.get())
                .add(BlockInit.SPRUCE_PEG_BLOCK.get())
                .add(BlockInit.BIRCH_PEG_BLOCK.get())
                .add(BlockInit.JUNGLE_PEG_BLOCK.get())
                .add(BlockInit.ACACIA_PEG_BLOCK.get())
                .add(BlockInit.DARK_OAK_PEG_BLOCK.get())
                .add(BlockInit.SPIKED_PEG_BLOCK.get())
                .add(BlockInit.SHADOW_BLOCK.get())
                .add(BlockInit.FALSE_SHADOW_BLOCK.get())
                .add(BlockInit.HIDDEN_SHADOW_BLOCK.get())
                .add(BlockInit.DINS_SACRED_PEDESTAL.get())
                .add(BlockInit.FARORES_SACRED_PEDESTAL.get())
                .add(BlockInit.NAYRUS_SACRED_PEDESTAL.get())
                .add(BlockInit.FAN.get())
                .add(BlockInit.SWITCHABLE_FAN.get())
                .add(BlockInit.OWL_STATUE.get())
                .add(BlockInit.SUN_SWITCH.get())
                .add(BlockInit.WARP_PAD.get())
                .add(BlockInit.WARP_PAD_LIGHT.get())
                .add(BlockInit.WARP_PAD_FOREST.get())
                .add(BlockInit.WARP_PAD_FIRE.get())
                .add(BlockInit.WARP_PAD_WATER.get())
                .add(BlockInit.WARP_PAD_SPIRIT.get())
                .add(BlockInit.WARP_PAD_SHADOW.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(BlockInit.RUSTED_PEG_BLOCK.get())
                .add(BlockInit.MASTER_ORE_BLOCK.get())
                .add(BlockInit.DEEPSLATE_MASTER_ORE_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockInit.RUPEE_BLOCK.get())
                .add(BlockInit.BLUE_RUPEE_BLOCK.get())
                .add(BlockInit.RED_RUPEE_BLOCK.get())
                .add(BlockInit.SILVER_RUPEE_BLOCK.get())
                .add(BlockInit.GOLD_RUPEE_BLOCK.get())
                .add(BlockInit.DUNGEON_DOOR.get())
                .add(BlockInit.BOSS_DOOR.get())
                .add(BlockInit.SPIKES_BLOCK.get())
                .add(BlockInit.GRAPPLE_BLOCK.get())
                .add(BlockInit.POT_BLOCK.get())
                .add(BlockInit.JAR_BLOCK.get())
                .add(BlockInit.GRATE_BLOCK.get())
                .add(BlockInit.PUSH_STONE.get())
                .add(BlockInit.SILVER_RUPEE_BLOCK.get())
                .add(BlockInit.BLACK_PUSH_STONE.get())
                .add(BlockInit.MASTER_ORE_BLOCK.get())
                .add(BlockInit.DEEPSLATE_MASTER_ORE_BLOCK.get())
                .add(BlockInit.BLUE_FLOOR_SWITCH.get())
                .add(BlockInit.YELLOW_FLOOR_SWITCH.get())
                .add(BlockInit.RED_FLOOR_SWITCH.get())
                .add(BlockInit.RUSTED_FLOOR_SWITCH.get())
                .add(BlockInit.RUSTED_PEG_BLOCK.get())
                .add(BlockInit.HAMMERED_RUSTED_PEG_BLOCK.get())
                .add(BlockInit.SPIKED_PEG_BLOCK.get())
                .add(BlockInit.SHADOW_BLOCK.get())
                .add(BlockInit.FALSE_SHADOW_BLOCK.get())
                .add(BlockInit.HIDDEN_SHADOW_BLOCK.get())
                .add(BlockInit.DINS_SACRED_PEDESTAL.get())
                .add(BlockInit.FARORES_SACRED_PEDESTAL.get())
                .add(BlockInit.NAYRUS_SACRED_PEDESTAL.get())
                .add(BlockInit.FAN.get())
                .add(BlockInit.SWITCHABLE_FAN.get())
                .add(BlockInit.OWL_STATUE.get())
                .add(BlockInit.SUN_SWITCH.get())
                .add(BlockInit.WARP_PAD.get())
                .add(BlockInit.WARP_PAD_LIGHT.get())
                .add(BlockInit.WARP_PAD_FOREST.get())
                .add(BlockInit.WARP_PAD_FIRE.get())
                .add(BlockInit.WARP_PAD_WATER.get())
                .add(BlockInit.WARP_PAD_SPIRIT.get())
                .add(BlockInit.WARP_PAD_SHADOW.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlockInit.BUSH_BLOCK.get())
                .add(BlockInit.POSTBOX_BLOCK.get())
                .add(BlockInit.POSTBOX_TOP.get())
                .add(BlockInit.CRATE_BLOCK.get())
                .add(BlockInit.OAK_PEG_BLOCK.get())
                .add(BlockInit.SPRUCE_PEG_BLOCK.get())
                .add(BlockInit.BIRCH_PEG_BLOCK.get())
                .add(BlockInit.JUNGLE_PEG_BLOCK.get())
                .add(BlockInit.ACACIA_PEG_BLOCK.get())
                .add(BlockInit.DARK_OAK_PEG_BLOCK.get())
                .add(BlockInit.SPIKED_PEG_BLOCK.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_OAK.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_SPRUCE.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_BIRCH.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_JUNGLE.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_ACACIA.get())
                .add(BlockInit.HAMMERED_WOODEN_PEG_DARK_OAK.get())
                .add(BlockInit.HAMMERED_SPIKED_PEG_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(BlockInit.DEKU_FLOWER_BLOCK.get())
                .add(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get());
    }
}
