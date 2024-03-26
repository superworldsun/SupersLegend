package com.superworldsun.superslegend.client.render.seeds;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.entities.projectiles.seeds.SeedEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SeedRenderer<T extends SeedEntity> extends EntityRenderer<T> {
    private final ResourceLocation texture;
    private final RenderType renderType;

    public SeedRenderer(EntityRendererProvider renderManager, ResourceLocation texture) {
        super((EntityRendererProvider.Context) renderManager);
        this.texture = texture;
        this.renderType = RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
    
    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float scale = 0.5f;
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose poseStack$entry = poseStack.last();
        Matrix4f pose = poseStack$entry.pose();
        Matrix3f normal = poseStack$entry.normal();
        VertexConsumer vertexBuilder = bufferSource.getBuffer(renderType);
        vertex(vertexBuilder, pose, normal, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexBuilder, pose, normal, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexBuilder, pose, normal, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexBuilder, pose, normal, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    private static void vertex(VertexConsumer vertexBuilder, Matrix4f pose, Matrix3f normal, int light, float x, float y, float u, float v) {
        vertexBuilder.vertex(pose, x - 0.5F, y - 0.25F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }
}