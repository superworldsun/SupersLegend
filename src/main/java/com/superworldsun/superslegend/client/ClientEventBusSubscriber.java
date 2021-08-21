package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.*;
import com.superworldsun.superslegend.client.screen.*;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ContainerInit;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.FluidInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
		RenderTypeLookup.setRenderLayer(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.GRATE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.SPIKES_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.GRASS_PATCH_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.ODD_MUSHROOM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.MAGIC_MUSHROOM.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.SHADOW_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.FALSE_SHADOW_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.HIDDEN_SHADOW_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.TOMBSTONE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.STONE_PATH_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.STONE_TILE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.RUST_PLATE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInit.RUST_BUTTON.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInit.DINS_FLAME.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInit.FARORES_FLAME.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInit.NAYRUS_FLAME.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(FluidInit.MUD_FLOWING.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(FluidInit.MUD_SOURCE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(FluidInit.POISON_FLOWING.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(FluidInit.POISON_SOURCE.get(), RenderType.translucent());

		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.PELLET.get(), PelletRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.POISON_ARROW.get(), PoisonArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FIRE_ARROW.get(), FireArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_ARROW.get(), IceArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.SHOCK_ARROW.get(), ShockArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BOMB_ARROW.get(), BombArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ANCIENT_ARROW.get(), AncientArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.SILVER_ARROW.get(), SilverArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_BEAM.get(), IceBeamRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MAGIC_LIGHT_ARROW.get(), MagicLightArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MAGIC_ICE_ARROW.get(), MagicIceArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MAGIC_FIRE_ARROW.get(), MagicFireArrowRender::new);
		
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.PEDESTAL.get(), PedestalRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.FAN.get(), FanRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.SWITCHABLE_FAN.get(), FanRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.FALSE_SHADOW.get(), FalseShadowRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.HIDDEN_SHADOW.get(), HiddenShadowRenderer::new);
		
		ScreenManager.register(ContainerInit.BAG.get(), BagScreen::new);
		ScreenManager.register(ContainerInit.LETTER.get(), LetterScreen::new);
		ScreenManager.register(ContainerInit.SMALL_QUIVER.get(), SmallQuiverScreen::new);
		ScreenManager.register(ContainerInit.MEDIUM_QUIVER.get(), MediumQuiverScreen::new);
		ScreenManager.register(ContainerInit.BIG_QUIVER.get(), BigQuiverScreen::new);
		ScreenManager.register(ContainerInit.SMALL_BOMB_BAG.get(), SmallBombBagScreen::new);
		//ScreenManager.register(ContainerInit.MEDIUM_BOMB_BAG.get(), MediumQuiverScreen::new);
		//ScreenManager.register(ContainerInit.BIG_BOMB_BAG.get(), BigQuiverScreen::new);
		ScreenManager.register(ContainerInit.POSTBOX.get(), PostboxScreen::new);
	}
}
