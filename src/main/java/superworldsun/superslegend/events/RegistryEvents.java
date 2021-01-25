package superworldsun.superslegend.events;

//import net.minecraft.arrows.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.PotionList;

public class RegistryEvents {

	
	// TODO , Unsure what this is, Need to attend to this when possible.
	/*@SubscribeEvent
	public static void registerPotions(final RegistryEvent.Register<Potion> event)
	{
		
		event.getRegistry().registerAll
		(
				PotionList.more_health_potion = new Potion(new EffectInstance(PotionList.more_health_effect, 3600)).setRegistryName(Location("more_health"))
		);
		
	}
	
	
	@SubscribeEvent
	public static void registerEffects(final RegistryEvent.Register<Effect> event)
	{
		
		event.getRegistry().registerAll
		
		(
				PotionList.more_health_effect = new PotionList.MoreHealthEffect(EffectType.BENEFICIAL, 0xd4FF00).addAttributesModifier(SharedMonsterAttributes.MAX_HEALTH, "55FCED67-E92A-486E-9800-B47F202C4386", (double)0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL).setRegistryName(Location("more_health"))
		);
		
	}*/


	private static String Location(String string) {
		return null;
	}
	
	
	
	
	
	
	
	
	
}
