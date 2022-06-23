package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class DesertVoeBootsModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftFoot_r1;
	private final ModelRenderer RightFoot_r1;
	
	public DesertVoeBootsModel()
	{
		super(0.0F);
		
		texWidth = 64;
		texHeight = 64;
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(12, 38).addBox(-2.0F, 11.15F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);
		leftLeg.texOffs(0, 35).addBox(-2.0F, 9.25F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);
		leftLeg.texOffs(32, 13).addBox(-2.0F, 7.25F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);

		LeftFoot_r1 = new ModelRenderer(this);
		LeftFoot_r1.setPos(-2.0F, 8.75F, -2.2F);
		leftLeg.addChild(LeftFoot_r1);
		setRotationAngle(LeftFoot_r1, 0.2182F, 0.0F, 0.0F);
		LeftFoot_r1.texOffs(48, 1).addBox(0.75F, -2.5F, -0.5F, 3.0F, 5.0F, 1.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(36, 4).addBox(-2.0F, 11.15F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);
		rightLeg.texOffs(26, 34).addBox(-2.0F, 9.25F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);
		rightLeg.texOffs(34, 24).addBox(-2.0F, 7.25F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);

		RightFoot_r1 = new ModelRenderer(this);
		RightFoot_r1.setPos(2.0F, 8.75F, -2.2F);
		rightLeg.addChild(RightFoot_r1);
		setRotationAngle(RightFoot_r1, 0.2182F, 0.0F, 0.0F);
		RightFoot_r1.texOffs(0, 47).addBox(-3.75F, -2.5F, -0.5F, 3.0F, 5.0F, 1.0F, 0.0F, false);
	}
	
	@Override
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of(leftLeg, rightLeg);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}