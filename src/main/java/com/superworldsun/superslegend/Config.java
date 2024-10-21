package com.superworldsun.superslegend;

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
    private static final ForgeConfigSpec.BooleanValue explosivegriefing =  BUILDER.comment("Whether bombs & bomb arrows will cause block destruction").define("explosivegriefing", true);
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

    public static boolean explosivegriefing() { return explosivegriefing.get(); }
}