package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SnowquillTunicModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer body;
	private final ModelRenderer Body_r1;
	private final ModelRenderer Body_r2;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;


	public SnowquillTunicModel()
	{
		super(0.0F);
		texWidth = 128;
		texHeight = 128;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(36, 17).addBox(-3.5F, -0.1F, -2.5F, 7.0F, 12.0F, 1.0F, 0.0F, false);
		body.texOffs(55, 39).addBox(-2.5F, 12.9F, -2.5F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(20, 13).addBox(-0.5F, 13.9F, -2.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 51).addBox(-4.0F, 11.9F, -2.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(0, 0).addBox(-4.5F, 9.4F, -2.5F, 9.0F, 1.0F, 5.0F, 0.1F, false);
		body.texOffs(0, 6).addBox(-1.0F, 8.9F, -2.85F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		body.texOffs(47, 44).addBox(-5.0F, -0.25F, -3.5F, 10.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(47, 42).addBox(-5.0F, -0.25F, 2.5F, 10.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(58, 6).addBox(-5.0F, -0.25F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		body.texOffs(58, 0).addBox(4.0F, -0.25F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		body.texOffs(35, 55).addBox(5.0F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		body.texOffs(55, 33).addBox(-6.0F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		body.texOffs(11, 31).addBox(-4.35F, -0.1F, -2.5F, 1.0F, 12.0F, 5.0F, 0.0F, false);
		body.texOffs(31, 0).addBox(3.35F, -0.1F, -2.5F, 1.0F, 12.0F, 5.0F, 0.0F, false);
		body.texOffs(23, 32).addBox(-3.5F, -0.1F, 1.5F, 7.0F, 12.0F, 1.0F, 0.0F, false);
		body.texOffs(38, 0).addBox(-5.75F, 10.9F, 2.525F, 11.0F, 5.0F, 0.0F, 0.0F, false);

		Body_r1 = new ModelRenderer(this);
		Body_r1.setPos(5.0F, -0.25F, 0.0F);
		body.addChild(Body_r1);
		setRotationAngle(Body_r1, 0.0F, 0.0F, -0.0873F);
		Body_r1.texOffs(0, 6).addBox(0.0F, 0.001F, -3.5F, 6.0F, 0.0F, 7.0F, 0.0F, false);

		Body_r2 = new ModelRenderer(this);
		Body_r2.setPos(-5.0F, -0.25F, 0.0F);
		body.addChild(Body_r2);
		setRotationAngle(Body_r2, 0.0F, 0.0F, 0.0873F);
		Body_r2.texOffs(12, 6).addBox(-6.0F, 0.001F, -3.5F, 6.0F, 0.0F, 7.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(6.5875F, 7.45F, 0.0063F);
		body.addChild(leftArm);
		leftArm.texOffs(43, 11).addBox(-2.8375F, 1.55F, -2.4813F, 5.0F, 1.0F, 5.0F, 0.0F, false);
		leftArm.texOffs(20, 17).addBox(-2.5875F, -7.45F, -2.0063F, 4.0F, 11.0F, 4.0F, 0.1F, false);
		leftArm.texOffs(54, 56).addBox(1.0125F, -0.65F, -2.0062F, 1.0F, 4.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(24, 54).addBox(-2.5875F, -1.95F, -2.0063F, 4.0F, 1.0F, 4.0F, 0.3F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		body.addChild(rightArm);
		rightArm.texOffs(43, 5).addBox(-3.5F, 7.0F, -2.475F, 5.0F, 1.0F, 5.0F, 0.0F, false);
		rightArm.texOffs(0, 21).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.1F, false);
		rightArm.texOffs(0, 55).addBox(-3.0F, 3.5F, -2.0F, 4.0F, 1.0F, 4.0F, 0.3F, false);
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