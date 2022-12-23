package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class DongerosMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;

	public DongerosMaskModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);

		base.texOffs(0, 0).addBox(-5.0F, -11.0F, -3.0F, 10.0F, 8.0F, 8.0F, 0.0F, false);
		base.texOffs(0, 26).addBox(2.0F, -6.0F, -5.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);
		base.texOffs(20, 21).addBox(-6.0F, -6.0F, -5.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);
		base.texOffs(0, 21).addBox(-5.0F, -10.0F, -6.0F, 10.0F, 2.0F, 3.0F, 0.0F, false);
		base.texOffs(14, 29).addBox(2.0F, -2.0F, -5.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
		base.texOffs(28, 0).addBox(-5.0F, -2.0F, -5.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
		base.texOffs(26, 31).addBox(4.0F, -3.0F, -4.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		base.texOffs(30, 10).addBox(-5.0F, -3.0F, -4.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		base.texOffs(0, 16).addBox(-7.0F, -12.0F, 3.0F, 14.0F, 5.0F, 0.0F, 0.0F, false);
		base.texOffs(0, 34).addBox(-3.0F, -10.0F, 5.0F, 6.0F, 5.0F, 1.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(4.5F, -12.5F, -0.5F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -0.1047F);
		cube_r1.texOffs(34, 17).addBox(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-4.5F, -12.5F, -0.5F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.1047F);
		cube_r2.texOffs(34, 17).addBox(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, 0.0F, false);
		
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
