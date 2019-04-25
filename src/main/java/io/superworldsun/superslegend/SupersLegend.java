package io.superworldsun.superslegend;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import io.superworldsun.superslegend.util.Reference;

import org.apache.logging.log4j.Logger;

import io.superworldsun.superslegend.lists.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SupersLegend
{
	
	@Instance
	public static SupersLegend instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	


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
    
    @Mod.EventBusSubscriber(modid = Reference.MODID)
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
