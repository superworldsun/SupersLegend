package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.*;
import com.superworldsun.superslegend.items.curios.charms.GoldenScale;
import com.superworldsun.superslegend.items.curios.charms.SilverScale;
import com.superworldsun.superslegend.items.curios.head.masks.*;
import com.superworldsun.superslegend.items.curios.rings.*;
import com.superworldsun.superslegend.items.customclass.ItemCustomBow;
import com.superworldsun.superslegend.items.customclass.ItemCustomSword;
import com.superworldsun.superslegend.items.item.*;
import com.superworldsun.superslegend.items.item.ammo.*;
import com.superworldsun.superslegend.items.weapons.bow.BitBow;
import com.superworldsun.superslegend.items.weapons.hammer.MagicHammer;
import com.superworldsun.superslegend.items.weapons.hammer.MegatonHammer;
import com.superworldsun.superslegend.items.weapons.hammer.SkullHammer;
import com.superworldsun.superslegend.items.weapons.swords.BiggoronsSword;
import com.superworldsun.superslegend.items.weapons.swords.BrokenGiantsKnife;
import com.superworldsun.superslegend.items.weapons.swords.GiantsKnife;
import com.superworldsun.superslegend.items.weapons.swords.RazorSword;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SupersLegendMain.MOD_ID);

    public static final RegistryObject<Item> RUPEE = ITEMS.register("rupee",
            () -> new Rupee(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_RUPEE = ITEMS.register("blue_rupee",
            () -> new BlueRupee(new Item.Properties()));
    public static final RegistryObject<Item> RED_RUPEE = ITEMS.register("red_rupee",
            () -> new RedRupee(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_RUPEE = ITEMS.register("silver_rupee",
            () -> new SilverRupee(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_RUPEE = ITEMS.register("gold_rupee",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEDALLION_LIGHT = ITEMS.register("medallion_light",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEDALLION_FOREST = ITEMS.register("medallion_forest",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEDALLION_FIRE = ITEMS.register("medallion_fire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEDALLION_WATER = ITEMS.register("medallion_water",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEDALLION_SPIRIT = ITEMS.register("medallion_spirit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEDALLION_SHADOW = ITEMS.register("medallion_shadow",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMALL_KEY = ITEMS.register("small_key",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOSS_KEY = ITEMS.register("boss_key",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGICAL_KEY = ITEMS.register("magical_key",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARROW_BUNDLE = ITEMS.register("arrow_bundle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRE_ARROW_BUNDLE = ITEMS.register("fire_arrow_bundle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ICE_ARROW_BUNDLE = ITEMS.register("ice_arrow_bundle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHOCK_ARROW_BUNDLE = ITEMS.register("shock_arrow_bundle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOMB_ARROW_BUNDLE = ITEMS.register("bomb_arrow_bundle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_ARROW_BUNDLE = ITEMS.register("ancient_arrow_bundle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TRIFORCE_POWER_SHARD = ITEMS.register("triforce_power_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIFORCE_WISDOM_SHARD = ITEMS.register("triforce_wisdom_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIFORCE_COURAGE_SHARD = ITEMS.register("triforce_courage_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ODOLWAS_REMAINS = ITEMS.register("odolwas_remains",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOHTS_REMAINS = ITEMS.register("gohts_remains",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GYORGS_REMAINS = ITEMS.register("gyorgs_remains",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TWINMOLDS_REMAINS = ITEMS.register("twinmolds_remains",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_CORE = ITEMS.register("ancient_core",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_GEAR = ITEMS.register("ancient_gear",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_CORE_GIANT = ITEMS.register("ancient_core_giant",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_SCREW = ITEMS.register("ancient_screw",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_SHAFT = ITEMS.register("ancient_shaft",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_SPRING = ITEMS.register("ancient_spring",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MASTER_ORE = ITEMS.register("master_ore",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HEART_PIECE = ITEMS.register("heart_piece",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HEART_CONTAINER = ITEMS.register("heart_container",
            () -> new HeartContainer(new Item.Properties()));
    public static final RegistryObject<Item> VOID_CONTAINER = ITEMS.register("void_container",
            () -> new VoidContainer(new Item.Properties()));
    public static final RegistryObject<Item> UNAPPRAISED_RING = ITEMS.register("unappraised_ring",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> APPRAISED_RING_BOX = ITEMS.register("appraised_ring_box",
            () -> new AppraisedRingBox(new Item.Properties()));

    //Songs
    /*public static final RegistryObject<Item> ZELDAS_LULLABY_SHEET = ITEMS.register("zeldas_lullaby_sheet", ZeldasLullabySheet::new);
    public static final RegistryObject<Item> EPONAS_SONG_SHEET = ITEMS.register("eponas_song_sheet", EponasSongSheet::new);
    public static final RegistryObject<Item> SARIAS_SONG_SHEET = ITEMS.register("sarias_song_sheet", SariasSongSheet::new);
    public static final RegistryObject<Item> SONG_OF_TIME_SHEET = ITEMS.register("song_of_time_sheet", SongOfTimeSheet::new);
    public static final RegistryObject<Item> SUNS_SONG_SHEET = ITEMS.register("suns_song_sheet", SunsSongSheet::new);
    public static final RegistryObject<Item> SONG_OF_STORMS_SHEET = ITEMS.register("song_of_storms_sheet", SongOfStormsSheet::new);
    public static final RegistryObject<Item> MINUET_OF_FOREST_SHEET = ITEMS.register("minuet_of_forest_sheet", MinuetOfForestSheet::new);
    public static final RegistryObject<Item> BOLERO_OF_FIRE_SHEET = ITEMS.register("bolero_of_fire_sheet", BoleroOfFireSheet::new);
    public static final RegistryObject<Item> SERENADE_OF_WATER_SHEET = ITEMS.register("serenade_of_water_sheet", SerenadeOfWaterSheet::new);
    public static final RegistryObject<Item> NOCTURNE_OF_SHADOW_SHEET = ITEMS.register("nocturne_of_shadow_sheet", NocturneOfShadowSheet::new);
    public static final RegistryObject<Item> REQUIEM_OF_SPIRIT_SHEET = ITEMS.register("requiem_of_spirit_sheet", RequiemOfSpiritSheet::new);
    public static final RegistryObject<Item> SONG_OF_SOARING_SHEET = ITEMS.register("song_of_soaring_sheet", SongOfSoaringSheet::new);
    public static final RegistryObject<Item> INVERTED_SONG_OF_TIME_SHEET = ITEMS.register("inverted_song_of_time_sheet", InvertedSongOfTimeSheet::new);
    public static final RegistryObject<Item> SONATA_OF_AWAKENING_SHEET = ITEMS.register("sonata_of_awakening_sheet", SonataOfAwakeningSheet::new);
    public static final RegistryObject<Item> SONG_OF_DOUBLE_TIME_SHEET = ITEMS.register("song_of_double_time_sheet", SongOfDoubleTimeSheet::new);
    public static final RegistryObject<Item> PRELUDE_OF_LIGHT_SHEET = ITEMS.register("prelude_of_light_sheet", PreludeOfLightSheet::new);
    public static final RegistryObject<Item> GORON_LULLABY_SHEET = ITEMS.register("goron_lullaby_sheet", GoronLullabySheet::new);
    public static final RegistryObject<Item> OATH_TO_ORDER_SHEET = ITEMS.register("oath_to_order_sheet", OathToOrderSheet::new);
    public static final RegistryObject<Item> NEW_WAVE_BOSSA_NOVA_SHEET = ITEMS.register("new_wave_bossa_sheet", NewWaveBossaNovaSheet::new);
    public static final RegistryObject<Item> ELEGY_OF_EMPTYNESS_SHEET = ITEMS.register("elegy_of_emptyness_sheet", ElegyOfEmptinessSheet::new);
    public static final RegistryObject<Item> SONG_OF_HEALING_SHEET = ITEMS.register("song_of_healing_sheet", SongOfHealingSheet::new);
    public static final RegistryObject<Item> ALL_SONGS_SHEET = ITEMS.register("all_songs_sheet", AllSongsSheet::new);
    public static final RegistryObject<Item> AMNEISA_SHEET = ITEMS.register("amneisa_sheet", AmnesiaSheet::new);*/

    // FOOD

    public static final RegistryObject<Item> HYRULE_BASS = ITEMS.register("hyrule_bass",
            () -> new Item(new Item.Properties().food(FoodInit.HYRULE_BASS)));
    public static final RegistryObject<Item> COOKED_HYRULE_BASS = ITEMS.register("cooked_hyrule_bass",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_HYRULE_BASS)));
    public static final RegistryObject<Item> HYLIAN_LOACH = ITEMS.register("hylian_loach",
            () -> new Item(new Item.Properties().food(FoodInit.HYLIAN_LOACH)));
    public static final RegistryObject<Item> COOKED_HYLIAN_LOACH = ITEMS.register("cooked_hylian_loach",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_HYLIAN_LOACH)));

    // WEAPONS & TOOLS

    public static final RegistryObject<SwordItem> KOKIRI_SWORD = ITEMS.register("kokiri_sword",
            () -> new ItemCustomSword(ItemToolTiers.KOKIRI_SWORD, 2, -2.5f, new Item.Properties()));
    public static final RegistryObject<SwordItem> RAZOR_SWORD = ITEMS.register("razor_sword",
            () -> new RazorSword(ItemToolTiers.RAZOR_SWORD, 2, -2.5f, new Item.Properties()));
    public static final RegistryObject<SwordItem> GILDED_SWORD = ITEMS.register("gilded_sword",
            () -> new ItemCustomSword(ItemToolTiers.GILDED_SWORD, 2, -2.4f, new Item.Properties()));

    public static final RegistryObject<SwordItem> GIANTS_KNIFE = ITEMS.register("giants_knife",
            () -> new GiantsKnife(ItemToolTiers.GIANTS_KNIFE, 2, -2.5f, new Item.Properties()));
    public static final RegistryObject<SwordItem> BROKEN_GIANTS_KNIFE = ITEMS.register("broken_giants_knife",
            () -> new BrokenGiantsKnife(ItemToolTiers.BROKEN_GIANTS_KNIFE, 2, -2.5f, new Item.Properties()));
    public static final RegistryObject<SwordItem> BIGGORONS_SWORD = ITEMS.register("biggorons_sword",
            () -> new BiggoronsSword(ItemToolTiers.BIGGORONS_SWORD, 2, -2.5f, new Item.Properties()));

    public static final RegistryObject<SwordItem> GODDESS_SWORD = ITEMS.register("goddess_sword",
            () -> new ItemCustomSword(ItemToolTiers.GODDESS_SWORD, 2, -2.5f, new Item.Properties()));
    public static final RegistryObject<SwordItem> GODDESS_LONGSWORD = ITEMS.register("goddess_longsword",
            () -> new ItemCustomSword(ItemToolTiers.GODDESS_LONGSWORD, 2, -2.4f, new Item.Properties()));
    public static final RegistryObject<SwordItem> GODDESS_WHITE_SWORD = ITEMS.register("goddess_white_sword",
            () -> new ItemCustomSword(ItemToolTiers.GODDESS_WHITE_SWORD, 2, -2.4f, new Item.Properties()));

    public static final RegistryObject<SwordItem> MASTER_SWORD = ITEMS.register("master_sword",
            () -> new ItemCustomSword(ItemToolTiers.MASTER_SWORD, 2, -2.4f, new Item.Properties()));
    public static final RegistryObject<SwordItem> MASTER_SWORD_V2 = ITEMS.register("master_sword_v2",
            () -> new ItemCustomSword(ItemToolTiers.MASTER_SWORD_V2, 2, -2.3f, new Item.Properties()));
    public static final RegistryObject<SwordItem> TRUE_MASTER_SWORD = ITEMS.register("true_master_sword",
            () -> new ItemCustomSword(ItemToolTiers.TRUE_MASTER_SWORD, 2, -2.2f, new Item.Properties()));

    public static final RegistryObject<SwordItem> GUARDIAN_SWORD = ITEMS.register("guardian_sword",
            () -> new ItemCustomSword(ItemToolTiers.GUARDIAN_SWORD, 2, -2.3f, new Item.Properties()));


    public static final RegistryObject<BowItem> FAIRY_BOW = ITEMS.register("fairy_bow",
            () -> new ItemCustomBow(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<BowItem> HEROS_BOW = ITEMS.register("heros_bow",
            () -> new ItemCustomBow(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BIT_BOW = ITEMS.register("bit_bow",
            () -> new BitBow(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<BowItem> LYNEL_BOW_X3 = ITEMS.register("lynel_bow_x3",
            () -> new BowItem(new Item.Properties()));
    public static final RegistryObject<BowItem> LYNEL_BOW_X5 = ITEMS.register("lynel_bow_x5",
            () -> new BowItem(new Item.Properties()));
    public static final RegistryObject<Item> SLING_SHOT = ITEMS.register("sling_shot",
            () -> new BowItem(new Item.Properties()));
    public static final RegistryObject<ShieldItem> DEKU_SHIELD = ITEMS.register("deku_shield",
            () -> new ShieldItem(new Item.Properties().stacksTo(1).durability(500)));
    public static final RegistryObject<ShieldItem> HYLIAN_SHIELD = ITEMS.register("hylian_shield",
            () -> new ShieldItem(new Item.Properties().stacksTo(1).durability(3000)));
    public static final RegistryObject<ShieldItem> SACRED_SHIELD = ITEMS.register("sacred_shield",
            () -> new ShieldItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> DEKU_SEEDS = ITEMS.register("deku_seeds",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MAGIC_FIRE_ARROW = ITEMS.register("magic_fire_arrow",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_ICE_ARROW = ITEMS.register("magic_ice_arrow",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_LIGHT_ARROW = ITEMS.register("magic_light_arrow",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRE_ARROW = ITEMS.register("fire_arrow",
            () -> new ArrowFire(new Item.Properties()));
    public static final RegistryObject<Item> ICE_ARROW = ITEMS.register("ice_arrow",
            () -> new ArrowIce(new Item.Properties()));
    public static final RegistryObject<Item> SHOCK_ARROW = ITEMS.register("shock_arrow",
            () -> new ArrowShock(new Item.Properties()));
    public static final RegistryObject<Item> BOMB_ARROW = ITEMS.register("bomb_arrow",
            () -> new ArrowBomb(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_ARROW = ITEMS.register("ancient_arrow",
            () -> new ArrowAncient(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_ARROW = ITEMS.register("silver_arrow",
            () -> new Item(new Item.Properties()));

    //BAGS

    public static final RegistryObject<Item> SPOILS_BAG = ITEMS.register("spoils_bag",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DELIVERY_BAG = ITEMS.register("delivery_bag",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BAIT_BAG = ITEMS.register("bait_bag",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RING_BOX_L1 = ITEMS.register("ring_box_l1",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RING_BOX_L2 = ITEMS.register("ring_box_l2",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RING_BOX_L3 = ITEMS.register("ring_box_l3",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LETTER = ITEMS.register("letter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RED_LETTER = ITEMS.register("red_letter",
            () -> new Item(new Item.Properties()));

	/*public static final RegistryObject<Item> WALLET = ITEMS.register("wallet", SimpleResourceItem::new);
	public static final RegistryObject<Item> MEDIUM_WALLET = ITEMS.register("medium_wallet", SimpleResourceItem::new);
	public static final RegistryObject<Item> GIANTS_WALLET = ITEMS.register("giants_wallet", SimpleResourceItem::new);
	public static final RegistryObject<Item> COLOSSAL_WALLET = ITEMS.register("colossal_wallet", SimpleResourceItem::new);*/

    //Sets

    public static final RegistryObject<Item> KOKIRI_SET = ITEMS.register("kokiri_set",
            () -> new KokiriSet(new Item.Properties()));
    public static final RegistryObject<Item> GORON_SET = ITEMS.register("goron_set",
            () -> new GoronSet(new Item.Properties()));
    public static final RegistryObject<Item> ZORA_SET = ITEMS.register("zora_set",
            () -> new ZoraSet(new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_SET = ITEMS.register("purple_set",
            () -> new PurpleSet(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_ARMOR_SET = ITEMS.register("magic_armor_set",
            () -> new MagicArmorSet(new Item.Properties()));
    public static final RegistryObject<Item> DARK_SET = ITEMS.register("dark_set",
            () -> new DarkSet(new Item.Properties()));
    public static final RegistryObject<Item> ZORA_ARMOR_SET = ITEMS.register("zora_armor_set",
            () -> new ZoraArmorSet(new Item.Properties()));

    //Tools

    /*public static final RegistryObject<Item> LANTERN = ITEMS.register("lantern",
            () -> new Lantern(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).durability(4000)));
    public static final RegistryObject<Item> EXTINGUISHEDLANTERN = ITEMS.register("extinguishedlantern",
            () -> new ExtinguishedLantern(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> OIL = ITEMS.register("oil_bottle",
            () -> new Oil(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));*/
    public static final RegistryObject<Item> BLUE_CANDLE = ITEMS.register("blue_candle",
            () -> new BlueCandle(new Item.Properties()));
    public static final RegistryObject<Item> RED_CANDLE = ITEMS.register("red_candle",
            () -> new RedCandle(new Item.Properties()));
    public static final RegistryObject<Item> HEROS_SECRET_STASH = ITEMS.register("heros_secret_stash",
            () -> new HerosSecretStash(new Item.Properties()));
    //TODO, change how the book of mudora works to translate new hylian text.
    public static final RegistryObject<Item> BOOK_OF_MUDORA = ITEMS.register("book_of_mudora",
            () -> new Item(new Item.Properties()));
    //TODO, re implement rocs feather, currently dosent do anything
    public static final RegistryObject<Item> ROCS_FEATHER = ITEMS.register("rocs_feather",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FISHING_ROD = ITEMS.register("fishing_rod",
            () -> new FishingRodItem(new Item.Properties()));
    //TODO, BugNet doesn't work
    public static final RegistryObject<Item> BUG_NET = ITEMS.register("bug_net",
            () -> new BugNet(new Item.Properties()));
    //TODO, magic mirror isnt working
    public static final RegistryObject<Item> MAGIC_MIRROR = ITEMS.register("magic_mirror",
            () -> new MagicMirror(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MAGIC_CAPE = ITEMS.register("magic_cape",
            () -> new MagicCape(new Item.Properties()));
    /*public static final RegistryObject<Item> MAGIC_POWDER = ITEMS.register("magic_powder",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));*/
    //TODO, Boomeranges not yet added, no entities added yet
    public static final RegistryObject<Item> BOOMERANG = ITEMS.register("boomerang",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_BOOMERANG = ITEMS.register("magic_boomerang",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WW_BOOMERANG = ITEMS.register("ww_boomerang",
            () -> new Item(new Item.Properties()));

    //public static final RegistryObject<Item> GALE_BOOMERANG = ITEMS.register("gale_boomerang",
    //		() -> new GaleBoomerangItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BOMB = ITEMS.register("bomb",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WATER_BOMB = ITEMS.register("water_bomb",
            () -> new Item(new Item.Properties()));
    /*public static final RegistryObject<Item> BOMBCHU = ITEMS.register("bombchu",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));*/
    public static final RegistryObject<Item> HOOKSHOT = ITEMS.register("hookshot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LONGSHOT = ITEMS.register("longshot",
            () -> new Item(new Item.Properties()));

    //add back in ItemModelPropertiesInit
	/*public static final RegistryObject<Item> CLAWSHOT = ITEMS.register("clawshot",
			() -> new ClawshotItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));*/
    public static final RegistryObject<Item> MAGIC_HAMMER = ITEMS.register("magic_hammer",
            () -> new MagicHammer(new Item.Properties()));
    public static final RegistryObject<Item> MEGATON_HAMMER = ITEMS.register("megaton_hammer",
            () -> new MegatonHammer(new Item.Properties()));
    public static final RegistryObject<Item> SKULL_HAMMER = ITEMS.register("skull_hammer",
            () -> new SkullHammer(new Item.Properties()));


    public static final RegistryObject<Item> FIRE_ROD = ITEMS.register("fire_rod",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ICE_ROD = ITEMS.register("ice_rod",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEKU_STICK = ITEMS.register("deku_stick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEKU_STICK_LIT = ITEMS.register("deku_stick_lit",
            () -> new Item(new Item.Properties()));
    /*public static final RegistryObject<Item> DEKU_NUTS = ITEMS.register("deku_nuts",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));*/
    public static final RegistryObject<Item> EMPTY_CONTAINER = ITEMS.register("empty_container",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DINS_FIRE = ITEMS.register("dins_fire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FARORES_WIND = ITEMS.register("farores_wind",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NAYRUS_LOVE = ITEMS.register("nayrus_love",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEKU_LEAF = ITEMS.register("deku_leaf",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LENS_OF_TRUTH = ITEMS.register("lens_of_truth",
            () -> new LensOfTruth(new Item.Properties()));
    public static final RegistryObject<Item> FAIRY_OCARINA = ITEMS.register("fairy_ocarina",
            () -> new FairyOcarina(new Item.Properties()));
    public static final RegistryObject<Item> OCARINA_OF_TIME = ITEMS.register("ocarina_of_time",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RED_JELLY = ITEMS.register("red_jelly",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GREEN_JELLY = ITEMS.register("green_jelly",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_JELLY = ITEMS.register("blue_jelly",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RED_POTION_MIX = ITEMS.register("red_potion_mix",
            () -> new RedPotionMix(new Item.Properties()));
    public static final RegistryObject<Item> GREEN_POTION_MIX = ITEMS.register("green_potion_mix",
            () -> new GreenPotionMix(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_POTION_MIX = ITEMS.register("blue_potion_mix",
            () -> new BluePotionMix(new Item.Properties()));
    //TODO, fix Red Potion
    public static final RegistryObject<Item> RED_POTION = ITEMS.register("red_potion",
            () -> new RedPotion(new Item.Properties()));
    public static final RegistryObject<Item> GREEN_POTION = ITEMS.register("green_potion",
            () -> new GreenPotion(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_POTION = ITEMS.register("blue_potion",
            () -> new BluePotion(new Item.Properties()));
    public static final RegistryObject<Item> BOTTLED_BEE = ITEMS.register("bottled_bee",
            () -> new BottledBee(new Item.Properties()));
    public static final RegistryObject<Item> BOTTLED_SILVERFISH = ITEMS.register("bottled_silverfish",
            () -> new BottledSilverfish(new Item.Properties()));
    public static final RegistryObject<Item> BOTTLED_ENDERMITE = ITEMS.register("bottled_endermite",
            () -> new BottledEndermite(new Item.Properties()));
    public static final RegistryObject<Item> MAGNETIC_GLOVE = ITEMS.register("magnetic_glove",
            () -> new MagneticGlove(new Item.Properties()));
    public static final RegistryObject<Item> TRIFORCE = ITEMS.register("triforce",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIFORCE_POWER = ITEMS.register("triforce_power",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIFORCE_WISDOM = ITEMS.register("triforce_wisdom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIFORCE_COURAGE = ITEMS.register("triforce_courage",
            () -> new Item(new Item.Properties()));


    //Masks

    public static final RegistryObject<Item> MASK_CLAY = ITEMS.register("mask_clay",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MASK_POSTMANSHAT = ITEMS.register("mask_postmanshat",
            () -> new PostmansHat(new Item.Properties()));
    public static final RegistryObject<Item> MASK_ALLNIGHTMASK = ITEMS.register("mask_allnightmask",
            () -> new AllNightMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_BLASTMASK = ITEMS.register("mask_blastmask",
            () -> new BlastMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_STONEMASK = ITEMS.register("mask_stonemask",
            () -> new StoneMask(new Item.Properties()));
    //TODO, Needs marching re added
    public static final RegistryObject<Item> MASK_BREMANMASK = ITEMS.register("mask_bremenmask",
            () -> new BremenMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_GREATFAIRYMASK = ITEMS.register("mask_greatfairymask",
            () -> new GreatfairyMask(new Item.Properties()));
    //TODO, Deku mask needs DamageSource changed
    public static final RegistryObject<Item> MASK_DEKUMASK = ITEMS.register("mask_dekumask",
            () -> new DekuMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_KEATONMASK = ITEMS.register("mask_keatonmask",
            () -> new KeatonMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_BUNNYHOOD = ITEMS.register("mask_bunnyhood",
            () -> new BunnyHoodMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_DONGEROSMASK = ITEMS.register("mask_dongerosmask",
            () -> new DongerosMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_MASKOFSCENTS = ITEMS.register("mask_maskofscents",
            () -> new MaskOfScentsMask(new Item.Properties()));
    //TODO, GORON MASK DOSENT WORK
    public static final RegistryObject<Item> MASK_GORONMASK = ITEMS.register("mask_goronmask",
            () -> new GoronMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_ROMANISMASK = ITEMS.register("mask_romanismask",
            () -> new RomanisMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_TROUPELEADERSMASK = ITEMS.register("mask_troupeleadersmask",
            () -> new TroupeLeadersMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_KAFEISMASK = ITEMS.register("mask_kafeismask",
            () -> new KafeisMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_COUPLESMASK = ITEMS.register("mask_couplesmask",
            () -> new CouplesMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_MASKOFTRUTH = ITEMS.register("mask_maskoftruth",
            () -> new MaskOfTruth(new Item.Properties()));
    public static final RegistryObject<Item> MASK_ZORAMASK = ITEMS.register("mask_zoramask",
            () -> new ZoraMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_KAMAROSMASK = ITEMS.register("mask_kamarosmask",
            () -> new KamarosMask(new Item.Properties()));
    //TODO, Gibdo isnt finished
    public static final RegistryObject<Item> MASK_GIBDOMASK = ITEMS.register("mask_gibdomask",
            () -> new GibdoMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_GAROSMASK = ITEMS.register("mask_garosmask",
            () -> new GarosMask(new Item.Properties()));
    //TODO, CaptainsHat needs function re added
    public static final RegistryObject<Item> MASK_CAPTAINSHAT = ITEMS.register("mask_captainshat",
            () -> new CaptainsHatMask(new Item.Properties()));
    //TODO, skipped
    public static final RegistryObject<Item> MASK_GIANTSMASK = ITEMS.register("mask_giantsmask",
            () -> new GiantsMask(new Item.Properties()));
    //TODO, skipped
    public static final RegistryObject<Item> MASK_FIERCEDEITYSMASK = ITEMS.register("mask_fiercedeitysmask",
            () -> new FierceDeitysMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_MAJORASMASK = ITEMS.register("mask_majorasmask",
            () -> new MajorasMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_MOONMASK = ITEMS.register("mask_moonmask",
            () -> new MoonMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_SUNMASK = ITEMS.register("mask_sunmask",
            () -> new SunMask(new Item.Properties()));
    public static final RegistryObject<Item> MASK_HAWKEYEMASK = ITEMS.register("mask_hawkeyemask",
            () -> new HawkeyeMask(new Item.Properties()));
    public static final RegistryObject<Item> GNAT_HAT = ITEMS.register("gnat_hat",
            () -> new GnatHat(new Item.Properties()));
	/*public static final RegistryObject<Item> MASK_HEROS_CHARM = ITEMS.register("mask_heroscharm",
			() -> new HerosCharmMask(new Item.Properties().stacksTo(1).tab(SupersLegendMain.APPAREL)));*/

    // ARMORS

    public static final RegistryObject<Item> ROCS_CAPE = ITEMS.register("rocs_cape",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> KOKIRI_CAP = ITEMS.register("kokiri_cap",
            () -> new KokiriArmor(ArmorInit.ArmorTiers.KOKIRI, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> KOKIRI_TUNIC = ITEMS.register("kokiri_tunic",
            () -> new KokiriArmor(ArmorInit.ArmorTiers.KOKIRI, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> KOKIRI_LEGGINGS = ITEMS.register("kokiri_leggings",
            () -> new KokiriArmor(ArmorInit.ArmorTiers.KOKIRI, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> KOKIRI_BOOTS = ITEMS.register("kokiri_boots",
            () -> new KokiriArmor(ArmorInit.ArmorTiers.KOKIRI, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ZORA_CAP = ITEMS.register("zora_cap",
            () -> new ZoraArmor(ArmorInit.ArmorTiers.ZORA, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ZORA_TUNIC = ITEMS.register("zora_tunic",
            () -> new ZoraArmor(ArmorInit.ArmorTiers.ZORA, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ZORA_LEGGINGS = ITEMS.register("zora_leggings",
            () -> new ZoraArmor(ArmorInit.ArmorTiers.ZORA, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> IRON_BOOTS = ITEMS.register("iron_boots",
            () -> new IronBootsArmor(ArmorInit.ArmorTiers.IRON_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ZORA_FLIPPERS = ITEMS.register("zoras_flippers",
            () -> new ZoraFlippersArmor(ArmorInit.ArmorTiers.FLIPPERS, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> GORON_CAP = ITEMS.register("goron_cap",
            () -> new GoronArmor(ArmorInit.ArmorTiers.GORON, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> GORON_TUNIC = ITEMS.register("goron_tunic",
            () -> new GoronArmor(ArmorInit.ArmorTiers.GORON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> GORON_LEGGINGS = ITEMS.register("goron_leggings",
            () -> new GoronArmor(ArmorInit.ArmorTiers.GORON, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> HOVER_BOOTS = ITEMS.register("hover_boots",
            () -> new ArmorItem(ArmorInit.ArmorTiers.HOVER_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> PURPLE_CAP = ITEMS.register("purple_cap",
            () -> new ArmorItem(ArmorInit.ArmorTiers.PURPLE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_TUNIC = ITEMS.register("purple_tunic",
            () -> new ArmorItem(ArmorInit.ArmorTiers.PURPLE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_LEGGINGS = ITEMS.register("purple_leggings",
            () -> new ArmorItem(ArmorInit.ArmorTiers.PURPLE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> PEGASUS_BOOTS = ITEMS.register("pegasus_boots",
            () -> new PegasusBootsArmor(ArmorInit.ArmorTiers.PEGASUS_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> HEROS_NEW_CAP = ITEMS.register("heros_new_cap",
            () -> new ArmorItem(ArmorInit.ArmorTiers.HEROS_NEW, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> HEROS_NEW_TUNIC = ITEMS.register("heros_new_tunic",
            () -> new ArmorItem(ArmorInit.ArmorTiers.HEROS_NEW, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> HEROS_NEW_LEGGINGS = ITEMS.register("heros_new_leggings",
            () -> new ArmorItem(ArmorInit.ArmorTiers.HEROS_NEW, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> HEROS_NEW_BOOTS = ITEMS.register("heros_new_boots",
            () -> new ArmorItem(ArmorInit.ArmorTiers.HEROS_NEW, ArmorItem.Type.BOOTS, new Item.Properties()));

    /*public static final RegistryObject<Item> ENGINEERS_HAT = ITEMS.register("engineers_hat",
            () -> new ArmorHerosNew(EquipmentSlotType.HEAD, new Item.Properties().tab(SupersLegendMain.APPAREL)));
    public static final RegistryObject<Item> ENGINEERS_SHIRT = ITEMS.register("engineers_shirt",
            () -> new ArmorHerosNew(EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.APPAREL)));
    public static final RegistryObject<Item> ENGINEERS_PANTS = ITEMS.register("engineers_pants",
            () -> new ArmorHerosNew(EquipmentSlotType.LEGS, new Item.Properties().tab(SupersLegendMain.APPAREL)));
    public static final RegistryObject<Item> ENGINEERS_BOOTS = ITEMS.register("engineers_boots",
            () -> new ArmorHerosNew(EquipmentSlotType.FEET, new Item.Properties().tab(SupersLegendMain.APPAREL)));*/

    public static final RegistryObject<Item> MAGIC_ARMOR_CAP = ITEMS.register("magic_armor_cap",
            () -> new MagicArmor(ArmorInit.ArmorTiers.MAGIC, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_ARMOR_TUNIC = ITEMS.register("magic_armor_tunic",
            () -> new MagicArmor(ArmorInit.ArmorTiers.MAGIC, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_ARMOR_LEGGINGS = ITEMS.register("magic_armor_leggings",
            () -> new MagicArmor(ArmorInit.ArmorTiers.MAGIC, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_ARMOR_BOOTS = ITEMS.register("magic_armor_boots",
            () -> new MagicArmor(ArmorInit.ArmorTiers.MAGIC, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> DARK_CAP = ITEMS.register("dark_cap",
            () -> new DarkArmor(ArmorInit.ArmorTiers.DARK, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DARK_TUNIC = ITEMS.register("dark_tunic",
            () -> new DarkArmor(ArmorInit.ArmorTiers.DARK, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> DARK_LEGGINGS = ITEMS.register("dark_leggings",
            () -> new DarkArmor(ArmorInit.ArmorTiers.DARK, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> DARK_BOOTS = ITEMS.register("dark_boots",
            () -> new DarkArmor(ArmorInit.ArmorTiers.DARK, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ZORA_ARMOR_CAP = ITEMS.register("zora_armor_cap",
            () -> new ZoraArmorArmor(ArmorInit.ArmorTiers.ZORAARMOR, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ZORA_ARMOR_TUNIC = ITEMS.register("zora_armor_tunic",
            () -> new ZoraArmorArmor(ArmorInit.ArmorTiers.ZORAARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ZORA_ARMOR_LEGGINGS = ITEMS.register("zora_armor_leggings",
            () -> new ZoraArmorArmor(ArmorInit.ArmorTiers.ZORAARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ZORA_ARMOR_BOOTS = ITEMS.register("zora_armor_flippers",
            () -> new ZoraArmorArmor(ArmorInit.ArmorTiers.ZORAARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> FLAMEBREAKER_HELMET = ITEMS.register("flamebreaker_helmet",
            () -> new FlamebreakerArmor(ArmorInit.ArmorTiers.FLAMEBREAKER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> FLAMEBREAKER_TUNIC = ITEMS.register("flamebreaker_tunic",
            () -> new FlamebreakerArmor(ArmorInit.ArmorTiers.FLAMEBREAKER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> FLAMEBREAKER_LEGGINGS = ITEMS.register("flamebreaker_leggings",
            () -> new FlamebreakerArmor(ArmorInit.ArmorTiers.FLAMEBREAKER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> FLAMEBREAKER_BOOTS = ITEMS.register("flamebreaker_boots",
            () -> new FlamebreakerArmor(ArmorInit.ArmorTiers.FLAMEBREAKER, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ANCIENT_HELMET = ITEMS.register("ancient_helmet",
            () -> new AncientArmor(ArmorInit.ArmorTiers.ANCIENT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_CUIRASS = ITEMS.register("ancient_cuirass",
            () -> new AncientArmor(ArmorInit.ArmorTiers.ANCIENT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_GREAVES = ITEMS.register("ancient_greaves",
            () -> new AncientArmor(ArmorInit.ArmorTiers.ANCIENT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_BOOTS = ITEMS.register("ancient_boots",
            () -> new AncientArmor(ArmorInit.ArmorTiers.ANCIENT, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BARBARIAN_HELMET = ITEMS.register("barbarian_helmet",
            () -> new BarbarianArmor(ArmorInit.ArmorTiers.BARBARIAN, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> BARBARIAN_ARMOR = ITEMS.register("barbarian_armor",
            () -> new BarbarianArmor(ArmorInit.ArmorTiers.BARBARIAN, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> BARBARIAN_LEG_WRAPS = ITEMS.register("barbarian_leg_wraps",
            () -> new BarbarianArmor(ArmorInit.ArmorTiers.BARBARIAN, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> BARBARIAN_BOOTS = ITEMS.register("barbarian_boots",
            () -> new BarbarianArmor(ArmorInit.ArmorTiers.BARBARIAN, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> CLIMBERS_BANDANNA = ITEMS.register("climbers_bandanna",
            () -> new ClimbingGearArmor(ArmorInit.ArmorTiers.CLIMBING, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> CLIMBING_GEAR = ITEMS.register("climbing_gear",
            () -> new ClimbingGearArmor(ArmorInit.ArmorTiers.CLIMBING, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> CLIMBING_PANTS = ITEMS.register("climbing_pants",
            () -> new ClimbingGearArmor(ArmorInit.ArmorTiers.CLIMBING, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> CLIMBING_BOOTS = ITEMS.register("climbing_boots",
            () -> new ClimbingGearArmor(ArmorInit.ArmorTiers.CLIMBING, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> DESERT_VOE_HEADBAND = ITEMS.register("desert_voe_headband",
            () -> new ArmorItem(ArmorInit.ArmorTiers.DESERTVOE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DESERT_VOE_SPAULDER = ITEMS.register("desert_voe_spaulder",
            () -> new ArmorItem(ArmorInit.ArmorTiers.DESERTVOE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> DESERT_VOE_TROUSERS = ITEMS.register("desert_voe_trousers",
            () -> new ArmorItem(ArmorInit.ArmorTiers.DESERTVOE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> DESERT_VOE_BOOTS = ITEMS.register("desert_voe_boots",
            () -> new ArmorItem(ArmorInit.ArmorTiers.DESERTVOE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> SNOWQUILL_HEADDRESS = ITEMS.register("snowquill_headdress",
            () -> new ArmorItem(ArmorInit.ArmorTiers.SNOWQUILL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SNOWQUILL_TUNIC = ITEMS.register("snowquill_tunic",
            () -> new ArmorItem(ArmorInit.ArmorTiers.SNOWQUILL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SNOWQUILL_TROUSERS = ITEMS.register("snowquill_trousers",
            () -> new ArmorItem(ArmorInit.ArmorTiers.SNOWQUILL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SNOWQUILL_BOOTS = ITEMS.register("snowquill_boots",
            () -> new ArmorItem(ArmorInit.ArmorTiers.SNOWQUILL, ArmorItem.Type.BOOTS, new Item.Properties()));

    //Back
    public static final RegistryObject<Item> QUIVER = ITEMS.register("quiver",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIG_QUIVER = ITEMS.register("big_quiver",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIGGEST_QUIVER = ITEMS.register("biggest_quiver",
            () -> new Item(new Item.Properties()));

    //Belt
    public static final RegistryObject<Item> BULLET_BAG = ITEMS.register("bullet_bag",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIG_BULLET_BAG = ITEMS.register("big_bullet_bag",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIGGEST_BULLET_BAG = ITEMS.register("biggest_bullet_bag",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BOMB_BAG = ITEMS.register("bomb_bag",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIG_BOMB_BAG = ITEMS.register("big_bomb_bag",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIGGEST_BOMB_BAG = ITEMS.register("biggest_bomb_bag",
            () -> new Item(new Item.Properties()));

    //Hands (gloves, gauntlets, bracelets)
    //public static final RegistryObject<Item> POWER_BRACELETS = ITEMS.register("power_bracelets",
    //		() -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.APPAREL)));
    public static final RegistryObject<Item> GORONS_BRACELET = ITEMS.register("gorons_bracelet",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_GAUNTLETS = ITEMS.register("silver_gauntlets",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_GAUNTLETS = ITEMS.register("golden_gauntlets",
            () -> new Item(new Item.Properties()));

    //CHARMS
    public static final RegistryObject<Item> SILVER_SCALE = ITEMS.register("silver_scale",
            () -> new SilverScale(new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SCALE = ITEMS.register("golden_scale",
            () -> new GoldenScale(new Item.Properties()));

    // Rings
    public static final RegistryObject<Item> BLUE_RING = ITEMS.register("blue_ring",
            () -> new BlueRing(new Item.Properties()));
    public static final RegistryObject<Item> RED_RING = ITEMS.register("red_ring",
            () -> new RedRing(new Item.Properties()));
    public static final RegistryObject<Item> GREEN_RING = ITEMS.register("green_ring",
            () -> new GreenRing(new Item.Properties()));
    public static final RegistryObject<Item> POWER_RING_L1 = ITEMS.register("power_ring_l1",
            () -> new PowerRingL1(new Item.Properties()));
    public static final RegistryObject<Item> POWER_RING_L2 = ITEMS.register("power_ring_l2",
            () -> new PowerRingL2(new Item.Properties()));
    public static final RegistryObject<Item> POWER_RING_L3 = ITEMS.register("power_ring_l3",
            () -> new PowerRingL3(new Item.Properties()));
    public static final RegistryObject<Item> ARMOR_RING_L1 = ITEMS.register("armor_ring_l1",
            () -> new ArmorRingL1(new Item.Properties()));
    public static final RegistryObject<Item> ARMOR_RING_L2 = ITEMS.register("armor_ring_l2",
            () -> new ArmorRingL2(new Item.Properties()));
    public static final RegistryObject<Item> ARMOR_RING_L3 = ITEMS.register("armor_ring_l3",
            () -> new ArmorRingL3(new Item.Properties()));
    public static final RegistryObject<Item> CURSED_RING = ITEMS.register("cursed_ring",
            () -> new CursedRing(new Item.Properties()));
    public static final RegistryObject<Item> HEART_RING_L1 = ITEMS.register("heart_ring_l1",
            () -> new HeartRingL1(new Item.Properties()));
    public static final RegistryObject<Item> HEART_RING_L2 = ITEMS.register("heart_ring_l2",
            () -> new HeartRingL2(new Item.Properties()));
    public static final RegistryObject<Item> GREEN_LUCK_RING = ITEMS.register("green_luck_ring",
            () -> new GreenLuckRing(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_LUCK_RING = ITEMS.register("blue_luck_ring",
            () -> new BlueLuckRing(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_LUCK_RING = ITEMS.register("gold_luck_ring",
            () -> new GoldLuckRing(new Item.Properties()));
    public static final RegistryObject<Item> RED_LUCK_RING = ITEMS.register("red_luck_ring",
            () -> new RedLuckRing(new Item.Properties()));
    public static final RegistryObject<Item> STEADFAST_RING = ITEMS.register("steadfast_ring",
            () -> new SteadfastRing(new Item.Properties()));
    //TODO, GreenHolyRing dosent work
    public static final RegistryObject<Item> GREEN_HOLY_RING = ITEMS.register("green_holy_ring",
            () -> new GreenHolyRing(new Item.Properties()));
    //public static final RegistryObject<Item> BLUE_HOLY_RING = ITEMS.register("blue_holy_ring", BlueHolyRing::new);
    //public static final RegistryObject<Item> RED_HOLY_RING = ITEMS.register("red_holy_ring", RedHolyRing::new);
    public static final RegistryObject<Item> SWIMMERS_RING = ITEMS.register("swimmers_ring",
            () -> new SwimmersRing(new Item.Properties()));
    //When Adding Rings, Remember to Add them to AppraisedRingBox

    //BUCKETS
    /*public static final RegistryObject<Item> POISON_BUCKET = ITEMS.register("poison_bucket",
            () -> new BucketItem(FluidInit.POISON_SOURCE, new Item.Properties().stacksTo(1).tab(ItemGroupInit.RESOURCES)));
    public static final RegistryObject<Item> MUD_BUCKET = ITEMS.register("mud_bucket",
            () -> new BucketItem(FluidInit.MUD_SOURCE, new Item.Properties().stacksTo(1).tab(ItemGroupInit.RESOURCES)));*/

    //Music Discs

    /*public static final RegistryObject<Item> FOREST_TEMPLE_DISC = ITEMS.register("forest_temple_disc",
            () -> new MusicDisc(SoundInit.FOREST_TEMPLE_DISC));
    public static final RegistryObject<Item> FIRE_TEMPLE_DISC = ITEMS.register("fire_temple_disc",
            () -> new MusicDisc(SoundInit.FIRE_TEMPLE_DISC));
    public static final RegistryObject<Item> WATER_TEMPLE_DISC = ITEMS.register("water_temple_disc",
            () -> new MusicDisc(SoundInit.WATER_TEMPLE_DISC));
    public static final RegistryObject<Item> SHADOW_TEMPLE_DISC = ITEMS.register("shadow_temple_disc",
            () -> new MusicDisc(SoundInit.SHADOW_TEMPLE_DISC));
    public static final RegistryObject<Item> SPIRIT_TEMPLE_DISC = ITEMS.register("spirit_temple_disc",
            () -> new MusicDisc(SoundInit.SPIRIT_TEMPLE_DISC));
    public static final RegistryObject<Item> WOODFALL_TEMPLE_DISC = ITEMS.register("woodfall_temple_disc",
            () -> new MusicDisc(SoundInit.WOODFALL_TEMPLE_DISC));
    public static final RegistryObject<Item> SNOWHEAD_TEMPLE_DISC = ITEMS.register("snowhead_temple_disc",
            () -> new MusicDisc(SoundInit.SNOWHEAD_TEMPLE_DISC));
    public static final RegistryObject<Item> GREAT_BAY_TEMPLE_DISC = ITEMS.register("great_bay_temple_disc",
            () -> new MusicDisc(SoundInit.GREAT_BAY_TEMPLE_DISC));
    public static final RegistryObject<Item> STONE_TOWER_TEMPLE_REALITY_DISC = ITEMS.register("stone_tower_temple_reality_disc",
            () -> new MusicDisc(SoundInit.STONE_TOWER_TEMPLE_REALITY_DISC));
    public static final RegistryObject<Item> STONE_TOWER_TEMPLE_ILLUSION_DISC = ITEMS.register("stone_tower_temple_illusion_disc",
            () -> new MusicDisc(SoundInit.STONE_TOWER_TEMPLE_ILLUSION_DISC));*/

    //MobEggs
    //public static final RegistryObject<TPBokoblinSpawnEgg> TP_BOKOBLIN_EGG = ITEMS.register("tp_bokoblin_egg",
    //		() -> new TPBokoblinSpawnEgg(new Item.Properties().stacksTo(64).tab(SupersLegendMain.RESOURCES)));

    //Blocks
    //public static final RegistryObject<Item> CRACKED_FLOOR = ITEMS.register("cracked_floor", () -> new ModBlockItem(BlockInit.CRACKED_FLOOR));
    //public static final RegistryObject<Item> QUICK_SAND = ITEMS.register("quick_sand", () -> new ModBlockItem(BlockInit.QUICK_SAND));
    /*public static final RegistryObject<Item> RUPEE_BLOCK = ITEMS.register("rupee_block", () -> new ModBlockItem(BlockInit.RUPEE_BLOCK));
    public static final RegistryObject<Item> BLUE_RUPEE_BLOCK = ITEMS.register("blue_rupee_block", () -> new ModBlockItem(BlockInit.BLUE_RUPEE_BLOCK));
    public static final RegistryObject<Item> RED_RUPEE_BLOCK = ITEMS.register("red_rupee_block", () -> new ModBlockItem(BlockInit.RED_RUPEE_BLOCK));
    public static final RegistryObject<Item> SILVER_RUPEE_BLOCK = ITEMS.register("silver_rupee_block", () -> new ModBlockItem(BlockInit.SILVER_RUPEE_BLOCK));
    public static final RegistryObject<Item> GOLD_RUPEE_BLOCK = ITEMS.register("gold_rupee_block", () -> new ModBlockItem(BlockInit.GOLD_RUPEE_BLOCK));
    public static final RegistryObject<Item> MASTER_ORE_BLOCK = ITEMS.register("master_ore_block", () -> new ModBlockItem(BlockInit.MASTER_ORE_BLOCK));
    public static final RegistryObject<Item> CRACKED_BOMB_WALL = ITEMS.register("cracked_bomb_wall", () -> new CrackedBombWallItem(BlockInit.CRACKED_BOMB_WALL));
    public static final RegistryObject<Item> BLOCK_OF_TIME = ITEMS.register("block_of_time", () -> new BlockOfTimeItem(BlockInit.BLOCK_OF_TIME));
    public static final RegistryObject<Item> SHADOW_BLOCK = ITEMS.register("shadow_block", ShadowBlockItem::new);
    public static final RegistryObject<Item> HIDDEN_SHADOW_BLOCK = ITEMS.register("hidden_shadow_block", HiddenShadowBlockItem::new);
    public static final RegistryObject<Item> FALSE_SHADOW_BLOCK = ITEMS.register("false_shadow_block", FalseShadowBlockItem::new);
    public static final RegistryObject<Item> SHADOW_MODEL_BLOCK = ITEMS.register("shadow_model_block", () -> new ModHiddenBlockItem(BlockInit.SHADOW_MODEL_BLOCK));
    public static final RegistryObject<Item> DINS_SACRED_PEDESTAL = ITEMS.register("dins_sacred_pedestal", () -> new SacredPedestalItem(BlockInit.DINS_SACRED_PEDESTAL));
    public static final RegistryObject<Item> FARORES_SACRED_PEDESTAL = ITEMS.register("farores_sacred_pedestal", () -> new SacredPedestalItem(BlockInit.FARORES_SACRED_PEDESTAL));
    public static final RegistryObject<Item> NAYRUS_SACRED_PEDESTAL = ITEMS.register("nayrus_sacred_pedestal", () -> new SacredPedestalItem(BlockInit.NAYRUS_SACRED_PEDESTAL));
    public static final RegistryObject<Item> DINS_FLAME = ITEMS.register("dins_flame", () -> new ModBlockItem(BlockInit.DINS_FLAME));
    public static final RegistryObject<Item> FARORES_FLAME = ITEMS.register("farores_flame", () -> new ModBlockItem(BlockInit.FARORES_FLAME));
    public static final RegistryObject<Item> NAYRUS_FLAME = ITEMS.register("nayrus_flame", () -> new ModBlockItem(BlockInit.NAYRUS_FLAME));
    public static final RegistryObject<Item> FAN = ITEMS.register("fan", () -> new ModBlockItem(BlockInit.FAN));
    public static final RegistryObject<Item> SWITCHABLE_FAN = ITEMS.register("switchable_fan", () -> new ModBlockItem(BlockInit.SWITCHABLE_FAN));
    public static final RegistryObject<Item> GRAPPLE_BLOCK = ITEMS.register("grapple_block", () -> new ModBlockItem(BlockInit.GRAPPLE_BLOCK));
    public static final RegistryObject<Item> GRATE_BLOCK = ITEMS.register("grate_block", () -> new GrateItem(BlockInit.GRATE_BLOCK));
    public static final RegistryObject<Item> CRATE_BLOCK = ITEMS.register("crate_block", () -> new CrateBlockItem(BlockInit.CRATE_BLOCK));
    public static final RegistryObject<Item> PUSH_STONE = ITEMS.register("push_stone", () -> new PushStoneItem(BlockInit.PUSH_STONE));
    public static final RegistryObject<Item> SILVER_PUSH_STONE = ITEMS.register("silver_push_stone", () -> new SilverPushStoneItem(BlockInit.SILVER_PUSH_STONE));
    public static final RegistryObject<Item> BLACK_PUSH_STONE = ITEMS.register("black_push_stone", () -> new BlackPushStoneItem(BlockInit.BLACK_PUSH_STONE));
    public static final RegistryObject<Item> GOSSIP_STONE_BLOCK = ITEMS.register("gossip_stone_block", () -> new GossipStoneItem(BlockInit.GOSSIP_STONE_BLOCK));
    //public static final RegistryObject<Item> SMALL_LOCK = ITEMS.register("small_lock", () -> new ModBlockItem(BlockInit.SMALL_LOCK));
    //public static final RegistryObject<Item> LOCKED_DOOR = ITEMS.register("locked_door", () -> new ModBlockItem(BlockInit.LOCKED_DOOR));
    public static final RegistryObject<Item> DUNGEON_DOOR = ITEMS.register("dungeon_door", () -> new ModBlockItem(BlockInit.DUNGEON_DOOR));
    public static final RegistryObject<Item> LOCKED_DUNGEON_DOOR = ITEMS.register("locked_dungeon_door", () -> new ModBlockItem(BlockInit.LOCKED_DUNGEON_DOOR));
    public static final RegistryObject<Item> BOSS_DOOR = ITEMS.register("boss_door", () -> new ModBlockItem(BlockInit.BOSS_DOOR));
    public static final RegistryObject<Item> LOCKED_BOSS_DOOR = ITEMS.register("locked_boss_door", () -> new ModBlockItem(BlockInit.LOCKED_BOSS_DOOR));
    public static final RegistryObject<Item> LOCKED_WOODEN_DOOR = ITEMS.register("locked_wooden_door", () -> new ModBlockItem(BlockInit.LOCKED_WOODEN_DOOR));
    public static final RegistryObject<Item> WOODEN_BOSS_DOOR = ITEMS.register("wooden_boss_door", () -> new ModBlockItem(BlockInit.WOODEN_BOSS_DOOR));
    public static final RegistryObject<Item> POSTBOX_BLOCK = ITEMS.register("postbox_block", () -> new ModBlockItem(BlockInit.POSTBOX_BLOCK));
    public static final RegistryObject<Item> TOMBSTONE_BLOCK = ITEMS.register("tombstone_block", () -> new ModBlockItem(BlockInit.TOMBSTONE_BLOCK));
    public static final RegistryObject<Item> CHAIN_LINK_FENCE_BLOCK = ITEMS.register("chain_link_fence_block", () -> new ModBlockItem(BlockInit.CHAIN_LINK_FENCE_BLOCK));
    public static final RegistryObject<Item> SPIKES_BLOCK = ITEMS.register("spikes_block", () -> new ModBlockItem(BlockInit.SPIKES_BLOCK));
    public static final RegistryObject<Item> TORCH_TOWER = ITEMS.register("torch_tower", () -> new TorchTowerItem(BlockInit.TORCH_TOWER));
    public static final RegistryObject<Item> OWL_STATUE = ITEMS.register("owl_statue", () -> new OwlStatueItem(BlockInit.OWL_STATUE));
    public static final RegistryObject<Item> POT_BLOCK = ITEMS.register("pot_block", () -> new ModBlockItem(BlockInit.POT_BLOCK));
    public static final RegistryObject<Item> JAR_BLOCK = ITEMS.register("jar_block", () -> new ModBlockItem(BlockInit.JAR_BLOCK));
    public static final RegistryObject<Item> PEDESTAL = ITEMS.register("pedestal", () -> new ModBlockItem(BlockInit.PEDESTAL));
    public static final RegistryObject<Item> STONE_PATH_BLOCK = ITEMS.register("stone_path_block", () -> new ModBlockItem(BlockInit.STONE_PATH_BLOCK));
    public static final RegistryObject<Item> STONE_TILE_BLOCK = ITEMS.register("stone_tile_block", () -> new ModBlockItem(BlockInit.STONE_TILE_BLOCK));
    public static final RegistryObject<Item> BLUE_FLOOR_SWITCH = ITEMS.register("blue_floor_switch", () -> new ModBlockItem(BlockInit.BLUE_FLOOR_SWITCH));
    public static final RegistryObject<Item> YELLOW_FLOOR_SWITCH = ITEMS.register("yellow_floor_switch", () -> new ModBlockItem(BlockInit.YELLOW_FLOOR_SWITCH));
    public static final RegistryObject<Item> RED_FLOOR_SWITCH = ITEMS.register("red_floor_switch", () -> new ModBlockItem(BlockInit.RED_FLOOR_SWITCH));
    public static final RegistryObject<Item> RUSTED_FLOOR_SWITCH = ITEMS.register("rusted_floor_switch", () -> new ModBlockItem(BlockInit.RUSTED_FLOOR_SWITCH));
    public static final RegistryObject<Item> ROYAL_TILE = ITEMS.register("royal_tile", () -> new RoyalTileItem(BlockInit.ROYAL_TILE));
    public static final RegistryObject<Item> WARP_PAD = ITEMS.register("warp_pad", () -> new WarpPadBlockItem(BlockInit.WARP_PAD));
    public static final RegistryObject<Item> OAK_PEG_BLOCK = ITEMS.register("oak_peg_block", () -> new WoodenPegItem(BlockInit.OAK_PEG_BLOCK));
    public static final RegistryObject<Item> SPRUCE_PEG_BLOCK = ITEMS.register("spruce_peg_block", () -> new WoodenPegItem(BlockInit.SPRUCE_PEG_BLOCK));
    public static final RegistryObject<Item> BIRCH_PEG_BLOCK = ITEMS.register("birch_peg_block", () -> new WoodenPegItem(BlockInit.BIRCH_PEG_BLOCK));
    public static final RegistryObject<Item> JUNGLE_PEG_BLOCK = ITEMS.register("jungle_peg_block", () -> new WoodenPegItem(BlockInit.JUNGLE_PEG_BLOCK));
    public static final RegistryObject<Item> ACACIA_PEG_BLOCK = ITEMS.register("acacia_peg_block", () -> new WoodenPegItem(BlockInit.ACACIA_PEG_BLOCK));
    public static final RegistryObject<Item> DARK_OAK_PEG_BLOCK = ITEMS.register("dark_oak_peg_block", () -> new WoodenPegItem(BlockInit.DARK_OAK_PEG_BLOCK));
    public static final RegistryObject<Item> RUSTED_PEG_BLOCK = ITEMS.register("rusted_peg_block", () -> new RustedPegItem(BlockInit.RUSTED_PEG_BLOCK));
    public static final RegistryObject<Item> SPIKED_PEG_BLOCK = ITEMS.register("spiked_peg_block", () -> new SpikedPegItem(BlockInit.SPIKED_PEG_BLOCK));
    //public static final RegistryObject<Item> LIGHT_EMITTER = ITEMS.register("light_emitter", () -> new ModBlockItem(BlockInit.LIGHT_EMITTER));
    //public static final RegistryObject<Item> LIGHT_PRISM = ITEMS.register("light_prism", () -> new ModBlockItem(BlockInit.LIGHT_PRISM));

    public static final RegistryObject<Item> ODD_MUSHROOM = ITEMS.register("odd_mushroom", () -> new ModBlockItem(BlockInit.ODD_MUSHROOM));
    public static final RegistryObject<Item> MAGIC_MUSHROOM = ITEMS.register("magic_mushroom", () -> new ModBlockItem(BlockInit.MAGIC_MUSHROOM));
    public static final RegistryObject<Item> DEKU_FLOWER_BLOCK = ITEMS.register("deku_flower_block", () -> new ModBlockItem(BlockInit.DEKU_FLOWER_BLOCK));
    public static final RegistryObject<Item> YELLOW_DEKU_FLOWER_BLOCK = ITEMS.register("yellow_deku_flower_block", () -> new ModBlockItem(BlockInit.YELLOW_DEKU_FLOWER_BLOCK));
    public static final RegistryObject<Item> BUSH_BLOCK = ITEMS.register("bush_block", () -> new ModBlockItem(BlockInit.BUSH_BLOCK));*/
    //public static final RegistryObject<Item> GRASS_PATCH_BLOCK = ITEMS.register("grass_patch_block", () -> new ModBlockItem(BlockInit.GRASS_PATCH_BLOCK));


    //public static final RegistryObject<Item> COOKING_POT = ITEMS.register("cooking_pot", () -> new ModBlockItem(BlockInit.COOKING_POT));
    //public static final RegistryObject<Item> SUN_SWITCH = ITEMS.register("sun_switch", () -> new SunSwitchItem(BlockInit.SUN_SWITCH));

    /*public static final Map<FoodCategory, Item> foodCategoryItems = Util.make(new EnumMap<FoodCategory, Item>(FoodCategory.class), map ->
    {
        for (FoodCategory category : FoodCategory.values())
        {
            Item categoryItem = new Item(new Item.Properties());
            ITEMS.register("food_category_" + category.name().toLowerCase(), () -> categoryItem);
            map.put(category, categoryItem);
        }
    });*/

        public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
