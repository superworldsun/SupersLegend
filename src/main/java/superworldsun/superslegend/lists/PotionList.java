package superworldsun.superslegend.lists;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.SupersLegend;

public class PotionList {

	public static Potion more_health_potion = null;
	
	
	public static Effect more_health_effect = null;
	public static Effect iron_boots_effect = null;
	public static Effect hover_boots_effect = null;
	public static Effect zoras_grace_effect = null;

	
	/* Create a new Deferred Registry for all our potions and effects to register to
	 * This is called in our Main class and added to the EventBus, which saves us making each class one
	 * Alternatively you could use registry events. Though it doesn't make a massive difference.
	 * Deferred Registries are a new and more efficient way of registering lots of things.
	 * 
	 * For updating to 1.16 you'll need to create method from the Deferred Register class, instead of calling on the 
	 * constructor. (DeferredRegister.create(), instead of a new DeferredRegister()).
	 */
	
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, SupersLegend.modid);

	public static final RegistryObject<Effect> MORE_HEALTH_EFFECT = EFFECTS.register("more_health", () -> new MoreHealthEffect(EffectType.BENEFICIAL, 0xd4ff00));
	
	public static final RegistryObject<Effect> IRON_BOOTS_EFFECT = EFFECTS.register("iron_boots", () -> new IronBootsEffect(EffectType.BENEFICIAL, 0xd4ff10));
	
	public static final RegistryObject<Effect> HOVER_BOOTS_EFFECT = EFFECTS.register("hover_boots", () -> new HoverBootsEffect(EffectType.BENEFICIAL, 0xd4ff10));
	
	public static final RegistryObject<Effect> ZORAS_GRACE_EFFECT = EFFECTS.register("zoras_grace", () -> new ZorasGraceEffect(EffectType.BENEFICIAL, 0xd4ff10));

	
	
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, SupersLegend.modid);
	
	public static final RegistryObject<Potion> MORE_HEALTH_POTION = POTIONS.register("more_health", () -> new Potion(new EffectInstance(MORE_HEALTH_EFFECT.get(), 3600)));
	
	//public static final RegistryObject<Potion> SIZE_POTION = POTIONS.register("size", () -> new Potion(new EffectInstance(SIZE_EFFECT.get(), 3600)));

	public static class MoreHealthEffect extends Effect {

		public MoreHealthEffect(EffectType typeIn, int liquidColorIn) {
			super(typeIn, liquidColorIn);
		}
		
	}
	
	public static class IronBootsEffect extends Effect {

		public IronBootsEffect(EffectType typeIn, int liquidColorIn) {
			super(typeIn, liquidColorIn);
		}
	}
	
	public static class HoverBootsEffect extends Effect {

		public HoverBootsEffect(EffectType typeIn, int liquidColorIn) {
			super(typeIn, liquidColorIn);
		}
	}
	
	public static class ZorasGraceEffect extends Effect {

		public ZorasGraceEffect(EffectType typeIn, int liquidColorIn) {
			super(typeIn, liquidColorIn);
		}
	}
}