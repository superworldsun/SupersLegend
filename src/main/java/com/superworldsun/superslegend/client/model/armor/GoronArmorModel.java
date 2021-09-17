package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class GoronArmorModel extends BipedModel<LivingEntity>
{
	private final EquipmentSlotType armorSlot;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;

	public GoronArmorModel(EquipmentSlotType armorSlot)
	{
		super(0F);
		
		texWidth = 144;
		texHeight = 144;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(0, 16).addBox(-4.0F, -8.2F, -2.0F, 8.0F, 8.0F, 7.0F, 0.2F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(cube_r1);
		setRotationAngle(cube_r1, -1.1781F, 0.0F, 0.0F);
		cube_r1.texOffs(0, 16).addBox(-1.0F, -3.0F, 8.8F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(cube_r2);
		setRotationAngle(cube_r2, -1.1345F, 0.0F, 0.0F);
		cube_r2.texOffs(16, 31).addBox(-1.5F, -3.4F, 6.9F, 3.0F, 4.0F, 2.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(cube_r3);
		setRotationAngle(cube_r3, -1.0908F, 0.0F, 0.0F);
		cube_r3.texOffs(16, 57).addBox(-2.0F, -3.9F, 5.7F, 4.0F, 5.0F, 2.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(cube_r4);
		setRotationAngle(cube_r4, -1.0036F, 0.0F, 0.0F);
		cube_r4.texOffs(44, 57).addBox(-2.5F, -4.4F, 4.3F, 5.0F, 6.0F, 2.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.8727F, 0.0F, 0.0F);
		cube_r5.texOffs(48, 38).addBox(-3.0F, -4.9F, 1.9F, 6.0F, 7.0F, 3.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.6545F, 0.0F, 0.0F);
		cube_r6.texOffs(28, 12).addBox(-3.5F, -4.7F, -0.2F, 7.0F, 7.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(26, 27).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, 0.2F, false);
		body.texOffs(46, 19).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, 0.1F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(16, 41).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(28, 55).addBox(-0.7F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.2F, false);
		leftArm.texOffs(0, 47).addBox(-0.9F, 3.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(0, 31).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		rightArm.texOffs(0, 55).addBox(-3.3F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.2F, false);
		rightArm.texOffs(24, 0).addBox(-3.1F, 3.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(32, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.0F, -0.2618F);
		cube_r7.texOffs(50, 25).addBox(-3.3F, -0.2F, -2.0F, 5.0F, 5.0F, 4.0F, 0.2F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(46, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.0F, 0.2618F);
		cube_r8.texOffs(48, 48).addBox(-1.8F, -0.2F, -2.0F, 5.0F, 5.0F, 4.0F, 0.2F, false);
		
		this.armorSlot = armorSlot;
	}
	
	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_,
			float p_225598_7_, float p_225598_8_)
	{
		if (armorSlot == EquipmentSlotType.LEGS)
		{
			body.visible = false;
		}
		
		super.renderToBuffer(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
