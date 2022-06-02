package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class MaskOfTruthModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;

	public MaskOfTruthModel()
	{
		texWidth = 32;
		texHeight = 32;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 22.0F, 0.0F);
		base.texOffs(0, 10).addBox(-3.0F, 1.1F, -4.3F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 6).addBox(-4.5F, 0.1F, -4.3F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 12).addBox(-2.0F, -1.9F, -4.3F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(17, 13).addBox(-5.75F, -0.9F, -4.3F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 16).addBox(2.75F, -0.9F, -4.3F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 8).addBox(-4.0F, -3.9F, -4.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 0).addBox(-6.5F, -8.9F, -4.3F, 13.0F, 5.0F, 1.0F, 0.0F, false);
		base.texOffs(8, 14).addBox(-6.0F, -9.9F, -4.3F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 14).addBox(-1.5F, -9.9F, -4.3F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 20).addBox(-1.0F, -10.9F, -4.3F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 18).addBox(-6.0F, -10.9F, -4.3F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(18, 8).addBox(4.0F, -10.9F, -4.3F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(10, 12).addBox(3.0F, -9.9F, -4.3F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(15, 15).addBox(-4.0F, -2.9F, -4.3F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(14, 10).addBox(1.0F, -2.9F, -4.3F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(20, 6).addBox(0.5F, -2.9F, -4.3F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(5, 19).addBox(-1.5F, -2.9F, -4.3F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(8, 16).addBox(4.0F, -3.9F, -4.3F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		base.texOffs(14, 17).addBox(-6.0F, -3.9F, -4.3F, 2.0F, 3.0F, 1.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		base.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
	
	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_)
	{
	}
}
