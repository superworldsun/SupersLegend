package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;

public class GoldSkulltulaTokenRenderer extends ItemEntityRenderer {
    private static final double MODEL_Y_OFFSET = -0.2D;

    public GoldSkulltulaTokenRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(net.minecraft.world.entity.item.ItemEntity entity, float entityYaw,
                       float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                       int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, MODEL_Y_OFFSET, 0.0D);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
