package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.MasterSwordModel;
import com.superworldsun.superslegend.entities.projectiles.mastersword.MasterSwordSwordEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MasterSwordRenderer extends EntityRenderer<MasterSwordSwordEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/mastersword.png");
	private static final MasterSwordModel MODEL = new MasterSwordModel();
	
	public MasterSwordRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	@Override
	public void render(MasterSwordSwordEntity mastersword, float yaw, float tick, MatrixStack matrixStack, IRenderTypeBuffer provider, int light)
	{
		matrixStack.pushPose();
		matrixStack.scale(3.0F, 0, 3.0F);
		matrixStack.mulPose(Vector3f.YN.rotationDegrees(mastersword.boomerangRotation));
		rendersword(tick, mastersword.tickCount, matrixStack, provider, light);
		matrixStack.popPose();
		super.render(mastersword, yaw, tick, matrixStack, provider, light);
	}
	
	public void rendersword(float tickDelta, int age, MatrixStack stack, IRenderTypeBuffer provider, int light)
	{
		IVertexBuilder ivertexbuilder = provider.getBuffer(RenderType.entityTranslucent(TEXTURE));
		MODEL.renderToBuffer(stack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(MasterSwordSwordEntity entity)
	{
		return TEXTURE;
	}
}
