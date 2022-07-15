package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class DesertVoeLeggingsModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftLeg_r1;
	private final ModelRenderer RightLeg_r1;
	private final ModelRenderer Body_r1;
	private final ModelRenderer Body_r2;
	private final ModelRenderer Body_r3;
	private final ModelRenderer Body_r4;
	private final ModelRenderer Body_r5;
	private final ModelRenderer Body_r6;
	private final ModelRenderer Body_r7;
	
	public DesertVoeLeggingsModel()
	{
		super(0.0F);
		
		texWidth = 64;
		texHeight = 64;
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(16, 20).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.3F, false);
		
		LeftLeg_r1 = new ModelRenderer(this);
		LeftLeg_r1.setPos(0.35F, 2.5F, -2.0F);
		leftLeg.addChild(LeftLeg_r1);
		setRotationAngle(LeftLeg_r1, -0.1309F, 0.0F, 0.0F);
		LeftLeg_r1.texOffs(44, 13).addBox(-1.75F, -1.5F, -0.55F, 4.0F, 3.0F, 1.0F, 0.0F, false);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(16, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.3F, false);
		
		RightLeg_r1 = new ModelRenderer(this);
		RightLeg_r1.setPos(-0.35F, 2.5F, -2.0F);
		rightLeg.addChild(RightLeg_r1);
		setRotationAngle(RightLeg_r1, -0.1309F, 0.0F, 0.0F);
		RightLeg_r1.texOffs(44, 35).addBox(-2.15F, -1.5F, -0.55F, 4.0F, 3.0F, 1.0F, 0.0F, false);
		
		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(12, 37).addBox(-0.95F, 9.55F, -2.56F, 2.0F, 2.0F, 0.0F, 0.0F, false);
		body.texOffs(21, 47).addBox(3.55F, 10.05F, -1.55F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		body.texOffs(28, 22).addBox(-4.45F, 10.05F, 1.45F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(12, 9).addBox(-4.45F, 10.05F, -1.55F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		body.texOffs(28, 20).addBox(-4.45F, 10.05F, -2.55F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		
		Body_r1 = new ModelRenderer(this);
		Body_r1.setPos(0.05F, 11.75F, -2.0F);
		body.addChild(Body_r1);
		setRotationAngle(Body_r1, -0.1134F, 0.0F, 0.0F);
		Body_r1.texOffs(47, 43).addBox(-4.45F, -0.75F, -0.5F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		Body_r1.texOffs(0, 37).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body_r1.texOffs(48, 20).addBox(0.45F, -0.75F, -0.5F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		
		Body_r2 = new ModelRenderer(this);
		Body_r2.setPos(-3.9F, 12.0F, 0.4F);
		body.addChild(Body_r2);
		setRotationAngle(Body_r2, 0.0F, 0.0F, 0.3927F);
		Body_r2.texOffs(0, 25).addBox(-0.75F, -0.85F, -3.225F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		
		Body_r3 = new ModelRenderer(this);
		Body_r3.setPos(0.3F, 12.0F, 1.9F);
		body.addChild(Body_r3);
		setRotationAngle(Body_r3, 0.1309F, 0.0F, 0.0F);
		Body_r3.texOffs(42, 9).addBox(-4.7F, -1.0F, -0.15F, 5.0F, 3.0F, 1.0F, 0.0F, false);
		Body_r3.texOffs(46, 24).addBox(0.2F, -1.0F, -0.15F, 4.0F, 3.0F, 1.0F, 0.0F, false);
		
		Body_r4 = new ModelRenderer(this);
		Body_r4.setPos(4.25F, 12.0F, 0.5F);
		body.addChild(Body_r4);
		setRotationAngle(Body_r4, 0.0F, 0.0F, -0.3927F);
		Body_r4.texOffs(26, 25).addBox(-0.5F, -1.0F, -3.35F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		
		Body_r5 = new ModelRenderer(this);
		Body_r5.setPos(2.85F, 14.5F, 2.25F);
		body.addChild(Body_r5);
		setRotationAngle(Body_r5, 0.1309F, 0.0F, 0.0F);
		Body_r5.texOffs(24, 0).addBox(-7.3F, -1.5F, -0.3F, 9.0F, 3.0F, 1.0F, 0.0F, false);
		
		Body_r6 = new ModelRenderer(this);
		Body_r6.setPos(0.4399F, 12.6286F, -0.1F);
		body.addChild(Body_r6);
		setRotationAngle(Body_r6, 0.0F, 0.0F, 0.1309F);
		Body_r6.texOffs(37, 35).addBox(-5.0F, 1.0F, -2.225F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		
		Body_r7 = new ModelRenderer(this);
		Body_r7.setPos(2.2041F, 14.7143F, 0.0F);
		body.addChild(Body_r7);
		setRotationAngle(Body_r7, 0.0F, 0.0F, -0.1309F);
		Body_r7.texOffs(23, 39).addBox(1.75F, -1.4F, -2.325F, 1.0F, 3.0F, 5.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		// if some model parts shouldn't be animated, you can reset rotations like so
		setRotationAngle(body, 0.0F, 0.0F, 0.0F);
		
		if (crouching)
		{
			body.setPos(0.0F, 0.0F, 4.0F);
		}
		
		super.renderToBuffer(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}
	
	@Override
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of(leftLeg, rightLeg, body);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}