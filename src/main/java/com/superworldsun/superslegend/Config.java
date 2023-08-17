package com.superworldsun.superslegend;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD)
public class Config {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private final ForgeConfigSpec.BooleanValue temperature;



	private static final IntValue BASE_PLAYER_HEALTH = BUILDER.defineInRange("Base Player Health", 20, 1, 20);
	//private static final IntValue TEMPATURE = BUILDER.defineInRange("Activate the Temperature System.").define("temperature", true);

	public static int basePlayerHealth;
	public static int Tempsystem;
	
	static final ForgeConfigSpec SPEC = BUILDER.build();

	private Config(ForgeConfigSpec.Builder configSpecBuilder) {
		//General
		temperature = configSpecBuilder.comment("Activate the Temperature System.").define("temperature", true);
	}

	@SubscribeEvent
	static void load(ModConfigEvent event) {
		basePlayerHealth = BASE_PLAYER_HEALTH.get();
		//Tempsystem = TEMPATURE.get();
	}
}
