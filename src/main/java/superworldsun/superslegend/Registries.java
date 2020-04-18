package superworldsun.superslegend;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import superworldsun.superslegend.blocks.*;
import superworldsun.superslegend.items.*;
import superworldsun.superslegend.items.armors.*;
import superworldsun.superslegend.items.arrows.*;
import superworldsun.superslegend.items.bows.BowLynelSavage;
import superworldsun.superslegend.items.masks.*;
import superworldsun.superslegend.lists.BlockList;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.lists.ToolMaterialList;
import superworldsun.superslegend.util.handlers.SoundHandler;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registries
{
    public static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger();

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
                (
                        //Items

                        ItemList.rupee = new Rupee(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("rupee")),
                        ItemList.blue_rupee = new BlueRupee(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("blue_rupee")),
                        ItemList.red_rupee = new RedRupee(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("red_rupee")),
                        ItemList.silver_rupee = new SilverRupee(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("silver_rupee")),
                        ItemList.gold_rupee = new GoldRupee(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("gold_rupee")),
                        ItemList.heart = new Heart(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("heart")),
                        ItemList.triforce_power_shard = new Item(new Item.Properties().maxStackSize(7).group(SupersLegend.supers_legend)).setRegistryName(location("triforce_power_shard")),
                        ItemList.triforce_wisdom_shard = new Item(new Item.Properties().maxStackSize(7).group(SupersLegend.supers_legend)).setRegistryName(location("triforce_wisdom_shard")),
                        ItemList.triforce_courage_shard = new Item(new Item.Properties().maxStackSize(7).group(SupersLegend.supers_legend)).setRegistryName(location("triforce_courage_shard")),
                        ItemList.rupee_pouch = new Item(new Item.Properties().maxStackSize(1)).setRegistryName(location("rupee_pouch")),
                        ItemList.odolwas_remains = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("odolwas_remains")),
                        ItemList.gohts_remains = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("gohts_remains")),
                        ItemList.gyorgs_remains = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("gyorgs_remains")),
                        ItemList.twinmolds_remains = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("twinmolds_remains")),
                        ItemList.master_ore = new Item(new Item.Properties().maxStackSize(16).group(SupersLegend.supers_legend)).setRegistryName(location("master_ore")),
                        ItemList.master_sword_blade = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("master_sword_blade")),
                        ItemList.master_sword_hilt = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("master_sword_hilt")),

                        //Block Items

                        ItemList.rupee_block = new BlockItem(BlockList.rupee_block, new Item.Properties().maxStackSize(5).group(SupersLegend.supers_legend)).setRegistryName(BlockList.rupee_block.getRegistryName()),
                        ItemList.blue_rupee_block = new BlockItem(BlockList.blue_rupee_block, new Item.Properties().maxStackSize(5).group(SupersLegend.supers_legend)).setRegistryName(BlockList.blue_rupee_block.getRegistryName()),
                        ItemList.red_rupee_block = new BlockItem(BlockList.red_rupee_block, new Item.Properties().maxStackSize(5).group(SupersLegend.supers_legend)).setRegistryName(BlockList.red_rupee_block.getRegistryName()),
                        ItemList.silver_rupee_block = new BlockItem(BlockList.silver_rupee_block, new Item.Properties().maxStackSize(5).group(SupersLegend.supers_legend)).setRegistryName(BlockList.silver_rupee_block.getRegistryName()),
                        ItemList.gold_rupee_block = new BlockItem(BlockList.gold_rupee_block, new Item.Properties().maxStackSize(5).group(SupersLegend.supers_legend)).setRegistryName(BlockList.gold_rupee_block.getRegistryName()),
                        ItemList.spikes_block = new BlockItem(BlockList.spikes_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.spikes_block.getRegistryName()),
                        ItemList.gossip_stone_block = new BlockItem(BlockList.gossip_stone_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.gossip_stone_block.getRegistryName()),
                        ItemList.bush_block = new BlockItem(BlockList.bush_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.bush_block.getRegistryName()),
                        ItemList.chain_link_fence_block = new BlockItem(BlockList.chain_link_fence_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.chain_link_fence_block.getRegistryName()),
                        ItemList.deku_flower_block = new BlockItem(BlockList.deku_flower_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.deku_flower_block.getRegistryName()),
                        ItemList.pot_block = new BlockItem(BlockList.pot_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.pot_block.getRegistryName()),
                        ItemList.jar_block = new BlockItem(BlockList.jar_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.jar_block.getRegistryName()),
                        ItemList.grate_block = new BlockItem(BlockList.grate_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.grate_block.getRegistryName()),
                        ItemList.grass_patch_block = new BlockItem(BlockList.grass_patch_block, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(BlockList.grass_patch_block.getRegistryName()),
                        ItemList.torch_tower = new TorchTower(new Item.Properties().maxStackSize(16).group(SupersLegend.supers_legend)).setRegistryName(location("torch_tower")),
                        ItemList.master_ore_block = new BlockItem(BlockList.master_ore_block, new Item.Properties().maxStackSize(64).group(SupersLegend.supers_legend)).setRegistryName(BlockList.master_ore_block.getRegistryName()),

                        //Weapons

                        ItemList.kokiri_sword = new ItemCustomSword(ToolMaterialList.kokiri_sword,		(int) 2, -2.3f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("kokiri_sword")),
                        ItemList.razor_sword = new ItemCustomSword(ToolMaterialList.razor_sword, 			(int) 2, -2.5f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("razor_sword")),
                        ItemList.gilded_sword = new ItemCustomSword(ToolMaterialList.gilded_sword, 		(int) 2, -2.4f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("gilded_sword")),
                        ItemList.master_sword = new ItemCustomSword(ToolMaterialList.master_sword,		(int) 2, -2.3f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("master_sword")),

                        ItemList.heros_bow = new ItemCustomBow(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("heros_bow")),
                        //ItemList.bit_bow = new BitBow(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("bit_bow")),

                        ItemList.lynel_bow_x3 = new BowLynelSavage(1, new Item.Properties().maxStackSize(1).maxDamage(45).group(SupersLegend.supers_legend)).setRegistryName(location("lynel_bow_x3")),
                        ItemList.lynel_bow_x5 = new BowLynelSavage(2, new Item.Properties().maxStackSize(1).maxDamage(45).group(SupersLegend.supers_legend)).setRegistryName(location("lynel_bow_x5")),

                        ItemList.deku_shield = new ItemCustomShield(new Item.Properties().maxStackSize(1).maxDamage(337).group(SupersLegend.supers_legend)).setRegistryName(location("deku_shield")),
                        ItemList.hylian_shield = new ShieldItem(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("hylian_shield")),

                        ItemList.bomb_arrow = new ArrowBomb(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("bomb_arrow")),
                        ItemList.fire_arrow = new ArrowFire(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("fire_arrow")),
                        ItemList.shock_arrow = new ArrowShock(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("shock_arrow")),
                        ItemList.ice_arrow = new ArrowIce(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("ice_arrow")),
                        ItemList.ancient_arrow = new ArrowAncient(new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("ancient_arrow")),

                        ItemList.moon_pearl = new MoonPearl(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("moon_pearl")),
                        ItemList.heros_secret_stash = new HerosSecretStash(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("heros_secret_stash")),
                        ItemList.book_of_mudora = new BookOfMudora(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("book_of_mudora")),
                        ItemList.silver_scale = new SilverScale(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("silver_scale")),
                        ItemList.golden_scale = new GoldenScale(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("golden_scale")),
                        ItemList.rocs_feather = new RocsFeather(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("rocs_feather")),
                        ItemList.magic_mirror = new MagicMirror(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("magic_mirror")),
                        ItemList.magic_cape = new MagicCape(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("magic_cape")),
                        ItemList.empty_container = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("empty_container")),
                        ItemList.farores_wind = new FaroresWind(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("farores_wind")),
                        ItemList.dins_fire = new DinsFire(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("dins_fire")),
                        ItemList.nayrus_love = new NayrusLove(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("nayrus_love")),
                        ItemList.deku_leaf = new DekuLeaf(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("deku_leaf")),
                        ItemList.lens_of_truth = new LensOfTruth(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("lens_of_truth")),
                        ItemList.fairy_ocarina = new FairyOcarina(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("fairy_ocarina")),
                        ItemList.ocarina_of_time = new OcarinaOfTime(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("ocarina_of_time")),
                        ItemList.red_jelly = new Item(new Item.Properties().maxStackSize(16).group(SupersLegend.supers_legend)).setRegistryName(location("red_jelly")),
                        ItemList.green_jelly = new Item(new Item.Properties().maxStackSize(16).group(SupersLegend.supers_legend)).setRegistryName(location("green_jelly")),
                        ItemList.blue_jelly = new Item(new Item.Properties().maxStackSize(16).group(SupersLegend.supers_legend)).setRegistryName(location("blue_jelly")),
                        ItemList.red_potion_mix = new RedPotionMix(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("red_potion_mix")),
                        ItemList.green_potion_mix = new GreenPotionMix(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("green_potion_mix")),
                        ItemList.blue_potion_mix = new BluePotionMix(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("blue_potion_mix")),
                        ItemList.red_potion = new RedPotion(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("red_potion")),
                        ItemList.green_potion = new GreenPotion(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("green_potion")),
                        ItemList.blue_potion = new BluePotion(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("blue_potion")),
                        //ItemList.magnetic_glove = new MagneticGlove(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("magnetic_glove")),
                        ItemList.triforce = new Triforce(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("triforce")),
                        ItemList.triforce_power = new TriforcePower(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("triforce_power")),
                        ItemList.triforce_wisdom = new TriforceWisdom(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("triforce_wisdom")),
                        ItemList.triforce_courage = new TriforceCourage(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("triforce_courage")),
                        //Tools
                        ItemList.rupee_sword = new ItemCustomSword(ToolMaterialList.rupee_sword, 				(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("rupee_sword")),
                        ItemList.blue_rupee_sword = new ItemCustomSword(ToolMaterialList.blue_rupee_sword, 	(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("blue_rupee_sword")),
                        ItemList.red_rupee_sword = new ItemCustomSword(ToolMaterialList.red_rupee_sword, 		(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("red_rupee_sword")),
                        ItemList.silver_rupee_sword = new ItemCustomSword(ToolMaterialList.silver_rupee_sword,(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("silver_rupee_sword")),
                        ItemList.gold_rupee_sword = new ItemCustomSword(ToolMaterialList.gold_rupee_sword,(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("gold_rupee_sword")),

                        ItemList.rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.rupee_pickaxe, 				(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("rupee_pickaxe")),
                        ItemList.blue_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.blue_rupee_pickaxe, 	(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("blue_rupee_pickaxe")),
                        ItemList.red_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.red_rupee_pickaxe, 		(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("red_rupee_pickaxe")),
                        ItemList.silver_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.silver_rupee_pickaxe,(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("silver_rupee_pickaxe")),
                        ItemList.gold_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.gold_rupee_pickaxe,(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("gold_rupee_pickaxe")),

                        ItemList.rupee_axe = new ItemCustomAxe(ToolMaterialList.rupee_axe, 						(int) 0, -3.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("rupee_axe")),
                        ItemList.blue_rupee_axe = new ItemCustomAxe(ToolMaterialList.blue_rupee_axe, 			(int) 0, -3.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("blue_rupee_axe")),
                        ItemList.red_rupee_axe = new ItemCustomAxe(ToolMaterialList.red_rupee_axe, 				(int) 0, -3.1f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("red_rupee_axe")),
                        ItemList.silver_rupee_axe = new ItemCustomAxe(ToolMaterialList.silver_rupee_axe, 		(int) 0, -3.1f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("silver_rupee_axe")),
                        ItemList.gold_rupee_axe = new ItemCustomAxe(ToolMaterialList.gold_rupee_axe, 		(int) 0, -3.1f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("gold_rupee_axe")),

                        ItemList.rupee_shovel = new ShovelItem(ToolMaterialList.rupee_shovel, 				(int) 0, -2.8f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("rupee_shovel")),
                        ItemList.blue_rupee_shovel = new ShovelItem(ToolMaterialList.blue_rupee_shovel, 	(int) 0, -2.8f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("blue_rupee_shovel")),
                        ItemList.red_rupee_shovel = new ShovelItem(ToolMaterialList.red_rupee_shovel, 		(int) 0, -2.6f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("red_rupee_shovel")),
                        ItemList.silver_rupee_shovel = new ShovelItem(ToolMaterialList.silver_rupee_shovel,	(int) 0, -2.6f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("silver_rupee_shovel")),
                        ItemList.gold_rupee_shovel = new ShovelItem(ToolMaterialList.gold_rupee_shovel,	(int) 0, -2.6f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("gold_rupee_shovel")),

                        ItemList.rupee_hoe = new ItemCustomHoe(ToolMaterialList.rupee_hoe, 				(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("rupee_hoe")),
                        ItemList.blue_rupee_hoe = new ItemCustomHoe(ToolMaterialList.blue_rupee_hoe, 	(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("blue_rupee_hoe")),
                        ItemList.red_rupee_hoe = new ItemCustomHoe(ToolMaterialList.red_rupee_hoe, 		(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("red_rupee_hoe")),
                        ItemList.silver_rupee_hoe = new ItemCustomHoe(ToolMaterialList.silver_rupee_hoe,(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("silver_rupee_hoe")),
                        ItemList.gold_rupee_hoe = new ItemCustomHoe(ToolMaterialList.gold_rupee_hoe,(int) 0, -2.2f, new Item.Properties().group(SupersLegend.supers_legend)).setRegistryName(location("gold_rupee_hoe")),

                        //Masks
                        ItemList.mask_clay = new Item(new Item.Properties().maxStackSize(1).group(SupersLegend.supers_legend)).setRegistryName(location("mask_clay")),
                        ItemList.mask_postmanshat = new MaskPostmanshat("mask_postmanshat", EquipmentSlotType.HEAD),
                        ItemList.mask_allnightmask = new MaskAllnightmaskEffects("mask_allnightmask", EquipmentSlotType.HEAD),
                        ItemList.mask_blastmask = new MaskBlastmask("mask_blastmask", EquipmentSlotType.HEAD),
                        ItemList.mask_stonemask = new MaskStonemaskEffects("mask_stonemask", EquipmentSlotType.HEAD),
                        ItemList.mask_bremenmask = new MaskBremenmask("mask_bremenmask", EquipmentSlotType.HEAD),
                        ItemList.mask_greatfairymask = new MaskGreatfairymask("mask_greatfairymask", EquipmentSlotType.HEAD),
                        ItemList.mask_dekumask = new MaskDekumask("mask_dekumask", EquipmentSlotType.HEAD),
                        ItemList.mask_keatonmask = new MaskKeatonmask("mask_keatonmask", EquipmentSlotType.HEAD),
                        ItemList.mask_bunnyhood = new MaskBunnyhoodEffects("mask_bunnyhood", EquipmentSlotType.HEAD),
                        ItemList.mask_dongerosmask = new MaskDongerosmaskEffects("mask_dongerosmask", EquipmentSlotType.HEAD),
                        ItemList.mask_maskofscents = new MaskMaskofscents("mask_maskofscents", EquipmentSlotType.HEAD),
                        ItemList.mask_goronmask = new MaskGoronmask("mask_goronmask", EquipmentSlotType.HEAD),
                        ItemList.mask_romanismask = new MaskRomanismask("mask_romanismask", EquipmentSlotType.HEAD),
                        ItemList.mask_troupeleadersmask = new MaskTroupeleadersmask("mask_troupeleadersmask", EquipmentSlotType.HEAD),
                        ItemList.mask_kafeismask = new MaskKafeismask("mask_kafeismask", EquipmentSlotType.HEAD),
                        ItemList.mask_couplesmask = new MaskCouplesmask("mask_couplesmask", EquipmentSlotType.HEAD),
                        ItemList.mask_maskoftruth = new MaskMaskofTruth("mask_maskoftruth", EquipmentSlotType.HEAD),
                        ItemList.mask_zoramask = new MaskZoramask("mask_zoramask", EquipmentSlotType.HEAD),
                        ItemList.mask_kamarosmask = new MaskKamarosmask("mask_kamarosmask", EquipmentSlotType.HEAD),
                        ItemList.mask_gibdomask = new MaskGibdomask("mask_gibdomask", EquipmentSlotType.HEAD),
                        ItemList.mask_garosmask = new MaskGarosmask("mask_garosmask", EquipmentSlotType.HEAD),
                        ItemList.mask_captainshat = new MaskCaptainshat("mask_captainshat", EquipmentSlotType.HEAD),
                        ItemList.mask_giantsmask = new MaskGiantsmask("mask_giantsmask", EquipmentSlotType.HEAD),
                        ItemList.mask_fiercedeitysmask = new MaskFiercedeitysmask("mask_fiercedeitysmask", EquipmentSlotType.HEAD),
                        ItemList.mask_majorasmask = new MaskMajorasmask("mask_majorasmask",EquipmentSlotType.HEAD),

                        //Armors
                        ItemList.rocs_cape = new RocsCapeEffects("rocs_cape",EquipmentSlotType.CHEST),
                        ItemList.kokiri_cap = new ArmorKokiriEffects("kokiri_cap",EquipmentSlotType.HEAD),
                        ItemList.kokiri_tunic = new ArmorKokiriEffects("kokiri_tunic",EquipmentSlotType.CHEST),
                        ItemList.kokiri_leggings = new ArmorKokiriEffects("kokiri_leggings",EquipmentSlotType.LEGS),
                        ItemList.kokiri_boots = new ArmorKokiriEffects("kokiri_boots",EquipmentSlotType.FEET),
                        ItemList.zora_cap = new ArmorZoraEffects("zora_cap", EquipmentSlotType.HEAD),
                        ItemList.zora_tunic = new ArmorZoraEffects("zora_tunic", EquipmentSlotType.CHEST),
                        ItemList.zora_leggings = new ArmorZoraEffects("zora_leggings", EquipmentSlotType.LEGS),
                        ItemList.iron_boots = new IronBoots("iron_boots", EquipmentSlotType.FEET),

                        ItemList.zoras_flippers = new ArmorFlippersEffects("zoras_flippers", EquipmentSlotType.FEET),

                        ItemList.goron_cap = new ArmorGoronEffects("goron_cap", EquipmentSlotType.HEAD),
                        ItemList.goron_tunic = new ArmorGoronEffects("goron_tunic", EquipmentSlotType.CHEST),
                        ItemList.goron_leggings = new ArmorGoronEffects("goron_leggings", EquipmentSlotType.LEGS),
                        ItemList.hover_boots = new HoverBoots("hover_boots", EquipmentSlotType.FEET),

                        ItemList.purple_cap = new ArmorPurpleEffects("purple_cap",EquipmentSlotType.HEAD),
                        ItemList.purple_tunic = new ArmorPurpleEffects("purple_tunic",EquipmentSlotType.CHEST),
                        ItemList.purple_leggings = new ArmorPurpleEffects("purple_leggings",EquipmentSlotType.LEGS),
                        ItemList.pegasus_boots = new PegasusBoots("pegasus_boots",EquipmentSlotType.FEET),

                        ItemList.magic_armor_cap = new ArmorMagicArmor("magic_armor_cap",EquipmentSlotType.HEAD),
                        ItemList.magic_armor_tunic = new ArmorMagicArmor("magic_armor_tunic",EquipmentSlotType.CHEST),
                        ItemList.magic_armor_leggings = new ArmorMagicArmor("magic_armor_leggings",EquipmentSlotType.LEGS),
                        ItemList.magic_armor_boots = new ArmorMagicArmor("magic_armor_boots",EquipmentSlotType.FEET),

                        ItemList.dark_cap = new ArmorDarkEffects("dark_cap",EquipmentSlotType.HEAD),
                        ItemList.dark_tunic = new ArmorDarkEffects("dark_tunic",EquipmentSlotType.CHEST),
                        ItemList.dark_leggings = new ArmorDarkEffects("dark_leggings",EquipmentSlotType.LEGS),
                        ItemList.dark_boots = new ArmorDarkEffects("dark_boots",EquipmentSlotType.FEET),

                        ItemList.zora_armor_cap = new ArmorZoraArmorEffects("zora_armor_cap",EquipmentSlotType.HEAD),
                        ItemList.zora_armor_tunic = new ArmorZoraArmorEffects("zora_armor_tunic",EquipmentSlotType.CHEST),
                        ItemList.zora_armor_leggings = new ArmorZoraArmorEffects("zora_armor_leggings",EquipmentSlotType.LEGS),
                        ItemList.zora_armor_flippers = new ArmorZoraArmorEffects("zora_armor_flippers",EquipmentSlotType.FEET),

                        ItemList.flamebreaker_helmet = new ArmorFlamebreakerEffects("flamebreaker_helmet",EquipmentSlotType.HEAD),
                        ItemList.flamebreaker_tunic = new ArmorFlamebreakerEffects("flamebreaker_tunic",EquipmentSlotType.CHEST),
                        ItemList.flamebreaker_leggings = new ArmorFlamebreakerEffects("flamebreaker_leggings",EquipmentSlotType.LEGS),
                        ItemList.flamebreaker_boots = new ArmorFlamebreakerEffects("flamebreaker_boots",EquipmentSlotType.FEET)


                );
        Logger.info("Items registered.");
    }



    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll
                (
                        //Blocks

                        BlockList.rupee_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f, 1.0f).lightValue(0).sound(SoundType.GLASS)).setRegistryName(location("rupee_block")),
                        BlockList.blue_rupee_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f, 1.0f).lightValue(0).sound(SoundType.GLASS)).setRegistryName(location("blue_rupee_block")),
                        BlockList.red_rupee_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f, 1.0f).lightValue(0).sound(SoundType.GLASS)).setRegistryName(location("red_rupee_block")),
                        BlockList.silver_rupee_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f, 1.0f).lightValue(0).sound(SoundType.GLASS)).setRegistryName(location("silver_rupee_block")),
                        BlockList.gold_rupee_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f, 1.0f).lightValue(0).sound(SoundType.GLASS)).setRegistryName(location("gold_rupee_block")),
                        BlockList.spikes_block = new SpikesBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(13.0f, 13.0f).lightValue(0).sound(SoundType.STONE)).setRegistryName(location("spikes_block")),
                        BlockList.gossip_stone_block = new GossipStoneBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).lightValue(0).sound(SoundType.STONE)).setRegistryName(location("gossip_stone_block")),
                        BlockList.bush_block = new BushBlock(Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f, 0.0f).lightValue(0).sound(SoundType.CROP)).setRegistryName(location("bush_block")),
                        BlockList.chain_link_fence_block = new ChainLinkFenceBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.0f, 2.0f).lightValue(0).sound(SoundType.STONE)).setRegistryName(location("chain_link_fence_block")),
                        BlockList.torch_tower_block_bottom = new TorchTowerBlockBottom(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.8f, 0.8f).lightValue(0).sound(SoundType.WOOD)).setRegistryName(location("torch_tower_block_bottom")),
                        BlockList.torch_tower_block_top = new TorchTowerBlockTop(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.8f, 0.8f).lightValue(15).sound(SoundType.WOOD)).setRegistryName(location("torch_tower_block_top")),
                        BlockList.deku_flower_block = new DekuFlowerBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.4f, 0.4f).lightValue(0).sound(SoundType.CROP)).setRegistryName(location("deku_flower_block")),
                        BlockList.pot_block = new PotBlock(Block.Properties.create(Material.CLAY).hardnessAndResistance(0.1f, 0.1f).lightValue(0).sound(SoundType.GLASS)).setRegistryName(location("pot_block")),
                        BlockList.jar_block = new JarBlock(Block.Properties.create(Material.CLAY).hardnessAndResistance(0.1f, 0.1f).lightValue(0).sound(SoundType.GLASS)).setRegistryName(location("jar_block")),
                        BlockList.grate_block = new GrateBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).lightValue(0).sound(SoundType.STONE)).setRegistryName(location("grate_block")),
                        BlockList.grass_patch_block = new GrassPatch(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f, 0.2f).lightValue(0).sound(SoundType.SWEET_BERRY_BUSH)).setRegistryName(location("grass_patch_block")),
                        BlockList.master_ore_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(100.0f, 400.0f).lightValue(0).sound(SoundType.STONE)).setRegistryName(location("master_ore_block"))
                );
        Logger.info("Blocks registered.");
    }

    public void onFluidRegistry(final RegistryEvent.Register<Fluid> event)
    {
        //IForgeRegistry<Fluid> registry = event.getRegistry();

        //FluidLiquid.register(registry);
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(SoundHandler.class);
    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(SupersLegend.modid, name);
    }
}
