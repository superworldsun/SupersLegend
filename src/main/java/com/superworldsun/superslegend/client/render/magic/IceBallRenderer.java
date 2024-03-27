package com.superworldsun.superslegend.client.render.magic;

import com.superworldsun.superslegend.entities.projectiles.magic.IceballEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class IceBallRenderer extends EntityRenderer<IceballEntity> {

    public IceBallRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(IceballEntity pEntity) {
        return null;
    }
}