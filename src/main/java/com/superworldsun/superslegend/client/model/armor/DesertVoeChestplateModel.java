package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class DesertVoeChestplateModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer Body_r1;
	private final ModelRenderer Body_r2;
	private final ModelRenderer Body_r3;
	private final ModelRenderer Body_r4;
	private final ModelRenderer Body_r5;
	private final ModelRenderer LeftArm_r1;
	
	public DesertVoeChestplateModel()
	{
		super(0.0F);
		
		texWidth = 64;
		texHeight = 64;
		
		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 35).addBox(4.501F, 0.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);
		body.texOffs(28, 6).addBox(4.501F, 5.25F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, false);
		body.texOffs(50, 50).addBox(1.5F, 0.0F, 2.499F, 3.0F, 4.0F, 0.0F, 0.0F, false);
		body.texOffs(12, 9).addBox(0.5F, 1.0F, 2.499F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		body.texOffs(38, 37).addBox(2.5F, 4.0F, 2.499F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		body.texOffs(38, 36).addBox(2.5F, 4.0F, -2.501F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		body.texOffs(50, 46).addBox(1.5F, 0.0F, -2.501F, 3.0F, 4.0F, 0.0F, 0.0F, false);
		body.texOffs(5, 0).addBox(0.5F, 1.0F, -2.501F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		
		Body_r1 = new ModelRenderer(this);
		Body_r1.setPos(1.0F, 2.75F, -2.526F);
		body.addChild(Body_r1);
		setRotationAngle(Body_r1, 0.0F, 0.0F, -0.5236F);
		Body_r1.texOffs(34, 24).addBox(-6.05F, -1.25F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);
		Body_r1.texOffs(43, 0).addBox(-6.05F, -1.25F, 5.0F, 7.0F, 1.0F, 0.0F, 0.0F, false);
		Body_r1.texOffs(0, 12).addBox(-2.3F, -1.25F, -0.025F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		Body_r1.texOffs(0, 0).addBox(-1.8F, -1.75F, -0.05F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		Body_r1.texOffs(38, 35).addBox(-1.05F, -1.25F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		Body_r1.texOffs(24, 4).addBox(-6.05F, -1.25F, 0.0F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		
		Body_r2 = new ModelRenderer(this);
		Body_r2.setPos(-1.5758F, 4.7758F, -2.6475F);
		body.addChild(Body_r2);
		setRotationAngle(Body_r2, 0.0F, 0.0611F, 1.0036F);
		Body_r2.texOffs(48, 23).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		
		Body_r3 = new ModelRenderer(this);
		Body_r3.setPos(-1.574F, 4.7778F, -2.2809F);
		body.addChild(Body_r3);
		setRotationAngle(Body_r3, 0.0F, 0.0F, 1.0036F);
		Body_r3.texOffs(32, 18).addBox(-5.5F, -0.5F, -0.275F, 9.0F, 1.0F, 0.0F, 0.0F, false);
		Body_r3.texOffs(12, 31).addBox(-5.5F, -0.5F, -0.275F, 0.0F, 1.0F, 5.0F, 0.0F, false);
		Body_r3.texOffs(32, 19).addBox(-5.5F, -0.5F, 4.725F, 9.0F, 1.0F, 0.0F, 0.0F, false);
		
		Body_r4 = new ModelRenderer(this);
		Body_r4.setPos(1.4186F, 8.3826F, -2.5559F);
		body.addChild(Body_r4);
		setRotationAngle(Body_r4, 0.0F, 0.0F, 0.5672F);
		Body_r4.texOffs(48, 7).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		Body_r4.texOffs(48, 17).addBox(-1.5F, -0.5F, 5.0F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		
		Body_r5 = new ModelRenderer(this);
		Body_r5.setPos(3.9934F, 9.5155F, -2.5559F);
		body.addChild(Body_r5);
		setRotationAngle(Body_r5, 0.0F, 0.0F, 0.2618F);
		Body_r5.texOffs(16, 23).addBox(-1.5F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		Body_r5.texOffs(30, 34).addBox(0.5F, -0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);
		Body_r5.texOffs(34, 27).addBox(-1.5F, -0.5F, 5.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		
		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(0, 9).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.01F, false);
		leftArm.texOffs(35, 48).addBox(1.6F, 4.5F, -2.5F, 2.0F, 5.0F, 1.0F, 0.0F, false);
		leftArm.texOffs(49, 41).addBox(1.6F, 4.5F, -2.75F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		leftArm.texOffs(0, 35).addBox(1.6F, 5.0F, -2.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftArm.texOffs(34, 25).addBox(1.6F, 5.0F, 1.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftArm.texOffs(30, 40).addBox(1.6F, 4.5F, 1.75F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		leftArm.texOffs(29, 48).addBox(1.6F, 4.5F, 1.5F, 2.0F, 5.0F, 1.0F, 0.0F, false);
		leftArm.texOffs(42, 43).addBox(2.6F, 4.5F, -1.5F, 1.0F, 5.0F, 3.0F, 0.0F, false);
		leftArm.texOffs(12, 43).addBox(2.85F, 4.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		leftArm.texOffs(12, 30).addBox(-1.4F, 7.75F, -2.5F, 0.0F, 1.0F, 5.0F, 0.0F, false);
		leftArm.texOffs(19, 43).addBox(-1.4F, 7.75F, 2.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		leftArm.texOffs(44, 39).addBox(-1.4F, 7.75F, -2.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		leftArm.texOffs(0, 34).addBox(-1.4F, 5.25F, -2.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		leftArm.texOffs(24, 0).addBox(-1.4F, 5.25F, -2.5F, 0.0F, 1.0F, 5.0F, 0.0F, false);
		leftArm.texOffs(34, 30).addBox(-1.4F, 5.25F, 2.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		
		LeftArm_r1 = new ModelRenderer(this);
		LeftArm_r1.setPos(2.75F, 0.5F, 0.0F);
		leftArm.addChild(LeftArm_r1);
		setRotationAngle(LeftArm_r1, 0.0F, 0.0F, -0.3491F);
		LeftArm_r1.texOffs(15, 50).addBox(-1.25F, -3.25F, 2.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(0, 9).addBox(-0.75F, -0.25F, 1.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(16, 20).addBox(-0.75F, -0.25F, -2.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(49, 28).addBox(-1.25F, -3.25F, 2.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(49, 39).addBox(-1.25F, -3.25F, -3.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(8, 49).addBox(-1.25F, -1.25F, 2.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(37, 43).addBox(-1.25F, -1.25F, -3.25F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(36, 4).addBox(-1.25F, -2.25F, 2.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(36, 6).addBox(-1.25F, -2.25F, -3.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(50, 30).addBox(-1.25F, -3.25F, -3.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		LeftArm_r1.texOffs(5, 40).addBox(0.25F, -0.25F, -2.5F, 1.0F, 2.0F, 5.0F, 0.0F, false);
		LeftArm_r1.texOffs(16, 31).addBox(1.0F, -3.25F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		LeftArm_r1.texOffs(8, 28).addBox(1.0F, -1.25F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		LeftArm_r1.texOffs(28, 4).addBox(0.75F, -3.25F, -3.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		
		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(32, 13).addBox(-2.6F, 5.0F, 1.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightArm.texOffs(32, 15).addBox(-2.6F, 5.0F, -2.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightArm.texOffs(38, 30).addBox(-3.0F, 1.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.1F, false);
		rightArm.texOffs(0, 25).addBox(-3.6F, 4.5F, -2.5F, 2.0F, 5.0F, 1.0F, 0.0F, false);
		rightArm.texOffs(24, 34).addBox(-3.6F, 4.5F, -2.75F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		rightArm.texOffs(16, 31).addBox(-3.6F, 4.5F, 1.75F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		rightArm.texOffs(0, 0).addBox(-3.6F, 4.5F, -1.5F, 1.0F, 5.0F, 3.0F, 0.0F, false);
		rightArm.texOffs(30, 42).addBox(-3.85F, 4.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		rightArm.texOffs(8, 25).addBox(-3.6F, 4.5F, 1.5F, 2.0F, 5.0F, 1.0F, 0.0F, false);
		rightArm.texOffs(37, 45).addBox(-1.6F, 7.75F, 2.499F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		rightArm.texOffs(24, 2).addBox(1.4F, 7.75F, -2.5F, 0.0F, 1.0F, 5.0F, 0.0F, false);
		rightArm.texOffs(7, 47).addBox(-1.6F, 7.75F, -2.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		rightArm.texOffs(36, 9).addBox(-1.6F, 5.25F, -2.5F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		rightArm.texOffs(24, 1).addBox(1.4F, 5.25F, -2.5F, 0.0F, 1.0F, 5.0F, 0.0F, false);
		rightArm.texOffs(24, 36).addBox(-1.6F, 5.25F, 2.499F, 3.0F, 1.0F, 0.0F, 0.0F, false);
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