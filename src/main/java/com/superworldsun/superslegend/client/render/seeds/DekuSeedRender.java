package com.superworldsun.superslegend.client.render.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.DekuSeedEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DekuSeedRender extends SeedRenderer<DekuSeedEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/seeds/deku_seed.png");

    public DekuSeedRender(EntityRendererProvider renderManager) {
        super(renderManager, TEXTURE);
    }
}