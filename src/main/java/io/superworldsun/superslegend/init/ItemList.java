package io.superworldsun.superslegend.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

import io.superworldsun.superslegend.items.armor.*;
import io.superworldsun.superslegend.items.tools.*;
import io.superworldsun.superslegend.items.ItemBase;
import io.superworldsun.superslegend.util.Reference;

//TODO: Document this class and methods inside
public class ItemList
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	
	//Materials (name, harvestLevel, maxUses, efficiency, damage, enchantability)
	
	public static final ToolMaterial MATERIAL_KOKIRI_SWORD = EnumHelper.addToolMaterial	("material_kokiri_sword" , 1, 0, -2.6f, 1.0f, 0);
	public static final ToolMaterial MATERIAL_RAZOR_SWORD = EnumHelper.addToolMaterial	("material_razor_sword"	 , 1, 100, -2.6f, 2.0f, 0);
	public static final ToolMaterial MATERIAL_GILDED_SWORD = EnumHelper.addToolMaterial	("material_gilded_sword" , 1, 0, -2.6f, 2.0f, 0);
	public static final ToolMaterial MATERIAL_RUPEE = EnumHelper.addToolMaterial		("material_rupee"		 , 1, 32, 2f, 2f, 0);
	public static final ToolMaterial MATERIAL_BLUE_RUPEE = EnumHelper.addToolMaterial	("material_blue_rupee"	 , 540, 240, 2f, 2f, 0);
	public static final ToolMaterial MATERIAL_RED_RUPEE = EnumHelper.addToolMaterial	("material_red_rupee"	 , 3, 720, 2f, 2f, 0);
	public static final ToolMaterial MATERIAL_ORANGE_RUPEE = EnumHelper.addToolMaterial	("material_orange_rupee" , 4, 1204, 2f, 2f, 0);
	
	public static final ArmorMaterial ARMOR_MATERIAL_KOKIRI = EnumHelper.addArmorMaterial("armor_material_kokiri", Reference.MODID + ":kokiri", 5,
			new int[] {1, 2, 3, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_ZORA = EnumHelper.addArmorMaterial("armor_material_zora", Reference.MODID + ":zora", 5,
			new int[] {1, 2, 3, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_GORON = EnumHelper.addArmorMaterial("armor_material_zora", Reference.MODID + ":goron", 5,
			new int[] {1, 2, 3, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_POSTMANSHAT = EnumHelper.addArmorMaterial("armor_material_mask_postmanshat", Reference.MODID + ":mask_postmanshat", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_ALLNIGHTMASK = EnumHelper.addArmorMaterial("armor_material_mask_allnightmask", Reference.MODID + ":mask_allnightmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_BLASTMASK = EnumHelper.addArmorMaterial("armor_material_mask_blastmask", Reference.MODID + ":mask_blastmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_STONEMASK = EnumHelper.addArmorMaterial("armor_material_mask_stonemask", Reference.MODID + ":mask_stonemask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_GREATFAIRYMASK = EnumHelper.addArmorMaterial("armor_material_mask_greatfairymask", Reference.MODID + ":mask_greatfairymask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_DEKUMASK = EnumHelper.addArmorMaterial("armor_material_mask_dekumask", Reference.MODID + ":mask_dekumask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_KEATONMASK = EnumHelper.addArmorMaterial("armor_material_mask_keatonmask", Reference.MODID + ":mask_keatonmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_BREMENMASK = EnumHelper.addArmorMaterial("armor_material_mask_bremenmask", Reference.MODID + ":mask_bremenmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_BUNNYHOOD = EnumHelper.addArmorMaterial("armor_material_mask_bunnyhood", Reference.MODID + ":mask_bunnyhood", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_DONGEROSMASK = EnumHelper.addArmorMaterial("armor_material_mask_dongerosmask", Reference.MODID + ":mask_dongerosmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_MASKOFSCENTS = EnumHelper.addArmorMaterial("armor_material_mask_maskofscents", Reference.MODID + ":mask_maskofscents", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_GORONMASK = EnumHelper.addArmorMaterial("armor_material_mask_goronmask", Reference.MODID + ":mask_goronmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_ROMANISMASK = EnumHelper.addArmorMaterial("armor_material_mask_romanismask", Reference.MODID + ":mask_romanismask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_TROUPELEADERSMASK = EnumHelper.addArmorMaterial("armor_material_mask_troupeleadersmask", Reference.MODID + ":mask_troupeleadersmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_KAFEISMASK = EnumHelper.addArmorMaterial("armor_material_mask_kafeismask", Reference.MODID + ":mask_kafeismask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_COUPLESMASK = EnumHelper.addArmorMaterial("armor_material_mask_couplesmask", Reference.MODID + ":mask_couplesmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_MASKOFTRUTH = EnumHelper.addArmorMaterial("armor_material_mask_maskoftruth", Reference.MODID + ":mask_maskoftruth", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_ZORAMASK = EnumHelper.addArmorMaterial("armor_material_mask_zoramask", Reference.MODID + ":mask_zoramask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_KAMAROSMASK = EnumHelper.addArmorMaterial("armor_material_mask_kamarosmask", Reference.MODID + ":mask_kamarosmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_GIBDOMASK = EnumHelper.addArmorMaterial("armor_material_mask_gibdomask", Reference.MODID + ":mask_gibdomask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_GAROSMASK = EnumHelper.addArmorMaterial("armor_material_mask_garosmask", Reference.MODID + ":mask_garosmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_CAPTAINSHAT = EnumHelper.addArmorMaterial("armor_material_mask_captainshat", Reference.MODID + ":mask_captainshat", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_GIANTSMASK = EnumHelper.addArmorMaterial("armor_material_mask_giantsmask", Reference.MODID + ":mask_giantsmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MATERIAL_MASK_FIERCEDEITYSMASK = EnumHelper.addArmorMaterial("armor_material_mask_fiercedeitysmask", Reference.MODID + ":mask_fiercedeitysmask", 5,
			new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	
	
	//Items
	public static final Item RUPEE = new ItemBase("rupee");
	public static final Item BLUE_RUPEE = new ItemBase("blue_rupee");
	public static final Item RED_RUPEE = new ItemBase("red_rupee");
	public static final Item ORANGE_RUPEE = new ItemBase("orange_rupee");
	public static final Item MASK_CLAY = new ItemBase("mask_clay");
	
	//Swords
	public static final ItemSword KOKIRI_SWORD = new ToolSword("kokiri_sword", MATERIAL_KOKIRI_SWORD);
	public static final ItemSword RAZOR_SWORD = new ToolSword("razor_sword", MATERIAL_RAZOR_SWORD);
	public static final ItemSword GILDED_SWORD = new ToolSword("gilded_sword", MATERIAL_GILDED_SWORD);
	
	public static final ItemSword RUPEE_SWORD = new ToolSword("rupee_sword", MATERIAL_RUPEE);
	public static final ItemSword BLUE_RUPEE_SWORD = new ToolSword("blue_rupee_sword", MATERIAL_BLUE_RUPEE);
	public static final ItemSword RED_RUPEE_SWORD = new ToolSword("red_rupee_sword", MATERIAL_RED_RUPEE);
	public static final ItemSword ORANGE_RUPEE_SWORD = new ToolSword("orange_rupee_sword", MATERIAL_ORANGE_RUPEE);
	
	//Tools
	public static final ItemPickaxe RUPEE_PICKAXE = new ToolPickaxe("rupee_pickaxe", MATERIAL_RUPEE);
	public static final ItemPickaxe BLUE_RUPEE_PICKAXE = new ToolPickaxe("blue_rupee_pickaxe", MATERIAL_BLUE_RUPEE);
	public static final ItemPickaxe RED_RUPEE_PICKAXE = new ToolPickaxe("red_rupee_pickaxe", MATERIAL_RED_RUPEE);
	public static final ItemPickaxe ORANGE_RUPEE_PICKAXE = new ToolPickaxe("orange_rupee_pickaxe", MATERIAL_ORANGE_RUPEE);
	
	public static final ItemAxe RUPEE_AXE = new ToolAxe("rupee_axe", MATERIAL_RUPEE);
	public static final ItemAxe BLUE_RUPEE_AXE = new ToolAxe("blue_rupee_axe", MATERIAL_BLUE_RUPEE);
	public static final ItemAxe RED_RUPEE_AXE = new ToolAxe("red_rupee_axe", MATERIAL_RED_RUPEE);
	public static final ItemAxe ORANGE_RUPEE_AXE = new ToolAxe("orange_rupee_axe", MATERIAL_ORANGE_RUPEE);
	
	public static final ItemHoe RUPEE_HOE = new ToolHoe("rupee_hoe", MATERIAL_RUPEE);
	public static final ItemHoe BLUE_RUPEE_HOE = new ToolHoe("blue_rupee_hoe", MATERIAL_BLUE_RUPEE);
	public static final ItemHoe RED_RUPEE_HOE = new ToolHoe("red_rupee_hoe", MATERIAL_RED_RUPEE);
	public static final ItemHoe ORANGE_RUPEE_HOE = new ToolHoe("orange_rupee_hoe", MATERIAL_ORANGE_RUPEE);
	
	public static final ItemSpade RUPEE_SHOVEL = new ToolSpade("rupee_shovel", MATERIAL_RUPEE);
	public static final ItemSpade BLUE_RUPEE_SHOVEL = new ToolSpade("blue_rupee_shovel", MATERIAL_BLUE_RUPEE);
	public static final ItemSpade RED_RUPEE_SHOVEL = new ToolSpade("red_rupee_shovel", MATERIAL_RED_RUPEE);
	public static final ItemSpade ORANGE_RUPEE_SHOVEL = new ToolSpade("orange_rupee_shovel", MATERIAL_ORANGE_RUPEE);
	
	//Masks
	public static final Item MASK_POSTMANSHAT = new ArmorBase("mask_postmanshat", ARMOR_MATERIAL_MASK_POSTMANSHAT, 1, EntityEquipmentSlot.HEAD);
	public static final Item MASK_ALLNIGHTMASK = new ArmorBase("mask_allnightmask", ARMOR_MATERIAL_MASK_ALLNIGHTMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_BLASTMASK = new ArmorBase("mask_blastmask", ARMOR_MATERIAL_MASK_BLASTMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_STONEMASK = new ArmorBase("mask_stonemask", ARMOR_MATERIAL_MASK_STONEMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_GREATFAIRYMASK = new ArmorBase("mask_greatfairymask", ARMOR_MATERIAL_MASK_GREATFAIRYMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_DEKUMASK = new ArmorBase("mask_dekumask", ARMOR_MATERIAL_MASK_DEKUMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_KEATONMASK = new ArmorBase("mask_keatonmask", ARMOR_MATERIAL_MASK_KEATONMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_BREMENMASK = new ArmorBase("mask_bremenmask", ARMOR_MATERIAL_MASK_BREMENMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_BUNNYHOOD = new ArmorBase("mask_bunnyhood", ARMOR_MATERIAL_MASK_BUNNYHOOD, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_DONGEROSMASK = new ArmorBase("mask_dongerosmask", ARMOR_MATERIAL_MASK_DONGEROSMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_MASKOFSCENTS = new ArmorBase("mask_maskofscents", ARMOR_MATERIAL_MASK_MASKOFSCENTS, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_GORONMASK = new ArmorBase("mask_goronmask", ARMOR_MATERIAL_MASK_GORONMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_ROMANISMASK = new ArmorBase("mask_romanismask", ARMOR_MATERIAL_MASK_ROMANISMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_TROUPELEADERSMASK= new ArmorBase("mask_troupeleadersmask", ARMOR_MATERIAL_MASK_TROUPELEADERSMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_KAFEISMASK = new ArmorBase("mask_kafeismask", ARMOR_MATERIAL_MASK_KAFEISMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_COUPLESMASK = new ArmorBase("mask_couplesmask", ARMOR_MATERIAL_MASK_COUPLESMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_MASKOFTRUTH = new ArmorBase("mask_maskoftruth", ARMOR_MATERIAL_MASK_MASKOFTRUTH, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_ZORAMASK = new ArmorBase("mask_zoramask", ARMOR_MATERIAL_MASK_ZORAMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_KAMAROSMASK = new ArmorBase("mask_kamarosmask", ARMOR_MATERIAL_MASK_KAMAROSMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_GIBDOMASK = new ArmorBase("mask_gibdomask", ARMOR_MATERIAL_MASK_GIBDOMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_GAROSMASK = new ArmorBase("mask_garosmask", ARMOR_MATERIAL_MASK_GAROSMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_CAPTAINSHAT = new ArmorBase("mask_captainshat", ARMOR_MATERIAL_MASK_CAPTAINSHAT, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_GIANTSMASK = new ArmorBase("mask_giantsmask", ARMOR_MATERIAL_MASK_GIANTSMASK, 1,EntityEquipmentSlot.HEAD);
	public static final Item MASK_FIERCEDEITYSMASK = new ArmorBase("mask_fiercedeitysmask", ARMOR_MATERIAL_MASK_FIERCEDEITYSMASK, 1,EntityEquipmentSlot.HEAD);
	
	//Armors
	public static final Item KOKIRI_CAP = new ArmorBase("kokiri_cap", ARMOR_MATERIAL_KOKIRI, 1, EntityEquipmentSlot.HEAD);
	public static final Item KOKIRI_TUNIC = new ArmorBase("kokiri_tunic", ARMOR_MATERIAL_KOKIRI, 1, EntityEquipmentSlot.CHEST);
	public static final Item KOKIRI_LEGGINGS = new ArmorBase("kokiri_leggings", ARMOR_MATERIAL_KOKIRI, 2, EntityEquipmentSlot.LEGS);
	public static final Item KOKIRI_BOOTS = new ArmorBase("kokiri_boots", ARMOR_MATERIAL_KOKIRI, 1, EntityEquipmentSlot.FEET);
	public static final Item ZORA_CAP = new ArmorBase("zora_cap", ARMOR_MATERIAL_ZORA, 1, EntityEquipmentSlot.HEAD);
	public static final Item ZORA_TUNIC = new ZoraArmor("zora_tunic", ARMOR_MATERIAL_ZORA, 1, EntityEquipmentSlot.CHEST);
	public static final Item ZORA_LEGGINGS = new ArmorBase("zora_leggings", ARMOR_MATERIAL_ZORA, 2, EntityEquipmentSlot.LEGS);
	public static final Item ZORA_BOOTS = new ZoraArmor("iron_boots", ARMOR_MATERIAL_ZORA, 1, EntityEquipmentSlot.FEET);
	public static final Item GORON_CAP = new ArmorBase("goron_cap", ARMOR_MATERIAL_GORON, 1, EntityEquipmentSlot.HEAD);
	public static final Item GORON_TUNIC = new ArmorBase("goron_tunic", ARMOR_MATERIAL_GORON, 1, EntityEquipmentSlot.CHEST);
	public static final Item GORON_LEGGINGS = new ArmorBase("goron_leggings", ARMOR_MATERIAL_GORON, 2, EntityEquipmentSlot.LEGS);
	public static final Item HOVER_BOOTS = new ArmorBase("hover_boots", ARMOR_MATERIAL_GORON, 1, EntityEquipmentSlot.FEET);
	
	
}
