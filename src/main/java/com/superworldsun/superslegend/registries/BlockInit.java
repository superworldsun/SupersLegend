package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.*;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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
    public static final RegistryObject<Block> SPIKES_BLOCK = registerBlock("spikes_block",
            () -> new SpikesBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(13).explosionResistance(13).sound(SoundType.METAL)));
    public static final RegistryObject<Block> GOSSIP_STONE_BLOCK = registerBlock("gossip_stone_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(11).explosionResistance(11).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GOSSIP_STONE_TOP = registerBlock("gossip_stone_top",
            () -> new Block(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().destroyTime(11).explosionResistance(11).sound(SoundType.STONE)));

    public static final RegistryObject<Block> DUNGEON_DOOR = registerBlock("dungeon_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(5).explosionResistance(99).noOcclusion().pushReaction(PushReaction.IGNORE).sound(SoundType.METAL), BlockSetType.IRON));
    public static final RegistryObject<Block> LOCKED_DUNGEON_DOOR = registerBlock("locked_dungeon_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(5).explosionResistance(99).noOcclusion().pushReaction(PushReaction.IGNORE).sound(SoundType.METAL), BlockSetType.IRON));
    public static final RegistryObject<Block> BOSS_DOOR = registerBlock("boss_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(5).explosionResistance(99).noOcclusion().pushReaction(PushReaction.IGNORE).sound(SoundType.METAL), BlockSetType.IRON));
    public static final RegistryObject<Block> LOCKED_BOSS_DOOR = registerBlock("locked_boss_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(5).explosionResistance(99).noOcclusion().pushReaction(PushReaction.IGNORE).sound(SoundType.METAL), BlockSetType.IRON));
    public static final RegistryObject<Block> LOCKED_WOODEN_DOOR = registerBlock("locked_wooden_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(5).explosionResistance(99).noOcclusion().pushReaction(PushReaction.IGNORE).sound(SoundType.METAL), BlockSetType.IRON));
    public static final RegistryObject<Block> WOODEN_BOSS_DOOR = registerBlock("wooden_boss_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(5).explosionResistance(99).noOcclusion().pushReaction(PushReaction.IGNORE).sound(SoundType.METAL), BlockSetType.IRON));

    public static final RegistryObject<Block> BUSH_BLOCK = registerBlock("bush_block",
            () -> new BushBlock(BlockBehaviour.Properties.of().noLootTable().destroyTime(0.1f).explosionResistance(0.1f).sound(SoundType.CROP)));
    public static final RegistryObject<Block> CHAIN_LINK_FENCE_BLOCK = registerBlock("chain_link_fence_block",
            () -> new ChainLinkFenceBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(5).explosionResistance(5).sound(SoundType.METAL)));
    public static final RegistryObject<Block> GRAPPLE_BLOCK = registerBlock("grapple_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(7).explosionResistance(7).sound(SoundType.METAL)));
    public static final RegistryObject<Block> DEKU_FLOWER_BLOCK = registerBlock("deku_flower_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(0.5f).explosionResistance(0.5f).sound(SoundType.CROP)));
    public static final RegistryObject<Block> YELLOW_DEKU_FLOWER_BLOCK = registerBlock("yellow_deku_flower_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(0.1f).explosionResistance(0.1f).sound(SoundType.CROP)));
    public static final RegistryObject<Block> POT_BLOCK = registerBlock("pot_block",
            () -> new Block(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().destroyTime(0.1f).explosionResistance(0.1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> JAR_BLOCK = registerBlock("jar_block",
            () -> new Block(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().destroyTime(0.5f).explosionResistance(0.5f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> POSTBOX_BLOCK = registerBlock("postbox_block",
            () -> new Block(BlockBehaviour.Properties.of().destroyTime(2).explosionResistance(2).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POSTBOX_TOP = registerBlock("postbox_top",
            () -> new Block(BlockBehaviour.Properties.of().noLootTable().destroyTime(2).explosionResistance(2).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GRATE_BLOCK = registerBlock("grate_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(6).explosionResistance(6).sound(SoundType.METAL)));
    public static final RegistryObject<Block> CRATE_BLOCK = registerBlock("crate_block",
            () -> new CrateBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(3).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PUSH_STONE = registerBlock("push_stone",
            () -> new PushStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(6).explosionResistance(6).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SILVER_PUSH_STONE = registerBlock("silver_push_stone",
            () -> new SilverPushStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(8).explosionResistance(25).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACK_PUSH_STONE = registerBlock("black_push_stone",
            () -> new BlackPushStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(60).explosionResistance(1300).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> GRASS_PATCH_BLOCK = registerBlock("grass_patch_block",
            () -> new GrassPatch(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().destroyTime(0.1f).explosionResistance(0.1f).sound(SoundType.CROP)));
    public static final RegistryObject<Block> ODD_MUSHROOM = registerBlock("odd_mushroom",
            () -> new OddMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY), TreeFeatures.HUGE_RED_MUSHROOM));
    public static final RegistryObject<Block> MAGIC_MUSHROOM = registerBlock("magic_mushroom",
            () -> new MagicMushroomBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY), TreeFeatures.HUGE_RED_MUSHROOM));
    public static final RegistryObject<Block> TORCH_TOWER = registerBlock("torch_tower",
            () -> new TorchTower(BlockBehaviour.Properties.of().destroyTime(1).explosionResistance(1).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TORCH_TOWER_TOP_UNLIT = registerBlock("torch_tower_top_unlit",
            () -> new TorchTowerTopUnlit(BlockBehaviour.Properties.of().noLootTable().destroyTime(1).explosionResistance(1).noCollission().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TORCH_TOWER_TOP_LIT = registerBlock("torch_tower_top_lit",
            () -> new TorchTowerTopLit(BlockBehaviour.Properties.of().noLootTable().destroyTime(1).explosionResistance(1).noCollission().sound(SoundType.WOOD).lightLevel(value -> 15)));

    public static final RegistryObject<Block> MASTER_ORE_BLOCK = registerBlock("master_ore_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(400).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DEEPSLATE_MASTER_ORE_BLOCK = registerBlock("deepslate_master_ore_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(100).explosionResistance(400).sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> CRACKED_BOMB_WALL = registerBlock("cracked_bomb_wall",
            () -> new Block(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().destroyTime(Integer.MAX_VALUE).explosionResistance(0).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLUE_FLOOR_SWITCH = registerBlock("blue_floor_switch",
            () -> new BlueFloorSwitch(BlueFloorSwitch.SensitivityMod.PLAYER, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops().strength(3.5F).pushReaction(PushReaction.DESTROY), BlockSetType.IRON));
    public static final RegistryObject<Block> YELLOW_FLOOR_SWITCH = registerBlock("yellow_floor_switch",
            () -> new YellowFloorSwitch(YellowFloorSwitch.SensitivityMod.PLAYER, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops().strength(3.5F).pushReaction(PushReaction.DESTROY), BlockSetType.IRON));
    public static final RegistryObject<Block> RED_FLOOR_SWITCH = registerBlock("red_floor_switch",
            () -> new RedFloorSwitch(RedFloorSwitch.SensitivityMod.PLAYER, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops().strength(3.5F).pushReaction(PushReaction.DESTROY), BlockSetType.IRON));
    //TODO, have way to remove the RedFloorSwitch.SensitivityMod.PLAYER part for Rusted Floor switch, shouldnt be needed
    public static final RegistryObject<Block> RUSTED_FLOOR_SWITCH = registerBlock("rusted_floor_switch",
            () -> new RustedFloorSwitch(RedFloorSwitch.SensitivityMod.PLAYER, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops().strength(3.5F).pushReaction(PushReaction.DESTROY).sound(SoundType.COPPER), BlockSetType.IRON));

    public static final RegistryObject<Block> OAK_PEG_BLOCK = registerBlock("oak_peg_block",
            () -> new WoodenOakPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SPRUCE_PEG_BLOCK = registerBlock("spruce_peg_block",
            () -> new WoodenSprucePeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BIRCH_PEG_BLOCK = registerBlock("birch_peg_block",
            () -> new WoodenBirchPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> JUNGLE_PEG_BLOCK = registerBlock("jungle_peg_block",
            () -> new WoodenJunglePeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ACACIA_PEG_BLOCK = registerBlock("acacia_peg_block",
            () -> new WoodenAcaciaPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DARK_OAK_PEG_BLOCK = registerBlock("dark_oak_peg_block",
            () -> new WoodenDarkOakPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RUSTED_PEG_BLOCK = registerBlock("rusted_peg_block",
            () -> new RustedPegBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.METAL)));
    public static final RegistryObject<Block> SPIKED_PEG_BLOCK = registerBlock("spiked_peg_block",
            () -> new SpikedPegBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(80).explosionResistance(100).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_OAK = registerBlock("hammered_wooden_peg_oak",
            () -> new HammeredWoodenOakPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_SPRUCE = registerBlock("hammered_wooden_peg_spruce",
            () -> new HammeredWoodenSprucePeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_BIRCH = registerBlock("hammered_wooden_peg_birch",
            () -> new HammeredWoodenBirchPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_JUNGLE = registerBlock("hammered_wooden_peg_jungle",
            () -> new HammeredWoodenJunglePeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_ACACIA = registerBlock("hammered_wooden_peg_acacia",
            () -> new HammeredWoodenAcaciaPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_DARK_OAK = registerBlock("hammered_wooden_peg_dark_oak",
            () -> new HammeredWoodenDarkOakPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HAMMERED_RUSTED_PEG_BLOCK = registerBlock("hammered_rusted_peg_block",
            () -> new HammeredRustedPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.METAL)));
    public static final RegistryObject<Block> HAMMERED_SPIKED_PEG_BLOCK = registerBlock("hammered_spiked_peg_block",
            () -> new HammeredSpikedPeg(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).randomTicks().sound(SoundType.WOOD)));

    public static final RegistryObject<Block> SHADOW_BLOCK = registerBlock("shadow_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(3).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> FALSE_SHADOW_BLOCK = registerBlock("false_shadow_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(3).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> HIDDEN_SHADOW_BLOCK = registerBlock("hidden_shadow_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(3).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> SHADOW_MODEL_BLOCK = registerBlock("shadow_model_block",
            () -> new ShadowModelBlock(BlockBehaviour.Properties.of().noLootTable()));
    public static final RegistryObject<Block> TOMBSTONE_BLOCK = registerBlock("tombstone_block",
            () -> new TombstoneBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(3).explosionResistance(3).sound(SoundType.STONE)));
    public static final RegistryObject<Block> STONE_PATH_BLOCK = registerBlock("stone_path_block",
            () -> new StonePathBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(0.7f).explosionResistance(0.7f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> STONE_TILE_BLOCK = registerBlock("stone_tile_block",
            () -> new StoneTileBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(0.7f).explosionResistance(0.7f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> DINS_FLAME = registerBlock("dins_flame",
            () -> new DinsFlame(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().lightLevel((state) -> 15).noCollission().destroyTime(Integer.MAX_VALUE).explosionResistance(Integer.MAX_VALUE)));
    public static final RegistryObject<Block> FARORES_FLAME = registerBlock("farores_flame",
            () -> new FaroresFlame(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().lightLevel((state) -> 15).noCollission().destroyTime(Integer.MAX_VALUE).explosionResistance(Integer.MAX_VALUE)));
    public static final RegistryObject<Block> NAYRUS_FLAME = registerBlock("nayrus_flame",
            () -> new NayrusFlame(BlockBehaviour.Properties.of().noLootTable().requiresCorrectToolForDrops().lightLevel((state) -> 15).noCollission().destroyTime(Integer.MAX_VALUE).explosionResistance(Integer.MAX_VALUE)));
    public static final RegistryObject<Block> DINS_SACRED_PEDESTAL = registerBlock("dins_sacred_pedestal",
            () -> new DinsSacredPedestal(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().lightLevel((state) -> 11).destroyTime(30).explosionResistance(30).sound(SoundType.METAL)));
    public static final RegistryObject<Block> FARORES_SACRED_PEDESTAL = registerBlock("farores_sacred_pedestal",
            () -> new FaroresSacredPedestal(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().lightLevel((state) -> 11).destroyTime(30).explosionResistance(30).sound(SoundType.METAL)));
    public static final RegistryObject<Block> NAYRUS_SACRED_PEDESTAL = registerBlock("nayrus_sacred_pedestal",
            () -> new NayrusSacredPedestal(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().lightLevel((state) -> 11).destroyTime(30).explosionResistance(30).sound(SoundType.METAL)));

    public static final RegistryObject<Block> PEDESTAL = registerBlock("pedestal",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2.2F).explosionResistance(2.2F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> FAN = registerBlock("fan",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(7).explosionResistance(10).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SWITCHABLE_FAN = registerBlock("switchable_fan",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(7).explosionResistance(10).sound(SoundType.STONE)));
    /*public static final RegistryObject<Block> LIGHT_EMITTER = registerBlock("light_emitter",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> LIGHT_PRISM = registerBlock("light_prism",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.GLASS)));*/
    public static final RegistryObject<Block> ROYAL_TILE = registerBlock("royal_tile",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(0.5f).explosionResistance(0.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLOCK_OF_TIME = registerBlock("block_of_time",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(30).explosionResistance(120).sound(SoundType.STONE)));
    public static final RegistryObject<Block> OWL_STATUE = registerBlock("owl_statue",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(8).explosionResistance(8).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SUN_SWITCH = registerBlock("sun_switch",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(2).explosionResistance(2).sound(SoundType.STONE)));

    public static final RegistryObject<Block> WARP_PAD = registerBlock("warp_pad",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(4).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WARP_PAD_LIGHT = registerBlock("warp_pad_light",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(4).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WARP_PAD_FOREST = registerBlock("warp_pad_forest",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(4).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WARP_PAD_FIRE = registerBlock("warp_pad_fire",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(4).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WARP_PAD_WATER = registerBlock("warp_pad_water",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(4).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WARP_PAD_SPIRIT = registerBlock("warp_pad_spirit",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(4).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WARP_PAD_SHADOW = registerBlock("warp_pad_shadow",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().destroyTime(4).explosionResistance(4).sound(SoundType.STONE)));


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
