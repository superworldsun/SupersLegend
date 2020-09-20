package superworldsun.superslegend.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.ConfigReloading;

import static net.minecraftforge.fml.Logging.CORE;
import static net.minecraftforge.fml.loading.LogMarkers.FORGEMOD;

@Mod.EventBusSubscriber
public class Config 
{
	private static final Builder COMMON_BUILDER = new Builder();
    private static final Builder CLIENT_BUILDER = new Builder();



    public static final ForgeConfigSpec COMMON_CONFIG = COMMON_BUILDER.build();
    public static final ForgeConfigSpec CLIENT_CONFIG = CLIENT_BUILDER.build();
	
	public static final class CategoryDeveloper {

        public static ForgeConfigSpec.BooleanValue enableLogger;

        private CategoryDeveloper(){
            COMMON_BUILDER.comment("Developer Settings").push("DEVELOPER");

            enableLogger = COMMON_BUILDER
                    .comment("Enable / Disable LogManager logging (DEV)")
                    .translation("codenamerevy.config.logging")
                    .define("logger", false);
            COMMON_BUILDER.pop();
        }
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        Ref.LOGGER.debug(FORGEMOD, "Loaded {} config file {}", Ref.MODID, configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ConfigReloading configEvent) {
        Ref.LOGGER.fatal(CORE, "{} config just got changed on the file system!", Ref.MODID);
    }
}