package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.items.BombModel;
import com.superworldsun.superslegend.entities.projectiles.bombs.BombEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BombRenderer extends GeoEntityRenderer<BombEntity> {
    public BombRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BombModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BombEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"textures/entity/bomb.png");
    }

    @Override
    public void render(BombEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
