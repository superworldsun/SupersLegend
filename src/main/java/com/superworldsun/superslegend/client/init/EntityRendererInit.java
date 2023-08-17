package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.arrows.FireArrowRender;
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
