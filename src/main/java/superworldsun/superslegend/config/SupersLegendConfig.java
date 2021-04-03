package superworldsun.superslegend.config;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.item.ItemTier;
import net.minecraftforge.common.ForgeConfigSpec;
import superworldsun.superslegend.items.OcarinaOfTime;

public final class SupersLegendConfig {

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        // Regular Boomerang Public Finals
        //public final ForgeConfigSpec.BooleanValue boomerangsEnabled;
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

        // Bomb Public Finals
        //public final ForgeConfigSpec.BooleanValue BombEnabled;
        //public final ForgeConfigSpec.BooleanValue OcarinaOfTimeEnabled;


        public Common(ForgeConfigSpec.Builder builder) {

            //Boomerang Configuration options
            builder.push("Regular Boomerang Config Options");
            //boomerangsEnabled = builder.comment("Set this to true if you would like boomerangs to be craftable and found in the creative tab.").define("boomerangsEnabled", true);

            // Boomerang Functions
            turnAroundItem = builder.comment("Comes back to the player after picking up items.").define("turnAroundItem", true);

            turnAroundMob = builder.comment("Comes back to the player after hitting a mob.").define("turnAroundMob", true);

            turnAroundButton = builder.comment("Comes back to player after hitting a button.").define("turnAroundButton", true);

            RegularBoomerangRange = builder.comment("The maximum range of travel before returning to player.").defineInRange("RegularBoomerangRange", 30, 1, 200);

            RegularBoomerangDamage = builder.comment("The amount of damage that is done when hitting any living entity.").defineInRange("RegularBoomerangDamage", 5, 1, 500);

            RegularBoomerangFollows = builder.comment("The Regular Boomerang will follow your mouse till it hits it's range limit.").define("RegularBoomerangFollows", true);
            // Boomerang Break Capabilities
            breaksTorches = builder.comment("Can boomerang break torches.").define("breaksTorches", true);

            breaksFlowers = builder.comment("Can boomerang break Flowers.").define("breaksFlowers", true);

            //breaksGrass = builder.comment("Can boomerang break Glass.").define("breaksGrass", true);

            breaksTallGrass = builder.comment("Can boomerang break Tall Grass.").define("breaksTallGrass", true);
            // Boomerang Activate/Redstone Capabilities

            activatesLevers = builder.comment("Can boomerang switch levers on and off.").define("activatesLevers", true);

            activatesButtons = builder.comment("Can boomerang activate/push buttons.").define("activatesButtons", true);

            activatesPressurePlates = builder.comment("Can boomerang activate regular and lightweight pressure plates.").define("activatesPressurePlates", true);

            activatesTripWire = builder.comment("Can boomerang activate/trigger tripwire(s).").define("activatesTripWire", true);

            builder.pop();

            //Boomerang Configuration options
            /*builder.push("Bomb Options");
            BombEnabled = builder.comment("Enable or Disable the Bomb. 'True'=Enabled, 'False'=Disabled").define("BombEnabled", true);

            builder.push("OcarinaOfTime Options");
            OcarinaOfTimeEnabled = builder.comment("Set this to true if you would like OcarinaOfTime to be craftable and found in the creative tab.").define("OcarinaOfTimeEnabled", true);
*/
        }
    }
}
