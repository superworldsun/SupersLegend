package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class GarosMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer hood;
	private final ModelRenderer hoodtail;

	public GarosMaskModel() {
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 22.0F, 0.0F);


		hood = new ModelRenderer(this);
		hood.setPos(0.0F, -3.25F, 0.0F);
		base.addChild(hood);
		hood.texOffs(0, 18).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.1F, false);
		hood.texOffs(0, 0).addBox(-4.5F, -5.5F, -4.5F, 9.0F, 9.0F, 9.0F, 0.1F, false);
		hood.texOffs(24, 26).addBox(-3.95F, 3.6F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);

		hoodtail = new ModelRenderer(this);
		hoodtail.setPos(0.0F, -3.4F, 9.4F);
		base.addChild(hoodtail);
		setRotationAngle(hoodtail, -0.1745F, 0.0F, 0.0F);
		hoodtail.texOffs(0, 34).addBox(-4.0F, -4.1F, -5.9F, 8.0F, 8.0F, 2.0F, 0.0F, false);
		hoodtail.texOffs(24, 18).addBox(-3.5F, -3.1F, -3.9F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		hoodtail.texOffs(27, 0).addBox(-3.0F, -2.1F, -2.9F, 6.0F, 6.0F, 1.0F, 0.0F, false);
		hoodtail.texOffs(20, 35).addBox(-2.5F, -1.1F, -1.9F, 5.0F, 5.0F, 1.0F, 0.0F, false);
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
