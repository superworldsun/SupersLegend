package superworldsun.superslegend.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.config.Ref;


//MANUALLY IMPORT EACH SOUND EFFECT HERE <<<<<<>>>>>>

import static superworldsun.superslegend.init.SoundInit.JAWA;
import static superworldsun.superslegend.init.SoundInit.SUNS_SONG;
import static superworldsun.superslegend.init.SoundInit.NAYRUS_LOVE_CAST;
import static superworldsun.superslegend.init.SoundInit.ZELDA_ERROR;
import static superworldsun.superslegend.init.SoundInit.DASH;
import static superworldsun.superslegend.init.SoundInit.MAGIC_CAPE_OFF;
import static superworldsun.superslegend.init.SoundInit.MAGIC_CAPE_ON;

//MANUALLY IMPORT EACH SOUND EFFECT HERE <<<<<<>>>>>>

//MANUALLY IMPORT EACH SOUND EFFECT HERE <<<<<<>>>>>>

//MANUALLY IMPORT EACH SOUND EFFECT HERE <<<<<<>>>>>>

//MANUALLY IMPORT EACH SOUND EFFECT HERE <<<<<<>>>>>>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundHandler
{
	
	
    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
    {
    	NAYRUS_LOVE_CAST = registerSound("nayrus_love_cast");
    	JAWA = registerSound("jawa");
    	ZELDA_ERROR = registerSound("zelda_error");
    	DASH = registerSound("dash");
    	MAGIC_CAPE_OFF = registerSound("magic_cape_off");
    	MAGIC_CAPE_ON = registerSound("magic_cape_on");
    	
    	
    	SUNS_SONG = registerSound("suns_song");
        
    }

    
    
    private static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Ref.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}