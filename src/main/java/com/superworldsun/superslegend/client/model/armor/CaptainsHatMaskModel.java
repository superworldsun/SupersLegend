package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CaptainsHatMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;

	public CaptainsHatMaskModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);


		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -8.0F, 0.5F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -3.1416F, 0.0F, 3.1416F);
		cube_r1.texOffs(0, 0).addBox(-5.0F, -1.0F, -4.5F, 10.0F, 6.0F, 6.0F, 0.0F, false);
		cube_r1.texOffs(18, 12).addBox(-4.0F, 0.0F, -5.5F, 8.0F, 3.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(0, 25).addBox(-8.0F, -5.0F, 2.5F, 3.0F, 4.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(25, 22).addBox(5.0F, -5.0F, 2.5F, 3.0F, 4.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(26, 0).addBox(-7.0F, -1.0F, 1.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(26, 2).addBox(3.0F, -1.0F, 1.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(11, 22).addBox(-2.0F, -2.0F, -3.5F, 4.0F, 1.0F, 3.0F, 0.0F, false);
		cube_r1.texOffs(15, 16).addBox(-3.0F, -3.0F, -0.5F, 6.0F, 3.0F, 3.0F, 0.0F, false);
		cube_r1.texOffs(0, 12).addBox(-4.0F, 0.0F, 4.5F, 8.0F, 6.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, -8.0F, 0.5F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 2.6616F, 0.0F, 3.1416F);
		cube_r2.texOffs(0, 0).addBox(1.0F, -2.5965F, 3.8445F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r2.texOffs(0, 2).addBox(1.0F, -2.5965F, 1.8445F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r2.texOffs(0, 4).addBox(-3.0F, -2.5965F, 3.8445F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r2.texOffs(9, 19).addBox(-3.0F, -2.5965F, 1.8445F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r2.texOffs(0, 19).addBox(-1.0F, -2.5965F, -0.1555F, 2.0F, 1.0F, 5.0F, 0.0F, false);
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
