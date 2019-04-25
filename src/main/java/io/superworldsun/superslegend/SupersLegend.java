package io.superworldsun.superslegend;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

import io.superworldsun.superslegend.lists.*;

@Mod(modid = SupersLegend.MODID, name = SupersLegend.NAME, version = SupersLegend.VERSION)
public class SupersLegend
{
    public static final String MODID = "superslegend";
    public static final String NAME = "SupersLegend";
    public static final String VERSION = "1.0";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Initializing SupersLegend mod...");
    }
    
    @Mod.EventBusSubscriber(modid = SupersLegend.MODID)
    public static class RegistrationHandler {
    
    	/**
		 * Register this mod's {@link Item}s.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			logger.info("Registering all mod items...");
			ItemList.registerItems(event.getRegistry());
		}
    }
}
