package com.superworldsun.superslegend.client.render.seeds;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.entities.projectiles.seeds.SeedEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class SeedRenderer<T extends SeedEntity> extends EntityRenderer<T> {
	private final ResourceLocation texture;
	private final RenderType renderType;

	public SeedRenderer(EntityRendererManager renderManager, ResourceLocation texture) {
		super(renderManager);
		this.texture = texture;
		this.renderType = RenderType.entityCutoutNoCull(texture);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return texture;
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
		matrixStack.pushPose();
		float scale = 0.5f;
		matrixStack.scale(scale, scale, scale);
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		MatrixStack.Entry matrixstack$entry = matrixStack.last();
		Matrix4f pose = matrixstack$entry.pose();
		Matrix3f normal = matrixstack$entry.normal();
		IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
		vertex(vertexBuilder, pose, normal, light, 0.0F, 0, 0, 1);
		vertex(vertexBuilder, pose, normal, light, 1.0F, 0, 1, 1);
		vertex(vertexBuilder, pose, normal, light, 1.0F, 1, 1, 0);
		vertex(vertexBuilder, pose, normal, light, 0.0F, 1, 0, 0);
		matrixStack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, light);
	}

	private static void vertex(IVertexBuilder vertexBuilder, Matrix4f pose, Matrix3f normal, int light, float x, float y, float u, float v) {
		vertexBuilder.vertex(pose, x - 0.5F, y - 0.25F, 0.0F)
				.color(255, 255, 255, 255)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(light)
				.normal(normal, 0.0F, 1.0F, 0.0F)
				.endVertex();
	}
}