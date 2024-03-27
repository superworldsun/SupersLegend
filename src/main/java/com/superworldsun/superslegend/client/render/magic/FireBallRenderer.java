package com.superworldsun.superslegend.client.render.magic;

import com.superworldsun.superslegend.entities.projectiles.magic.FireballEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireBallRenderer extends EntityRenderer<FireballEntity> {

    public FireBallRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(FireballEntity pEntity) {
        return null;
    }
}