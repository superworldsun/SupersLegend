package com.superworldsun.superslegend.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class PegasusBootsModel extends BipedModel<LivingEntity>
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
	private final ModelRenderer cube_r11;
	private final ModelRenderer cube_r12;
	
	public PegasusBootsModel()
	{
		super(0F);
		
		texWidth = 32;
		texHeight = 32;
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(0, 9).addBox(-2.0F, 6.7F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
		leftLeg.texOffs(12, 14).addBox(-2.0F, 6.8F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
		leftLeg.texOffs(17, 20).addBox(-2.0F, 10.8F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(0, 22).addBox(-2.0F, 11.8F, -2.0F, 4.0F, 0.0F, 1.0F, 0.2F, false);
		leftLeg.texOffs(11, 11).addBox(-1.0F, 11.8F, -4.0F, 2.0F, 0.0F, 1.0F, 0.2F, false);
		leftLeg.texOffs(0, 23).addBox(-1.0F, 10.8F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(20, 22).addBox(-1.0F, 9.8F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, 11.8F, -3.5F);
		leftLeg.addChild(cube_r1);
		setRotationAngle(cube_r1, -3.1416F, 0.0F, 3.1416F);
		cube_r1.texOffs(11, 11).addBox(-0.99F, 0.0F, -0.501F, 2.0F, 0.0F, 1.0F, 0.2F, false);
		
		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-1.6F, 10.8F, 3.5F);
		leftLeg.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.829F, 0.0F, 0.0F);
		cube_r2.texOffs(16, 7).addBox(3.802F, -6.0F, -1.0F, 0.0F, 3.0F, 4.0F, 0.0F, false);
		
		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, 11.8F, -1.0F);
		leftLeg.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -1.5708F, 0.0F);
		cube_r3.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
		
		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(0.0F, 11.8F, -1.0F);
		leftLeg.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 1.5708F, 0.0F);
		cube_r4.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
		
		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(0.0F, 11.8F, 0.0F);
		leftLeg.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 1.5708F, 0.0F);
		cube_r5.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
		
		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.0F, 11.8F, 0.0F);
		leftLeg.addChild(cube_r6);
		setRotationAngle(cube_r6, -3.1416F, 0.0F, 3.1416F);
		cube_r6.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(0, 0).addBox(-2.0F, 6.7F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
		rightLeg.texOffs(7, 20).addBox(-2.0F, 10.8F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(8, 22).addBox(-1.0F, 10.8F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(14, 22).addBox(-1.0F, 9.8F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(12, 5).addBox(-2.0F, 6.8F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
		rightLeg.texOffs(11, 11).addBox(-1.0F, 11.8F, -4.0F, 2.0F, 0.0F, 1.0F, 0.2F, false);
		
		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(0.0F, 11.8F, -3.5F);
		rightLeg.addChild(cube_r7);
		setRotationAngle(cube_r7, -3.1416F, 0.0F, 3.1416F);
		cube_r7.texOffs(11, 11).addBox(-0.99F, 0.0F, -0.501F, 2.0F, 0.0F, 1.0F, 0.2F, false);
		
		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(0.0F, 11.8F, 0.0F);
		rightLeg.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.0F, 0.0F);
		cube_r8.texOffs(8, 0).addBox(-1.999F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
		
		cube_r9 = new ModelRenderer(this);
		cube_r9.setPos(0.0F, 11.8F, 0.0F);
		rightLeg.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, -1.5708F, 0.0F);
		cube_r9.texOffs(8, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
		
		cube_r10 = new ModelRenderer(this);
		cube_r10.setPos(1.6F, 10.8F, 3.5F);
		rightLeg.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.829F, 0.0F, 0.0F);
		cube_r10.texOffs(0, 14).addBox(-3.8F, -6.0F, -1.0F, 0.0F, 3.0F, 4.0F, 0.0F, false);
		
		cube_r11 = new ModelRenderer(this);
		cube_r11.setPos(0.0F, 11.8F, -1.0F);
		rightLeg.addChild(cube_r11);
		setRotationAngle(cube_r11, -3.1416F, 0.0F, 3.1416F);
		cube_r11.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
		
		cube_r12 = new ModelRenderer(this);
		cube_r12.setPos(0.0F, 11.8F, -1.0F);
		rightLeg.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, 0.0F, 0.0F);
		cube_r12.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.2F, false);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
