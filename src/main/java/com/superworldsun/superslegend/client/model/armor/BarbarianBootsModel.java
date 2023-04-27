package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BarbarianBootsModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftFoot_r1;
	private final ModelRenderer RightFoot_r1;

	public BarbarianBootsModel()
	{
		super(0.0F);

		texWidth = 128;
		texHeight = 128;

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(56, 66).addBox(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);
		leftLeg.texOffs(60, 40).addBox(-2.0F, 7.65F, -2.0F, 4.0F, 2.0F, 4.0F, 0.3F, false);
		leftLeg.texOffs(64, 61).addBox(-2.0F, 5.75F, -2.0F, 4.0F, 1.0F, 4.0F, 0.6F, false);
		leftLeg.texOffs(0, 53).addBox(-2.5F, 9.75F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, false);

		LeftFoot_r1 = new ModelRenderer(this);
		leftLeg.addChild(LeftFoot_r1);
		setRotationAngle(LeftFoot_r1, 0.2182F, 0.0F, 0.0F);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(26, 66).addBox(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.2F, false);
		rightLeg.texOffs(44, 65).addBox(-2.0F, 5.75F, -2.0F, 4.0F, 1.0F, 4.0F, 0.6F, false);
		rightLeg.texOffs(48, 0).addBox(-2.5F, 9.75F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, false);
		rightLeg.texOffs(60, 32).addBox(-2.0F, 7.65F, -2.0F, 4.0F, 2.0F, 4.0F, 0.3F, false);

		RightFoot_r1 = new ModelRenderer(this);
		rightLeg.addChild(RightFoot_r1);
		setRotationAngle(RightFoot_r1, 0.2182F, 0.0F, 0.0F);
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