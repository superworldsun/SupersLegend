package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.*;
import com.superworldsun.superslegend.items.items.*;
import com.superworldsun.superslegend.items.weapons.GiantsKnife;
import com.superworldsun.superslegend.items.weapons.HerosBow;
import com.superworldsun.superslegend.items.weapons.LynelBowX3;
import com.superworldsun.superslegend.items.weapons.LynelBowX5;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            SupersLegendMain.MOD_ID);

    public static final RegistryObject<Item> LANZANITE = ITEMS.register("lanzanite",
            () -> new LanzaniteItem(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<Rupee> RUPEE = ITEMS.register("rupee",
            () -> new Rupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<BlueRupee> BLUE_RUPEE = ITEMS.register("blue_rupee",
            () -> new BlueRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<RedRupee> RED_RUPEE = ITEMS.register("red_rupee",
            () -> new RedRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SilverRupee> SILVER_RUPEE = ITEMS.register("silver_rupee",
            () -> new SilverRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<GoldRupee> GOLD_RUPEE = ITEMS.register("gold_rupee",
            () -> new GoldRupee(new Item.Properties().tab(SupersLegendMain.RESOURCES)));


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
    public static final RegistryObject<Item> RUPEE_POUCH = ITEMS.register("rupee_pouch",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
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

    public static final RegistryObject<Item> MASTER_ORE = ITEMS.register("master_ore",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASTER_SWORD_BLADE = ITEMS.register("master_sword_blade",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASTER_SWORLD_HILT = ITEMS.register("master_sword_hilt",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<Item> KOKIRI_SET = ITEMS.register("kokiri_set",
            () -> new KokiriSet(new Item.Properties().tab(SupersLegendMain.RESOURCES)));


    //FOOD

    public static final RegistryObject<Item> HYRULE_BASS = ITEMS.register("hyrule_bass",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> COOKED_HYRULE_BASS = ITEMS.register("cooked_hyrule_bass",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> HYLIAN_LOACH = ITEMS.register("hylian_loach",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> COOKED_HYLIAN_LOACH = ITEMS.register("cooked_hylian_loach",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));


    //WEAPONS & TOOLS

    public static final RegistryObject<SwordItem> KOKIRI_SWORD = ITEMS.register("kokiri_sword",
            () -> new SwordItem(ItemToolTiers.KOKIRI_SWORD, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> RAZOR_SWORD = ITEMS.register("razor_sword",
            () -> new SwordItem(ItemToolTiers.RAZOR_SWORD, 2, -2.5f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> GILDED_SWORD = ITEMS.register("gilded_sword",
            () -> new SwordItem(ItemToolTiers.GILDED_SWORD, 2, -2.4f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<SwordItem> MASTER_SWORD = ITEMS.register("master_sword",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD, 2, -2.4f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> MASTER_SWORD_V2 = ITEMS.register("master_sword_v2",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD_V2, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> MASTER_SWORD_D = ITEMS.register("master_sword_d",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD_D, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> MASTER_SWORD_N = ITEMS.register("master_sword_n",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD_N, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> MASTER_SWORD_F = ITEMS.register("master_sword_f",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD_F, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> MASTER_SWORD_DN = ITEMS.register("master_sword_dn",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD_DN, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> MASTER_SWORD_NF = ITEMS.register("master_sword_nf",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD_NF, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> MASTER_SWORD_FD = ITEMS.register("master_sword_fd",
            () -> new SwordItem(ItemToolTiers.MASTER_SWORD_FD, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> TRUE_MASTER_SWORD = ITEMS.register("true_master_sword",
            () -> new SwordItem(ItemToolTiers.TRUE_MASTER_SWORD, 2, -2.2f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));


    public static final RegistryObject<GiantsKnife> GIANTS_KNIFE = ITEMS.register("giants_knife",
            () -> new GiantsKnife(ItemToolTiers.GIANTS_KNIFE, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> BIGGORONS_SWORD = ITEMS.register("biggorons_sword",
            () -> new SwordItem(ItemToolTiers.BIGGORONS_SWORD, 2, -2.5f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SwordItem> GUARDIAN_SWORD = ITEMS.register("guardian_sword",
            () -> new SwordItem(ItemToolTiers.GUARDIAN_SWORD, 2, -2.3f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<HerosBow> HEROS_BOW = ITEMS.register("heros_bow",
            () -> new HerosBow(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BIT_BOW = ITEMS.register("bit_bow",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<LynelBowX3> LYNEL_BOW_X3 = ITEMS.register("lynel_bow_x3",
            () -> new LynelBowX3(1, new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<LynelBowX5> LYNEL_BOW_X5 = ITEMS.register("lynel_bow_x5",
            () -> new LynelBowX5(1,new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> SLING_SHOT = ITEMS.register("sling_shot",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<DekuShield> DEKU_SHIELD = ITEMS.register("deku_shield",
            () -> new DekuShield(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<ShieldItem> HYLIAN_SHIELD = ITEMS.register("hylian_shield",
            () -> new ShieldItem(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<Item> FIRE_ARROW = ITEMS.register("fire_arrow",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ICE_ARROW = ITEMS.register("ice_arrow",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> SHOCK_ARROW = ITEMS.register("shock_arrow",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BOMB_ARROW = ITEMS.register("bomb_arrow",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ANCIENT_ARROW = ITEMS.register("ancient_arrow",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> SILVER_ARROW = ITEMS.register("silver_arrow",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));


    public static final RegistryObject<Item> LANTERN = ITEMS.register("lantern",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BLUE_CANDLE = ITEMS.register("blue_candle",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> RED_CANDLE = ITEMS.register("red_candle",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> HEROS_SECRET_STASH = ITEMS.register("heros_secret_stash",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<BookOfMudora> BOOK_OF_MUDORA = ITEMS.register("book_of_mudora",
            () -> new BookOfMudora(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<SilverScale> SILVER_SCALE = ITEMS.register("silver_scale",
            () -> new SilverScale(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<GoldenScale> GOLDEN_SCALE = ITEMS.register("golden_scale",
            () -> new GoldenScale(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<RocsFeather> ROCS_FEATHER = ITEMS.register("rocs_feather",
            () -> new RocsFeather(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> FISHING_ROD = ITEMS.register("fishing_rod",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<BugNet> BUG_NET = ITEMS.register("bug_net",
            () -> new BugNet(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror",
            () -> new MagicMirror(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<MagicCape> MAGIC_CAPE = ITEMS.register("magic_cape",
            () -> new MagicCape(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> REGULAR_BOOMERANG = ITEMS.register("regular_boomerang",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BOMB = ITEMS.register("bomb",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> FIRE_ROD = ITEMS.register("fire_rod",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ICE_ROD = ITEMS.register("ice_rod",
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
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> RED_JELLY = ITEMS.register("red_jelly",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> GREEN_JELLY = ITEMS.register("green_jelly",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BLUE_JELLY = ITEMS.register("blue_jelly",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> RED_POTION_MIX = ITEMS.register("red_potion_mix",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> GREEN_POTION_MIX = ITEMS.register("green_potion_mix",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BLUE_POTION_MIX = ITEMS.register("blue_potion_mix",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> RED_POTION = ITEMS.register("red_potion",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> GREEN_POTION = ITEMS.register("green_potion",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BLUE_POTION = ITEMS.register("blue_potion",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
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

    public static final RegistryObject<Item> MASK_CLAY = ITEMS.register("mask_clay",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_POSTMANSHAT = ITEMS.register("mask_postmanshat",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_ALLNIGHTMASK = ITEMS.register("mask_allnightmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_BLASTMASK = ITEMS.register("mask_blastmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_STONEMASK = ITEMS.register("mask_stonemask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_BREMANMASK = ITEMS.register("mask_bremenmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_GREATFAIRYMASK = ITEMS.register("mask_greatfairymask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_DEKUMASK = ITEMS.register("mask_dekumask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_KEATONMASK = ITEMS.register("mask_keatonmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_BUNNYHOOD = ITEMS.register("mask_bunnyhood",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_DONGEROSMASK = ITEMS.register("mask_dongerosmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_MASKOFSCENTS = ITEMS.register("mask_maskofscents",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_GORONMASK = ITEMS.register("mask_goronmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_ROMANISMASK = ITEMS.register("mask_romanismask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_TROUPELEADERSMASK = ITEMS.register("mask_troupeleadersmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_KAFEISMASK = ITEMS.register("mask_kafeismask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_COUPLESMASK = ITEMS.register("mask_couplesmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_MASKOFTRUTH = ITEMS.register("mask_maskoftruth",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_ZORAMASK = ITEMS.register("mask_zoramask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_KAMAROSMASK = ITEMS.register("mask_kamarosmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_GIBDOMASK = ITEMS.register("mask_gibdomask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_GAROSMASK = ITEMS.register("mask_garosmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_CAPTAINSHAT = ITEMS.register("mask_captainshat",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_GIANTSMASK = ITEMS.register("mask_giantsmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_FIERCEDEITYSMASK = ITEMS.register("mask_fiercedeitysmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_MAJORASMASK = ITEMS.register("mask_majorasmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_MOONMASK = ITEMS.register("mask_moonmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_SUNMASK = ITEMS.register("mask_sunmask",
            () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MASK_HAWKEYEMASK = ITEMS.register("mask_hawkeyemask",
           () -> new Item(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES)));

    //ARMORS


    public static final RegistryObject<Item> ROCS_CAPE = ITEMS.register("rocs_cape",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> KOKIRI_CAP = ITEMS.register("kokiri_cap",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> KOKIRI_TUNIC = ITEMS.register("kokiri_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> KOKIRI_LEGGINGS = ITEMS.register("kokiri_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> KOKIRI_BOOTS = ITEMS.register("kokiri_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_CAP = ITEMS.register("zora_cap",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_TUNIC = ITEMS.register("zora_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_LEGGINGS = ITEMS.register("zora_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> IRON_BOOTS = ITEMS.register("iron_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_FLIPPERS = ITEMS.register("zoras_flippers",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> GORON_CAP = ITEMS.register("goron_cap",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> GORON_TUNIC = ITEMS.register("goron_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> GORON_LEGGINGS = ITEMS.register("goron_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> HOVER_BOOTS = ITEMS.register("hover_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> PURPLE_CAP = ITEMS.register("purple_cap",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> PURPLE_TUNIC = ITEMS.register("purple_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> PURPLE_LEGGINGS = ITEMS.register("purple_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> PEGASUS_BOOTS = ITEMS.register("pegasus_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MAGIC_ARMOR_CAP = ITEMS.register("magic_armor_cap",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MAGIC_ARMOR_TUNIC = ITEMS.register("magic_armor_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MAGIC_ARMOR_LEGGINGS = ITEMS.register("magic_armor_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> MAGIC_ARMOR_BOOTS = ITEMS.register("magic_armor_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> DARK_CAP = ITEMS.register("dark_cap",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> DARK_TUNIC = ITEMS.register("dark_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> DARK_LEGGINGS = ITEMS.register("dark_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> DARK_BOOTS = ITEMS.register("dark_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_ARMOR_CAP = ITEMS.register("zora_armor_cap",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_ARMOR_TUNIC = ITEMS.register("zora_armor_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_ARMOR_LEGGINGS = ITEMS.register("zora_armor_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ZORA_ARMOR_BOOTS = ITEMS.register("zora_armor_flippers",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> FLAMEBREAKER_HELMET = ITEMS.register("flamebreaker_helmet",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> FLAMEBREAKER_TUNIC = ITEMS.register("flamebreaker_tunic",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> FLAMEBREAKER_LEGGINGS = ITEMS.register("flamebreaker_leggings",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> FLAMEBREAKER_BOOTS = ITEMS.register("flamebreaker_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ANCIENT_HELMET = ITEMS.register("ancient_helmet",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ANCIENT_CUIRASS = ITEMS.register("ancient_cuirass",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ANCIENT_GREAVES = ITEMS.register("ancient_greaves",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> ANCIENT_BOOTS = ITEMS.register("ancient_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BARBARIAN_HELMET = ITEMS.register("barbarian_helmet",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BARBARIAN_ARMOR = ITEMS.register("barbarian_armor",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BARBARIAN_LEG_WRAPS = ITEMS.register("barbarian_leg_wraps",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> BARBARIAN_BOOTS = ITEMS.register("barbarian_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> CLIMBERS_BANDANNA = ITEMS.register("climbers_bandanna",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> CLIMBING_GEAR = ITEMS.register("climbing_gear",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> CLIMBING_PANTS = ITEMS.register("climbing_pants",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));
    public static final RegistryObject<Item> CLIMBING_BOOTS = ITEMS.register("climbing_boots",
            () -> new Item(new Item.Properties().tab(SupersLegendMain.RESOURCES)));







    public static final RegistryObject<Item> FRIED_EGG = ITEMS.register("fried_egg",
            () -> new FriedEggItem(new Item.Properties().food(new Food.Builder()
                    .nutrition(8).effect(() -> new EffectInstance(Effects.HARM, 20, 1), 1F)
                    .saturationMod(0.7f).alwaysEat()
                    .build()).tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<PickaxeItem> LANZANITE_PICKAXE = ITEMS.register("lanzanite_pickaxe",
            () -> new LanzanitePickaxeItem(ItemToolTiers.LANZANITE, 1, -2.8f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<LanzaniteMultiToolItem> LANZANITE_MULTITOOL = ITEMS.register("lanzanite_multitool",
            () -> new LanzaniteMultiToolItem(ItemToolTiers.LANZANITE, 3, -3.0f,
                    new Item.Properties().tab(SupersLegendMain.RESOURCES)));

    //Remember to set custom properties for your bows such as durability here
    public static final RegistryObject<BowItem> METAL_BOW = ITEMS.register("metal_bow",
            () -> new MetalBowItem(new Item.Properties().stacksTo(1).durability(700).tab(SupersLegendMain.RESOURCES)));

    public static final RegistryObject<Item> POISON_ARROW = ITEMS.register("poison_arrow",
            () -> new PoisonArrowItem(new Item.Properties().tab(SupersLegendMain.RESOURCES)));

        public static final RegistryObject<Item> BUBBLEGLOOP_DISC = ITEMS.register("bubblegloop_disc", () -> new MusicDiscItem(1,
            SoundInit.BUBBLEGLOOP_DISC_LAZY.get(), new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES).rarity(Rarity.RARE)));
}





