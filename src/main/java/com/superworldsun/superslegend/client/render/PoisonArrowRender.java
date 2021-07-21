package com.superworldsun.superslegend.client.render;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.PoisonArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class PoisonArrowRender extends ArrowRenderer<PoisonArrowEntity> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/arrows/poison_arrow.png");

    public PoisonArrowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(PoisonArrowEntity entity) {
        return TEXTURE;
    }


}

