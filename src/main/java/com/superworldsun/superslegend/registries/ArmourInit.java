package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class ArmourInit implements IArmorMaterial
{
	//Armors
	public static final ArmourInit KOKIRI = new ArmourInit("kokiri").defence(1, 3, 5, 1);
	public static final ArmourInit ZORA = new ArmourInit("zora").defence(1, 3, 5, 1);
	public static final ArmourInit GORON = new ArmourInit("goron").defence(1, 3, 5, 1);
	public static final ArmourInit PURPLE = new ArmourInit("purple").defence(1, 3, 5, 1);
	public static final ArmourInit MAGIC = new ArmourInit("magic").defence(1, 1, 1, 1);
	public static final ArmourInit HEROS_NEW = new ArmourInit("herosnew").defence(1, 3, 5, 1);
	public static final ArmourInit TWILIGHT = new ArmourInit("twilight").defence(1, 3, 5, 1);
	public static final ArmourInit WIND = new ArmourInit("wind").defence(1, 3, 5, 1);
	public static final ArmourInit LOBSTER = new ArmourInit("lobster").defence(1, 3, 5, 1);
	public static final ArmourInit ENGINEER = new ArmourInit("engineer").defence(1, 3, 5, 1);
	public static final ArmourInit NSWITCH = new ArmourInit("nswitch").defence(1, 3, 5, 1);
	public static final ArmourInit HYRULE = new ArmourInit("hyrule").defence(1, 3, 5, 1);
	public static final ArmourInit WILD = new ArmourInit("wild").defence(1, 3, 5, 1);
	public static final ArmourInit MINISH = new ArmourInit("minish").defence(1, 3, 5, 1);
	public static final ArmourInit SKY = new ArmourInit("sky").defence(1, 3, 5, 1);
	public static final ArmourInit LEGEND = new ArmourInit("legend").defence(1, 3, 5, 1);
	public static final ArmourInit DARK = new ArmourInit("dark").defence(1, 2, 3, 1);
	public static final ArmourInit ZORAARMOR = new ArmourInit("zoraarmor").defence(1, 2, 3, 1);
	public static final ArmourInit FLAMEBREAKER = new ArmourInit("flamebreaker").defence(1, 2, 3, 1);
	public static final ArmourInit ANCIENT = new ArmourInit("ancient").defence(1, 3, 5, 1).toughness(3.0F);
	public static final ArmourInit BARBARIAN = new ArmourInit("barbarian").defence(1, 1, 2, 1);
	public static final ArmourInit SNOWQUILL = new ArmourInit("snowquill").defence(1, 2, 3, 1);
	public static final ArmourInit DESERTVOE = new ArmourInit("desertvoe").defence(1, 2, 3, 1);
	public static final ArmourInit CLIMBING = new ArmourInit("climbing").defence(1, 2, 3, 1);
	public static final ArmourInit FLIPPERS = new ArmourInit("flippers");
	public static final ArmourInit ROCSCAPE = new ArmourInit("rocscape").defence(0, 0, 2, 0);
	public static final ArmourInit HOVER_BOOTS = new ArmourInit("hoverboots").defence(1, 0, 0, 0);
	public static final ArmourInit IRON_BOOTS = new ArmourInit("ironboots").defence(1, 0, 0, 0);
	public static final ArmourInit PEGASUS_BOOTS = new ArmourInit("pegasusboots").defence(1, 0, 0, 0);
	//Masks
	public static final ArmourInit POSTMANS_HAT = new ArmourInit("postmanshat");
	public static final ArmourInit ALLNIGHT_MASK = new ArmourInit("allnightmask");
	public static final ArmourInit BLAST_MASK = new ArmourInit("blastmask");
	public static final ArmourInit STONE_MASK = new ArmourInit("stonemask");
	public static final ArmourInit GREAT_FAIRY_MASK = new ArmourInit("greatfairymask");
	public static final ArmourInit DEKU_MASK = new ArmourInit("dekumask");
	public static final ArmourInit KEATON_MASK = new ArmourInit("keatonmask");
	public static final ArmourInit BREMEN_MASK = new ArmourInit("bremenmask");
	public static final ArmourInit BUNNY_HOOD = new ArmourInit("bunnyhood");
	public static final ArmourInit DONGEROS_MASK = new ArmourInit("dongerosmask");
	public static final ArmourInit SCENTS_MASK = new ArmourInit("maskofscents");
	public static final ArmourInit GORON_MASK = new ArmourInit("goronmask");
	public static final ArmourInit ROMANIS_MASK = new ArmourInit("romanismask");
	public static final ArmourInit TROUPE_LEADERS_MASK = new ArmourInit("troupeleadersmask");
	public static final ArmourInit KAFEIS_MASK = new ArmourInit("kafeismask");
	public static final ArmourInit COUPLES_MASK = new ArmourInit("couplesmask");
	public static final ArmourInit TRUTH_MASK = new ArmourInit("maskoftruth");
	public static final ArmourInit ZORA_MASK = new ArmourInit("zoramask");
	public static final ArmourInit KAMAROS_MASK = new ArmourInit("kamarosmask");
	public static final ArmourInit GIBDO_MASK = new ArmourInit("gibdomask");
	public static final ArmourInit GAROS_MASK = new ArmourInit("garosmask");
	public static final ArmourInit CAPTAINS_HAT = new ArmourInit("captainshat");
	public static final ArmourInit GNAT_HAT = new ArmourInit("gnathat");
	public static final ArmourInit GIANTS_MASK = new ArmourInit("giantsmask");
	public static final ArmourInit FIERCE_DEITYS_MASK = new ArmourInit("fiercedeitysmask");
	public static final ArmourInit MAJORAS_MASK = new ArmourInit("majorasmask");
	public static final ArmourInit HAWKEYE = new ArmourInit("hawkeye");
	public static final ArmourInit HEROS_CHARM = new ArmourInit("heroscharm");
	public static final ArmourInit MOON_MASK = new ArmourInit("moonmask");
	public static final ArmourInit SUN_MASK = new ArmourInit("sunmask");
	//Rings
	public static final ArmourInit FRIENDSHIP_RING = new ArmourInit("friendship_ring");
	public static final ArmourInit POWER_RING_L1 = new ArmourInit("power_ring_l1");
	public static final ArmourInit POWER_RING_L2 = new ArmourInit("power_ring_l2");
	public static final ArmourInit POWER_RING_L3 = new ArmourInit("power_ring_l3");
	public static final ArmourInit ARMOR_RING_L1 = new ArmourInit("armor_ring_l1");
	public static final ArmourInit ARMOR_RING_L2 = new ArmourInit("armor_ring_l2");
	public static final ArmourInit ARMOR_RING_L3 = new ArmourInit("armor_ring_l3");
	
	protected final String name;
	protected SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_LEATHER;
	protected int enchantability = 0;
	protected Item repairItem = null;
	protected int[] defence = { 0, 0, 0, 0 };
	protected int[] durability = { 0, 0, 0, 0 };
	protected float toughness = 0.0f;
	
	protected ArmourInit(String name)
	{
		this.name = name;
	}
	
	@Override
	public int getDefenseForSlot(EquipmentSlotType slot)
	{
		return this.defence[slot.getIndex()];
	}
	
	@Override
	public int getDurabilityForSlot(EquipmentSlotType slot)
	{
		return durability[slot.getIndex()];
	}
	
	@Override
	public int getEnchantmentValue()
	{
		return this.enchantability;
	}
	
	@Override
	public String getName()
	{
		return SupersLegendMain.MOD_ID + ":" + this.name;
	}
	
	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.of(this.repairItem);
	}
	
	@Override
	public SoundEvent getEquipSound()
	{
		return equipSound;
	}
	
	@Override
	public float getToughness()
	{
		return this.toughness;
	}
	
	@Override
	public float getKnockbackResistance()
	{
		return 0;
	}
	
	protected ArmourInit defence(int boots, int leggings, int chestplate, int helemt)
	{
		this.defence = new int[] { boots, leggings, chestplate, helemt };
		return this;
	}
	
	protected ArmourInit durability(int boots, int leggings, int chestplate, int helemt)
	{
		this.durability = new int[] { boots, leggings, chestplate, helemt };
		return this;
	}
	
	protected ArmourInit toughness(float toughness)
	{
		this.toughness = toughness;
		return this;
	}
	
	protected ArmourInit enchantability(int enchantability)
	{
		this.enchantability = enchantability;
		return this;
	}
	
	protected ArmourInit equipSound(SoundEvent equipSound)
	{
		this.equipSound = equipSound;
		return this;
	}
	
	protected ArmourInit repairItem(Item repairItem)
	{
		this.repairItem = repairItem;
		return this;
	}
}
