package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.*;
import com.superworldsun.superslegend.client.render.seeds.*;
import com.superworldsun.superslegend.entities.projectiles.boomerang.*;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class EntityRendererInit {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.TP_BOKOBLIN.get(), TPBokoblinRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.DEKU_SEED.get(), DekuSeedRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MELON_SEED.get(), MelonSeedRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.PUMPKIN_SEED.get(), PumpkinSeedRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.WHEAT_SEED.get(), WheatSeedRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BEETROOT_SEED.get(), BeetrootSeedRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.COCOA_BEAN.get(), CocoaBeanRender::new);
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
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.SPINNER.get(), SpinnerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.HEART.get(), HeartRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MAGIC_JAR.get(), MagicJarRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.LARGE_MAGIC_JAR.get(), LargeMagicJarRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FIREBALL.get(), EmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICEBALL.get(), EmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.GUST.get(), EmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.HOOKSHOT_ENTITY.get(), HookshotRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.LONGSHOT_ENTITY.get(), LongshotRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.CLAWSHOT_ENTITY.get(), ClawshotRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.REGULAR_BOOMERANG.get(), BoomerangRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MAGIC_BOOMERANG.get(), MagicBoomerangRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.WW_BOOMERANG.get(), WWBoomerangRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.GALE_BOOMERANG.get(), GaleBoomerangRender::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MASTERSWORD_SWORD_ENTITY.get(), MasterSwordRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BOMB.get(), BombRender::new);
	}
}
