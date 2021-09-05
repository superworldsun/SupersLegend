package com.superworldsun.superslegend.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class IronBootsModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	
	public IronBootsModel()
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
		cube_r1.setPos(1.8F, 9.5F, 0.0F);
		leftLeg.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, 0.1309F);
		cube_r1.texOffs(16, 20).addBox(-4.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
		
		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-1.8F, 9.5F, 0.0F);
		leftLeg.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, -0.1309F);
		cube_r2.texOffs(22, 22).addBox(3.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
		
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
		
		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(-1.8F, 9.5F, 0.0F);
		rightLeg.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, -0.1309F);
		cube_r3.texOffs(6, 20).addBox(3.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
		
		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(1.8F, 9.5F, 0.0F);
		rightLeg.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, 0.1309F);
		cube_r4.texOffs(0, 18).addBox(-4.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
