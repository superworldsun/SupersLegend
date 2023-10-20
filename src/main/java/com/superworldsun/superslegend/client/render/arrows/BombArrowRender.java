package com.superworldsun.superslegend.client.render.arrows;

import com.superworldsun.superslegend.entities.projectiles.arrows.BombArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


public class BombArrowRender extends ArrowRenderer<BombArrowEntity> {

    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("superslegend:textures/entity/arrows/bomb_arrow.png");

    public BombArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override @Nonnull @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(BombArrowEntity entity) {
        return ARROW_TEXTURE;
    }
}