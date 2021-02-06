package superworldsun.superslegend.config;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.item.ItemTier;
import net.minecraftforge.common.ForgeConfigSpec;

public final class ToolsConfig {

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue turnAroundItem;
        public final ForgeConfigSpec.BooleanValue turnAroundMob;
        public final ForgeConfigSpec.BooleanValue breaksTorches;
        public final ForgeConfigSpec.BooleanValue breaksPlants;
        public final ForgeConfigSpec.BooleanValue hitsButtons;
        public final ForgeConfigSpec.BooleanValue turnAroundButton;
        public final ForgeConfigSpec.IntValue RegularBoomerangRange;
        public final ForgeConfigSpec.IntValue RegularBoomerangDamage;
        public final ForgeConfigSpec.BooleanValue RegularBoomerangFollows;
        public final ForgeConfigSpec.BooleanValue boomerangsEnabled;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Parts");
            boomerangsEnabled = builder.comment("Set this to true if you would like boomerangs to be craftable and found in the creative tab.").define("boomerangsEnabled", true);
            builder.pop();

            builder.push("Boomerangs");
            turnAroundItem = builder.comment("Set this to true if you would like boomerangs to turn around after they have picked up items.").define("turnAroundItem", true);
            turnAroundMob = builder.comment("Set this to true if you would like boomerangs to turn around after they have hit a mob.").define("turnAroundMob", true);
            turnAroundButton = builder.comment("Set this to false if you would like boomerangs to not turn around after they have hit a button or a lever.").define("turnAroundButton", true);
            breaksTorches = builder.comment("Set this to true if you would like boomerangs to be able to break torches.").define("breaksTorches", false);
            breaksPlants = builder.comment("Set this to true if you would like boomerangs to be able to break plants.").define("breaksPlants", true);
            hitsButtons = builder.comment("Set this to false if you would like boomerangs to not be able to hit buttons or levers.").define("hitsButtons", true);

            RegularBoomerangRange = builder.comment("The maximum range away from the player the boomerang plus will travel before turning around.").defineInRange("BoomerangPlusRange", 30, 1, 200);
            RegularBoomerangDamage = builder.comment("The amount of damage the boomerang plus does to mobs.").defineInRange("boomerangPlusDamage", 5, 1, 500);
            RegularBoomerangFollows = builder.comment("Set to true if you would like the boomerang plus to follow where the player is looking.").define("boomerangPlusFollows", true);
            builder.pop();
        }
    }
}
