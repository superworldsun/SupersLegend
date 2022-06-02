package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class GiantsMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;

	public GiantsMaskModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 23.0F, 0.0F);
		base.texOffs(24, 16).addBox(-4.0F, -4.0F, -5.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(6, 23).addBox(1.0F, -4.0F, -5.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 12).addBox(-4.5F, -4.0F, -4.75F, 9.0F, 5.0F, 1.0F, 0.0F, false);
		base.texOffs(24, 31).addBox(3.5F, -2.0F, -3.75F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		base.texOffs(30, 16).addBox(-4.5F, -2.0F, -3.75F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		base.texOffs(29, 8).addBox(-4.5F, -1.0F, -0.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		base.texOffs(25, 7).addBox(3.5F, -1.0F, -0.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		base.texOffs(24, 14).addBox(-4.0F, -5.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(30, 22).addBox(-1.0F, -7.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 0).addBox(-0.5F, -8.5F, -5.25F, 1.0F, 4.0F, 1.0F, 0.0F, false);
		base.texOffs(20, 12).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 18).addBox(-4.0F, -8.0F, -4.75F, 8.0F, 4.0F, 1.0F, 0.0F, false);
		base.texOffs(24, 21).addBox(-4.99F, -8.0F, -5.0F, 1.0F, 6.0F, 4.0F, 0.0F, false);
		base.texOffs(0, 23).addBox(-4.99F, -8.0F, -1.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
		base.texOffs(18, 14).addBox(3.99F, -8.0F, -1.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
		base.texOffs(10, 23).addBox(3.999F, -8.0F, -5.0F, 1.0F, 6.0F, 4.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -8.9629F, -0.0558F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.0436F, 0.0F, 0.0F);
		cube_r1.texOffs(0, 0).addBox(-5.0F, -0.1164F, -0.0678F, 10.0F, 1.0F, 5.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, -8.9516F, -0.1858F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.0436F, 0.0F, 0.0F);
		cube_r2.texOffs(15, 28).addBox(-0.5F, -0.6164F, -0.0678F, 1.0F, 1.0F, 5.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, -7.376F, -4.3495F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.2182F, 0.0F, 0.0F);
		cube_r3.texOffs(25, 1).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		cube_r3.texOffs(0, 6).addBox(-5.0F, -0.75F, -0.5F, 10.0F, 1.0F, 5.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(0.0F, -2.0F, -4.5F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.1745F, 0.0F, 0.0F);
		cube_r4.texOffs(32, 0).addBox(-0.5F, -2.0F, -0.75F, 1.0F, 5.0F, 1.0F, 0.0F, false);
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
