package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class TroupeLeadersMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;

	public TroupeLeadersMaskModel()
	{
		texWidth = 32;
		texHeight = 32;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);
		base.texOffs(10, 6).addBox(-1.0204F, -5.0F, -6.8941F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-2.6801F, -8.0071F, -4.5314F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.2427F, -0.0992F, -1.9514F);
		cube_r1.texOffs(10, 10).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(5.5593F, -4.0F, -2.551F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -1.0472F, 0.0F);
		cube_r2.texOffs(14, 14).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(-0.0204F, -2.5F, -5.1441F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -0.1745F, 0.0F);
		cube_r3.texOffs(10, 0).addBox(0.0F, -1.5F, 0.0F, 6.0F, 3.0F, 0.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-0.0204F, -2.5F, -5.1441F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.1745F, 0.0F);
		cube_r4.texOffs(10, 3).addBox(-6.0F, -1.5F, 0.0F, 6.0F, 3.0F, 0.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-0.8897F, -3.0F, -5.377F);
		base.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, -0.2618F, 0.0F);
		cube_r5.texOffs(0, 0).addBox(0.9F, -6.0F, 0.0F, 5.0F, 9.0F, 0.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(2.6394F, -8.0071F, -4.5314F);
		base.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.2427F, 0.0992F, 1.9514F);
		cube_r6.texOffs(10, 16).addBox(0.0F, -5.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(-5.6F, -4.0F, -2.551F);
		base.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 1.0472F, 0.0F);
		cube_r7.texOffs(14, 10).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(-2.4352F, -4.5F, -4.497F);
		base.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.2618F, 0.0F);
		cube_r8.texOffs(0, 9).addBox(-2.5F, -4.5F, 0.0F, 5.0F, 9.0F, 0.0F, 0.0F, false);
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
