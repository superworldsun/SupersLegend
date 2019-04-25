package io.superworldsun.superslegend;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SupersLegend.MODID, name = SupersLegend.NAME, version = SupersLegend.VERSION)
public class SupersLegend
{
    public static final String MODID = "superslegend";
    public static final String NAME = "SupersLegend";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("Initializing SupersLegend mod...");
    }
}
