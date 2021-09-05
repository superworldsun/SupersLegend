package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class MagicArmorModel extends BipedModel<LivingEntity>
{
	private final EquipmentSlotType armorSlot;
	private final ModelRenderer head_r1;
	private final ModelRenderer head_r2;
	private final ModelRenderer head_r3;
	private final ModelRenderer head_r4;
	private final ModelRenderer head_r5;
	private final ModelRenderer head_r6;
	private final ModelRenderer leftShoe;
	private final ModelRenderer cube_r1;
	private final ModelRenderer rightShoe;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	
	public MagicArmorModel(EquipmentSlotType armorSlot)
	{
		super(0F);
		
		texWidth = 160;
		texHeight = 160;
		
		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(22, 23).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.1F, false);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(0, 16).addBox(-4.0F, -8.2F, -2.0F, 8.0F, 8.0F, 7.0F, 0.2F, false);
		head.texOffs(0, 16).addBox(-1.5F, -9.9F, -4.2F, 3.0F, 4.0F, 0.0F, 0.0F, false);
		
		head_r1 = new ModelRenderer(this);
		head_r1.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(head_r1);
		setRotationAngle(head_r1, -1.1781F, 0.0F, 0.0F);
		head_r1.texOffs(24, 4).addBox(-1.0F, -3.0F, 8.8F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		
		head_r2 = new ModelRenderer(this);
		head_r2.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(head_r2);
		setRotationAngle(head_r2, -1.1345F, 0.0F, 0.0F);
		head_r2.texOffs(60, 65).addBox(-1.5F, -3.4F, 6.9F, 3.0F, 4.0F, 2.0F, 0.0F, false);
		
		head_r3 = new ModelRenderer(this);
		head_r3.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(head_r3);
		setRotationAngle(head_r3, -1.0908F, 0.0F, 0.0F);
		head_r3.texOffs(62, 58).addBox(-2.0F, -3.9F, 5.7F, 4.0F, 5.0F, 2.0F, 0.0F, false);
		
		head_r4 = new ModelRenderer(this);
		head_r4.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(head_r4);
		setRotationAngle(head_r4, -1.0036F, 0.0F, 0.0F);
		head_r4.texOffs(62, 0).addBox(-2.5F, -4.4F, 4.3F, 5.0F, 6.0F, 2.0F, 0.0F, false);
		
		head_r5 = new ModelRenderer(this);
		head_r5.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(head_r5);
		setRotationAngle(head_r5, -0.8727F, 0.0F, 0.0F);
		head_r5.texOffs(32, 49).addBox(-3.0F, -4.9F, 1.9F, 6.0F, 7.0F, 3.0F, 0.0F, false);
		
		head_r6 = new ModelRenderer(this);
		head_r6.setPos(0.0F, -4.2F, 2.0F);
		head.addChild(head_r6);
		setRotationAngle(head_r6, -0.6545F, 0.0F, 0.0F);
		head_r6.texOffs(32, 0).addBox(-3.5F, -4.7F, -0.2F, 7.0F, 7.0F, 4.0F, 0.0F, false);
		
		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 31).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		body.texOffs(46, 22).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, 0.3F, false);
		body.texOffs(27, 11).addBox(-4.5F, 0.0F, -2.7F, 9.0F, 6.0F, 5.0F, 0.0F, false);
		body.texOffs(6, 6).addBox(-0.5F, 1.2F, -2.8F, 1.0F, 1.0F, 0.0F, 0.0F, false);
		body.texOffs(0, 0).addBox(-1.5F, 4.0F, -2.6F, 3.0F, 8.0F, 0.0F, 0.0F, false);
		
		leftShoe = new ModelRenderer(this);
		leftShoe.setPos(2.0F, 12.0F, 0.0F);
		leftShoe.texOffs(16, 49).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		leftShoe.texOffs(24, 2).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		leftShoe.texOffs(28, 49).addBox(-1.0F, 10.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		leftShoe.texOffs(12, 49).addBox(-1.0F, 11.0F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, 0.0F, -0.1F);
		leftShoe.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.1309F, 0.0F, 0.0F);
		cube_r1.texOffs(36, 35).addBox(-1.5F, 6.0F, -3.0F, 3.0F, 2.0F, 0.0F, 0.2F, false);
		
		rightShoe = new ModelRenderer(this);
		rightShoe.setPos(-2.0F, 12.0F, 0.0F);
		rightShoe.texOffs(0, 47).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		rightShoe.texOffs(0, 20).addBox(-1.0F, 11.0F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		rightShoe.texOffs(24, 0).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		rightShoe.texOffs(20, 33).addBox(-1.0F, 10.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		
		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, 0.0F, -0.1F);
		rightShoe.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.1309F, 0.0F, 0.0F);
		cube_r2.texOffs(36, 33).addBox(-1.5F, 6.0F, -3.0F, 3.0F, 2.0F, 0.0F, 0.2F, false);
		
		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(40, 33).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(16, 65).addBox(1.2F, 2.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.1F, false);
		leftArm.texOffs(62, 45).addBox(1.2F, 5.0F, -2.0F, 2.0F, 3.0F, 4.0F, 0.1F, false);
		
		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, 0.0F, 0.0F);
		leftArm.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, -0.0873F);
		cube_r3.texOffs(46, 23).addBox(-0.2F, -3.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.2F, false);
		
		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(0.0F, 0.0F, 0.0F);
		leftArm.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, -0.1309F);
		cube_r4.texOffs(64, 28).addBox(0.8F, 3.3F, -2.0F, 2.0F, 1.0F, 4.0F, 0.1F, false);
		
		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(0.0F, 0.0F, 0.0F);
		leftArm.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, 0.0436F);
		cube_r5.texOffs(28, 61).addBox(-0.3F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.2F, false);
		
		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(24, 33).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		rightArm.texOffs(0, 63).addBox(-3.2F, 2.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.1F, false);
		rightArm.texOffs(62, 12).addBox(-3.2F, 5.0F, -2.0F, 2.0F, 3.0F, 4.0F, 0.1F, false);
		
		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.0F, 0.0F, 0.0F);
		rightArm.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 0.0F, 0.1309F);
		cube_r6.texOffs(8, 64).addBox(-2.8F, 3.3F, -2.0F, 2.0F, 1.0F, 4.0F, 0.1F, false);
		
		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(0.0F, 0.0F, 0.0F);
		rightArm.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.0F, -0.0436F);
		cube_r7.texOffs(46, 58).addBox(-3.7F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.2F, false);
		
		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(0.0F, 0.0F, 0.0F);
		rightArm.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.0F, 0.0873F);
		cube_r8.texOffs(12, 42).addBox(0.2F, -3.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.2F, false);
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(23, 16).addBox(-1.8F, 0.2F, -2.3F, 1.0F, 6.0F, 0.0F, 0.3F, false);
		
		cube_r9 = new ModelRenderer(this);
		cube_r9.setPos(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, 0.0F, -0.1309F);
		cube_r9.texOffs(56, 37).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.3F, false);
		cube_r9.texOffs(50, 49).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.2F, false);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(6, 0).addBox(0.8F, 0.2F, -2.3F, 1.0F, 6.0F, 0.0F, 0.3F, false);
		
		cube_r10 = new ModelRenderer(this);
		cube_r10.setPos(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 0.0F, 0.1309F);
		cube_r10.texOffs(52, 29).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.3F, false);
		cube_r10.texOffs(50, 7).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.2F, false);
		
		if (armorSlot == EquipmentSlotType.FEET)
		{
			rightLeg = rightShoe;
			leftLeg = leftShoe;
		}
		
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
