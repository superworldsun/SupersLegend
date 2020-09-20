package superworldsun.superslegend.lists;

import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import superworldsun.superslegend.SupersLegend.RegistryEvents;
import superworldsun.superslegend.objects.fluids.FluidPoison;

public class FluidList {
	
	public static FluidPoison.Source poison = null;
	public static FluidPoison.Flowing flowing_poison = null;

	public static class Tags 
	{
		
		public static final Tag<Fluid> POISON = new FluidTags.Wrapper(RegistryEvents.location("poison"));
		
	}
	
}
