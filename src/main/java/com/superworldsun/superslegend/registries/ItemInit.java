package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.*;
import com.superworldsun.superslegend.items.armors.*;
import com.superworldsun.superslegend.items.curios.rings.*;
import com.superworldsun.superslegend.items.food.HylianLoach;
import com.superworldsun.superslegend.items.food.HylianLoachCooked;
import com.superworldsun.superslegend.items.food.HyruleBass;
import com.superworldsun.superslegend.items.food.HyruleBassCooked;
import com.superworldsun.superslegend.items.items.*;
import com.superworldsun.superslegend.items.masks.*;
import com.superworldsun.superslegend.items.weapons.*;
import com.superworldsun.superslegend.items.weapons.SlingShot;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<Item> LANZANITE = ITEMS.register("lanzanite",
			() -> new LanzaniteItem(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Rupee> RUPEE = ITEMS.register("rupee", () -> new Rupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<BlueRupee> BLUE_RUPEE = ITEMS.register("blue_rupee",
			() -> new BlueRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<RedRupee> RED_RUPEE = ITEMS.register("red_rupee",
			() -> new RedRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SilverRupee> SILVER_RUPEE = ITEMS.register("silver_rupee",
			() -> new SilverRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<GoldRupee> GOLD_RUPEE = ITEMS.register("gold_rupee",
			() -> new GoldRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> SMALL_KEY = ITEMS.register("small_key",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BIG_KEY = ITEMS.register("big_key",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGICAL_KEY = ITEMS.register("magical_key",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> SMALL_LOCK = ITEMS.register("small_lock",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BIG_LOCK = ITEMS.register("big_lock",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> ARROW_BUNDLE = ITEMS.register("arrow_bundle",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> FIRE_ARROW_BUNDLE = ITEMS.register("fire_arrow_bundle",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ICE_ARROW_BUNDLE = ITEMS.register("ice_arrow_bundle",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> SHOCK_ARROW_BUNDLE = ITEMS.register("shock_arrow_bundle",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BOMB_ARROW_BUNDLE = ITEMS.register("bomb_arrow_bundle",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_ARROW_BUNDLE = ITEMS.register("ancient_arrow_bundle",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<Item> TRIFORCE_POWER_SHARD = ITEMS.register("triforce_power_shard",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> TRIFORCE_WISDOM_SHARD = ITEMS.register("triforce_wisdom_shard",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> TRIFORCE_COURAGE_SHARD = ITEMS.register("triforce_courage_shard",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RUPEE_POUCH = ITEMS.register("rupee_pouch", () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ODOLWAS_REMAINS = ITEMS.register("odolwas_remains",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GOHTS_REMAINS = ITEMS.register("gohts_remains",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GYORGS_REMAINS = ITEMS.register("gyorgs_remains",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> TWINMOLDS_REMAINS = ITEMS.register("twinmolds_remains",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<Item> ANCIENT_CORE = ITEMS.register("ancient_core",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_GEAR = ITEMS.register("ancient_gear",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_CORE_GIANT = ITEMS.register("ancient_core_giant",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_SCREW = ITEMS.register("ancient_screw",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_SHAFT = ITEMS.register("ancient_shaft",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_SPRING = ITEMS.register("ancient_spring",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> MOON_TEAR = ITEMS.register("moon_tear",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<Item> MASTER_ORE = ITEMS.register("master_ore", () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASTER_SWORD_BLADE = ITEMS.register("master_sword_blade",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASTER_SWORLD_HILT = ITEMS.register("master_sword_hilt",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<Item> HEART_PIECE = ITEMS.register("heart_piece", () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HEART_CONTAINER = ITEMS.register("heart_container",
			() -> new HeartContainer(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> VOID_CONTAINER = ITEMS.register("void_container",
			() -> new VoidContainer(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> SPINNER = ITEMS.register("spinner",
			() -> new SpinnerItem(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	// FOOD
	
	public static final RegistryObject<Item> HYRULE_BASS = ITEMS.register("hyrule_bass", HyruleBass::new);
	public static final RegistryObject<Item> COOKED_HYRULE_BASS = ITEMS.register("cooked_hyrule_bass",
			() -> new HyruleBassCooked(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HYLIAN_LOACH = ITEMS.register("hylian_loach", HylianLoach::new);
	public static final RegistryObject<Item> COOKED_HYLIAN_LOACH = ITEMS.register("cooked_hylian_loach",
			() -> new HylianLoachCooked(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	// WEAPONS & TOOLS
	
	public static final RegistryObject<SwordItem> KOKIRI_SWORD = ITEMS.register("kokiri_sword",
			() -> new SwordItem(ItemToolTiers.KOKIRI_SWORD, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> RAZOR_SWORD = ITEMS.register("razor_sword",
			() -> new SwordItem(ItemToolTiers.RAZOR_SWORD, 2, -2.5f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> GILDED_SWORD = ITEMS.register("gilded_sword",
			() -> new SwordItem(ItemToolTiers.GILDED_SWORD, 2, -2.4f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<SwordItem> GREAT_FAIRYS_SWORD = ITEMS.register("great_fairys_sword",
			() -> new SwordItem(ItemToolTiers.GREAT_FAIRYS_SWORD, 2, -2.4f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<GiantsKnife> GIANTS_KNIFE = ITEMS.register("giants_knife",
			() -> new GiantsKnife(ItemToolTiers.GIANTS_KNIFE, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<GiantsKnife> BROKEN_GIANTS_KNIFE = ITEMS.register("broken_giants_knife",
			() -> new GiantsKnife(ItemToolTiers.BROKEN_GIANTS_KNIFE, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> BIGGORONS_SWORD = ITEMS.register("biggorons_sword",
			() -> new BiggornsSword(ItemToolTiers.BIGGORONS_SWORD, 2, -2.5f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<SwordItem> GODDESS_SWORD = ITEMS.register("goddess_sword",
			() -> new SwordItem(ItemToolTiers.GODDESS_SWORD, 2, -2.4f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> GODDESS_LONGSWORD = ITEMS.register("goddess_longsword",
			() -> new SwordItem(ItemToolTiers.GODDESS_LONGSWORD, 2, -2.4f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> GODDESS_WHITE_SWORD = ITEMS.register("goddess_white_sword",
			() -> new SwordItem(ItemToolTiers.GODDESS_WHITE_SWORD, 2, -2.4f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<SwordItem> MASTER_SWORD = ITEMS.register("master_sword",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD, 2, -2.4f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> MASTER_SWORD_V2 = ITEMS.register("master_sword_v2",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD_V2, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> MASTER_SWORD_D = ITEMS.register("master_sword_d",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD_D, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> MASTER_SWORD_N = ITEMS.register("master_sword_n",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD_N, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> MASTER_SWORD_F = ITEMS.register("master_sword_f",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD_F, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> MASTER_SWORD_DN = ITEMS.register("master_sword_dn",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD_DN, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> MASTER_SWORD_NF = ITEMS.register("master_sword_nf",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD_NF, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> MASTER_SWORD_FD = ITEMS.register("master_sword_fd",
			() -> new SwordItem(ItemToolTiers.MASTER_SWORD_FD, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> TRUE_MASTER_SWORD = ITEMS.register("true_master_sword",
			() -> new SwordItem(ItemToolTiers.TRUE_MASTER_SWORD, 2, -2.2f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<SwordItem> GUARDIAN_SWORD = ITEMS.register("guardian_sword",
			() -> new SwordItem(ItemToolTiers.GUARDIAN_SWORD, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GUARDIAN_SHIELD = ITEMS.register("guardian_shield",
			() -> new ShieldItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> ANCIENT_BATTLE_AXE = ITEMS.register("ancient_battle_axe",
			() -> new SwordItem(ItemToolTiers.ANCIENT_BATTLE_AXE, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SwordItem> GUARDIAN_SPEAR = ITEMS.register("guardian_spear",
			() -> new SwordItem(ItemToolTiers.GUARDIAN_SPEAR, 2, -2.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<HerosBow> FAIRY_BOW = ITEMS.register("fairy_bow",
			() -> new HerosBow(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<HerosBow> HEROS_BOW = ITEMS.register("heros_bow",
			() -> new HerosBow(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BIT_BOW = ITEMS.register("bit_bow",
			() -> new BitBow(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<LynelBowX3> LYNEL_BOW_X3 = ITEMS.register("lynel_bow_x3",
			() -> new LynelBowX3(1, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<LynelBowX5> LYNEL_BOW_X5 = ITEMS.register("lynel_bow_x5",
			() -> new LynelBowX5(1, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> SLING_SHOT = ITEMS.register("sling_shot",
			() -> new SlingShot(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<DekuShield> DEKU_SHIELD = ITEMS.register("deku_shield",
			() -> new DekuShield(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<ShieldItem> HYLIAN_SHIELD = ITEMS.register("hylian_shield",
			() -> new ShieldItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<ShieldItem> SACRED_SHIELD = ITEMS.register("sacred_shield",
			() -> new SacredShieldItem(new Item.Properties()));
	public static final RegistryObject<Item> MIRROR_SHIELD = ITEMS.register("mirror_shield", MirrorShield::new);

	public static final RegistryObject<Item> DEKU_SEEDS = ITEMS.register("deku_seeds",
			() -> new DekuSeed(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> FIRE_ARROW = ITEMS.register("fire_arrow",
			() -> new ArrowFire(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ICE_ARROW = ITEMS.register("ice_arrow", () -> new ArrowIce(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> SHOCK_ARROW = ITEMS.register("shock_arrow",
			() -> new ArrowShock(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BOMB_ARROW = ITEMS.register("bomb_arrow",
			() -> new ArrowBomb(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_ARROW = ITEMS.register("ancient_arrow",
			() -> new ArrowAncient(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> SILVER_ARROW = ITEMS.register("silver_arrow",
			() -> new ArrowSilver(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	//BAGS

	public static final RegistryObject<Item> SPOILS_BAG = ITEMS.register("spoils_bag", SpoilsBagItem::new);
	public static final RegistryObject<Item> DELIVERY_BAG = ITEMS.register("delivery_bag", DeliveryBagItem::new);
	public static final RegistryObject<Item> BAIT_BAG = ITEMS.register("bait_bag", BaitBagItem::new);

	public static final RegistryObject<Item> LETTER = ITEMS.register("letter", Letter::new);
	public static final RegistryObject<Item> RED_LETTER = ITEMS.register("red_letter", Letter::new);

	public static final RegistryObject<Item> BULLET_BAG = ITEMS.register("bullet_bag",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> BIG_BULLET_BAG = ITEMS.register("big_bullet_bag",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> BIGGEST_BULLET_BAG = ITEMS.register("biggest_bullet_bag",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> QUIVER = ITEMS.register("quiver", SmallQuiver::new);
	public static final RegistryObject<Item> BIG_QUIVER = ITEMS.register("big_quiver", MediumQuiver::new);
	public static final RegistryObject<Item> BIGGEST_QUIVER = ITEMS.register("biggest_quiver", BigQuiver::new);

	public static final RegistryObject<Item> BOMB_BAG = ITEMS.register("bomb_bag", SmallBombBag::new);

	public static final RegistryObject<Item> BIG_BOMB_BAG = ITEMS.register("big_bomb_bag",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> BIGGEST_BOMB_BAG = ITEMS.register("biggest_bomb_bag",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> WALLET = ITEMS.register("wallet",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> MEDIUM_WALLET = ITEMS.register("medium_wallet",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> GIANTS_WALLET = ITEMS.register("giants_wallet",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> COLOSSAL_WALLET = ITEMS.register("colossal_wallet",
			() -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	//Sets

	public static final RegistryObject<Item> KOKIRI_SET = ITEMS.register("kokiri_set", KokiriSet::new);
	public static final RegistryObject<Item> GORON_SET = ITEMS.register("goron_set", GoronSet::new);
	public static final RegistryObject<Item> ZORA_SET = ITEMS.register("zora_set", ZoraSet::new);
	public static final RegistryObject<Item> PURPLE_SET = ITEMS.register("purple_set", PurpleSet::new);
	public static final RegistryObject<Item> MAGIC_ARMOR_SET = ITEMS.register("magic_armor_set", MagicArmorSet::new);
	public static final RegistryObject<Item> DARK_SET = ITEMS.register("dark_set", DarkSet::new);
	public static final RegistryObject<Item> ZORA_ARMOR_SET = ITEMS.register("zora_armor_set", ZoraArmorSet::new);
	public static final RegistryObject<Item> FLAMEBREAKER_SET = ITEMS.register("flamebreaker_set", FlamebreakerSet::new);
	public static final RegistryObject<Item> ANCIENT_SET = ITEMS.register("ancient_set", AncientSet::new);
	public static final RegistryObject<Item> BARBARIAN_SET = ITEMS.register("barbarian_set", BarbarianSet::new);
	public static final RegistryObject<Item> CLIMBING_SET = ITEMS.register("climbing_set", ClimbingSet::new);
	public static final RegistryObject<Item> TWILIGHT_SET = ITEMS.register("twilight_set", EngineersSet::new);
	public static final RegistryObject<Item> HEROSNEW_SET = ITEMS.register("herosnew_set", HerosNewSet::new);
	public static final RegistryObject<Item> WIND_SET = ITEMS.register("wind_set", HerosNewSet::new);
	public static final RegistryObject<Item> ENGINEERS_SET = ITEMS.register("engineers_set", EngineersSet::new);

	//Tools

	public static final RegistryObject<Item> LANTERN = ITEMS.register("lantern",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BLUE_CANDLE = ITEMS.register("blue_candle",
			() -> new BlueCandle(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RED_CANDLE = ITEMS.register("red_candle",
			() -> new RedCandle(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HEROS_SECRET_STASH = ITEMS.register("heros_secret_stash",
			() -> new HerosSecretStash(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<BookOfMudora> BOOK_OF_MUDORA = ITEMS.register("book_of_mudora",
			() -> new BookOfMudora(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<SilverScale> SILVER_SCALE = ITEMS.register("silver_scale",
			() -> new SilverScale(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<GoldenScale> GOLDEN_SCALE = ITEMS.register("golden_scale",
			() -> new GoldenScale(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<RocsFeather> ROCS_FEATHER = ITEMS.register("rocs_feather",
			() -> new RocsFeather(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> FISHING_ROD = ITEMS.register("fishing_rod",
			() -> new FishingRodItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<BugNet> BUG_NET = ITEMS.register("bug_net",
			() -> new BugNet(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror",
			() -> new MagicMirror(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<MagicCape> MAGIC_CAPE = ITEMS.register("magic_cape",
			() -> new MagicCape(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGIC_POWDER = ITEMS.register("magic_powder",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> REGULAR_BOOMERANG = ITEMS.register("regular_boomerang",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> WW_BOOMERANG = ITEMS.register("ww_boomerang",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BOOMERANG = ITEMS.register("boomerang",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGICAL_BOOMERANG = ITEMS.register("magical_boomerang",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GALE_BOOMERANG = ITEMS.register("gale_boomerang",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BOMB = ITEMS.register("bomb",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> WATER_BOMB = ITEMS.register("water_bomb",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BOMBCHU = ITEMS.register("bombchu",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HOOKSHOT = ITEMS.register("hookshot",
			() -> new HookshotItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> LONGSHOT = ITEMS.register("longshot",
			() -> new LongshotItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> CLAWSHOT = ITEMS.register("clawshot",
			() -> new ClawshotItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGIC_HAMMER = ITEMS.register("magic_hammer",
			() -> new MagicHammer(ItemToolTiers.MAGIC_HAMMER, 2, -2.6f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MEGATON_HAMMER = ITEMS.register("megaton_hammer",
			() -> new MegatonHammer(ItemToolTiers.MEGATON_HAMMER, 2, -2.8f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<SkullHammer> SKULL_HAMMER = ITEMS.register("skull_hammer",
		() -> new SkullHammer(ItemToolTiers.SKULL_HAMMER, 2, -3.3f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));


	public static final RegistryObject<Item> BOMBOS_MEDALLION = ITEMS.register("bombos_medallion",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ETHER_MEDALLION = ITEMS.register("ether_medallion",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> QUAKE_MEDALLION = ITEMS.register("quake_medallion",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> FIRE_ROD = ITEMS.register("fire_rod",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ICE_ROD = ITEMS.register("ice_rod",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> DEKU_STICK = ITEMS.register("deku_stick",
			() -> new Item(new Item.Properties().stacksTo(15).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> DEKU_NUTS = ITEMS.register("deku_nuts",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> EMPTY_CONTAINER = ITEMS.register("empty_container",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<DinsFire> DINS_FIRE = ITEMS.register("dins_fire",
			() -> new DinsFire(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<FaroresWind> FARORES_WIND = ITEMS.register("farores_wind",
			() -> new FaroresWind(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<NayrusLove> NAYRUS_LOVE = ITEMS.register("nayrus_love",
			() -> new NayrusLove(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<DekuLeaf> DEKU_LEAF = ITEMS.register("deku_leaf",
			() -> new DekuLeaf(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<LensOfTruth> LENS_OF_TRUTH = ITEMS.register("lens_of_truth",
			() -> new LensOfTruth(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<FairyOcarina> FAIRY_OCARINA = ITEMS.register("fairy_ocarina",
			() -> new FairyOcarina(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> OCARINA_OF_TIME = ITEMS.register("ocarina_of_time",
			() -> new OcarinaOfTime(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RED_JELLY = ITEMS.register("red_jelly", () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GREEN_JELLY = ITEMS.register("green_jelly", () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BLUE_JELLY = ITEMS.register("blue_jelly", () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RED_POTION_MIX = ITEMS.register("red_potion_mix",
			() -> new RedPotionMix(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GREEN_POTION_MIX = ITEMS.register("green_potion_mix",
			() -> new GreenPotionMix(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BLUE_POTION_MIX = ITEMS.register("blue_potion_mix",
			() -> new BluePotionMix(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RED_POTION = ITEMS.register("red_potion",
			() -> new RedPotion(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GREEN_POTION = ITEMS.register("green_potion",
			() -> new GreenPotion(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BLUE_POTION = ITEMS.register("blue_potion",
			() -> new BluePotion(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<BottledBee> BOTTLED_BEE = ITEMS.register("bottled_bee",
			() -> new BottledBee(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<BottledSilverfish> BOTTLED_SILVERFISH = ITEMS.register("bottled_silverfish",
			() -> new BottledSilverfish(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<BottledEndermite> BOTTLED_ENDERMITE = ITEMS.register("bottled_endermite",
			() -> new BottledEndermite(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<MagneticGlove> MAGNETIC_GLOVE = ITEMS.register("magnetic_glove",
			() -> new MagneticGlove(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Triforce> TRIFORCE = ITEMS.register("triforce",
			() -> new Triforce(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<TriforcePower> TRIFORCE_POWER = ITEMS.register("triforce_power",
			() -> new TriforcePower(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<TriforceWisdom> TRIFORCE_WISDOM = ITEMS.register("triforce_wisdom",
			() -> new TriforceWisdom(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<TriforceCourage> TRIFORCE_COURAGE = ITEMS.register("triforce_courage",
			() -> new TriforceCourage(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));

	//Masks

	public static final RegistryObject<Item> MASK_CLAY = ITEMS.register("mask_clay",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_POSTMANSHAT = ITEMS.register("mask_postmanshat",
			() -> new MaskPostmanshat(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_ALLNIGHTMASK = ITEMS.register("mask_allnightmask",
			() -> new MaskAllnightmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_BLASTMASK = ITEMS.register("mask_blastmask",
			() -> new BlastMask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_STONEMASK = ITEMS.register("mask_stonemask",
			() -> new MaskStonemask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_BREMANMASK = ITEMS.register("mask_bremenmask",
			() -> new BremenMask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_GREATFAIRYMASK = ITEMS.register("mask_greatfairymask",
			() -> new MaskGreatfairymask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_DEKUMASK = ITEMS.register("mask_dekumask",
			() -> new DekuMask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_KEATONMASK = ITEMS.register("mask_keatonmask", KeatonMask::new);
	public static final RegistryObject<Item> MASK_BUNNYHOOD = ITEMS.register("mask_bunnyhood",
			() -> new MaskBunnyhood(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_DONGEROSMASK = ITEMS.register("mask_dongerosmask",
			() -> new MaskDongerosmaskEffects(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_MASKOFSCENTS = ITEMS.register("mask_maskofscents",
			() -> new ScentsMask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_GORONMASK = ITEMS.register("mask_goronmask",
			() -> new MaskGoronmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_ROMANISMASK = ITEMS.register("mask_romanismask",
			() -> new MaskRomanismask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_TROUPELEADERSMASK = ITEMS.register("mask_troupeleadersmask",
			() -> new MaskTroupeleadersmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_KAFEISMASK = ITEMS.register("mask_kafeismask",
			() -> new MaskKafeismask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_COUPLESMASK = ITEMS.register("mask_couplesmask",
			() -> new MaskCouplesmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_MASKOFTRUTH = ITEMS.register("mask_maskoftruth",
			() -> new MaskMaskofTruth(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_ZORAMASK = ITEMS.register("mask_zoramask",
			() -> new MaskZoramask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_KAMAROSMASK = ITEMS.register("mask_kamarosmask",
			() -> new MaskKamarosmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_GIBDOMASK = ITEMS.register("mask_gibdomask", GibdoMask::new);
	public static final RegistryObject<Item> MASK_GAROSMASK = ITEMS.register("mask_garosmask",
			() -> new MaskGarosmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_CAPTAINSHAT = ITEMS.register("mask_captainshat",
			() -> new MaskCaptainshat(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_GIANTSMASK = ITEMS.register("mask_giantsmask",
			() -> new GiantsMask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_FIERCEDEITYSMASK = ITEMS.register("mask_fiercedeitysmask",
			() -> new MaskFiercedeitysmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_MAJORASMASK = ITEMS.register("mask_majorasmask",
			() -> new MaskMajorasmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_MOONMASK = ITEMS.register("mask_moonmask",
			() -> new MaskMoonmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_SUNMASK = ITEMS.register("mask_sunmask",
			() -> new MaskSunmask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_HAWKEYEMASK = ITEMS.register("mask_hawkeyemask",
			() -> new MaskHawkeyemask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MASK_HEROS_CHARM = ITEMS.register("mask_heroscharm",
			() -> new HerosCharmMask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	
	// ARMORS
	
	public static final RegistryObject<Item> ROCS_CAPE = ITEMS.register("rocs_cape",
			() -> new RocsCapeEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> KOKIRI_CAP = ITEMS.register("kokiri_cap",
			() -> new KokiriArmor(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> KOKIRI_TUNIC = ITEMS.register("kokiri_tunic",
			() -> new KokiriArmor(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> KOKIRI_LEGGINGS = ITEMS.register("kokiri_leggings",
			() -> new KokiriArmor(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> KOKIRI_BOOTS = ITEMS.register("kokiri_boots",
			() -> new KokiriArmor(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ZORA_CAP = ITEMS.register("zora_cap",
			() -> new ArmorZoraEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ZORA_TUNIC = ITEMS.register("zora_tunic",
			() -> new ArmorZoraEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ZORA_LEGGINGS = ITEMS.register("zora_leggings",
			() -> new ArmorZoraEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> IRON_BOOTS = ITEMS.register("iron_boots",
			() -> new IronBoots(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ZORA_FLIPPERS = ITEMS.register("zoras_flippers",
			() -> new ArmorFlippersEffects(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GORON_CAP = ITEMS.register("goron_cap",
			() -> new ArmorGoronEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GORON_TUNIC = ITEMS.register("goron_tunic",
			() -> new ArmorGoronEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GORON_LEGGINGS = ITEMS.register("goron_leggings",
			() -> new ArmorGoronEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HOVER_BOOTS = ITEMS.register("hover_boots",
			() -> new HoverBoots(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> PURPLE_CAP = ITEMS.register("purple_cap",
			() -> new ArmorPurpleEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> PURPLE_TUNIC = ITEMS.register("purple_tunic",
			() -> new ArmorPurpleEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> PURPLE_LEGGINGS = ITEMS.register("purple_leggings",
			() -> new ArmorPurpleEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> PEGASUS_BOOTS = ITEMS.register("pegasus_boots",
			() -> new PegasusBoots(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> LOBSTER_SHIRT = ITEMS.register("lobster_shirt",
			() -> new KokiriArmor(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> NINTENDO_SWITCH_SHIRT = ITEMS.register("nintendo_switch_shirt",
			() -> new KokiriArmor(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> SNOW_BOOTS = ITEMS.register("snow_boots",
			() -> new KokiriArmor(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> SAND_BOOTS = ITEMS.register("sand_boots",
			() -> new KokiriArmor(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_HYRULE_CAP = ITEMS.register("hero_of_hyrule_cap",
			() -> new ArmorHeroOfHyrule(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_HYRULE_TUNIC = ITEMS.register("hero_of_hyrule_tunic",
			() -> new ArmorHeroOfHyrule(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_HYRULE_LEGGINGS = ITEMS.register("hero_of_hyrule_leggings",
			() -> new ArmorHeroOfHyrule(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_HYRULE_BOOTS = ITEMS.register("hero_of_hyrule_boots",
			() -> new ArmorHeroOfHyrule(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	/*public static final RegistryObject<Item> HERO_OF_MENS_HEADBAND = ITEMS.register("hero_of_",
			() -> new ArmorHerosNew(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_MENS_TUNIC = ITEMS.register("hero_of_",
			() -> new ArmorHerosNew(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_MENS_LEGGINGS = ITEMS.register("hero_of_",
			() -> new ArmorHerosNew(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_MENS_BOOTS = ITEMS.register("hero_of_",
			() -> new ArmorHerosNew(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));*/

	/*public static final RegistryObject<Item> HERO_OF_MINISH_CAP = ITEMS.register("hero_of_minish_cap",
			() -> new ArmorHerosOfMinish(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_MINISH_TUNIC = ITEMS.register("hero_of_minish_tunic",
			() -> new ArmorHerosOfMinish(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_MINISH_LEGGINGS = ITEMS.register("hero_of_minish_leggings",
			() -> new ArmorHerosOfMinish(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_MINISH_BOOTS = ITEMS.register("hero_of_minish_boots",
			() -> new ArmorHerosOfMinish(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));*/

	public static final RegistryObject<Item> HERO_OF_TWILIGHT_CAP = ITEMS.register("hero_of_twilight_cap",
			() -> new ArmorHeroOfTwilight(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_TWILIGHT_TUNIC = ITEMS.register("hero_of_twilight_tunic",
			() -> new ArmorHeroOfTwilight(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_TWILIGHT_LEGGINGS = ITEMS.register("hero_of_twilight_leggings",
			() -> new ArmorHeroOfTwilight(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_TWILIGHT_BOOTS = ITEMS.register("hero_of_twilight_boots",
			() -> new ArmorHeroOfTwilight(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_LEGEND_CAP = ITEMS.register("hero_of_legend_cap",
			() -> new ArmorHeroOfLegend(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_LEGEND_TUNIC = ITEMS.register("hero_of_legend_tunic",
			() -> new ArmorHeroOfLegend(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_LEGEND_LEGGINGS = ITEMS.register("hero_of_legend_leggings",
			() -> new ArmorHeroOfLegend(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_LEGEND_BOOTS = ITEMS.register("hero_of_legend_boots",
			() -> new ArmorHeroOfLegend(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_WIND_CAP = ITEMS.register("hero_of_wind_cap",
			() -> new ArmorHeroOfWind(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_WIND_TUNIC = ITEMS.register("hero_of_wind_tunic",
			() -> new ArmorHeroOfWind(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_WIND_LEGGINGS = ITEMS.register("hero_of_wind_leggings",
			() -> new ArmorHeroOfWind(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_WIND_BOOTS = ITEMS.register("hero_of_wind_boots",
			() -> new ArmorHeroOfWind(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	//public static final RegistryObject<Item> HERO_OF_THE_WILD = ITEMS.register("hero_of_",
	//		() -> new ArmorHeroOfWild(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_THE_WILD_TUNIC = ITEMS.register("hero_of_wild_tunic",
			() -> new ArmorHeroOfWild(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_THE_WILD_LEGGINGS = ITEMS.register("hero_of_wild_leggings",
			() -> new ArmorHeroOfWild(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_THE_WILD_BOOTS = ITEMS.register("hero_of_wild_boots",
			() -> new ArmorHeroOfWild(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_SKY_CAP = ITEMS.register("hero_of_sky_cap",
			() -> new ArmorHeroOfSky(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_SKY_TUNIC = ITEMS.register("hero_of_sky_tunic",
			() -> new ArmorHeroOfSky(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_SKY_LEGGINGS = ITEMS.register("hero_of_sky_leggings",
			() -> new ArmorHeroOfSky(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> HERO_OF_SKY_BOOTS = ITEMS.register("hero_of_sky_boots",
			() -> new ArmorHeroOfSky(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));



	public static final RegistryObject<Item> HEROS_NEW_CAP = ITEMS.register("heros_new_cap",
			() -> new ArmorHerosNew(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HEROS_NEW_TUNIC = ITEMS.register("heros_new_tunic",
			() -> new ArmorHerosNew(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HEROS_NEW_LEGGINGS = ITEMS.register("heros_new_leggings",
			() -> new ArmorHerosNew(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HEROS_NEW_BOOTS = ITEMS.register("heros_new_boots",
			() -> new ArmorHerosNew(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ENGINEERS_HAT = ITEMS.register("engineers_hat",
			() -> new ArmorHerosNew(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ENGINEERS_SHIRT = ITEMS.register("engineers_shirt",
			() -> new ArmorHerosNew(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ENGINEERS_PANTS = ITEMS.register("engineers_pants",
			() -> new ArmorHerosNew(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ENGINEERS_BOOTS = ITEMS.register("engineers_boots",
			() -> new ArmorHerosNew(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGIC_ARMOR_CAP = ITEMS.register("magic_armor_cap",
			() -> new MagicArmor(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGIC_ARMOR_TUNIC = ITEMS.register("magic_armor_tunic",
			() -> new MagicArmor(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGIC_ARMOR_LEGGINGS = ITEMS.register("magic_armor_leggings",
			() -> new MagicArmor(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MAGIC_ARMOR_BOOTS = ITEMS.register("magic_armor_boots",
			() -> new MagicArmor(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> DARK_CAP = ITEMS.register("dark_cap",
			() -> new ArmorDarkEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> DARK_TUNIC = ITEMS.register("dark_tunic",
			() -> new ArmorDarkEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> DARK_LEGGINGS = ITEMS.register("dark_leggings",
			() -> new ArmorDarkEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> DARK_BOOTS = ITEMS.register("dark_boots",
			() -> new ArmorDarkEffects(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	public static final RegistryObject<Item> ZORA_ARMOR_CAP = ITEMS.register("zora_armor_cap",
			() -> new ArmorZoraArmorEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ZORA_ARMOR_TUNIC = ITEMS.register("zora_armor_tunic",
			() -> new ArmorZoraArmorEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ZORA_ARMOR_LEGGINGS = ITEMS.register("zora_armor_leggings",
			() -> new ArmorZoraArmorEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ZORA_ARMOR_BOOTS = ITEMS.register("zora_armor_flippers",
			() -> new ArmorZoraArmorEffects(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> FLAMEBREAKER_HELMET = ITEMS.register("flamebreaker_helmet",
			() -> new ArmorFlamebreakerEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> FLAMEBREAKER_TUNIC = ITEMS.register("flamebreaker_tunic",
			() -> new ArmorFlamebreakerEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> FLAMEBREAKER_LEGGINGS = ITEMS.register("flamebreaker_leggings",
			() -> new ArmorFlamebreakerEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> FLAMEBREAKER_BOOTS = ITEMS.register("flamebreaker_boots",
			() -> new ArmorFlamebreakerEffects(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_HELMET = ITEMS.register("ancient_helmet",
			() -> new ArmorAncientEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_CUIRASS = ITEMS.register("ancient_cuirass",
			() -> new ArmorAncientEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_GREAVES = ITEMS.register("ancient_greaves",
			() -> new ArmorAncientEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ANCIENT_BOOTS = ITEMS.register("ancient_boots",
			() -> new ArmorAncientEffects(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BARBARIAN_HELMET = ITEMS.register("barbarian_helmet",
			() -> new ArmorBarbarianEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BARBARIAN_ARMOR = ITEMS.register("barbarian_armor",
			() -> new ArmorBarbarianEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BARBARIAN_LEG_WRAPS = ITEMS.register("barbarian_leg_wraps",
			() -> new ArmorBarbarianEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BARBARIAN_BOOTS = ITEMS.register("barbarian_boots",
			() -> new ArmorBarbarianEffects(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> CLIMBERS_BANDANNA = ITEMS.register("climbers_bandanna",
			() -> new ArmorClimbingGearEffects(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> CLIMBING_GEAR = ITEMS.register("climbing_gear",
			() -> new ArmorClimbingGearEffects(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> CLIMBING_PANTS = ITEMS.register("climbing_pants",
			() -> new ArmorClimbingGearEffects(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> CLIMBING_BOOTS = ITEMS.register("climbing_boots",
			() -> new ArmorClimbingGearEffects(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.RESOURCES)));

	//Pendants

	public static final RegistryObject<Item> PENDANT_OF_COURAGE = ITEMS.register("pendant_of_courage",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> PENDANT_OF_WISDOM = ITEMS.register("pendant_of_wisdom",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> PENDANT_OF_POWER = ITEMS.register("pendant_of_power",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> JOY_PENDANT = ITEMS.register("joy_pendant",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> PIRATES_CHARM = ITEMS.register("pirates_charm",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> SKULL_NECKLACE = ITEMS.register("skull_neklace",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));



	//Hands (gloves, gauntlets, bracelets)
	public static final RegistryObject<Item> POWER_BRACELETS = ITEMS.register("power_bracelets",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GORONS_BRACELET = ITEMS.register("gorons_bracelet",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> SILVER_GAUNTLETS = ITEMS.register("silver_gauntlets",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GOLDEN_GAUNTLETS = ITEMS.register("golden_gauntlets",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));

	//Rings
	public static final RegistryObject<Item> BLUE_RING = ITEMS.register("blue_ring",
			() -> new BlueRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RED_RING = ITEMS.register("red_ring",
			() -> new RedRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GREEN_RING = ITEMS.register("green_ring",
			() -> new GreenRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> POWER_RING_L1 = ITEMS.register("power_ring_l1",
			() -> new PowerRingL1(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> POWER_RING_L2 = ITEMS.register("power_ring_l2",
			() -> new PowerRingL2(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> POWER_RING_L3 = ITEMS.register("power_ring_l3",
			() -> new PowerRingL3(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ARMOR_RING_L1 = ITEMS.register("armor_ring_l1",
			() -> new ArmorRingL1(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ARMOR_RING_L2 = ITEMS.register("armor_ring_l2",
			() -> new ArmorRingL2(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> ARMOR_RING_L3 = ITEMS.register("armor_ring_l3",
			() -> new ArmorRingL3(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> CURSED_RING = ITEMS.register("cursed_ring",
			() -> new CursedRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HEART_RING_1 = ITEMS.register("heart_ring_l1",
			() -> new HeartRingL1(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> HEART_RING_L2 = ITEMS.register("heart_ring_l2",
			() -> new HeartRingL2(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GREEN_LUCK_RING = ITEMS.register("green_luck_ring",
			() -> new GreenLuckRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BLUE_LUCK_RING = ITEMS.register("blue_luck_ring",
			() -> new BlueLuckRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GOLD_LUCK_RING = ITEMS.register("gold_luck_ring",
			() -> new GoldLuckRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RED_LUCK_RING = ITEMS.register("red_luck_ring",
			() -> new RedLuckRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> STEADFAST_RING = ITEMS.register("steadfast_ring",
			() -> new SteadfastRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GREEN_HOLY_RING = ITEMS.register("green_holy_ring",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> BLUE_HOLY_RING = ITEMS.register("blue_holy_ring",
			() -> new BlueHolyRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> RED_HOLY_RING = ITEMS.register("red_holy_ring",
			() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> SWIMMERS_RING = ITEMS.register("swimmers_ring",
			() -> new SwimmersRing(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));




	//Music Discs
	public static final RegistryObject<Item> BUBBLEGLOOP_DISC = ITEMS.register("bubblegloop_disc",
			() -> new MusicDiscItem(1, SoundInit.BUBBLEGLOOP_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> FOREST_TEMPLE_DISC = ITEMS.register("forest_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.FOREST_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> FIRE_TEMPLE_DISC = ITEMS.register("fire_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.FIRE_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> WATER_TEMPLE_DISC = ITEMS.register("water_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.WATER_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> SHADOW_TEMPLE_DISC = ITEMS.register("shadow_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.SHADOW_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> SPIRIT_TEMPLE_DISC = ITEMS.register("spirit_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.SPIRIT_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> WOODFALL_TEMPLE_DISC = ITEMS.register("woodfall_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.WOODFALL_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> SNOWHEAD_TEMPLE_DISC = ITEMS.register("snowhead_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.SNOWHEAD_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> GREAT_BAY_TEMPLE_DISC = ITEMS.register("great_bay_temple_disc",
			() -> new MusicDiscItem(1, SoundInit.GREAT_BAY_TEMPLE_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> STONE_TOWER_TEMPLE_REALITY_DISC_LAZY = ITEMS.register("stone_tower_temple_reality_disc",
			() -> new MusicDiscItem(1, SoundInit.STONE_TOWER_TEMPLE_REALITY_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> STONE_TOWER_TEMPLE_ILLUSION_DISC = ITEMS.register("stone_tower_temple_illusion_disc",
			() -> new MusicDiscItem(1, SoundInit.STONE_TOWER_TEMPLE_ILLUSION_DISC::get, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> FRIED_EGG = ITEMS.register("fried_egg",
			() -> new FriedEggItem(new Item.Properties()
					.food(new Food.Builder().nutrition(8).effect(() -> new EffectInstance(Effects.HARM, 20, 1), 1F).saturationMod(0.7f).alwaysEat().build())
					.tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<PickaxeItem> LANZANITE_PICKAXE = ITEMS.register("lanzanite_pickaxe",
			() -> new LanzanitePickaxeItem(ItemToolTiers.LANZANITE, 1, -2.8f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<LanzaniteMultiToolItem> LANZANITE_MULTITOOL = ITEMS.register("lanzanite_multitool",
			() -> new LanzaniteMultiToolItem(ItemToolTiers.LANZANITE, 3, -3.0f, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	
	// Remember to set custom properties for your bows such as durability here
	public static final RegistryObject<BowItem> METAL_BOW = ITEMS.register("metal_bow",
			() -> new MetalBowItem(new Item.Properties().stacksTo(1).durability(700).tab(SupersLegendMain.RESOURCES)));
	
	public static final RegistryObject<Item> POISON_ARROW = ITEMS.register("poison_arrow",
			() -> new PoisonArrowItem(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> GNAT_HAT = ITEMS.register("gnat_hat", GnatHat::new);
	public static final RegistryObject<Item> MAGIC_FIRE_ARROW = ITEMS.register("magic_fire_arrow", MagicFireArrow::new);
	public static final RegistryObject<Item> MAGIC_ICE_ARROW = ITEMS.register("magic_ice_arrow", MagicIceArrow::new);
	public static final RegistryObject<Item> MAGIC_LIGHT_ARROW = ITEMS.register("magic_light_arrow", MagicLightArrow::new);

	public static final RegistryObject<Item> POISON_BUCKET = ITEMS.register("poison_bucket", () -> new BucketItem(FluidInit.POISON_SOURCE, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
	public static final RegistryObject<Item> MUD_BUCKET = ITEMS.register("mud_bucket", () -> new BucketItem(FluidInit.MUD_SOURCE, new Item.Properties().tab(SupersLegendMain.RESOURCES)));
}