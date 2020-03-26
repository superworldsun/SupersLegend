package superworldsun.superslegend.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ref
{
    public static final String MODID = "superslegend";
    public static final Logger LOGGER = LogManager.getLogger();

    private Ref()
    {
        if(Config.CategoryDeveloper.enableLogger.get()) {
            LOGGER.info("Reference called!");
        }
    }
}