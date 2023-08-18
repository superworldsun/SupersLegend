package com.superworldsun.superslegend.client.render.arrows;

import com.superworldsun.superslegend.entities.projectiles.arrows.IceArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


public class IceArrowRender extends ArrowRenderer<IceArrowEntity> {

    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("superslegend:textures/entity/arrows/ice_arrow.png");

    public IceArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override @Nonnull @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(IceArrowEntity entity) {
        return ARROW_TEXTURE;
    }
}