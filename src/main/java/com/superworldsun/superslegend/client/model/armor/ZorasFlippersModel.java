package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ZorasFlippersModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;

	public ZorasFlippersModel()
	{
		super(0F);
		
		texWidth = 64;
		texHeight = 64;

		left_leg = new ModelRenderer(this);
		left_leg.setPos(-2.0F, 12.0F, 0.0F);
		left_leg.texOffs(0, 9).addBox(1.05F, 11.99F, -6.9F, 6.0F, 0.0F, 9.0F, 0.0F, false);
		left_leg.texOffs(21, 6).addBox(1.55F, 10.2F, -2.5F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		left_leg.texOffs(21, 3).addBox(1.55F, 10.2F, 1.5F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		left_leg.texOffs(0, 19).addBox(5.55F, 10.2F, -1.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		left_leg.texOffs(0, 10).addBox(1.55F, 10.2F, -1.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		left_leg.texOffs(9, 18).addBox(2.55F, 11.2F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setPos(2.0F, 12.0F, 0.0F);
		right_leg.texOffs(0, 15).addBox(-5.45F, 11.2F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
		right_leg.texOffs(0, 0).addBox(-6.45F, 10.2F, -1.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		right_leg.texOffs(0, 5).addBox(-2.45F, 10.2F, -1.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		right_leg.texOffs(18, 18).addBox(-6.45F, 10.2F, 1.5F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		right_leg.texOffs(21, 0).addBox(-6.45F, 10.2F, -2.5F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		right_leg.texOffs(0, 0).addBox(-6.95F, 11.99F, -6.9F, 6.0F, 0.0F, 9.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
