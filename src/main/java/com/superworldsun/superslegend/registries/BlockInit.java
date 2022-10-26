package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.*;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SupersLegendMain.MOD_ID);

	//public static final RegistryObject<Block> CRACKED_FLOOR = BLOCKS.register("cracked_floor", () -> new CrackedFloor(PropertiesInit.WRECKAGE));
	//public static final RegistryObject<Block> QUICK_SAND = BLOCKS.register("quick_sand", () -> new QuickSand(PropertiesInit.QUICK_SAND));
	public static final RegistryObject<Block> RUPEE_BLOCK = BLOCKS.register("rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> BLUE_RUPEE_BLOCK = BLOCKS.register("blue_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RED_RUPEE_BLOCK = BLOCKS.register("red_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> SILVER_RUPEE_BLOCK = BLOCKS.register("silver_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GOLD_RUPEE_BLOCK = BLOCKS.register("gold_rupee_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> SPIKES_BLOCK = BLOCKS.register("spikes_block", () -> new SpikesBlock(PropertiesInit.WRECKAGE));
	//public static final RegistryObject<Block> ANCIENT_TABLET = BLOCKS.register("ancient_tablet", () -> new AncientTablet(PropertiesInit.WRECKAGE));
	//public static final RegistryObject<Block> ANCIENT_TABLET_RUBBLE = BLOCKS.register("ancient_tablet_rubble", () -> new Block(PropertiesInit.WRECKAGE));
	//public static final RegistryObject<Block> ANCIENT_TABLET_BOMBOS = BLOCKS.register("ancient_tablet_bombos", () -> new AncientTablet(PropertiesInit.WRECKAGE));
	//public static final RegistryObject<Block> ANCIENT_TABLET_ETHER = BLOCKS.register("ancient_tablet_ether", () -> new AncientTablet(PropertiesInit.WRECKAGE));
	//public static final RegistryObject<Block> ANCIENT_TABLET_QUAKE = BLOCKS.register("ancient_tablet_quake", () -> new AncientTablet(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GOSSIP_STONE_BLOCK = BLOCKS.register("gossip_stone_block", () -> new GossipStoneBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GOSSIP_STONE_TOP = BLOCKS.register("gossip_stone_top", GossipStoneTopBlock::new);
	public static final RegistryObject<Block> BUSH_BLOCK = BLOCKS.register("bush_block", () -> new BushBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> CHAIN_LINK_FENCE_BLOCK = BLOCKS.register("chain_link_fence_block", () -> new ChainLinkFenceBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GRAPPLE_BLOCK = BLOCKS.register("grapple_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> DEKU_FLOWER_BLOCK = BLOCKS.register("deku_flower_block", () -> new DekuFlowerBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> YELLOW_DEKU_FLOWER_BLOCK = BLOCKS.register("yellow_deku_flower_block", () -> new DekuFlowerBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> POT_BLOCK = BLOCKS.register("pot_block", () -> new PotBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> JAR_BLOCK = BLOCKS.register("jar_block", () -> new JarBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> POSTBOX_BLOCK = BLOCKS.register("postbox_block", () -> new PostboxBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> POSTBOX_TOP = BLOCKS.register("postbox_top", PostboxTopBlock::new);
	public static final RegistryObject<Block> GRATE_BLOCK = BLOCKS.register("grate_block", () -> new GrateBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> GRASS_PATCH_BLOCK = BLOCKS.register("grass_patch_block", () -> new GrassPatch(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> ODD_MUSHROOM = BLOCKS.register("odd_mushroom", () -> new OddMushroomBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> MAGIC_MUSHROOM = BLOCKS.register("magic_mushroom", () -> new MagicMushroomBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TORCH_TOWER = BLOCKS.register("torch_tower", () -> new TorchTower(PropertiesInit.TORCH_TOWER));
	public static final RegistryObject<Block> TORCH_TOWER_TOP_UNLIT = BLOCKS.register("torch_tower_top_unlit", () -> new TorchTowerTopUnlit(PropertiesInit.TORCH_TOWER_TOP_UNLIT));
	public static final RegistryObject<Block> TORCH_TOWER_TOP_LIT = BLOCKS.register("torch_tower_top_lit", () -> new TorchTowerTopLit(PropertiesInit.TORCH_TOWER_TOP_LIT));
	public static final RegistryObject<Block> MASTER_ORE_BLOCK = BLOCKS.register("master_ore_block", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> BLUE_FLOOR_SWITCH = BLOCKS.register("blue_floor_switch"
			, () -> new BlueFloorSwitch(BlueFloorSwitch.SensitivityMod.PLAYER, AbstractBlock.Properties.of(Material.METAL)
					.strength(5f).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> YELLOW_FLOOR_SWITCH = BLOCKS.register("yellow_floor_switch"
			, () -> new YellowFloorSwitch(YellowFloorSwitch.SensitivityMod.PLAYER, AbstractBlock.Properties.of(Material.METAL)
			.strength(5f).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> RED_FLOOR_SWITCH = BLOCKS.register("red_floor_switch"
			, () -> new RedFloorSwitch(RedFloorSwitch.SensitivityMod.PLAYER, AbstractBlock.Properties.of(Material.METAL)
					.strength(5f).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> RUSTED_FLOOR_SWITCH = BLOCKS.register("rusted_floor_switch", RustedFloorSwitch::new);
	public static final RegistryObject<Block> WOODEN_PEG_BLOCK = BLOCKS.register("wooden_peg_block", () -> new WoodenPegBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> RUSTED_PEG_BLOCK = BLOCKS.register("rusted_peg_block", () -> new RustedPegBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> SPIKED_PEG_BLOCK = BLOCKS.register("spiked_peg_block", () -> new SpikedPegBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_BLOCK = BLOCKS.register("hammered_wooden_peg_block", () -> new HammeredWoodenPegBlock(PropertiesInit.HAMMERED_WOODEN_PEG));
	public static final RegistryObject<Block> HAMMERED_RUSTED_PEG_BLOCK = BLOCKS.register("hammered_rusted_peg_block", () -> new HammeredRustedPegBlock(PropertiesInit.HAMMERED_RUSTED_PEG));
	public static final RegistryObject<Block> HAMMERED_SPIKED_PEG_BLOCK = BLOCKS.register("hammered_spiked_peg_block", () -> new HammeredSpikedPegBlock(PropertiesInit.HAMMERED_SPIKED_PEG));
	public static final RegistryObject<Block> SHADOW_BLOCK = BLOCKS.register("shadow_block", ShadowBlock::new);
	public static final RegistryObject<Block> FALSE_SHADOW_BLOCK = BLOCKS.register("false_shadow_block", FalseShadowBlock::new);
	public static final RegistryObject<Block> HIDDEN_SHADOW_BLOCK = BLOCKS.register("hidden_shadow_block", HiddenShadowBlock::new);
	public static final RegistryObject<Block> SHADOW_MODEL_BLOCK = BLOCKS.register("shadow_model_block", ShadowModelBlock::new);
	public static final RegistryObject<Block> TOMBSTONE_BLOCK = BLOCKS.register("tombstone_block", () -> new TombstoneBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> STONE_PATH_BLOCK = BLOCKS.register("stone_path_block", () -> new StonePathBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> STONE_TILE_BLOCK = BLOCKS.register("stone_tile_block", () -> new StoneTileBlock(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> DINS_FLAME = BLOCKS.register("dins_flame", () -> new DinsFlame(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> FARORES_FLAME = BLOCKS.register("farores_flame", () -> new FaroresFlame(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> NAYRUS_FLAME = BLOCKS.register("nayrus_flame", () -> new NayrusFlame(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> PEDESTAL = BLOCKS.register("pedestal", PedestalBlock::new);
	public static final RegistryObject<Block> FAN = BLOCKS.register("fan", FanBlock::new);
	public static final RegistryObject<Block> SWITCHABLE_FAN = BLOCKS.register("switchable_fan", SwitchableFanBlock::new);
	public static final RegistryObject<FluidBlock> LIQUID_POISON = BLOCKS.register("liquid_poison", () -> new FluidBlock(FluidInit.POISON_SOURCE));
	public static final RegistryObject<FluidBlock> LIQUID_MUD = BLOCKS.register("liquid_mud", () -> new FluidBlock(FluidInit.MUD_SOURCE));
	public static final RegistryObject<Block> LIGHT_EMITTER = BLOCKS.register("light_emitter", LightEmitterBlock::new);
	public static final RegistryObject<Block> LIGHT_PRISM = BLOCKS.register("light_prism", LightPrismBlock::new);
	//public static final RegistryObject<Block> RIDGED_WALL_RAIL = BLOCKS.register("ridged_wall_rail", () -> new RidgedWallRail(AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(0.7F).sound(SoundType.METAL)));

	/*public static final RegistryObject<Block> TALKING_TIMBER_PLANKS = BLOCKS.register("talking_timber_planks", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_SAPLING = BLOCKS.register("talking_timber_sapling", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_LOG = BLOCKS.register("talking_timber_log", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_STRIPPED_LOG = BLOCKS.register("talking_timber_stripped_log", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_STRIPPED_WOOD = BLOCKS.register("talking_timber_stripped_wood", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_WOOD = BLOCKS.register("talking_timber_wood", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_LEAVES = BLOCKS.register("talking_timber_leaves", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_SLAB = BLOCKS.register("talking_timber_slab", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_PLATE = BLOCKS.register("talking_timber_plate", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_FENCE = BLOCKS.register("talking_timber_fence", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_TRAPDOOR = BLOCKS.register("talking_timber_trapdoor", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_FENCE_GATE = BLOCKS.register("talking_timber_fence_gate", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_STAIRS = BLOCKS.register("talking_timber_stairs", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_BUTTON = BLOCKS.register("talking_timber_button", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_DOOR = BLOCKS.register("talking_timber_door", () -> new Block(PropertiesInit.WRECKAGE));
	public static final RegistryObject<Block> TALKING_TIMBER_SIGN = BLOCKS.register("talking_timber_sign", () -> new Block(PropertiesInit.WRECKAGE));*/
	public static final RegistryObject<Block> ROYAL_TILE = BLOCKS.register("royal_tile", RoyalTileBlock::new);
	public static final RegistryObject<Block> BLOCK_OF_TIME = BLOCKS.register("block_of_time", TimeBlock::new);
	public static final RegistryObject<Block> OWL_STATUE = BLOCKS.register("owl_statue", () -> new OwlStatueBlock(PropertiesInit.WRECKAGE));
	//public static final RegistryObject<Block> COOKING_POT = BLOCKS.register("cooking_pot", CookingPotBlock::new);
	public static final RegistryObject<Block> SUN_SWITCH = BLOCKS.register("sun_switch", SunSwitchBlock::new);
	
	//For cooking pot
	public static final RegistryObject<Block> COOKING_POT = BLOCKS.register("cooking_pot", () -> new SimpleCookingPotBlock());
}