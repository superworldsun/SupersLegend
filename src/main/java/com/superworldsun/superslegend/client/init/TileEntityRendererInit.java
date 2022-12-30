package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.*;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class TileEntityRendererInit {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.PEDESTAL.get(), PedestalRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.FAN.get(), FanRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.SWITCHABLE_FAN.get(), FanRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.SHADOW.get(), ShadowRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.FALSE_SHADOW.get(), FalseShadowRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.HIDDEN_SHADOW.get(), HiddenShadowRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.LIGHT_EMITTER.get(), LightEmitterRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.LIGHT_PRISM.get(), LightPrismRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.OWL_STATUE.get(), OwlStatueRenderer::new);
	}
}
