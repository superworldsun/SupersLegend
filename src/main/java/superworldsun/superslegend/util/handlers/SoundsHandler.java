/*package superworldsun.superslegend.util.handlers;

import java.lang.ref.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundsHandler 
{
	public static SoundEvent ITEM_SELECT_EQUIPED;
	{
		ITEM_SELECT_EQUIPED = registerSound("56sdf");
	}
	
	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}*/
