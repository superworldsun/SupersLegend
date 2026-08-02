package com.superworldsun.superslegend;

import com.superworldsun.superslegend.songs.OcarinaSong;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue BASE_PLAYER_HEALTH = BUILDER.defineInRange("Base player health", 20, 1, 20);
    private static final ForgeConfigSpec.BooleanValue ENABLE_TEMPERATURE = BUILDER.define("Activate the temperature system", true);
    public static final ForgeConfigSpec.BooleanValue SEA_BREEZE_BOOMERANG_FOLLOWS = BUILDER.comment("The WW Boomerang will follow your mouse till it hits it's range limit.")
            .define("SeaBreezeBoomerangFollows", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_TURN_BACK_ON_HIT = BUILDER.define("Boomerangs turn back on hit", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_BREAK_SOFT_BLOCKS = BUILDER.define("Boomerangs break soft blocks", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_LEVERS = BUILDER.define("Boomerangs activate levers", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_BUTTONS = BUILDER.define("Boomerangs activate buttons", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_PRESSURE_PLATES = BUILDER.define("Boomerangs activate pressure plates", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_TRIP_WIRES = BUILDER.define("Boomerangs activate trip wires", true);
    private static final ForgeConfigSpec.BooleanValue SONG_SHEET_CONSUMED = BUILDER.define("songSheetConsumed", true);
    private static final ForgeConfigSpec.BooleanValue shockArrowCreeper = BUILDER.comment("Creepers transform into Charged Creepers with Shock Arrows.").define("shockArrowCreeper", true);
    private static final ForgeConfigSpec.BooleanValue disableAncientArrowDrops = BUILDER.comment("If true, entities killed by Ancient Arrows will not drop items.").define("disableAncientArrowDrops", true);
    private static final ForgeConfigSpec.BooleanValue explosivegriefing =  BUILDER.comment("Whether bombs & bomb arrows will cause block destruction").define("explosivegriefing", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_SONG_OF_DOUBLE_TIME;
    private static final ForgeConfigSpec.BooleanValue ENABLE_INVERTED_SONG_OF_TIME;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_DAY_NIGHT;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_CROP_GROWTH;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_FURNACES;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_BREWING_STANDS;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_MOB_SPEED_AND_ATTACKS;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_FIRE_SPREAD;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_ITEM_DESPAWN;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_WEATHER;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_REDSTONE;
    private static final ForgeConfigSpec.BooleanValue TIME_SONG_ANIMAL_GROWTH;
    private static final ForgeConfigSpec.EnumValue<FairyOcarinaRestriction> FAIRY_OCARINA_RESTRICTION;
    private static final ForgeConfigSpec.BooleanValue RANDOMIZE_DEKU_BUBBLE_FLIGHT_PATH;
    private static final ForgeConfigSpec.BooleanValue PEGASUS_BOOTS_BREAK_JARS_AND_POTS;
    private static final ForgeConfigSpec.BooleanValue PEGASUS_BOOTS_BREAK_PLANTS_AND_CROPS;
    private static final ForgeConfigSpec.BooleanValue ENABLE_GOLD_SKULLTULAS;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_MAX_LIGHT;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_CHUNK_COOLDOWN_NIGHTS;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_MEMORY_RADIUS;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_MIN_SEPARATION;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_MIN_PLAYER_DISTANCE;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_MAX_PLAYER_DISTANCE;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_MIN_PER_PLAYER;
    private static final ForgeConfigSpec.IntValue GOLD_SKULLTULA_MAX_PER_PLAYER;

    public enum FairyOcarinaRestriction {
        LIGHT,
        HARD
    }

    static {
        BUILDER.push("timeSongs");
        ENABLE_SONG_OF_DOUBLE_TIME = BUILDER.comment("Allow the Song of Double Time and its /time speed command mode.")
                .define("enableSongOfDoubleTime", true);
        ENABLE_INVERTED_SONG_OF_TIME = BUILDER.comment("Allow the Inverted Song of Time and its /time speed command mode.")
                .define("enableInvertedSongOfTime", true);
        TIME_SONG_DAY_NIGHT = BUILDER.comment("Time songs affect the day/night cycle.")
                .define("affectDayNightCycle", true);
        TIME_SONG_CROP_GROWTH = BUILDER.comment("Time songs affect crops, saplings, sugar cane, and other plant growth.")
                .define("affectCropAndPlantGrowth", true);
        TIME_SONG_FURNACES = BUILDER.comment("Time songs affect furnaces, smokers, and blast furnaces.")
                .define("affectFurnaces", true);
        TIME_SONG_BREWING_STANDS = BUILDER.comment("Time songs affect brewing stands.")
                .define("affectBrewingStands", true);
        TIME_SONG_MOB_SPEED_AND_ATTACKS = BUILDER.comment("Time songs affect mob movement, AI, and attacks. The timeSongAffectsMobSpeed gamerule can also disable this per world.")
                .define("affectMobSpeedAndAttacks", true);
        TIME_SONG_FIRE_SPREAD = BUILDER.comment("Time songs affect fire spreading and fire ticks.")
                .define("affectFireSpread", true);
        TIME_SONG_ITEM_DESPAWN = BUILDER.comment("Time songs affect the despawn timer of dropped items.")
                .define("affectItemDespawn", true);
        TIME_SONG_WEATHER = BUILDER.comment("Time songs affect rain, thunder, and clear-weather timers.")
                .define("affectWeather", true);
        TIME_SONG_REDSTONE = BUILDER.comment("Time songs affect delayed redstone component ticks.")
                .define("affectRedstone", true);
        TIME_SONG_ANIMAL_GROWTH = BUILDER.comment("Time songs affect baby animal growth and breeding cooldowns.")
                .define("affectAnimalGrowth", true);
        BUILDER.pop();

        BUILDER.push("ocarinas");
        FAIRY_OCARINA_RESTRICTION = BUILDER.comment(
                        "LIGHT blocks only Song of Time, Song of Double Time, and Inverted Song of Time.",
                        "HARD allows only Epona's Song, Song of Storms, Zelda's Lullaby, and Saria's Song.")
                .defineEnum("fairyOcarinaRestriction", FairyOcarinaRestriction.HARD);
        BUILDER.pop();

        BUILDER.push("dekuMagicBubble");
        RANDOMIZE_DEKU_BUBBLE_FLIGHT_PATH = BUILDER.comment(
                        "Adds a small smooth irregularity to the Deku Magic Bubble's spiral flight path.",
                        "Set this to false to restore the original perfectly even spiral.")
                .define("randomizeFlightPath", true);
        BUILDER.pop();

        BUILDER.push("pegasusBoots");
        PEGASUS_BOOTS_BREAK_JARS_AND_POTS = BUILDER.comment(
                        "Pegasus Boots charges break jars, pots, and decorated pots while a charge weapon is held.")
                .define("breakJarsAndPots", true);
        PEGASUS_BOOTS_BREAK_PLANTS_AND_CROPS = BUILDER.comment(
                        "Pegasus Boots charges break plants, flowers, foliage, sugar cane, and crops while a charge weapon is held.")
                .define("breakPlantsAndCrops", true);
        BUILDER.pop();

        BUILDER.push("goldSkulltula");
        ENABLE_GOLD_SKULLTULAS = BUILDER.comment("Enables nightly Gold Skulltula collectible spawns.")
                .define("enabled", true);
        GOLD_SKULLTULA_MAX_LIGHT = BUILDER.comment("Highest local light level where one may spawn.")
                .defineInRange("maximumLightLevel", 10, 0, 15);
        GOLD_SKULLTULA_CHUNK_COOLDOWN_NIGHTS = BUILDER.comment(
                        "Nights a surviving Gold Skulltula location is remembered and reused.",
                        "After this many nights the location expires and a new one may be selected.")
                .defineInRange("chunkCooldownNights", 7, 1, 1000);
        GOLD_SKULLTULA_MEMORY_RADIUS = BUILDER.comment("Blocks around a remembered spawn that remain unavailable during its cooldown.")
                .defineInRange("spawnMemoryRadius", 48, 8, 256);
        GOLD_SKULLTULA_MIN_SEPARATION = BUILDER.comment("Minimum distance between living Gold Skulltulas.")
                .defineInRange("minimumLivingSeparation", 32, 8, 256);
        GOLD_SKULLTULA_MIN_PLAYER_DISTANCE = BUILDER.comment("Preferred minimum spawn distance from every player.")
                .defineInRange("minimumPlayerDistance", 34, 8, 128);
        GOLD_SKULLTULA_MAX_PLAYER_DISTANCE = BUILDER.comment("Farthest distance searched around each player.")
                .defineInRange("maximumPlayerDistance", 90, 24, 192);
        GOLD_SKULLTULA_MIN_PER_PLAYER = BUILDER.comment("Minimum nightly spawn allowance generated for each player.")
                .defineInRange("minimumPerPlayerPerNight", 3, 0, 16);
        GOLD_SKULLTULA_MAX_PER_PLAYER = BUILDER.comment("Maximum nightly spawn allowance generated for each player.")
                .defineInRange("maximumPerPlayerPerNight", 8, 0, 16);
        BUILDER.pop();
    }

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static boolean isLoaded = false;

    private static int base_player_heath;
    private static boolean enable_temperature;
    private static boolean sea_breeze_boomerang_follows;
    private static boolean boomerangs_turn_back_on_hit;
    private static boolean boomerangs_break_soft_blocks;
    private static boolean boomerangs_activate_levers;
    private static boolean boomerangs_activate_buttons;
    private static boolean boomerangs_activate_pressure_plates;
    private static boolean boomerangs_activate_trip_wires;
    private static boolean song_sheet_consumed;
    private static boolean shock_arrow_creeper;
    private static boolean disable_ancient_arrow_drops;

    @SubscribeEvent
    static void load(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            base_player_heath = BASE_PLAYER_HEALTH.get();
            enable_temperature = ENABLE_TEMPERATURE.get();
            sea_breeze_boomerang_follows = SEA_BREEZE_BOOMERANG_FOLLOWS.get();
            boomerangs_turn_back_on_hit = BOOMERANGS_TURN_BACK_ON_HIT.get();
            boomerangs_break_soft_blocks = BOOMERANGS_BREAK_SOFT_BLOCKS.get();
            boomerangs_activate_levers = BOOMERANGS_ACTIVATE_LEVERS.get();
            boomerangs_activate_buttons = BOOMERANGS_ACTIVATE_BUTTONS.get();
            boomerangs_activate_pressure_plates = BOOMERANGS_ACTIVATE_PRESSURE_PLATES.get();
            boomerangs_activate_trip_wires = BOOMERANGS_ACTIVATE_TRIP_WIRES.get();
            song_sheet_consumed = SONG_SHEET_CONSUMED.get();
            disable_ancient_arrow_drops = disableAncientArrowDrops.get();
            isLoaded = true;
        }
    }

    public static int getBasePlayerHealth() {
        return isLoaded ? base_player_heath : BASE_PLAYER_HEALTH.get();
    }

    public static boolean isTemperatureEnabled() {
        return isLoaded ? enable_temperature : ENABLE_TEMPERATURE.get();
    }

    public static boolean doSeaBreezeBoomerangFollows() {
        return isLoaded ? sea_breeze_boomerang_follows : SEA_BREEZE_BOOMERANG_FOLLOWS.get();
    }

    public static boolean doBoomerangsTurnBackOnHit() {
        return isLoaded ? boomerangs_turn_back_on_hit : BOOMERANGS_TURN_BACK_ON_HIT.get();
    }

    public static boolean doBoomerangsBreakSoftBlocks() {
        return isLoaded ? boomerangs_break_soft_blocks : BOOMERANGS_BREAK_SOFT_BLOCKS.get();
    }

    public static boolean doBoomerangsActivateLevers() {
        return isLoaded ? boomerangs_activate_levers : BOOMERANGS_ACTIVATE_LEVERS.get();
    }

    public static boolean doBoomerangsActivateButtons() {
        return isLoaded ? boomerangs_activate_buttons : BOOMERANGS_ACTIVATE_BUTTONS.get();
    }

    public static boolean doBoomerangsActivatePressurePlates() {
        return isLoaded ? boomerangs_activate_pressure_plates : BOOMERANGS_ACTIVATE_PRESSURE_PLATES.get();
    }

    public static boolean doBoomerangsActivateTripWires() {
        return isLoaded ? boomerangs_activate_trip_wires : BOOMERANGS_ACTIVATE_TRIP_WIRES.get();
    }

    public static boolean isSongSheetConsumed() {
        return isLoaded ? song_sheet_consumed : SONG_SHEET_CONSUMED.get();
    }

    public static boolean shockArrowCreeper() { return shockArrowCreeper.get(); }

    public static boolean disableAncientArrowDrops() {
        return isLoaded ? disable_ancient_arrow_drops : disableAncientArrowDrops.get();
    }

    public static boolean explosivegriefing() { return explosivegriefing.get(); }

    public static boolean isSongOfDoubleTimeEnabled() { return ENABLE_SONG_OF_DOUBLE_TIME.get(); }

    public static boolean isInvertedSongOfTimeEnabled() { return ENABLE_INVERTED_SONG_OF_TIME.get(); }

    public static boolean timeSongsAffectDayNightCycle() { return TIME_SONG_DAY_NIGHT.get(); }

    public static boolean timeSongsAffectCropGrowth() { return TIME_SONG_CROP_GROWTH.get(); }

    public static boolean timeSongsAffectFurnaces() { return TIME_SONG_FURNACES.get(); }

    public static boolean timeSongsAffectBrewingStands() { return TIME_SONG_BREWING_STANDS.get(); }

    public static boolean timeSongsAffectMobSpeedAndAttacks() { return TIME_SONG_MOB_SPEED_AND_ATTACKS.get(); }

    public static boolean timeSongsAffectFireSpread() { return TIME_SONG_FIRE_SPREAD.get(); }

    public static boolean timeSongsAffectItemDespawn() { return TIME_SONG_ITEM_DESPAWN.get(); }

    public static boolean timeSongsAffectWeather() { return TIME_SONG_WEATHER.get(); }

    public static boolean timeSongsAffectRedstone() { return TIME_SONG_REDSTONE.get(); }

    public static boolean timeSongsAffectAnimalGrowth() { return TIME_SONG_ANIMAL_GROWTH.get(); }

    public static FairyOcarinaRestriction getFairyOcarinaRestriction() {
        return FAIRY_OCARINA_RESTRICTION.get();
    }

    public static boolean randomizeDekuBubbleFlightPath() {
        return RANDOMIZE_DEKU_BUBBLE_FLIGHT_PATH.get();
    }

    public static boolean pegasusBootsBreakJarsAndPots() {
        return PEGASUS_BOOTS_BREAK_JARS_AND_POTS.get();
    }

    public static boolean pegasusBootsBreakPlantsAndCrops() {
        return PEGASUS_BOOTS_BREAK_PLANTS_AND_CROPS.get();
    }

    public static boolean goldSkulltulasEnabled() { return ENABLE_GOLD_SKULLTULAS.get(); }
    public static int goldSkulltulaMaxLight() { return GOLD_SKULLTULA_MAX_LIGHT.get(); }
    public static int goldSkulltulaChunkCooldownNights() { return GOLD_SKULLTULA_CHUNK_COOLDOWN_NIGHTS.get(); }
    public static int goldSkulltulaMemoryRadius() { return GOLD_SKULLTULA_MEMORY_RADIUS.get(); }
    public static int goldSkulltulaMinimumSeparation() { return GOLD_SKULLTULA_MIN_SEPARATION.get(); }
    public static int goldSkulltulaMinimumPlayerDistance() { return GOLD_SKULLTULA_MIN_PLAYER_DISTANCE.get(); }
    public static int goldSkulltulaMaximumPlayerDistance() { return GOLD_SKULLTULA_MAX_PLAYER_DISTANCE.get(); }
    public static int goldSkulltulaMinimumPerPlayer() { return GOLD_SKULLTULA_MIN_PER_PLAYER.get(); }
    public static int goldSkulltulaMaximumPerPlayer() { return GOLD_SKULLTULA_MAX_PER_PLAYER.get(); }

    public static boolean canFairyOcarinaApply(OcarinaSong song) {
        if (song == null || song.getRegistryName() == null) {
            return false;
        }
        if (getFairyOcarinaRestriction() == FairyOcarinaRestriction.LIGHT) {
            return !song.requiresOcarinaOfTime();
        }

        return switch (song.getRegistryName().getPath()) {
            case "eponas_song", "song_of_storms", "zeldas_lullaby", "sarias_song" -> true;
            default -> false;
        };
    }
}
