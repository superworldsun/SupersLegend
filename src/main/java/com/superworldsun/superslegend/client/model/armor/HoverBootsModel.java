package com.superworldsun.superslegend.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class HoverBootsModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	
	public HoverBootsModel()
	{
		super(0F);
		
		texWidth = 64;
		texHeight = 64;
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(0, 9).addBox(-2.0F, 6.9F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
		leftLeg.texOffs(12, 14).addBox(-2.0F, 9.8F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
		leftLeg.texOffs(0, 1).addBox(-1.0F, 7.9F, 2.0F, 2.0F, 1.0F, 0.0F, 0.2F, false);
		leftLeg.texOffs(6, 19).addBox(-1.5F, 6.8F, 2.0F, 3.0F, 1.0F, 0.0F, 0.2F, false);
		leftLeg.texOffs(12, 2).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(12, 22).addBox(-1.5F, 10.9F, -4.4F, 3.0F, 1.0F, 1.0F, 0.1F, false);
		leftLeg.texOffs(21, 1).addBox(-1.5F, 11.0F, -4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(21, 3).addBox(-1.5F, 10.0F, -3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(2.9F, 12.0F, 2.0F);
		leftLeg.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.6981F, 0.0F, 0.0F);
		cube_r1.texOffs(0, 26).addBox(-1.0F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);
		cube_r1.texOffs(0, 26).addBox(-4.6F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);
		
		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-1.2F, 11.5F, -4.2F);
		leftLeg.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.3927F, 0.8727F, 0.0F);
		cube_r2.texOffs(18, 26).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		
		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(1.3F, 11.5F, -4.2F);
		leftLeg.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.3927F, -0.8727F, 0.0F);
		cube_r3.texOffs(18, 26).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		
		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(1.8F, 9.5F, 0.0F);
		leftLeg.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, 0.1309F);
		cube_r4.texOffs(16, 20).addBox(-4.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
		
		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-1.8F, 9.5F, 0.0F);
		leftLeg.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, -0.1309F);
		cube_r5.texOffs(22, 22).addBox(3.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(0, 0).addBox(-2.0F, 6.9F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
		rightLeg.texOffs(12, 5).addBox(-2.0F, 9.8F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
		rightLeg.texOffs(12, 11).addBox(-1.5F, 10.0F, -3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(12, 0).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(19, 12).addBox(-1.5F, 10.9F, -4.4F, 3.0F, 1.0F, 1.0F, 0.1F, false);
		rightLeg.texOffs(12, 20).addBox(-1.5F, 11.0F, -4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(0, 0).addBox(-1.0F, 7.9F, 2.0F, 2.0F, 1.0F, 0.0F, 0.2F, false);
		rightLeg.texOffs(6, 18).addBox(-1.5F, 6.8F, 2.0F, 3.0F, 1.0F, 0.0F, 0.2F, false);
		
		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.7F, 12.0F, 2.0F);
		rightLeg.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.6981F, 0.0F, 0.0F);
		cube_r6.texOffs(0, 26).addBox(1.0F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, true);
		cube_r6.texOffs(0, 26).addBox(-2.6F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, true);
		
		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(-1.3F, 11.5F, -4.2F);
		rightLeg.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.3927F, 0.8727F, 0.0F);
		cube_r7.texOffs(18, 26).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		
		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(1.2F, 11.5F, -4.2F);
		rightLeg.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.3927F, -0.8727F, 0.0F);
		cube_r8.texOffs(18, 26).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		
		cube_r9 = new ModelRenderer(this);
		cube_r9.setPos(-1.8F, 9.5F, 0.0F);
		rightLeg.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, 0.0F, -0.1309F);
		cube_r9.texOffs(6, 20).addBox(3.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
		
		cube_r10 = new ModelRenderer(this);
		cube_r10.setPos(1.8F, 9.5F, 0.0F);
		rightLeg.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 0.0F, 0.1309F);
		cube_r10.texOffs(0, 18).addBox(-4.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
