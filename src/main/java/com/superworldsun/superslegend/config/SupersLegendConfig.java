package com.superworldsun.superslegend.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class SupersLegendConfig
{
	private static final SupersLegendConfig INSTANCE;

	public static final ForgeConfigSpec SPEC;

	static {
		Pair<SupersLegendConfig, ForgeConfigSpec> specPair =
				new ForgeConfigSpec.Builder().configure(SupersLegendConfig::new);
		INSTANCE = specPair.getLeft();
		SPEC = specPair.getRight();
	}

	//General
	private final ForgeConfigSpec.DoubleValue playerMaxHealth;
	private final ForgeConfigSpec.BooleanValue explosivegriefing;

	private SupersLegendConfig(ForgeConfigSpec.Builder configSpecBuilder) {
		//General
		playerMaxHealth = configSpecBuilder
				.comment("Whether to enable dynamic hand/entity lighting for torches/etc")
				.defineInRange("playerMaxHealth", 20.0D, 1.0D, 20.0D);
		explosivegriefing = configSpecBuilder
				.comment("Whether bombs/etc cause block destruction")
				.define("explosivegriefing", true);
	}

	public static SupersLegendConfig getInstance() {
		return INSTANCE;
	}
	// Query Operations

	//General
	public double playerMaxHealth() { return playerMaxHealth.get(); }
	public boolean explosivegriefing() { return explosivegriefing.get(); }

	////////
	// Modification Operations
	////////
	//General
	public void changeplayerMaxHealth(double newValue) {
		playerMaxHealth.set(newValue);
	}
	public void changeexplosivegriefing(boolean newValue) {
		explosivegriefing.set(newValue);
	}
	public void save() {
		SPEC.save();
	}
}
