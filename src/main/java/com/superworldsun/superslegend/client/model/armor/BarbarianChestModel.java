package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BarbarianChestModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftArm_r1;
	private final ModelRenderer LeftArm_r2;
	private final ModelRenderer RightArm_r1;
	private final ModelRenderer RightArm_r2;
	private final ModelRenderer RightArm_r3;
	private final ModelRenderer RightArm_r4;
	private final ModelRenderer RightArm_r5;
	private final ModelRenderer RightArm_r6;

	public BarbarianChestModel()
	{
		super(0.0F);
		texWidth = 128;
		texHeight = 128;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(26, 38).addBox(-1.0F, 2.5F, -2.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
		body.texOffs(0, 59).addBox(-4.0F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, 0.1F, false);
		body.texOffs(0, 74).addBox(-1.0F, 0.0F, 2.125F, 2.0F, 7.0F, 0.0F, 0.0F, false);
		body.texOffs(54, 48).addBox(1.0F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, 0.1F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(60, 22).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
		leftArm.texOffs(20, 51).addBox(-1.0F, 2.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.1F, false);
		leftArm.texOffs(42, 32).addBox(-2.0F, 8.0F, -3.0F, 6.0F, 2.0F, 6.0F, -0.7F, false);
		leftArm.texOffs(42, 24).addBox(-2.0F, 1.0F, -3.0F, 6.0F, 2.0F, 6.0F, -0.7F, false);
		leftArm.texOffs(40, 16).addBox(-2.0F, 6.0F, -3.0F, 6.0F, 2.0F, 6.0F, -0.8F, false);
		leftArm.texOffs(24, 30).addBox(-2.0F, 2.25F, -3.0F, 6.0F, 2.0F, 6.0F, -0.8F, false);
		leftArm.texOffs(40, 8).addBox(-2.0F, 4.75F, -3.0F, 6.0F, 2.0F, 6.0F, -0.8F, false);

		LeftArm_r1 = new ModelRenderer(this);
		LeftArm_r1.setPos(-1.0F, 5.75F, 0.0F);
		leftArm.addChild(LeftArm_r1);
		setRotationAngle(LeftArm_r1, 0.0F, 0.0F, -0.0873F);
		LeftArm_r1.texOffs(24, 38).addBox(-1.0F, -1.5F, -3.0F, 6.0F, 2.0F, 6.0F, -0.8F, false);

		LeftArm_r2 = new ModelRenderer(this);
		LeftArm_r2.setPos(3.0F, 4.75F, 0.0F);
		leftArm.addChild(LeftArm_r2);
		setRotationAngle(LeftArm_r2, 0.0F, 0.0F, 0.1571F);
		LeftArm_r2.texOffs(0, 37).addBox(-5.0F, -1.0F, -3.0F, 6.0F, 2.0F, 6.0F, -0.8F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(52, 59).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
		rightArm.texOffs(20, 22).addBox(-5.0F, 5.75F, -3.0F, 7.0F, 2.0F, 6.0F, -0.8F, false);
		rightArm.texOffs(0, 45).addBox(-4.0F, 8.0F, -3.0F, 6.0F, 2.0F, 6.0F, -0.7F, false);
		rightArm.texOffs(42, 40).addBox(-4.0F, 1.0F, -3.0F, 6.0F, 2.0F, 6.0F, -0.7F, false);
		rightArm.texOffs(38, 48).addBox(-3.0F, 2.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.1F, false);
		rightArm.texOffs(21, 68).addBox(-4.1F, 1.85F, -1.5F, 1.0F, 7.0F, 3.0F, 0.0F, false);

		RightArm_r1 = new ModelRenderer(this);
		RightArm_r1.setPos(-6.5151F, 1.707F, 0.0F);
		rightArm.addChild(RightArm_r1);
		setRotationAngle(RightArm_r1, 0.0F, 0.0F, -1.5708F);
		RightArm_r1.texOffs(40, 12).addBox(-6.25F, 1.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		RightArm_r1.texOffs(40, 16).addBox(-4.0F, 0.5F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		RightArm_r2 = new ModelRenderer(this);
		RightArm_r2.setPos(-6.5151F, 4.207F, 0.0F);
		rightArm.addChild(RightArm_r2);
		setRotationAngle(RightArm_r2, 0.0F, 0.0F, -1.5708F);
		RightArm_r2.texOffs(42, 32).addBox(0.25F, 0.75F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		RightArm_r3 = new ModelRenderer(this);
		RightArm_r3.setPos(-4.6F, 1.85F, 0.0F);
		rightArm.addChild(RightArm_r3);
		setRotationAngle(RightArm_r3, 0.0F, 0.0F, -0.6981F);
		RightArm_r3.texOffs(42, 40).addBox(0.0F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		RightArm_r4 = new ModelRenderer(this);
		RightArm_r4.setPos(-1.5F, 6.0F, 0.0F);
		rightArm.addChild(RightArm_r4);
		setRotationAngle(RightArm_r4, 0.0F, 0.0F, -0.1745F);
		RightArm_r4.texOffs(20, 14).addBox(-3.4F, -1.45F, -3.0F, 7.0F, 2.0F, 6.0F, -0.8F, false);

		RightArm_r5 = new ModelRenderer(this);
		RightArm_r5.setPos(-1.5F, 4.5F, 0.0F);
		rightArm.addChild(RightArm_r5);
		setRotationAngle(RightArm_r5, 0.0F, 0.0F, -0.1309F);
		RightArm_r5.texOffs(0, 20).addBox(-3.4F, -1.5F, -3.0F, 7.0F, 2.0F, 6.0F, -0.8F, false);

		RightArm_r6 = new ModelRenderer(this);
		RightArm_r6.setPos(-1.5F, 3.0F, 0.0F);
		rightArm.addChild(RightArm_r6);
		setRotationAngle(RightArm_r6, 0.0F, 0.0F, 0.1309F);
		RightArm_r6.texOffs(0, 12).addBox(-3.5F, -1.0F, -3.0F, 7.0F, 2.0F, 6.0F, -0.8F, false);
	}
	
	@Override
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of(leftArm, rightArm, body);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}