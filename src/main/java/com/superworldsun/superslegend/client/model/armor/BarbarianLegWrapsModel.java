package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BarbarianLegWrapsModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftLeg_r1;
	private final ModelRenderer RightLeg_r1;
	private final ModelRenderer Body_r1;
	private final ModelRenderer Body_r2;
	private final ModelRenderer Body_r3;
	private final ModelRenderer Body_r4;

	public BarbarianLegWrapsModel()
	{
		super(0.0F);
		texWidth = 128;
		texHeight = 128;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(36, 59).addBox(-4.45F, 10.05F, -2.55F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(44, 0).addBox(-4.45F, 10.05F, -1.55F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		body.texOffs(20, 12).addBox(-4.45F, 10.05F, 1.45F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(40, 24).addBox(3.55F, 10.05F, -1.55F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		body.texOffs(18, 46).addBox(-4.0F, 10.65F, -2.0F, 8.0F, 1.0F, 4.0F, 0.3F, false);

		Body_r1 = new ModelRenderer(this);
		Body_r1.setPos(4.25F, 12.0F, 0.9F);
		body.addChild(Body_r1);
		setRotationAngle(Body_r1, 0.0F, 0.0F, -0.3927F);
		Body_r1.texOffs(37, 66).addBox(-0.5F, -1.0F, -3.35F, 1.0F, 3.0F, 5.0F, 0.0F, false);

		Body_r2 = new ModelRenderer(this);
		Body_r2.setPos(0.1293F, 12.3787F, -0.5365F);
		body.addChild(Body_r2);
		setRotationAngle(Body_r2, 0.1309F, 0.0F, 0.0F);
		Body_r2.texOffs(58, 6).addBox(-4.6293F, -0.9787F, 2.3365F, 9.0F, 3.0F, 1.0F, 0.0F, false);

		Body_r3 = new ModelRenderer(this);
		Body_r3.setPos(0.05F, 11.75F, -2.0F);
		body.addChild(Body_r3);
		setRotationAngle(Body_r3, -0.1134F, 0.0F, 0.0F);
		Body_r3.texOffs(58, 16).addBox(-4.45F, -0.75F, -0.45F, 9.0F, 6.0F, 0.0F, 0.0F, false);

		Body_r4 = new ModelRenderer(this);
		Body_r4.setPos(-3.9F, 12.0F, 0.8F);
		body.addChild(Body_r4);
		setRotationAngle(Body_r4, 0.0F, 0.0F, 0.3927F);
		Body_r4.texOffs(9, 67).addBox(-0.75F, -0.85F, -3.25F, 1.0F, 3.0F, 5.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(49, 0).addBox(1.5F, 2.5F, -1.975F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(0, 48).addBox(1.5F, 2.5F, 0.975F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(18, 45).addBox(0.975F, 2.5F, 1.75F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(0, 45).addBox(-0.5F, 2.5F, 1.75F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(38, 46).addBox(1.5F, 2.5F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(64, 55).addBox(-2.0F, 1.75F, -2.0F, 4.0F, 1.0F, 4.0F, 0.05F, false);
		leftLeg.texOffs(64, 46).addBox(-2.0F, 2.95F, -2.0F, 4.0F, 1.0F, 4.0F, 0.05F, false);
		leftLeg.texOffs(32, 61).addBox(-2.0F, 0.25F, -2.0F, 4.0F, 1.0F, 4.0F, 0.3F, false);

		LeftLeg_r1 = new ModelRenderer(this);
		LeftLeg_r1.setPos(-2.1207F, 0.8469F, 7.746F);
		leftLeg.addChild(LeftLeg_r1);
		setRotationAngle(LeftLeg_r1, -0.1134F, 0.0F, 0.0F);
		LeftLeg_r1.texOffs(0, 0).addBox(1.45F, -0.75F, -10.396F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(14, 62).addBox(-2.0F, 0.25F, -2.0F, 4.0F, 1.0F, 4.0F, 0.3F, false);

		RightLeg_r1 = new ModelRenderer(this);
		RightLeg_r1.setPos(2.1824F, 0.2106F, 1.8096F);
		rightLeg.addChild(RightLeg_r1);
		setRotationAngle(RightLeg_r1, -0.1134F, 0.0F, 0.0F);
		RightLeg_r1.texOffs(0, 4).addBox(-4.45F, -0.75F, -4.4596F, 3.0F, 3.0F, 1.0F, 0.0F, false);
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