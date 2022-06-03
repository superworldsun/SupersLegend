package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class DarkWorldForestFogEvents
{
	@SubscribeEvent
	public static void onFogColors(EntityViewRenderEvent.FogColors event)
	{
		Biome biome = event.getInfo().getEntity().level.getBiome(event.getInfo().getEntity().blockPosition());
		
		if (event.getInfo().getEntity().level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biome).toString().equals("superslegend:dark_world_forest"))
		{
			event.setRed(0.92F);
			event.setGreen(0.95F);
			event.setBlue(0.95F);
		}
	}
	
	@SubscribeEvent
	public static void onFogDensity(EntityViewRenderEvent.FogDensity event)
	{
		Biome biome = event.getInfo().getEntity().level.getBiome(event.getInfo().getEntity().blockPosition());
		
		if (event.getInfo().getEntity().level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biome).toString().equals("superslegend:dark_world_forest"))
		{
			event.setDensity(0.01f);
			event.setCanceled(true);
		}
	}
}
