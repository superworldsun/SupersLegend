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
import static superworldsun.superslegend.init.SoundInit.LENS_OF_TRUTH_ON;
import static superworldsun.superslegend.init.SoundInit.RUPEE_GREEN;
import static superworldsun.superslegend.init.SoundInit.RUPEE_BLUE;
import static superworldsun.superslegend.init.SoundInit.RUPEE_RED;
import static superworldsun.superslegend.init.SoundInit.RUPEE_SILVER;
import static superworldsun.superslegend.init.SoundInit.BOOK_OF_MUDORA;
import static superworldsun.superslegend.init.SoundInit.ARROW_HIT_FIRE;
import static superworldsun.superslegend.init.SoundInit.ARROW_HIT_ICE;
import static superworldsun.superslegend.init.SoundInit.ARROW_HIT_SHOCK;
import static superworldsun.superslegend.init.SoundInit.ARROW_HIT_ANCIENT;
import static superworldsun.superslegend.init.SoundInit.BITBOW_ARROW;
import static superworldsun.superslegend.init.SoundInit.HOVER_BOOTS;
import static superworldsun.superslegend.init.SoundInit.BOOMERANG_FLY_LOOP;
import static superworldsun.superslegend.init.SoundInit.BOOMERANG_THROW;
import static superworldsun.superslegend.init.SoundInit.BOMB_FUSE;
import static superworldsun.superslegend.init.SoundInit.BOMB_DEFUSE;
import static superworldsun.superslegend.init.SoundInit.FAIRY_HEAL_ON_TOUCH;
import static superworldsun.superslegend.init.SoundInit.FAIRY_TWINKLE;
import static superworldsun.superslegend.init.SoundInit.POE_LAUGH;


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
    	LENS_OF_TRUTH_ON = registerSound("lens_of_truth_on");
    	RUPEE_GREEN = registerSound("rupee_green");
    	RUPEE_BLUE = registerSound("rupee_blue");
    	RUPEE_RED = registerSound("rupee_red");
    	RUPEE_SILVER = registerSound("rupee_silver");
    	BOOK_OF_MUDORA = registerSound("book_of_mudora");
    	ARROW_HIT_FIRE = registerSound("arrow_hit_fire");
    	ARROW_HIT_ICE = registerSound("arrow_hit_ice");
    	ARROW_HIT_SHOCK = registerSound("arrow_hit_shock");
    	ARROW_HIT_ANCIENT = registerSound("arrow_hit_ancient");
		BITBOW_ARROW = registerSound("bitbow_arrow");
    	HOVER_BOOTS = registerSound("hover_boots");
		BOOMERANG_FLY_LOOP = registerSound("boomerang_fly_loop");
		BOOMERANG_THROW = registerSound("boomerang_throw");
		BOMB_FUSE = registerSound("bomb_fuse");
		BOMB_DEFUSE = registerSound("bomb_defuse");
    	FAIRY_HEAL_ON_TOUCH = registerSound("fairy_heal_on_touch");
		FAIRY_TWINKLE = registerSound("fairy_twinkle");
		POE_LAUGH = registerSound("poe_laugh");
    	
    	
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