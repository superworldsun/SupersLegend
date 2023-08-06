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

	private static final IntValue BASE_PLAYER_HEALTH = BUILDER.defineInRange("Base Player Health", 10, 1, 20);
	public static int basePlayerHealth;
	
	static final ForgeConfigSpec SPEC = BUILDER.build();

	@SubscribeEvent
	static void load(ModConfigEvent event) {
		basePlayerHealth = BASE_PLAYER_HEALTH.get();
	}
}
