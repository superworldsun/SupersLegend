package superworldsun.superslegend;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShieldItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import superworldsun.superslegend.CustomLootMobs.*;
import superworldsun.superslegend.blocks.*;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntity;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntityRenderer;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.items.*;
import superworldsun.superslegend.items.armors.*;
import superworldsun.superslegend.items.arrows.*;
import superworldsun.superslegend.items.bows.BitBow;
import superworldsun.superslegend.items.bows.HerosBow;
import superworldsun.superslegend.items.bows.LynelBowX3;
import superworldsun.superslegend.items.bows.LynelBowX5;
import superworldsun.superslegend.items.masks.*;
import superworldsun.superslegend.lists.BlockList;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.lists.PotionList;
import superworldsun.superslegend.lists.ToolMaterialList;
import superworldsun.superslegend.util.handlers.SoundHandler;
import superworldsun.superslegend.world.gen.OreGeneration;

import javax.annotation.Nonnull;

import static net.minecraft.item.ItemModelsProperties.registerProperty;

@Mod(SupersLegend.modid)
public class SupersLegend
{
	public static SupersLegend instance;
	public static final String modid = "superslegend";
	private static final Logger Logger = LogManager.getLogger();
	
	public static final ItemGroup supers_legend = new SupersLegendItemGroup();


	public SupersLegend() 
	{
		instance = this;
		
		//PotionList.EFFECTS.register(MinecraftForge.EVENT_BUS);
		//PotionList.POTIONS.register(MinecraftForge.EVENT_BUS);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);

		EntityInit.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		
		MinecraftForge.EVENT_BUS.register(this);
		//Custom Loot Drops
				CustomLootZombie customLootZombie = new CustomLootZombie();
				MinecraftForge.EVENT_BUS.register(customLootZombie);
				
				CustomLootSkeleton customLootSkeleton = new CustomLootSkeleton();
				MinecraftForge.EVENT_BUS.register(customLootSkeleton);
				
				CustomLootSpider customLootSpider = new CustomLootSpider();
				MinecraftForge.EVENT_BUS.register(customLootSpider);
				
				CustomLootSlime customLootSlime = new CustomLootSlime();
				MinecraftForge.EVENT_BUS.register(customLootSlime);
				
				CustomLootCreeper customLootCreeper = new CustomLootCreeper();
				MinecraftForge.EVENT_BUS.register(customLootCreeper);
				
				CustomLootSilverfish customLootSilverfish = new CustomLootSilverfish();
				MinecraftForge.EVENT_BUS.register(customLootSilverfish);
				
				CustomLootPhantom customLootPhantom = new CustomLootPhantom();
				MinecraftForge.EVENT_BUS.register(customLootPhantom);
				
				CustomLootEnderman customLootEnderman = new CustomLootEnderman();
				MinecraftForge.EVENT_BUS.register(customLootEnderman);
				
				CustomLootCavespider customLootCavespider = new CustomLootCavespider();
				MinecraftForge.EVENT_BUS.register(customLootCavespider);
				
				CustomLootBlaze customLootBlaze = new CustomLootBlaze();
				MinecraftForge.EVENT_BUS.register(customLootBlaze);
				
				CustomLootGhast customLootGhast = new CustomLootGhast();
				MinecraftForge.EVENT_BUS.register(customLootGhast);
				
				CustomLootWitch customLootWitch = new CustomLootWitch();
				MinecraftForge.EVENT_BUS.register(customLootWitch);
				
				CustomLootWitherskeleton customLootWitherskeleton = new CustomLootWitherskeleton();
				MinecraftForge.EVENT_BUS.register(customLootWitherskeleton);
				
				CustomLootMagmacube customLootMagmacube = new CustomLootMagmacube();
				MinecraftForge.EVENT_BUS.register(customLootMagmacube);
				
				CustomLootDrowned customLootDrowned = new CustomLootDrowned();
				MinecraftForge.EVENT_BUS.register(customLootDrowned);
				
				CustomLootGuardian customLootGuardian = new CustomLootGuardian();
				MinecraftForge.EVENT_BUS.register(customLootGuardian);
				
				CustomLootElderguardian customLootElderguardian = new CustomLootElderguardian();
				MinecraftForge.EVENT_BUS.register(customLootElderguardian);
				
				CustomLootEndermite customLootEdermite = new CustomLootEndermite();
				MinecraftForge.EVENT_BUS.register(customLootEdermite);
				
				CustomLootShulker customLootShulker = new CustomLootShulker();
				MinecraftForge.EVENT_BUS.register(customLootShulker);
				
				CustomLootDragon customLootDragon = new CustomLootDragon();
				MinecraftForge.EVENT_BUS.register(customLootDragon);
				
				CustomLootWither customLootWither = new CustomLootWither();
				MinecraftForge.EVENT_BUS.register(customLootWither);
				
				CustomLootEvoker customLootEvoker = new CustomLootEvoker();
				MinecraftForge.EVENT_BUS.register(customLootEvoker);
				
				CustomLootStray customLootStray = new CustomLootStray();
				MinecraftForge.EVENT_BUS.register(customLootStray);
				
				CustomLootHusk customLootHusk = new CustomLootHusk();
				MinecraftForge.EVENT_BUS.register(customLootHusk);
				
				CustomLootZombievillager customLootZombievillager = new CustomLootZombievillager();
				MinecraftForge.EVENT_BUS.register(customLootZombievillager);
				
				CustomLootPillager customLootPillager = new CustomLootPillager();
				MinecraftForge.EVENT_BUS.register(customLootPillager);
				
				CustomLootRavager customLootRavager = new CustomLootRavager();
				MinecraftForge.EVENT_BUS.register(customLootRavager);
				
				CustomLootVindicator CustomLootVindicator = new CustomLootVindicator();
				MinecraftForge.EVENT_BUS.register(CustomLootVindicator);
		
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		OreGeneration.registerOres();
		//MinecraftForge.EVENT_BUS.register(HealthHandler.class);
		Logger.info("Setup method registered");
		//OreGeneration.generate();

		event.enqueueWork(() -> {
			GlobalEntityTypeAttributes.put(EntityInit.FAIRYENTITY.get(), FairyEntity.prepareAttributes().create());
		});
	}

	private void clientRegistries(final FMLClientSetupEvent event)
	{
		Logger.info("ClientRegistries method registered");
	}


	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{

		@SubscribeEvent
		static void clientSetup(final FMLClientSetupEvent event)
		{
			MinecraftForge.EVENT_BUS.register(SoundHandler.class);

			RenderTypeLookup.setRenderLayer(BlockList.chain_link_fence_block, RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(BlockList.deku_flower_block, RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(BlockList.grate_block, RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(BlockList.spikes_block, RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(BlockList.grass_patch_block, RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(BlockList.hidden_shadow_block, RenderType.getTranslucent());

			RenderingRegistry.registerEntityRenderingHandler(EntityInit.FAIRYENTITY.get(), FairyEntityRenderer::new);

		}



		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			event.getRegistry().registerAll
			(	
		//Items
					
			ItemList.rupee = new Rupee(new Item.Properties().group(supers_legend)).setRegistryName(location("rupee")),
			ItemList.blue_rupee = new BlueRupee(new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee")),
			ItemList.red_rupee = new RedRupee(new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee")),
			ItemList.silver_rupee = new SilverRupee(new Item.Properties().group(supers_legend)).setRegistryName(location("silver_rupee")),
			ItemList.gold_rupee = new GoldRupee(new Item.Properties().group(supers_legend)).setRegistryName(location("gold_rupee")),
			ItemList.heart = new Heart(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("heart")),
			ItemList.triforce_power_shard = new Item(new Item.Properties().maxStackSize(7).group(supers_legend)).setRegistryName(location("triforce_power_shard")),
			ItemList.triforce_wisdom_shard = new Item(new Item.Properties().maxStackSize(7).group(supers_legend)).setRegistryName(location("triforce_wisdom_shard")),
			ItemList.triforce_courage_shard = new Item(new Item.Properties().maxStackSize(7).group(supers_legend)).setRegistryName(location("triforce_courage_shard")),
			ItemList.rupee_pouch = new Item(new Item.Properties().maxStackSize(1)).setRegistryName(location("rupee_pouch")),
			ItemList.odolwas_remains = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("odolwas_remains")),
			ItemList.gohts_remains = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("gohts_remains")),
			ItemList.gyorgs_remains = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("gyorgs_remains")),
			ItemList.twinmolds_remains = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("twinmolds_remains")),

			ItemList.ancient_core = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("ancient_core")),
			ItemList.ancient_core_giant = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("ancient_core_giant")),
			ItemList.ancient_screw = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("ancient_screw")),
			ItemList.ancient_shaft = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("ancient_shaft")),
			ItemList.ancient_spring = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("ancient_spring")),

			ItemList.master_ore = new Item(new Item.Properties().maxStackSize(16).group(supers_legend)).setRegistryName(location("master_ore")),
			ItemList.master_sword_blade = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("master_sword_blade")),
			ItemList.master_sword_hilt = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("master_sword_hilt")),
			ItemList.kokiri_set = new KokiriSet(new Item.Properties().group(supers_legend)).setRegistryName(location("kokiri_set")),
			//ItemList.goron_set = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("goron_set")),
			//ItemList.zora_set = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("zora_set")),
			//ItemList.purple_set = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("purple_set")),
			//ItemList.zora_armor_set = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("zora_armor_set")),
			//ItemList.flamebreaker_set = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("flamebreaker_set")),
			//ItemList.dark_set = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("dark_set")),
			//ItemList.magic_armor_set = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("magic_armor_set")),


	//LIQUID REGISTERS


			//ItemList.poison_bucket = new BucketItem(() -> FluidList.poison, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)).setRegistryName("poison_bucket"),



	//BLOCK ITEMS

			ItemList.rupee_block = new BlockItem(BlockList.rupee_block, new Item.Properties().maxStackSize(5).group(supers_legend)).setRegistryName(BlockList.rupee_block.getRegistryName()),
			ItemList.blue_rupee_block = new BlockItem(BlockList.blue_rupee_block, new Item.Properties().maxStackSize(5).group(supers_legend)).setRegistryName(BlockList.blue_rupee_block.getRegistryName()),
			ItemList.red_rupee_block = new BlockItem(BlockList.red_rupee_block, new Item.Properties().maxStackSize(5).group(supers_legend)).setRegistryName(BlockList.red_rupee_block.getRegistryName()),
			ItemList.silver_rupee_block = new BlockItem(BlockList.silver_rupee_block, new Item.Properties().maxStackSize(5).group(supers_legend)).setRegistryName(BlockList.silver_rupee_block.getRegistryName()),
			ItemList.gold_rupee_block = new BlockItem(BlockList.gold_rupee_block, new Item.Properties().maxStackSize(5).group(supers_legend)).setRegistryName(BlockList.gold_rupee_block.getRegistryName()),
			ItemList.spikes_block = new BlockItem(BlockList.spikes_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.spikes_block.getRegistryName()),
			ItemList.gossip_stone_block = new BlockItem(BlockList.gossip_stone_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.gossip_stone_block.getRegistryName()),
			ItemList.bush_block = new BlockItem(BlockList.bush_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.bush_block.getRegistryName()),
			ItemList.chain_link_fence_block = new BlockItem(BlockList.chain_link_fence_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.chain_link_fence_block.getRegistryName()),
			ItemList.deku_flower_block = new BlockItem(BlockList.deku_flower_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.deku_flower_block.getRegistryName()),
			ItemList.pot_block = new BlockItem(BlockList.pot_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.pot_block.getRegistryName()),
			ItemList.jar_block = new BlockItem(BlockList.jar_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.jar_block.getRegistryName()),
			ItemList.grate_block = new BlockItem(BlockList.grate_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.grate_block.getRegistryName()),
			ItemList.grass_patch_block = new BlockItem(BlockList.grass_patch_block, new Item.Properties().group(supers_legend)).setRegistryName(BlockList.grass_patch_block.getRegistryName()),
			ItemList.torch_tower = new TorchTower(new Item.Properties().maxStackSize(16).group(supers_legend)).setRegistryName(location("torch_tower")),
			ItemList.master_ore_block = new BlockItem(BlockList.master_ore_block, new Item.Properties().maxStackSize(64).group(supers_legend)).setRegistryName(BlockList.master_ore_block.getRegistryName()),
			ItemList.shadow_block = new BlockItem(BlockList.shadow_block, new Item.Properties().maxStackSize(64).group(supers_legend)).setRegistryName(BlockList.shadow_block.getRegistryName()),
			ItemList.false_shadow_block = new BlockItem(BlockList.false_shadow_block, new Item.Properties().maxStackSize(64).group(supers_legend)).setRegistryName(BlockList.false_shadow_block.getRegistryName()),
			ItemList.hidden_shadow_block = new BlockItem(BlockList.hidden_shadow_block, new Item.Properties().maxStackSize(64).group(supers_legend)).setRegistryName(BlockList.hidden_shadow_block.getRegistryName()),



	//WEAPONS
			
			ItemList.kokiri_sword = new ItemCustomSword(ToolMaterialList.kokiri_sword,2, -2.3f, new Item.Properties().group(supers_legend)).setRegistryName(location("kokiri_sword")),
			ItemList.razor_sword = new ItemCustomSword(ToolMaterialList.razor_sword, 2, -2.5f, new Item.Properties().group(supers_legend)).setRegistryName(location("razor_sword")),
			ItemList.gilded_sword = new ItemCustomSword(ToolMaterialList.gilded_sword,2, -2.4f, new Item.Properties().group(supers_legend)).setRegistryName(location("gilded_sword")),
			ItemList.master_sword = new ItemCustomSword(ToolMaterialList.master_sword,2, -2.3f, new Item.Properties().group(supers_legend)).setRegistryName(location("master_sword")),
			ItemList.gaurdian_sword = new ItemCustomSword(ToolMaterialList.gaurdian_sword, 2, -2.5f, new Item.Properties().group(supers_legend)).setRegistryName(location("gaurdian_sword")),
			ItemList.heros_bow = new HerosBow(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("heros_bow")),
			ItemList.bit_bow = new BitBow(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("bit_bow")),
			ItemList.lynel_bow_x3 = new LynelBowX3(1, new Item.Properties().maxStackSize(1).maxDamage(45).group(supers_legend)).setRegistryName(location("lynel_bow_x3")),
			ItemList.lynel_bow_x5 = new LynelBowX5(1, new Item.Properties().maxStackSize(1).maxDamage(45).group(supers_legend)).setRegistryName(location("lynel_bow_x5")),
			ItemList.deku_shield = new ItemCustomShield(new Item.Properties().maxStackSize(1).maxDamage(500).group(supers_legend)).setRegistryName(location("deku_shield")),
			ItemList.hylian_shield = new ShieldItem(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("hylian_shield")),
			ItemList.fire_arrow = new ArrowFire(new Item.Properties().group(supers_legend)).setRegistryName(location("fire_arrow")),
			ItemList.ice_arrow = new ArrowIce(new Item.Properties().group(supers_legend)).setRegistryName(location("ice_arrow")),
			ItemList.shock_arrow = new ArrowShock(new Item.Properties().group(supers_legend)).setRegistryName(location("shock_arrow")),
			ItemList.bomb_arrow = new ArrowBomb(new Item.Properties().group(supers_legend)).setRegistryName(location("bomb_arrow")),
			ItemList.ancient_arrow = new ArrowAncient(new Item.Properties().group(supers_legend)).setRegistryName(location("ancient_arrow")),
			//ItemList.moon_pearl = new MoonPearl(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("moon_pearl")),
			ItemList.heros_secret_stash = new HerosSecretStash(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("heros_secret_stash")),
			ItemList.book_of_mudora = new BookOfMudora(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("book_of_mudora")),
			ItemList.silver_scale = new SilverScale(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("silver_scale")),
			ItemList.golden_scale = new GoldenScale(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("golden_scale")),
			ItemList.rocs_feather = new RocsFeather(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("rocs_feather")),
			ItemList.magic_mirror = new MagicMirror(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("magic_mirror")),
			ItemList.magic_cape = new MagicCape(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("magic_cape")),
			ItemList.bomb = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("bomb")),
			ItemList.empty_container = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("empty_container")),
			ItemList.farores_wind = new FaroresWind(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("farores_wind")),
			ItemList.dins_fire = new DinsFire(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("dins_fire")),
			ItemList.nayrus_love = new NayrusLove(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("nayrus_love")),
			ItemList.deku_leaf = new DekuLeaf(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("deku_leaf")),
			ItemList.lens_of_truth = new LensOfTruth(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("lens_of_truth")),
			ItemList.fairy_ocarina = new FairyOcarina(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("fairy_ocarina")),
			ItemList.ocarina_of_time = new OcarinaOfTime(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("ocarina_of_time")),
			ItemList.red_jelly = new Item(new Item.Properties().maxStackSize(16).group(supers_legend)).setRegistryName(location("red_jelly")),
			ItemList.green_jelly = new Item(new Item.Properties().maxStackSize(16).group(supers_legend)).setRegistryName(location("green_jelly")),
			ItemList.blue_jelly = new Item(new Item.Properties().maxStackSize(16).group(supers_legend)).setRegistryName(location("blue_jelly")),
			ItemList.red_potion_mix = new RedPotionMix(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("red_potion_mix")),
			ItemList.green_potion_mix = new GreenPotionMix(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("green_potion_mix")),
			ItemList.blue_potion_mix = new BluePotionMix(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("blue_potion_mix")),
			ItemList.red_potion = new RedPotion(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("red_potion")),
			ItemList.green_potion = new GreenPotion(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("green_potion")),
			ItemList.blue_potion = new BluePotion(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("blue_potion")),
			//ItemList.magnetic_glove = new MagneticGlove(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("magnetic_glove")),
			ItemList.triforce = new Triforce(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("triforce")),
			ItemList.triforce_power = new TriforcePower(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("triforce_power")),
			ItemList.triforce_wisdom = new TriforceWisdom(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("triforce_wisdom")),
			ItemList.triforce_courage = new TriforceCourage(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("triforce_courage")),



	//TOOLS

			ItemList.rupee_sword = new ItemCustomSword(ToolMaterialList.rupee_sword,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_sword")),
			ItemList.blue_rupee_sword = new ItemCustomSword(ToolMaterialList.blue_rupee_sword,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_sword")),
			ItemList.red_rupee_sword = new ItemCustomSword(ToolMaterialList.red_rupee_sword,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_sword")),
			ItemList.silver_rupee_sword = new ItemCustomSword(ToolMaterialList.silver_rupee_sword,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("silver_rupee_sword")),
			ItemList.gold_rupee_sword = new ItemCustomSword(ToolMaterialList.gold_rupee_sword,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("gold_rupee_sword")),
			ItemList.rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.rupee_pickaxe, 				0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_pickaxe")),
			ItemList.blue_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.blue_rupee_pickaxe, 	0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_pickaxe")),
			ItemList.red_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.red_rupee_pickaxe, 		0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_pickaxe")),
			ItemList.silver_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.silver_rupee_pickaxe,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("silver_rupee_pickaxe")),
			ItemList.gold_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.gold_rupee_pickaxe,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("gold_rupee_pickaxe")),
			ItemList.rupee_axe = new ItemCustomAxe(ToolMaterialList.rupee_axe, 						0, -3.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_axe")),
			ItemList.blue_rupee_axe = new ItemCustomAxe(ToolMaterialList.blue_rupee_axe, 			0, -3.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_axe")),
			ItemList.red_rupee_axe = new ItemCustomAxe(ToolMaterialList.red_rupee_axe, 				0, -3.1f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_axe")),
			ItemList.silver_rupee_axe = new ItemCustomAxe(ToolMaterialList.silver_rupee_axe, 		0, -3.1f, new Item.Properties().group(supers_legend)).setRegistryName(location("silver_rupee_axe")),
			ItemList.gold_rupee_axe = new ItemCustomAxe(ToolMaterialList.gold_rupee_axe, 		0, -3.1f, new Item.Properties().group(supers_legend)).setRegistryName(location("gold_rupee_axe")),
			ItemList.rupee_shovel = new ItemCustomShovel(ToolMaterialList.rupee_shovel, 				0, -2.8f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_shovel")),
			ItemList.blue_rupee_shovel = new ItemCustomShovel(ToolMaterialList.blue_rupee_shovel, 	0, -2.8f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_shovel")),
			ItemList.red_rupee_shovel = new ItemCustomShovel(ToolMaterialList.red_rupee_shovel, 		0, -2.6f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_shovel")),
			ItemList.silver_rupee_shovel = new ItemCustomShovel(ToolMaterialList.silver_rupee_shovel,	0, -2.6f, new Item.Properties().group(supers_legend)).setRegistryName(location("silver_rupee_shovel")),
			ItemList.gold_rupee_shovel = new ItemCustomShovel(ToolMaterialList.gold_rupee_shovel,	0, -2.6f, new Item.Properties().group(supers_legend)).setRegistryName(location("gold_rupee_shovel")),
			ItemList.rupee_hoe = new ItemCustomHoe(ToolMaterialList.rupee_hoe, 				0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_hoe")),
			ItemList.blue_rupee_hoe = new ItemCustomHoe(ToolMaterialList.blue_rupee_hoe, 	0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_hoe")),
			ItemList.red_rupee_hoe = new ItemCustomHoe(ToolMaterialList.red_rupee_hoe, 		0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_hoe")),
			ItemList.silver_rupee_hoe = new ItemCustomHoe(ToolMaterialList.silver_rupee_hoe,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("silver_rupee_hoe")),
			ItemList.gold_rupee_hoe = new ItemCustomHoe(ToolMaterialList.gold_rupee_hoe,0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("gold_rupee_hoe")),



	//MASKS

			ItemList.mask_clay = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("mask_clay")),
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
			ItemList.mask_hawkeyemask = new MaskHawkeyemask("mask_hawkeyemask",EquipmentSlotType.HEAD),
			ItemList.mask_moonmask = new MaskMoonmask("mask_moonmask",EquipmentSlotType.HEAD),
			ItemList.mask_sunmask = new MaskSunmask("mask_sunmask",EquipmentSlotType.HEAD),



	//ARMORS
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
			ItemList.flamebreaker_boots = new ArmorFlamebreakerEffects("flamebreaker_boots",EquipmentSlotType.FEET),
			ItemList.ancient_helmet = new ArmorAncientEffects("ancient_helmet",EquipmentSlotType.HEAD),
			ItemList.ancient_cuirass = new ArmorAncientEffects("ancient_cuirass",EquipmentSlotType.CHEST),
			ItemList.ancient_greaves = new ArmorAncientEffects("ancient_greaves",EquipmentSlotType.LEGS),
			ItemList.ancient_boots = new ArmorAncientEffects("ancient_boots",EquipmentSlotType.FEET),
			ItemList.barbarian_helmet = new ArmorAncientEffects("barbarian_helmet",EquipmentSlotType.HEAD),
			ItemList.barbarian_armor = new ArmorAncientEffects("barbarian_armor",EquipmentSlotType.CHEST),
			ItemList.barbarian_leg_wraps = new ArmorAncientEffects("barbarian_leg_wraps",EquipmentSlotType.LEGS),
			ItemList.barbarian_boots = new ArmorAncientEffects("barbarian_boots",EquipmentSlotType.FEET)
			);
			Logger.info("Items registered.");		
		}
		
		
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event)
		{
			event.getRegistry().registerAll
			(	
		//BLOCKS
					
					BlockList.rupee_block = new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0f, 1.0f).setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("rupee_block")),
					BlockList.blue_rupee_block = new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0f, 1.0f).setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("blue_rupee_block")),
					BlockList.red_rupee_block = new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0f, 1.0f).setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("red_rupee_block")),
					BlockList.silver_rupee_block = new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0f, 1.0f).setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("silver_rupee_block")),
					BlockList.gold_rupee_block = new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0f, 1.0f).setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("gold_rupee_block")),
					BlockList.spikes_block = new SpikesBlock(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).notSolid().hardnessAndResistance(13.0f, 13.0f).setLightLevel(value -> 0).sound(SoundType.STONE)).setRegistryName(location("spikes_block")),
					BlockList.gossip_stone_block = new GossipStoneBlock(Block.Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).notSolid().hardnessAndResistance(1.0f, 1.0f).setLightLevel(value -> 0).sound(SoundType.STONE)).setRegistryName(location("gossip_stone_block")),
					BlockList.bush_block = new BushBlock(Block.Properties.create(Material.PLANTS).notSolid().hardnessAndResistance(0.0f, 0.0f).setLightLevel(value -> 0).sound(SoundType.CROP)).setRegistryName(location("bush_block")),
					BlockList.chain_link_fence_block = new ChainLinkFenceBlock(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).notSolid().hardnessAndResistance(2.0f, 2.0f).setLightLevel(value -> 0).sound(SoundType.STONE)).setRegistryName(location("chain_link_fence_block")),
					BlockList.torch_tower_block_bottom = new TorchTowerBlockBottom(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).hardnessAndResistance(0.8f, 0.8f).setLightLevel(value -> 0).sound(SoundType.WOOD)).setRegistryName(location("torch_tower_block_bottom")),
					BlockList.torch_tower_block_top = new TorchTowerBlockTop(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).hardnessAndResistance(0.8f, 0.8f).setLightLevel(value -> 15).sound(SoundType.WOOD)).setRegistryName(location("torch_tower_block_top")),
					BlockList.deku_flower_block = new DekuFlowerBlock(Block.Properties.create(Material.WOOD).notSolid().hardnessAndResistance(0.4f, 0.4f).setLightLevel(value -> 0).sound(SoundType.CROP)).setRegistryName(location("deku_flower_block")),
					BlockList.pot_block = new PotBlock(Block.Properties.create(Material.CLAY).notSolid().hardnessAndResistance(0.1f, 0.1f).setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("pot_block")),
					BlockList.jar_block = new JarBlock(Block.Properties.create(Material.CLAY).notSolid().hardnessAndResistance(0.1f, 0.1f).setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("jar_block")),
					BlockList.grate_block = new GrateBlock(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).notSolid().hardnessAndResistance(3.0f, 3.0f).setLightLevel(value -> 0).sound(SoundType.STONE)).setRegistryName(location("grate_block")),
					BlockList.grass_patch_block = new GrassPatch(Block.Properties.create(Material.LEAVES).notSolid().hardnessAndResistance(0.2f, 0.2f).setLightLevel(value -> 0).sound(SoundType.SWEET_BERRY_BUSH)).setRegistryName(location("grass_patch_block")),
					BlockList.master_ore_block = new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(100.0f, 400.0f).setLightLevel(value -> 0).sound(SoundType.STONE)).setRegistryName(location("master_ore_block")),
					BlockList.shadow_block = new Block(Block.Properties.create(Material.CLAY).variableOpacity().hardnessAndResistance(1.0f, 1.0f).notSolid().setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("shadow_block")),
					BlockList.false_shadow_block = new FalseShadowBlock(Block.Properties.create(Material.CLAY).variableOpacity().hardnessAndResistance(1.0f, 1.0f).notSolid().setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("false_shadow_block")),
					BlockList.hidden_shadow_block = new HiddenShadowBlock(Block.Properties.create(Material.CLAY).variableOpacity().hardnessAndResistance(1.0f, 1.0f).notSolid().setLightLevel(value -> 0).sound(SoundType.GLASS)).setRegistryName(location("hidden_shadow_block"))
					
					//BlockList.poison = new FlowingFluidBlock(() -> FluidList.poison, Block.Properties.create(Material.WATER).doesNotBlockMovement().noDrops()).setRegistryName(location("poison"))
			);
			Logger.info("Blocks registered.");
		}
		
		public static ResourceLocation location(String name)
		{
			return new ResourceLocation(modid, name);
		}
		
		@SubscribeEvent
		 public static void registerFluids(final RegistryEvent.Register<Fluid> event)
		 {
				
				event.getRegistry().registerAll
				(
						//FluidList.flowing_poison = (Flowing) new FluidPoison.Flowing().setRegistryName(location("flowing_poison")),
			        	//FluidList.poison = (Source) new FluidPoison.Source().setRegistryName(location("poison"))
				);
				
		 }

		@SubscribeEvent
		 public static void registerPotions(final RegistryEvent.Register<Potion> event)
		 {
				
				event.getRegistry().registerAll
				(
						//PotionList.more_health_potion = new Potion(new EffectInstance(PotionList.more_health_effect, 3600)).setRegistryName(location("more_health"))
						//PotionList.size_potion = new Potion(new EffectInstance(PotionList.size_effect, 3600)).setRegistryName(location("size"))

				);
				
		 }

		@SubscribeEvent
		 public static void registerEffects(final RegistryEvent.Register<Effect> event)
		 {
		 	event.getRegistry().registerAll
					(
							//PotionList.more_health_effect = new PotionList.MoreHealthEffect(EffectType.BENEFICIAL, 0xd4FF00).addAttributesModifier(SharedMonsterAttributes.MAX_HEALTH, "55FCED67-E92A-486E-9800-B47F202C4386", 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL).setRegistryName(location("more_health")),
							PotionList.iron_boots_effect = new PotionList.IronBootsEffect(EffectType.BENEFICIAL, 0xd4FF10).addAttributesModifier(ForgeMod.SWIM_SPEED.get(),"55FCED67-E92A-486E-9800-B47F202C4386", 2.0f, AttributeModifier.Operation.MULTIPLY_TOTAL).setRegistryName(location("iron_boots")),
							PotionList.hover_boots_effect = new PotionList.HoverBootsEffect(EffectType.BENEFICIAL, 0xd4FF10).addAttributesModifier(ForgeMod.ENTITY_GRAVITY.get(), "55FCED67-E92A-486E-9800-B47F202C4386", 0.0f, AttributeModifier.Operation.ADDITION).setRegistryName(location("hover_boots")),
							PotionList.zoras_grace_effect = new PotionList.ZorasGraceEffect(EffectType.BENEFICIAL, 0xd4FF10).addAttributesModifier(ForgeMod.SWIM_SPEED.get(), "55FCED67-E92A-486E-9800-B47F202C4386", 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL).setRegistryName(location("zoras_grace"))
					);
		 }

		@SubscribeEvent
		public static void setModelProperties(FMLClientSetupEvent event) {
			registerProperty(ItemList.heros_bow, new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
				if (p_239429_2_ == null) {
					return 0.0F;
				} else {
					return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
				}
			});
			registerProperty(ItemList.heros_bow, new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);

			registerProperty(ItemList.lynel_bow_x3, new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
				if (p_239429_2_ == null) {
					return 0.0F;
				} else {
					return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
				}
			});
			registerProperty(ItemList.lynel_bow_x3, new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);

			registerProperty(ItemList.lynel_bow_x5, new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
				if (p_239429_2_ == null) {
					return 0.0F;
				} else {
					return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
				}
			});
			registerProperty(ItemList.lynel_bow_x5, new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);

			registerProperty(ItemList.deku_shield, new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isHandActive() && p_239421_2_.getActiveItemStack() == p_239421_0_ ? 1.0F : 0.0F);

			registerProperty(ItemList.hylian_shield, new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isHandActive() && p_239421_2_.getActiveItemStack() == p_239421_0_ ? 1.0F : 0.0F);



		}
	}
}