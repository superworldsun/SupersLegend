package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.arrows.*;
import com.superworldsun.superslegend.client.render.entites.BombRender;
import com.superworldsun.superslegend.client.render.entites.HeartRender;
import com.superworldsun.superslegend.client.render.entites.LargeMagicJarRender;
import com.superworldsun.superslegend.client.render.entites.MagicJarRender;
import com.superworldsun.superslegend.entities.HeartEntity;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
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
        //event.registerEntityRenderer(EntityTypeInit.BOMB.get(), BombRender::new);
        event.registerEntityRenderer(EntityTypeInit.WATER_BOMB.get(), AncientArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.DEKU_SEED.get(), AncientArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.WHEAT_SEED.get(), AncientArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.BEETROOT_SEED.get(), AncientArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.MELON_SEED.get(), AncientArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.PUMPKIN_SEED.get(), AncientArrowRender::new);
        event.registerEntityRenderer(EntityTypeInit.COCOA_BEAN.get(), AncientArrowRender::new);
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void attachRenderLayers(LivingEntityRenderer<T, M> renderer) {

    }

    //For custom render layers, uses reflection to add it
    private static Field field_EntityRenderersEvent$AddLayers_renderers;

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void attachRenderLayers(EntityRenderersEvent.AddLayers event) {
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
