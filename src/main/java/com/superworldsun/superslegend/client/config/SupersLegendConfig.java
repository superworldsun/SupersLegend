package com.superworldsun.superslegend.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SupersLegendConfig {
    public static final SupersLegendConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<SupersLegendConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SupersLegendConfig::new);
        INSTANCE = specPair.getLeft();
        SPEC = specPair.getRight();
    }

    // General
    private final ForgeConfigSpec.DoubleValue playerMaxHealth;
    private final ForgeConfigSpec.BooleanValue temperature;
    private final ForgeConfigSpec.BooleanValue songSheetConsumed;
    private final ForgeConfigSpec.BooleanValue shockArrowCreeper;
    private final ForgeConfigSpec.BooleanValue explosivegriefing;
    private final ForgeConfigSpec.BooleanValue disableAncientArrowDrops;

    // Boomerang settings
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
//    public final ForgeConfigSpec.IntValue GaleBoomerangRange;
//    public final ForgeConfigSpec.IntValue GaleBoomerangDamage;
//    public final ForgeConfigSpec.BooleanValue GaleBoomerangFollows;
    public final ForgeConfigSpec.BooleanValue breaksTorches;
    public final ForgeConfigSpec.BooleanValue breaksFlowers;
    public final ForgeConfigSpec.BooleanValue breaksTallGrass;
    public final ForgeConfigSpec.BooleanValue activatesLevers;
    public final ForgeConfigSpec.BooleanValue activatesButtons;
    public final ForgeConfigSpec.BooleanValue activatesPressurePlates;
    public final ForgeConfigSpec.BooleanValue activatesTripWire;

    private SupersLegendConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");

        playerMaxHealth = builder
                .comment("Players health they will start with. One Minecraft heart is 2, and a full bar is 20. Its Recommended you only use Even numbers.")
                .defineInRange("playerMaxHealth", 20D, 1D, 40D);

        temperature = builder
                .comment("Activate the Temperature System.")
                .define("temperature", true);

        songSheetConsumed = builder
                .comment("Consume the Song Sheet?")
                .define("songSheetConsumed", true);

        shockArrowCreeper = builder
                .comment("Creepers transform into Charged Creepers with Shock Arrows.")
                .define("shockArrowCreeper", true);

        disableAncientArrowDrops = builder
                .comment("If true, entities killed by Ancient Arrows will not drop items.")
                .define("disableAncientArrowDrops", true);

        explosivegriefing = builder
                .comment("Whether bombs & bomb arrows will cause block destruction")
                .define("explosivegriefing", true);

        builder.pop();

        builder.push("Boomerang Settings");

        RegularBoomerangRange = builder
                .comment("The maximum range of travel before returning to player.")
                .defineInRange("RegularBoomerangRange", 28, 1, 200);
        RegularBoomerangDamage = builder
                .comment("The amount of damage that is done when hitting any living entity.")
                .defineInRange("RegularBoomerangDamage", 5, 1, 500);
        RegularBoomerangFollows = builder
                .comment("The Regular Boomerang will follow your mouse till it hits it's range limit.")
                .define("RegularBoomerangFollows", false);

        MagicBoomerangRange = builder
                .comment("The maximum range of travel before returning to player.")
                .defineInRange("MagicBoomerangRange", 19, 1, 200);
        MagicBoomerangDamage = builder
                .comment("The amount of damage that is done when hitting any living entity.")
                .defineInRange("MagicBoomerangDamage", 6, 1, 500);
        MagicBoomerangFollows = builder
                .comment("The Magic Boomerang will follow your mouse till it hits it's range limit.")
                .define("MagicBoomerangFollows", false);

        WWBoomerangRange = builder
                .comment("The maximum range of travel before returning to player.")
                .defineInRange("WWBoomerangRange", 40, 1, 200);
        WWBoomerangDamage = builder
                .comment("The amount of damage that is done when hitting any living entity.")
                .defineInRange("WWBoomerangDamage", 5, 1, 500);
        WWBoomerangFollows = builder
                .comment("The WW Boomerang will follow your mouse till it hits it's range limit.")
                .define("WWBoomerangFollows", true);

//        GaleBoomerangRange = builder
//                .comment("The maximum range of travel before returning to player.")
//                .defineInRange("GaleBoomerangRange", 40, 1, 200);
//        GaleBoomerangDamage = builder
//                .comment("The amount of damage that is done when hitting any living entity.")
//                .defineInRange("GaleBoomerangDamage", 5, 1, 500);
//        GaleBoomerangFollows = builder
//                .comment("The Gale Boomerang will follow your mouse till it hits it's range limit.")
//                .define("GaleBoomerangFollows", true);

        turnAroundItem = builder
                .comment("Comes back to the player after picking up items.")
                .define("turnAroundItem", true);
        turnAroundMob = builder
                .comment("Comes back to the player after hitting a mob.")
                .define("turnAroundMob", true);
        turnAroundButton = builder
                .comment("Comes back to player after hitting a button.")
                .define("turnAroundButton", true);
        breaksTorches = builder
                .comment("Can boomerang break torches.")
                .define("breaksTorches", true);
        breaksFlowers = builder
                .comment("Can boomerang break Flowers.")
                .define("breaksFlowers", true);
        breaksTallGrass = builder
                .comment("Can boomerang break Tall Grass.")
                .define("breaksTallGrass", true);
        activatesLevers = builder
                .comment("Can boomerang switch levers on and off.")
                .define("activatesLevers", true);
        activatesButtons = builder
                .comment("Can boomerang activate/push buttons.")
                .define("activatesButtons", true);
        activatesPressurePlates = builder
                .comment("Can boomerang activate regular and lightweight pressure plates.")
                .define("activatesPressurePlates", true);
        activatesTripWire = builder
                .comment("Can boomerang activate/trigger tripwire(s).")
                .define("activatesTripWire", true);

        builder.pop();
    }

    public static SupersLegendConfig getInstance() {
        return INSTANCE;
    }

    // Query Operations
    public double playerMaxHealth() { return playerMaxHealth.get(); }
    public boolean temperature() { return temperature.get(); }
    public boolean songSheetConsumed() { return songSheetConsumed.get(); }
    public boolean shockArrowCreeper() { return shockArrowCreeper.get(); }
    public boolean explosivegriefing() { return explosivegriefing.get(); }
    public boolean disableAncientArrowDrops() { return disableAncientArrowDrops.get(); }
    public boolean turnAroundItem() { return turnAroundItem.get(); }
    public boolean turnAroundMob() { return turnAroundMob.get(); }
    public boolean turnAroundButton() { return turnAroundButton.get(); }
    public int RegularBoomerangRange() { return RegularBoomerangRange.get(); }
    public int RegularBoomerangDamage() { return RegularBoomerangDamage.get(); }
    public boolean RegularBoomerangFollows() { return RegularBoomerangFollows.get(); }
    public boolean breaksTorches() { return breaksTorches.get(); }
    public boolean breaksFlowers() { return breaksFlowers.get(); }
    public boolean breaksTallGrass() { return breaksTallGrass.get(); }
    public boolean activatesLevers() { return activatesLevers.get(); }
    public boolean activatesButtons() { return activatesButtons.get(); }
    public boolean activatesPressurePlates() { return activatesPressurePlates.get(); }
    public boolean activatesTripWire() { return activatesTripWire.get(); }

    // Modification Operations
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