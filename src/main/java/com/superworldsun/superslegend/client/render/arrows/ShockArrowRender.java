package com.superworldsun.superslegend.client.render.arrows;

import com.superworldsun.superslegend.entities.projectiles.arrows.IceArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.ShockArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


public class ShockArrowRender extends ArrowRenderer<ShockArrowEntity> {

    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("superslegend:textures/entity/arrows/shock_arrow.png");

    public ShockArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override @Nonnull @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(ShockArrowEntity entity) {
        return ARROW_TEXTURE;
    }
}