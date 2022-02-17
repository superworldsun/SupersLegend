package com.superworldsun.superslegend.registries;


import com.superworldsun.superslegend.client.render.ClawshotRender;
import com.superworldsun.superslegend.client.render.HookshotRender;
import com.superworldsun.superslegend.client.render.LongshotRender;
import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangRender;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RendererManagerInit {
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.HOOKSHOT_ENTITY.get(), HookshotRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.LONGSHOT_ENTITY.get(), LongshotRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.CLAWSHOT_ENTITY.get(), ClawshotRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.REGULAR_BOOMERANG.get(), BoomerangRender::new);

    }

}
