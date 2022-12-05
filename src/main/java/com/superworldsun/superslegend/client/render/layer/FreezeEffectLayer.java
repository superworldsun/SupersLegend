package com.superworldsun.superslegend.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EffectInit;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class FreezeEffectLayer extends LayerRenderer<LivingEntity, EntityModel<LivingEntity>>
{
	private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/layer/frozen.png"));
	
	public FreezeEffectLayer(IEntityRenderer<LivingEntity, EntityModel<LivingEntity>> p_i50926_1_)
	{
		super(p_i50926_1_);
	}
	
	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_,
			float p_225628_9_, float p_225628_10_)
	{
		if (!livingEntity.hasEffect(EffectInit.FREEZE.get()))
			return;
		
		IVertexBuilder ivertexbuilder = renderTypeBuffer.getBuffer(RENDER_TYPE);
		getParentModel().renderToBuffer(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
