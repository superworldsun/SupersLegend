package superworldsun.superslegend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import superworldsun.superslegend.items.ArmorGoronEffects;
import superworldsun.superslegend.items.ArmorZoraEffects;
import superworldsun.superslegend.items.ItemCustomAxe;
import superworldsun.superslegend.items.ItemCustomHoe;
import superworldsun.superslegend.items.ItemCustomPickaxe;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.lists.ToolMaterialList;

@Mod("superslegend")
public class SupersLegend 
{
	public static SupersLegend istance;
	public static final String modid = "superslegend";
	private static final Logger Logger = LogManager.getLogger(modid);
	
	public static final ItemGroup supers_legend = new SupersLegendItemGroup();
	
	
	public SupersLegend() 
	{
		istance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		Logger.info("Setup method registered");
	}
	
	private void clientRegistries(final FMLClientSetupEvent event)
	{
		Logger.info("ClientRegistries method registered");
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			event.getRegistry().registerAll
			(	
			
			ItemList.rupee = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("rupee")),
			ItemList.blue_rupee = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee")),
			ItemList.red_rupee = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee")),
			ItemList.orange_rupee = new Item(new Item.Properties().group(supers_legend)).setRegistryName(location("orange_rupee")),
			
			ItemList.kokiri_sword = new SwordItem(ToolMaterialList.kokiri_sword,		(int) 2, -2f, new Item.Properties().group(supers_legend)).setRegistryName(location("kokiri_sword")),
			ItemList.razor_sword = new SwordItem(ToolMaterialList.razor_sword, 			(int) 2, -2.6f, new Item.Properties().group(supers_legend)).setRegistryName(location("razor_sword")),
			ItemList.gilded_sword = new SwordItem(ToolMaterialList.gilded_sword, 		(int) 2, -2.6f, new Item.Properties().group(supers_legend)).setRegistryName(location("gilded_sword")),
			
			ItemList.rupee_sword = new SwordItem(ToolMaterialList.rupee_sword, 				(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_sword")),
			ItemList.blue_rupee_sword = new SwordItem(ToolMaterialList.blue_rupee_sword, 	(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_sword")),
			ItemList.red_rupee_sword = new SwordItem(ToolMaterialList.red_rupee_sword, 		(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_sword")),
			ItemList.orange_rupee_sword = new SwordItem(ToolMaterialList.orange_rupee_sword,(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("orange_rupee_sword")),
			
		//Tools
			ItemList.rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.rupee_pickaxe, 				(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_pickaxe")),
			ItemList.blue_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.blue_rupee_pickaxe, 	(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_pickaxe")),
			ItemList.red_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.red_rupee_pickaxe, 		(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_pickaxe")),
			ItemList.orange_rupee_pickaxe = new ItemCustomPickaxe(ToolMaterialList.orange_rupee_pickaxe,(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("orange_rupee_pickaxe")),
			
			ItemList.rupee_axe = new ItemCustomAxe(ToolMaterialList.rupee_axe, 						(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_axe")),
			ItemList.blue_rupee_axe = new ItemCustomAxe(ToolMaterialList.blue_rupee_axe, 			(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_axe")),
			ItemList.red_rupee_axe = new ItemCustomAxe(ToolMaterialList.red_rupee_axe, 				(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_axe")),
			ItemList.orange_rupee_axe = new ItemCustomAxe(ToolMaterialList.orange_rupee_axe, 		(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("orange_rupee_axe")),
			
			ItemList.rupee_shovel = new ShovelItem(ToolMaterialList.rupee_shovel, 				(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_shovel")),
			ItemList.blue_rupee_shovel = new ShovelItem(ToolMaterialList.blue_rupee_shovel, 		(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_shovel")),
			ItemList.red_rupee_shovel = new ShovelItem(ToolMaterialList.red_rupee_shovel, 		(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_shovel")),
			ItemList.orange_rupee_shovel = new ShovelItem(ToolMaterialList.orange_rupee_shovel,	(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("orange_rupee_shovel")),
			
			ItemList.rupee_hoe = new ItemCustomHoe(ToolMaterialList.rupee_hoe, 				(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("rupee_hoe")),
			ItemList.blue_rupee_hoe = new ItemCustomHoe(ToolMaterialList.blue_rupee_hoe, 	(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("blue_rupee_hoe")),
			ItemList.red_rupee_hoe = new ItemCustomHoe(ToolMaterialList.red_rupee_hoe, 		(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("red_rupee_hoe")),
			ItemList.orange_rupee_hoe = new ItemCustomHoe(ToolMaterialList.orange_rupee_hoe,(int) 0, -2.2f, new Item.Properties().group(supers_legend)).setRegistryName(location("orange_rupee_hoe")),
			
					//Masks
			ItemList.mask_clay = new Item(new Item.Properties().maxStackSize(1).group(supers_legend)).setRegistryName(location("mask_clay")),
			ItemList.mask_postmanshat = new ArmorItem(ArmourMaterialList.postmanshat, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_postmanshat"),

			ItemList.mask_blastmask = new ArmorItem(ArmourMaterialList.blastmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_blastmask"),

			ItemList.mask_bremenmask = new ArmorItem(ArmourMaterialList.bremenmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_bremenmask"),
			ItemList.mask_greatfairymask = new ArmorItem(ArmourMaterialList.greatfairymask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_greatfairymask"),
			ItemList.mask_dekumask = new ArmorItem(ArmourMaterialList.dekumask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_dekumask"),
			ItemList.mask_keatonmask = new ArmorItem(ArmourMaterialList.keatonmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_keatonmask"),
			ItemList.mask_bremenmask = new ArmorItem(ArmourMaterialList.bremenmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_bremenmask"),

			ItemList.mask_maskofscents = new ArmorItem(ArmourMaterialList.maskofscents, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_maskofscents"),
			ItemList.mask_goronmask = new ArmorItem(ArmourMaterialList.goronmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_goronmask"),
			ItemList.mask_romanismask = new ArmorItem(ArmourMaterialList.romanismask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_romanismask"),
			ItemList.mask_troupeleadersmask = new ArmorItem(ArmourMaterialList.troupeleadersmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_troupeleadersmask"),
			ItemList.mask_kafeismask = new ArmorItem(ArmourMaterialList.kafeismask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_kafeismask"),
			ItemList.mask_couplesmask = new ArmorItem(ArmourMaterialList.couplesmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_couplesmask"),
			ItemList.mask_maskoftruth = new ArmorItem(ArmourMaterialList.maskoftruth, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_maskoftruth"),
			ItemList.mask_zoramask = new ArmorItem(ArmourMaterialList.zoramask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_zoramask"),
			ItemList.mask_kamarosmask = new ArmorItem(ArmourMaterialList.kamarosmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_kamarosmask"),
			ItemList.mask_gibdomask = new ArmorItem(ArmourMaterialList.gibdomask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_gibdomask"),
			ItemList.mask_garosmask = new ArmorItem(ArmourMaterialList.garosmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_garosmask"),
			ItemList.mask_captainshat = new ArmorItem(ArmourMaterialList.captainshat, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_captainshat"),
			ItemList.mask_giantsmask = new ArmorItem(ArmourMaterialList.giantsmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_giantsmask"),
			ItemList.mask_fiercedeitysmask = new ArmorItem(ArmourMaterialList.fiercedeitysmask, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("mask_fiercedeitysmask"),
			
			
			
		//Armors
			ItemList.kokiri_cap = new ArmorItem(ArmourMaterialList.kokiri, EquipmentSlotType.HEAD, new Item.Properties().group(supers_legend)).setRegistryName("kokiri_cap"),
			ItemList.kokiri_tunic = new ArmorItem(ArmourMaterialList.kokiri, EquipmentSlotType.CHEST, new Item.Properties().group(supers_legend)).setRegistryName("kokiri_tunic"),
			ItemList.kokiri_leggings = new ArmorItem(ArmourMaterialList.kokiri, EquipmentSlotType.LEGS, new Item.Properties().group(supers_legend)).setRegistryName("kokiri_leggings"),
			ItemList.kokiri_boots = new ArmorItem(ArmourMaterialList.kokiri, EquipmentSlotType.FEET, new Item.Properties().group(supers_legend)).setRegistryName("kokiri_boots"),
			ItemList.zora_cap = new ArmorZoraEffects("zora_cap", EquipmentSlotType.HEAD),
			ItemList.zora_tunic = new ArmorZoraEffects("zora_tunic", EquipmentSlotType.CHEST),
			ItemList.zora_leggings = new ArmorZoraEffects("zora_leggings", EquipmentSlotType.LEGS),
			ItemList.iron_boots = new ArmorZoraEffects("iron_boots", EquipmentSlotType.FEET),
			ItemList.goron_cap = new ArmorGoronEffects("goron_cap", EquipmentSlotType.HEAD),
			ItemList.goron_tunic = new ArmorGoronEffects("goron_tunic", EquipmentSlotType.CHEST),
			ItemList.goron_leggings = new ArmorGoronEffects("goron_leggings", EquipmentSlotType.LEGS),
			ItemList.hover_boots = new ArmorGoronEffects("hover_boots", EquipmentSlotType.FEET)
			);
			Logger.info("Items registered.");		
		}
		
		private static ResourceLocation location(String name)
		{
			return new ResourceLocation(modid, name);
		}
	}
}