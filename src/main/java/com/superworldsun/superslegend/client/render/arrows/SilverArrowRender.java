package com.superworldsun.superslegend.client.render.arrows;

import com.superworldsun.superslegend.entities.projectiles.arrows.ShockArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.SilverArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


public class SilverArrowRender extends ArrowRenderer<SilverArrowEntity> {

    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("superslegend:textures/entity/arrows/silver_arrow.png");

    public SilverArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override @Nonnull @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(SilverArrowEntity entity) {
        return ARROW_TEXTURE;
    }
}