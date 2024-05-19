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
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_TURN_BACK_ON_HIT = BUILDER.define("Boomerangs turn back on hit", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_BREAK_SOFT_BLOCKS = BUILDER.define("Boomerangs break soft blocks", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_LEVERS = BUILDER.define("Boomerangs activate levers", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_BUTTONS = BUILDER.define("Boomerangs activate buttons", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_PRESSURE_PLATES = BUILDER.define("Boomerangs activate pressure plates", true);
    private static final ForgeConfigSpec.BooleanValue BOOMERANGS_ACTIVATE_TRIP_WIRES = BUILDER.define("Boomerangs activate trip wires", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int base_player_heath;
    public static boolean enable_temperature;
    public static boolean boomerangs_turn_back_on_hit;
    public static boolean boomerangs_break_soft_blocks;
    public static boolean boomerangs_activate_levers;
    public static boolean boomerangs_activate_buttons;
    public static boolean boomerangs_activate_pressure_plates;
    public static boolean boomerangs_activate_trip_wires;

    @SubscribeEvent
    static void load(ModConfigEvent event) {
        base_player_heath = BASE_PLAYER_HEALTH.get();
        enable_temperature = ENABLE_TEMPERATURE.get();
        boomerangs_turn_back_on_hit = BOOMERANGS_TURN_BACK_ON_HIT.get();
        boomerangs_break_soft_blocks = BOOMERANGS_BREAK_SOFT_BLOCKS.get();
        boomerangs_activate_levers = BOOMERANGS_ACTIVATE_LEVERS.get();
        boomerangs_activate_buttons = BOOMERANGS_ACTIVATE_BUTTONS.get();
        boomerangs_activate_pressure_plates = BOOMERANGS_ACTIVATE_PRESSURE_PLATES.get();
        boomerangs_activate_trip_wires = BOOMERANGS_ACTIVATE_TRIP_WIRES.get();
    }
}