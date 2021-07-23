package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.*;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber
{
	@SubscribeEvent
	public static void onStaticClientSetup(FMLClientSetupEvent event)
	{
		RenderTypeLookup.setRenderLayer(BlockInit.CHAIN_LINK_FENCE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.DEKU_FLOWER_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.GRATE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.SPIKES_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.GRASS_PATCH_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.HIDDEN_SHADOW_BLOCK.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInit.TOMBSTONE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.STONE_PATH_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.STONE_TILE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.RUST_PLATE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInit.RUST_BUTTON.get(), RenderType.translucent());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.POISON_ARROW.get(), PoisonArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FIRE_ARROW.get(), FireArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_ARROW.get(), IceArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.SHOCK_ARROW.get(), ShockArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BOMB_ARROW.get(), BombArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ANCIENT_ARROW.get(), AncientArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.SILVER_ARROW.get(), SilverArrowRender::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_BEAM.get(), IceBeamRender::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.LIGHT_ARROW.get(), LightArrowRender::new);
	}
}
