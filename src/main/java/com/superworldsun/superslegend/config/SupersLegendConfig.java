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
	public final ForgeConfigSpec.BooleanValue turnAroundItem;
	public final ForgeConfigSpec.BooleanValue turnAroundMob;
	public final ForgeConfigSpec.BooleanValue turnAroundButton;
	public final ForgeConfigSpec.IntValue RegularBoomerangRange;
	public final ForgeConfigSpec.IntValue RegularBoomerangDamage;
	public final ForgeConfigSpec.BooleanValue RegularBoomerangFollows;
	public final ForgeConfigSpec.BooleanValue breaksTorches;
	public final ForgeConfigSpec.BooleanValue breaksFlowers;
	//public final ForgeConfigSpec.BooleanValue breaksGrass;
	public final ForgeConfigSpec.BooleanValue breaksTallGrass;
	public final ForgeConfigSpec.BooleanValue activatesLevers;
	public final ForgeConfigSpec.BooleanValue activatesButtons;
	public final ForgeConfigSpec.BooleanValue activatesPressurePlates;
	public final ForgeConfigSpec.BooleanValue activatesTripWire;

	private SupersLegendConfig(ForgeConfigSpec.Builder configSpecBuilder) {
		//General
		playerMaxHealth = configSpecBuilder
				.comment("Whether to enable dynamic hand/entity lighting for torches/etc")
				.defineInRange("playerMaxHealth", 20.0D, 1.0D, 20.0D);
		explosivegriefing = configSpecBuilder
				.comment("Whether bombs/etc cause block destruction")
				.define("explosivegriefing", true);
		turnAroundItem = configSpecBuilder
				.comment("Comes back to the player after picking up items.")
				.define("turnAroundItem", true);
		turnAroundMob = configSpecBuilder
				.comment("Comes back to the player after hitting a mob.")
				.define("turnAroundMob", true);
		turnAroundButton = configSpecBuilder
				.comment("Comes back to player after hitting a button.")
				.define("turnAroundButton", true);
		RegularBoomerangRange = configSpecBuilder
				.comment("The maximum range of travel before returning to player.")
				.defineInRange("RegularBoomerangRange", 30, 1, 200);
		RegularBoomerangDamage = configSpecBuilder
				.comment("The amount of damage that is done when hitting any living entity.")
				.defineInRange("RegularBoomerangDamage", 5, 1, 500);
		RegularBoomerangFollows = configSpecBuilder
				.comment("The Regular Boomerang will follow your mouse till it hits it's range limit.")
				.define("RegularBoomerangFollows", true);
		breaksTorches = configSpecBuilder
				.comment("Can boomerang break torches.")
				.define("breaksTorches", true);
		breaksFlowers = configSpecBuilder
				.comment("Can boomerang break Flowers.")
				.define("breaksFlowers", true);
		breaksTallGrass = configSpecBuilder
				.comment("Can boomerang break Tall Grass.")
				.define("breaksTallGrass", true);
		activatesLevers = configSpecBuilder
				.comment("Can boomerang switch levers on and off.")
				.define("activatesLevers", true);
		activatesButtons = configSpecBuilder
				.comment("Can boomerang activate/push buttons.")
				.define("activatesButtons", true);
		activatesPressurePlates = configSpecBuilder
				.comment("Can boomerang activate regular and lightweight pressure plates.")
				.define("activatesPressurePlates", true);
		activatesTripWire = configSpecBuilder
				.comment("Can boomerang activate/trigger tripwire(s).")
				.define("activatesTripWire", true);


	}

	public static SupersLegendConfig getInstance() {
		return INSTANCE;
	}
	// Query Operations

	//General
	public double playerMaxHealth() { return playerMaxHealth.get(); }
	public boolean explosivegriefing() { return explosivegriefing.get(); }
	public boolean turnAroundItem() { return turnAroundItem.get(); }
	public boolean turnAroundMob() { return turnAroundMob.get(); }
	public boolean turnAroundButton() { return turnAroundButton.get(); }
	public int RegularBoomerangRange() { return RegularBoomerangRange.get(); }
	public int RegularBoomerangDamage() { return RegularBoomerangDamage.get(); }
	public boolean RegularBoomerangFollows() { return RegularBoomerangFollows.get(); }
	public boolean breaksTorches() { return breaksTorches.get(); }
	public boolean breaksFlowers() { return breaksFlowers.get(); }
	//public final ForgeConfigSpec.BooleanValue breaksGrass() { return RegularBoomerangFollows.get(); }
	public boolean breaksTallGrass() { return breaksTallGrass.get(); }
	public boolean activatesLevers() { return activatesLevers.get(); }
	public boolean activatesButtons() { return activatesButtons.get(); }
	public boolean activatesPressurePlates() { return activatesPressurePlates.get(); }
	public boolean activatesTripWire() { return activatesTripWire.get(); }
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
