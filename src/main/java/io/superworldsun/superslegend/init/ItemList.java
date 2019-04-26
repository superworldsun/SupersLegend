package io.superworldsun.superslegend.init;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

import io.superworldsun.superslegend.items.ItemBase;
import io.superworldsun.superslegend.items.tools.ToolAxe;
import io.superworldsun.superslegend.items.tools.ToolHoe;
import io.superworldsun.superslegend.items.tools.ToolPickaxe;
import io.superworldsun.superslegend.items.tools.ToolSpade;
import io.superworldsun.superslegend.items.tools.ToolSword;

//TODO: Document this class and methods inside
public class ItemList
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	
	//Materials (name, harvestLevel, maxUses, efficiency, damage, enchantability)
	
	public static final ToolMaterial MATERIAL_KOKIRI_SWORD = EnumHelper.addToolMaterial	("material_kokiri_sword", 1, 0, -2.6f, 1.0f, 0);
	public static final ToolMaterial MATERIAL_RAZOR_SWORD = EnumHelper.addToolMaterial	("material_razor_sword", 1, 100, -2.6f, 2.0f, 0);
	public static final ToolMaterial MATERIAL_GILDED_SWORD = EnumHelper.addToolMaterial	("material_gilded_sword", 1, 0, -2.6f, 2.0f, 0);
	public static final ToolMaterial MATERIAL_RUPEE = EnumHelper.addToolMaterial		("material_rupee", 1, 32, 2f, 2f, 0);
	public static final ToolMaterial MATERIAL_BLUE_RUPEE = EnumHelper.addToolMaterial	("material_blue_rupee", 540, 240, 2f, 2f, 0);
	public static final ToolMaterial MATERIAL_RED_RUPEE = EnumHelper.addToolMaterial	("material_red_rupee", 3, 720, 2f, 2f, 0);
	public static final ToolMaterial MATERIAL_ORANGE_RUPEE = EnumHelper.addToolMaterial	("material_orange_rupee", 4, 1204, 2f, 2f, 0);
	
	//Items
	public static final Item RUPEE = new ItemBase("rupee");
	public static final Item BLUE_RUPEE = new ItemBase("blue_rupee");
	public static final Item RED_RUPEE = new ItemBase("red_rupee");
	public static final Item ORANGE_RUPEE = new ItemBase("orange_rupee");
	
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
	public static final Item MASK_CLAY = new ItemBase("mask_clay");
	public static final Item MASK_POSTMANSHAT = new ItemBase("mask_postmanshat");
	public static final Item MASK_ALLNIGHTMASK = new ItemBase("mask_allnightmask");
	public static final Item MASK_BLASTMASK = new ItemBase("mask_blastmask");
	public static final Item MASK_STONEMASK = new ItemBase("mask_stonemask");
	public static final Item MASK_GREATFAIRYMASK = new ItemBase("mask_greatfairymask");
	public static final Item MASK_DEKUMASK = new ItemBase("mask_dekumask");
	public static final Item MASK_KEATONMASK = new ItemBase("mask_keatonmask");
	public static final Item MASK_BREMENMASK = new ItemBase("mask_bremenmask");
	public static final Item MASK_BUNNYHOOD = new ItemBase("mask_bunnyhood");
	public static final Item MASK_DONGEROSMASK = new ItemBase("mask_dongerosmask");
	public static final Item MASK_MASKOFSCENTS = new ItemBase("mask_maskofscents");
	public static final Item MASK_GORONMASK = new ItemBase("mask_goronmask");
	public static final Item MASK_ROMANISMASK = new ItemBase("mask_romanismask");
	public static final Item MASK_TROUPELEADERSMASK= new ItemBase("mask_troupeleadersmask");
	public static final Item MASK_KAFEISMASK = new ItemBase("mask_kafeismask");
	public static final Item MASK_COUPLESMASK = new ItemBase("mask_couplesmask");
	public static final Item MASK_MASKOFTRUTH = new ItemBase("mask_maskoftruth");
	public static final Item MASK_ZORAMASK = new ItemBase("mask_zoramask");
	public static final Item MASK_KAMAROSMASK = new ItemBase("mask_kamarosmask");
	public static final Item MASK_GIBDOMASK = new ItemBase("mask_gibdomask");
	public static final Item MASK_GAROSMASK = new ItemBase("mask_garosmask");
	public static final Item MASK_CAPTAINSHAT = new ItemBase("mask_captainshat");
	public static final Item MASK_GIANTSMASK = new ItemBase("mask_giantsmask");
	public static final Item MASK_FIERCEDEITYSMASK = new ItemBase("mask_fiercedeitysmask");
	
	//Armors
	public static final Item KOKIRI_CAP = new ItemBase("kokiri_cap");
	public static final Item KOKIRI_TUNIC = new ItemBase("kokiri_tunic");
	public static final Item KOKIRI_LEGGINGS = new ItemBase("kokiri_leggings");
	public static final Item KOKIRI_BOOTS = new ItemBase("kokiri_boots");
	public static final Item ZORA_CAP = new ItemBase("zora_cap");
	public static final Item ZORA_TUNIC = new ItemBase("zora_tunic");
	public static final Item ZORA_LEGGINGS = new ItemBase("zora_leggings");
	public static final Item IRON_BOOTS = new ItemBase("iron_boots");
	public static final Item GORON_CAP = new ItemBase("goron_cap");
	public static final Item GORON_TUNIC = new ItemBase("goron_tunic");
	public static final Item GORON_LEGGINGS = new ItemBase("goron_leggings");
	public static final Item HOVER_BOOTS = new ItemBase("hover_boots");
	
	
}
