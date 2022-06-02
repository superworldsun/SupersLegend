package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class GnatHatModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;

	public GnatHatModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);
		base.texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.2F, false);
		base.texOffs(0, 36).addBox(-0.5F, -8.3F, -4.2F, 1.0F, 2.0F, 1.0F, 0.2F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -2.3F, 0.5F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -1.5272F, 0.0F, 0.0F);
		cube_r1.texOffs(21, 33).addBox(-1.5F, -8.1F, -4.9F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, -2.3F, 0.5F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, -1.2654F, 0.0F, 0.0F);
		cube_r2.texOffs(24, 19).addBox(-2.0F, -9.4F, -4.6F, 4.0F, 2.0F, 3.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, -2.3F, 0.5F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.7854F, 0.0F, 0.0F);
		cube_r3.texOffs(24, 26).addBox(-2.5F, -10.6F, -3.3F, 5.0F, 3.0F, 4.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(0.0F, -2.3F, 0.5F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.3927F, 0.0F, 0.0F);
		cube_r4.texOffs(0, 26).addBox(-3.0F, -11.4F, -4.7F, 6.0F, 4.0F, 6.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(0.0F, 0.0F, 0.0F);
		base.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.2618F, 0.0F, 0.0F);
		cube_r5.texOffs(24, 0).addBox(-2.99F, -12.5F, -4.3F, 6.0F, 3.0F, 5.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.0F, 0.0F, 0.0F);
		base.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.0873F, 0.0F, 0.0F);
		cube_r6.texOffs(25, 9).addBox(-4.0F, -10.6F, -4.0F, 8.0F, 3.0F, 7.0F, 0.0F, false);
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
