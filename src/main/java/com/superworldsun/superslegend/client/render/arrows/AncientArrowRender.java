package com.superworldsun.superslegend.client.render.arrows;

import com.superworldsun.superslegend.entities.projectiles.arrows.AncientArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


public class AncientArrowRender extends ArrowRenderer<AncientArrowEntity> {

    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("superslegend:textures/entity/arrows/ancient_arrow.png");

    public AncientArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override @Nonnull @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(AncientArrowEntity entity) {
        return ARROW_TEXTURE;
    }
}