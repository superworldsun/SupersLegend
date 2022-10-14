package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SnowquillTrousersModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftLeg_r1;
	private final ModelRenderer RightLeg_r1;
	private final ModelRenderer Body_r1;
	
	public SnowquillTrousersModel()
	{
		super(0.0F);
		texWidth = 128;
		texHeight = 128;
		
		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 13).addBox(-4.0F, 7.8F, -2.0F, 8.0F, 4.0F, 4.0F, 0.1F, false);
		
		Body_r1 = new ModelRenderer(this);
		Body_r1.setPos(0.05F, 11.75F, -2.0F);
		body.addChild(Body_r1);
		setRotationAngle(Body_r1, -0.1134F, 0.0F, 0.0F);
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(35, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.1F, false);
		
		LeftLeg_r1 = new ModelRenderer(this);
		leftLeg.addChild(LeftLeg_r1);
		setRotationAngle(LeftLeg_r1, -0.1309F, 0.0F, 0.0F);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(39, 30).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.1F, false);
		
		RightLeg_r1 = new ModelRenderer(this);
		rightLeg.addChild(RightLeg_r1);
		setRotationAngle(RightLeg_r1, -0.1309F, 0.0F, 0.0F);
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