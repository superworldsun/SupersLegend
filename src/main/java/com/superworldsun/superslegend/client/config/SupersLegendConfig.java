package com.superworldsun.superslegend.client.config;

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
	private final ForgeConfigSpec.BooleanValue temperature;
	private final ForgeConfigSpec.BooleanValue songSheetConsumed;
	private final ForgeConfigSpec.BooleanValue shockArrowCreeper;
	private final ForgeConfigSpec.DoubleValue playerMaxHealth;
	private final ForgeConfigSpec.BooleanValue explosivegriefing;
	public final ForgeConfigSpec.BooleanValue turnAroundItem;
	public final ForgeConfigSpec.BooleanValue turnAroundMob;
	public final ForgeConfigSpec.BooleanValue turnAroundButton;
	public final ForgeConfigSpec.IntValue RegularBoomerangRange;
	public final ForgeConfigSpec.IntValue RegularBoomerangDamage;
	public final ForgeConfigSpec.BooleanValue RegularBoomerangFollows;
	public final ForgeConfigSpec.IntValue MagicBoomerangRange;
	public final ForgeConfigSpec.IntValue MagicBoomerangDamage;
	public final ForgeConfigSpec.BooleanValue MagicBoomerangFollows;
	public final ForgeConfigSpec.IntValue WWBoomerangRange;
	public final ForgeConfigSpec.IntValue WWBoomerangDamage;
	public final ForgeConfigSpec.BooleanValue WWBoomerangFollows;
	public final ForgeConfigSpec.IntValue GaleBoomerangRange;
	public final ForgeConfigSpec.IntValue GaleBoomerangDamage;
	public final ForgeConfigSpec.BooleanValue GaleBoomerangFollows;
	public final ForgeConfigSpec.BooleanValue breaksTorches;
	public final ForgeConfigSpec.BooleanValue breaksFlowers;
	public final ForgeConfigSpec.BooleanValue breaksTallGrass;
	public final ForgeConfigSpec.BooleanValue activatesLevers;
	public final ForgeConfigSpec.BooleanValue activatesButtons;
	public final ForgeConfigSpec.BooleanValue activatesPressurePlates;
	public final ForgeConfigSpec.BooleanValue activatesTripWire;

	private SupersLegendConfig(ForgeConfigSpec.Builder configSpecBuilder) {
		//General
		playerMaxHealth = configSpecBuilder
				.comment("Players health they will start with. One Minecraft heart is 2, and a full bar is 20. Its Recommended you only use Even numbers.")
				.defineInRange("playerMaxHealth", 20D, 1D, 40D);

		temperature = configSpecBuilder
				.comment("Activate the Temperature System.")
				.define("temperature", true);

		songSheetConsumed = configSpecBuilder
				.comment("Consume the Song Sheet?")
				.define("songSheetConsumed", true);

		shockArrowCreeper = configSpecBuilder
				.comment("Creepers transform into Charged Creepers with Shock Arrows.")
				.define("shockArrowCreeper", true);

		explosivegriefing = configSpecBuilder
				.comment("Whether bombs will cause block destruction")
				.define("explosivegriefing", true);

		RegularBoomerangRange = configSpecBuilder
				.comment("The maximum range of travel before returning to player.")
				.defineInRange("RegularBoomerangRange", 28, 1, 200);
		RegularBoomerangDamage = configSpecBuilder
				.comment("The amount of damage that is done when hitting any living entity.")
				.defineInRange("RegularBoomerangDamage", 5, 1, 500);
		RegularBoomerangFollows = configSpecBuilder
				.comment("The Regular Boomerang will follow your mouse till it hits it's range limit.")
				.define("RegularBoomerangFollows", false);

		MagicBoomerangRange = configSpecBuilder
				.comment("The maximum range of travel before returning to player.")
				.defineInRange("MagicBoomerangRange", 19, 1, 200);
		MagicBoomerangDamage = configSpecBuilder
				.comment("The amount of damage that is done when hitting any living entity.")
				.defineInRange("MagicBoomerangDamage", 6, 1, 500);
		MagicBoomerangFollows = configSpecBuilder
				.comment("The Magic Boomerang will follow your mouse till it hits it's range limit.")
				.define("MagicBoomerangFollows", false);

		WWBoomerangRange = configSpecBuilder
				.comment("The maximum range of travel before returning to player.")
				.defineInRange("WWBoomerangRange", 40, 1, 200);
		WWBoomerangDamage = configSpecBuilder
				.comment("The amount of damage that is done when hitting any living entity.")
				.defineInRange("WWBoomerangDamage", 5, 1, 500);
		WWBoomerangFollows = configSpecBuilder
				.comment("The WW Boomerang will follow your mouse till it hits it's range limit.")
				.define("WWBoomerangFollows", true);

		GaleBoomerangRange = configSpecBuilder
				.comment("The maximum range of travel before returning to player.")
				.defineInRange("GaleBoomerangRange", 40, 1, 200);
		GaleBoomerangDamage = configSpecBuilder
				.comment("The amount of damage that is done when hitting any living entity.")
				.defineInRange("GaleBoomerangDamage", 5, 1, 500);
		GaleBoomerangFollows = configSpecBuilder
				.comment("The Gale Boomerang will follow your mouse till it hits it's range limit.")
				.define("GaleBoomerangFollows", true);

		turnAroundItem = configSpecBuilder
				.comment("Comes back to the player after picking up items.")
				.define("turnAroundItem", true);
		turnAroundMob = configSpecBuilder
				.comment("Comes back to the player after hitting a mob.")
				.define("turnAroundMob", true);
		turnAroundButton = configSpecBuilder
				.comment("Comes back to player after hitting a button.")
				.define("turnAroundButton", true);
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
	public boolean temperature() { return temperature.get(); }
	public boolean shockArrowCreeper() { return shockArrowCreeper.get(); }
	public boolean songSheetConsumed() { return songSheetConsumed.get(); }
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
