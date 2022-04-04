package com.superworldsun.superslegend.registries;


import com.superworldsun.superslegend.client.render.ClawshotRender;
import com.superworldsun.superslegend.client.render.HookshotRender;
import com.superworldsun.superslegend.client.render.LongshotRender;
import com.superworldsun.superslegend.client.render.MasterSword_Renderer;
import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangRender;
import com.superworldsun.superslegend.entities.projectiles.boomerang.GaleBoomerangRender;
import com.superworldsun.superslegend.entities.projectiles.boomerang.MagicBoomerangRender;
import com.superworldsun.superslegend.entities.projectiles.boomerang.WWBoomerangRender;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MAGIC_BOOMERANG.get(), MagicBoomerangRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.WW_BOOMERANG.get(), WWBoomerangRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.GALE_BOOMERANG.get(), GaleBoomerangRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MASTERSWORD_SWORD_ENTITY.get(), MasterSword_Renderer::new);

    }

}
