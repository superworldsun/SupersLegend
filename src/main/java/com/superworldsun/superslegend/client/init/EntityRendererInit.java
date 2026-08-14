package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.arrows.*;
import com.superworldsun.superslegend.client.render.boomerang.BoomerangRenderer;
import com.superworldsun.superslegend.client.render.entites.*;
import com.superworldsun.superslegend.client.render.hookshot.HookshotRender;
import com.superworldsun.superslegend.client.render.hookshot.LongshotRender;
import com.superworldsun.superslegend.client.render.magic.FireBallRenderer;
import com.superworldsun.superslegend.client.render.magic.DekuMagicBubbleRenderer;
import com.superworldsun.superslegend.client.render.magic.IceBallRenderer;
import com.superworldsun.superslegend.client.render.player.DekuFlowerGliderRenderLayer;
import com.superworldsun.superslegend.client.render.seeds.*;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;


@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRendererInit {

    @SubscribeEvent
    public static void initRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeInit.FIRE_ARROW.get(), FireArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.ICE_ARROW.get(), IceArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.SHOCK_ARROW.get(), ShockArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.BOMB_ARROW.get(), BombArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.ANCIENT_ARROW.get(), AncientArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.SILVER_ARROW.get(), SilverArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.MAGIC_FIRE_ARROW.get(), MagicFireArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.MAGIC_ICE_ARROW.get(), MagicIceArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.MAGIC_LIGHT_ARROW.get(), MagicLightArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.HEART.get(), HeartRender::new);
        event.registerEntityRenderer(EntityTypeInit.MAGIC_JAR.get(), MagicJarRender::new);
        event.registerEntityRenderer(EntityTypeInit.LARGE_MAGIC_JAR.get(), LargeMagicJarRender::new);
        event.registerEntityRenderer(EntityTypeInit.RUPEE_ENTITY.get(), ItemEntityRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.GOLD_SKULLTULA_TOKEN.get(), GoldSkulltulaTokenRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.BOMB.get(), BombRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.MASTERSWORD_SWORD_BEAM.get(), MasterSwordBeamRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.WATER_BOMB.get(), WaterBombRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.FIREBALL.get(), FireBallRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.ICEBALL.get(), IceBallRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.DEKU_MAGIC_BUBBLE.get(), DekuMagicBubbleRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.BOOMERANG.get(), ctx -> new BoomerangRenderer(ctx, ItemInit.BOOMERANG.get()));
        event.registerEntityRenderer(EntityTypeInit.MAGIC_BOOMERANG.get(), ctx -> new BoomerangRenderer(ctx, ItemInit.MAGIC_BOOMERANG.get()));
        event.registerEntityRenderer(EntityTypeInit.SEA_BREEZE_BOOMERANG.get(), ctx -> new BoomerangRenderer(ctx, ItemInit.SEA_BREEZE_BOOMERANG.get()));
        event.registerEntityRenderer(EntityTypeInit.DEKU_SEED.get(), DekuSeedRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.WHEAT_SEED.get(), WheatSeedRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.BEETROOT_SEED.get(), BeetrootSeedRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.MELON_SEED.get(), MelonSeedRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.PUMPKIN_SEED.get(), PumpkinSeedRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.COCOA_BEAN.get(), CocoaBeanRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.HOOKSHOT_ENTITY.get(),  HookshotRender::new);
        event.registerEntityRenderer(EntityTypeInit.LONGSHOT_ENTITY.get(),  LongshotRender::new);
        event.registerEntityRenderer(EntityTypeInit.GOLD_SKULLTULA.get(), GoldSkulltulaRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.NAYRUS_LOVE_CRYSTAL.get(), NayrusLoveCrystalRenderer::new);
        event.registerEntityRenderer(EntityTypeInit.ELEGY_STATUE.get(), ElegyStatueRenderer::new);
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void attachRenderLayers(LivingEntityRenderer<T, M> renderer) {

    }

    //For custom render layers, uses reflection to add it
    private static Field field_EntityRenderersEvent$AddLayers_renderers;

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void attachRenderLayers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(skinName -> {
            PlayerRenderer playerRenderer = event.getSkin(skinName);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new DekuFlowerGliderRenderLayer(playerRenderer));
            }
        });

        if (field_EntityRenderersEvent$AddLayers_renderers == null) {
            try {
                field_EntityRenderersEvent$AddLayers_renderers = EntityRenderersEvent.AddLayers.class.getDeclaredField("renderers");
                field_EntityRenderersEvent$AddLayers_renderers.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        if (field_EntityRenderersEvent$AddLayers_renderers != null) {
            event.getSkins().forEach(renderer -> {
                LivingEntityRenderer<Player, EntityModel<Player>> skin = event.getSkin(renderer);
                attachRenderLayers(Objects.requireNonNull(skin));
            });
            try {
                ((Map<EntityType<?>, EntityRenderer<?>>) field_EntityRenderersEvent$AddLayers_renderers.get(event))
                        .values().stream()
                        .filter(LivingEntityRenderer.class::isInstance)
                        .map(LivingEntityRenderer.class::cast)
                        .forEach(EntityRendererInit::attachRenderLayers);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
