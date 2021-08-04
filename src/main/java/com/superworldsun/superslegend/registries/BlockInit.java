package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.*;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<Block> RUST = BLOCKS.register("rust", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RUPEE_BLOCK = BLOCKS.register("rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> BLUE_RUPEE_BLOCK = BLOCKS.register("blue_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RED_RUPEE_BLOCK = BLOCKS.register("red_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> SILVER_RUPEE_BLOCK = BLOCKS.register("silver_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GOLD_RUPEE_BLOCK = BLOCKS.register("gold_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> SPIKES_BLOCK = BLOCKS.register("spikes_block", () -> new SpikesBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GOSSIP_STONE_BLOCK = BLOCKS.register("gossip_stone_block", () -> new GossipStoneBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GOSSIP_STONE_TOP = BLOCKS.register("gossip_stone_top", GossipStoneTopBlock::new);
	public static final RegistryObject<Block> BUSH_BLOCK = BLOCKS.register("bush_block", () -> new BushBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> CHAIN_LINK_FENCE_BLOCK = BLOCKS.register("chain_link_fence_block", () -> new ChainLinkFenceBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> DEKU_FLOWER_BLOCK = BLOCKS.register("deku_flower_block", () -> new DekuFlowerBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> POT_BLOCK = BLOCKS.register("pot_block", () -> new PotBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> JAR_BLOCK = BLOCKS.register("jar_block", () -> new JarBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GRATE_BLOCK = BLOCKS.register("grate_block", () -> new GrateBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GRASS_PATCH_BLOCK = BLOCKS.register("grass_patch_block", () -> new GrassPatch(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TORCH_TOWER = BLOCKS.register("torch_tower", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> MASTER_ORE_BLOCK = BLOCKS.register("master_ore_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> SHADOW_BLOCK = BLOCKS.register("shadow_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> FALSE_SHADOW_BLOCK = BLOCKS.register("false_shadow_block", () -> new FalseShadowBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> HIDDEN_SHADOW_BLOCK = BLOCKS.register("hidden_shadow_block", () -> new HiddenShadowBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TOMBSTONE_BLOCK = BLOCKS.register("tombstone_block", () -> new TombstoneBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> STONE_PATH_BLOCK = BLOCKS.register("stone_path_block", () -> new StonePathBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> STONE_TILE_BLOCK = BLOCKS.register("stone_tile_block", () -> new StoneTileBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> DINS_FLAME = BLOCKS.register("dins_flame", () -> new DinsFlame(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> FARORES_FLAME = BLOCKS.register("farores_flame", () -> new FaroresFlame(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> NAYRUS_FLAME = BLOCKS.register("nayrus_flame", () -> new NayrusFlame(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> POINTER_BLOCK = BLOCKS.register("pointer_block", () -> new PointerBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RUST_PLATE = BLOCKS.register("rust_plate", () -> new RustPlateBlock(PropertiesInit.RUST_PLATE));
	public static final RegistryObject<Block> RUST_BUTTON = BLOCKS.register("rust_button", () -> new RustButtonBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RUST_STAIRS = BLOCKS.register("rust_stairs", () -> new RustStairsBlock(RUST.get().defaultBlockState(), PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RUST_WALL = BLOCKS.register("rust_wall", () -> new RustWallBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RUST_SLAB = BLOCKS.register("rust_slab", () -> new RustSlabBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> PEDESTAL = BLOCKS.register("pedestal", PedestalBlock::new);
	public static final RegistryObject<Block> FAN = BLOCKS.register("fan", FanBlock::new);
	public static final RegistryObject<Block> SWITCHABLE_FAN = BLOCKS.register("switchable_fan", SwitchableFanBlock::new);
}
