package com.superworldsun.superslegend.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.interfaces.IHandRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class GoronPlayerModel extends PlayerModel<AbstractClientPlayerEntity> implements IHandRenderer
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
	private final ModelRenderer cube_r13;
	private final ModelRenderer cube_r14;
	private final ModelRenderer cube_r15;
	private final ModelRenderer cube_r16;
	private final ModelRenderer cube_r17;
	private final ModelRenderer cube_r18;
	private final ModelRenderer cube_r19;
	private final ModelRenderer cube_r20;
	private final ModelRenderer cube_r21;
	private final ModelRenderer cube_r22;
	
	public GoronPlayerModel()
	{
		super(0, false);
		texWidth = 128;
		texHeight = 128;
		
		head = new ModelRenderer(this);
		head.setPos(0.0814F, -15.0861F, -0.3284F);
		head.texOffs(0, 48).addBox(-5.0814F, -7.9139F, -4.6716F, 10.0F, 10.0F, 11.0F, 0.0F, false);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-6.5814F, 11.0861F, 1.3284F);
		head.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0873F, 0.0F, 0.0F);
		cube_r1.texOffs(84, 78).addBox(4.0F, -10.0F, 9.0F, 5.0F, 10.0F, 4.0F, 0.0F, false);
		
		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-6.5814F, 11.0861F, 1.3284F);
		head.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.6981F, 0.0F, 0.0F);
		cube_r2.texOffs(62, 0).addBox(3.0F, -10.0F, 11.0F, 7.0F, 10.0F, 6.0F, 0.0F, false);
		
		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(-6.5814F, 11.0861F, 1.3284F);
		head.addChild(cube_r3);
		setRotationAngle(cube_r3, 1.9635F, 0.0F, 0.0F);
		cube_r3.texOffs(42, 36).addBox(1.0F, 5.0F, 6.25F, 11.0F, 8.0F, 12.0F, 0.0F, false);
		
		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(8.4186F, 11.0861F, 1.3284F);
		head.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.2954F, 0.3687F, -0.0904F);
		cube_r4.texOffs(0, 0).addBox(-14.5F, -17.0F, -12.5F, 5.0F, 9.0F, 0.0F, 0.0F, false);
		
		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-6.5814F, 11.0861F, 1.3284F);
		head.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.2954F, -0.3687F, 0.0904F);
		cube_r5.texOffs(0, 28).addBox(7.5F, -17.0F, -12.0F, 5.0F, 9.0F, 0.0F, 0.0F, false);
		
		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(-6.5814F, 10.3394F, 3.8026F);
		head.addChild(cube_r6);
		setRotationAngle(cube_r6, 1.0908F, 0.0F, 0.0F);
		cube_r6.texOffs(7, 0).addBox(3.5F, -17.0F, 9.25F, 2.0F, 0.0F, 3.0F, 0.0F, false);
		cube_r6.texOffs(7, 3).addBox(7.5F, -17.0F, 9.25F, 2.0F, 0.0F, 3.0F, 0.0F, false);
		
		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(-6.5814F, 11.0861F, 1.3284F);
		head.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.2618F, 0.0F, 0.0F);
		cube_r7.texOffs(8, 6).addBox(7.5F, -17.0F, -11.75F, 2.0F, 0.0F, 2.0F, 0.0F, false);
		cube_r7.texOffs(8, 8).addBox(3.5F, -17.0F, -11.75F, 2.0F, 0.0F, 2.0F, 0.0F, false);
		
		body = new ModelRenderer(this);
		body.setPos(-0.0099F, -0.9688F, 0.1092F);
		body.texOffs(0, 0).addBox(-7.9901F, -5.0313F, -7.1092F, 16.0F, 13.0F, 15.0F, 0.0F, false);
		body.texOffs(0, 28).addBox(-6.9901F, -12.0313F, -5.1092F, 14.0F, 7.0F, 13.0F, 0.0F, false);
		body.texOffs(47, 0).addBox(-4.9901F, 7.9688F, -3.1092F, 10.0F, 4.0F, 0.0F, 0.0F, false);
		body.texOffs(0, 9).addBox(-1.4901F, -10.0313F, -8.1092F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		
		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(3.3246F, -11.5313F, -4.7411F);
		body.addChild(cube_r8);
		setRotationAngle(cube_r8, -3.1416F, 0.3927F, -3.1416F);
		cube_r8.texOffs(0, 9).addBox(1.0F, 0.75F, -16.75F, 3.0F, 3.0F, 3.0F, 0.0F, true);
		
		cube_r9 = new ModelRenderer(this);
		cube_r9.setPos(-1.3047F, -11.5313F, -4.7411F);
		body.addChild(cube_r9);
		setRotationAngle(cube_r9, -3.1416F, -0.3927F, 3.1416F);
		cube_r9.texOffs(0, 9).addBox(-2.0F, 0.75F, -16.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		
		cube_r10 = new ModelRenderer(this);
		cube_r10.setPos(3.3246F, -11.5313F, -4.7411F);
		body.addChild(cube_r10);
		setRotationAngle(cube_r10, -3.1416F, 1.1345F, -3.1416F);
		cube_r10.texOffs(0, 9).addBox(-6.5F, 0.0F, -15.0F, 3.0F, 3.0F, 3.0F, 0.0F, true);
		
		cube_r11 = new ModelRenderer(this);
		cube_r11.setPos(-1.3047F, -11.5313F, -4.7411F);
		body.addChild(cube_r11);
		setRotationAngle(cube_r11, -3.1416F, -1.1345F, 3.1416F);
		cube_r11.texOffs(0, 9).addBox(4.5F, 0.0F, -13.25F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		
		cube_r12 = new ModelRenderer(this);
		cube_r12.setPos(3.3246F, -11.5313F, -4.7411F);
		body.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, 1.5708F, 0.0F);
		cube_r12.texOffs(0, 9).addBox(-8.25F, -1.75F, -12.25F, 3.0F, 3.0F, 3.0F, 0.0F, true);
		
		cube_r13 = new ModelRenderer(this);
		cube_r13.setPos(-1.3047F, -11.5313F, -4.7411F);
		body.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.0F, -1.5708F, 0.0F);
		cube_r13.texOffs(0, 9).addBox(5.25F, -1.75F, -10.25F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		
		cube_r14 = new ModelRenderer(this);
		cube_r14.setPos(3.3246F, -11.5313F, -4.7411F);
		body.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.0F, 1.4399F, 0.0F);
		cube_r14.texOffs(0, 9).addBox(-6.0F, -0.75F, -11.5F, 3.0F, 3.0F, 3.0F, 0.0F, true);
		
		cube_r15 = new ModelRenderer(this);
		cube_r15.setPos(-1.3047F, -11.5313F, -4.7411F);
		body.addChild(cube_r15);
		setRotationAngle(cube_r15, 0.0F, -1.4399F, 0.0F);
		cube_r15.texOffs(0, 9).addBox(2.75F, -0.75F, -9.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		
		cube_r16 = new ModelRenderer(this);
		cube_r16.setPos(0.9216F, -10.7813F, -4.1085F);
		body.addChild(cube_r16);
		setRotationAngle(cube_r16, 0.0F, 1.0036F, 0.0F);
		cube_r16.texOffs(0, 9).addBox(-5.0F, -0.75F, -7.5F, 3.0F, 3.0F, 3.0F, 0.0F, true);
		
		cube_r17 = new ModelRenderer(this);
		cube_r17.setPos(-1.3047F, -11.5313F, -4.7411F);
		body.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.0F, -1.0036F, 0.0F);
		cube_r17.texOffs(0, 9).addBox(2.75F, 0.0F, -7.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		
		cube_r18 = new ModelRenderer(this);
		cube_r18.setPos(3.1024F, -10.7813F, -5.5086F);
		body.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.0F, 0.3054F, 0.0F);
		cube_r18.texOffs(0, 9).addBox(-7.5F, 0.0F, -4.0F, 3.0F, 3.0F, 3.0F, 0.0F, true);
		
		cube_r19 = new ModelRenderer(this);
		cube_r19.setPos(-1.0247F, -8.5313F, -5.3841F);
		body.addChild(cube_r19);
		setRotationAngle(cube_r19, 0.0F, -0.3054F, 0.0F);
		cube_r19.texOffs(0, 9).addBox(2.5F, -2.25F, -3.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		
		leftArm = new ModelRenderer(this);
		leftArm.setPos(6.0F, -13.0F, 1.0F);
		
		cube_r20 = new ModelRenderer(this);
		cube_r20.setPos(2.8934F, 5.723F, 0.0F);
		leftArm.addChild(cube_r20);
		setRotationAngle(cube_r20, 0.0F, 0.0F, -0.2182F);
		cube_r20.texOffs(84, 12).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		
		cube_r21 = new ModelRenderer(this);
		cube_r21.setPos(5.2742F, 16.4623F, 0.0F);
		leftArm.addChild(cube_r21);
		setRotationAngle(cube_r21, 0.0F, 0.0F, -0.2182F);
		cube_r21.texOffs(24, 69).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
		
		rightArm = new ModelRenderer(this);
		rightArm.setPos(-6.0F, -13.0F, 1.0F);
		
		cube_r22 = new ModelRenderer(this);
		cube_r22.setPos(-5.2742F, 16.4623F, 0.0F);
		rightArm.addChild(cube_r22);
		setRotationAngle(cube_r22, 0.0F, 0.0F, 0.2182F);
		cube_r22.texOffs(68, 78).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		cube_r22.texOffs(0, 69).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(4.0F, 5.0F, 1.0F);
		leftLeg.texOffs(48, 72).addBox(-2.75F, 2.0F, -1.75F, 5.0F, 9.0F, 5.0F, 0.0F, false);
		leftLeg.texOffs(65, 56).addBox(-3.75F, 11.0F, -2.75F, 7.0F, 4.0F, 7.0F, 0.0F, false);
		leftLeg.texOffs(51, 17).addBox(-3.25F, 14.0F, -7.0F, 6.0F, 5.0F, 11.0F, 0.0F, false);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-4.0F, 5.0F, 2.0F);
		rightLeg.texOffs(76, 33).addBox(-2.25F, 2.0F, -2.75F, 5.0F, 9.0F, 5.0F, 0.0F, false);
		rightLeg.texOffs(69, 67).addBox(-3.25F, 11.0F, -3.75F, 7.0F, 4.0F, 7.0F, 0.0F, false);
		rightLeg.texOffs(42, 56).addBox(-2.75F, 14.0F, -8.0F, 6.0F, 5.0F, 11.0F, 0.0F, false);
	}
	
	protected void setupAttackAnimation(AbstractClientPlayerEntity p_230486_1_, float p_230486_2_)
	{
		if (!(this.attackTime <= 0.0F))
		{
			HandSide handside = this.getAttackArm(p_230486_1_);
			ModelRenderer modelrenderer = this.getArm(handside);
			float f = this.attackTime;
			this.body.yRot = MathHelper.sin(MathHelper.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;
			if (handside == HandSide.LEFT)
			{
				this.body.yRot *= -1.0F;
			}
			// this.rightArm.z = MathHelper.sin(this.body.yRot) * 4.0F;
			// this.rightArm.x = -MathHelper.cos(this.body.yRot) * 4.0F;
			// this.leftArm.z = -MathHelper.sin(this.body.yRot) * 4.0F;
			// this.leftArm.x = MathHelper.cos(this.body.yRot) * 4.0F;
			this.rightArm.yRot += this.body.yRot;
			this.leftArm.yRot += this.body.yRot;
			this.leftArm.xRot += this.body.yRot;
			f = 1.0F - this.attackTime;
			f = f * f;
			f = f * f;
			f = 1.0F - f;
			float f1 = MathHelper.sin(f * (float) Math.PI);
			float f2 = MathHelper.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
			modelrenderer.xRot = (float) ((double) modelrenderer.xRot - ((double) f1 * 1.2D + (double) f2));
			modelrenderer.yRot += this.body.yRot * 2.0F;
			modelrenderer.zRot += MathHelper.sin(this.attackTime * (float) Math.PI) * -0.4F;
		}
	}
	
	@Override
	public void setupAnim(AbstractClientPlayerEntity player, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_)
	{
		boolean flag = player.getFallFlyingTicks() > 4;
		boolean flag1 = player.isVisuallySwimming();
		this.head.yRot = p_225597_5_ * ((float) Math.PI / 180F);
		
		if (flag)
		{
			this.head.xRot = (-(float) Math.PI / 4F);
		}
		else if (this.swimAmount > 0.0F)
		{
			if (flag1)
			{
				this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float) Math.PI / 4F));
			}
			else
			{
				this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, p_225597_6_ * ((float) Math.PI / 180F));
			}
		}
		else
		{
			this.head.xRot = p_225597_6_ * ((float) Math.PI / 180F);
		}
		
		this.body.yRot = 0F;
		this.rightArm.z = 1F;
		this.rightArm.x = -6F;
		this.leftArm.z = 1F;
		this.leftArm.x = 6F;
		float f = 1.0F;
		
		if (flag)
		{
			f = (float) player.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}
		
		if (f < 1.0F)
		{
			f = 1.0F;
		}
		
		this.leftArm.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float) Math.PI) * 2.0F * p_225597_3_ * 0.5F / f;
		this.rightArm.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 2.0F * p_225597_3_ * 0.5F / f;
		this.rightLeg.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_ / f;
		this.leftLeg.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float) Math.PI) * 1.4F * p_225597_3_ / f;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;
		this.leftLeg.zRot = 0.0F;
		
		if (this.riding)
		{
			this.rightArm.xRot += (-(float) Math.PI / 5F);
			this.leftArm.xRot += (-(float) Math.PI / 5F);
			this.rightLeg.xRot = -1.4137167F;
			this.rightLeg.yRot = ((float) Math.PI / 10F);
			this.rightLeg.zRot = 0.07853982F;
			this.leftLeg.xRot = -1.4137167F;
			this.leftLeg.yRot = (-(float) Math.PI / 10F);
			this.leftLeg.zRot = -0.07853982F;
		}
		
		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;
		boolean flag2 = player.getMainArm() == HandSide.RIGHT;
		boolean flag3 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
		
		if (flag2 != flag3)
		{
			this.poseLeftArm(player);
			this.poseRightArm(player);
		}
		else
		{
			this.poseRightArm(player);
			this.poseLeftArm(player);
		}
		
		this.setupAttackAnimation(player, p_225597_4_);
		
		if (this.crouching)
		{
			// this.body.xRot = 0.5F;
			// this.rightArm.xRot += 0.0F;
			// this.leftArm.xRot += 0.0F;
			// this.rightLeg.z = 3.5F;
			// this.leftLeg.z = 3.5F;
			// this.rightLeg.y = 17.0F;
			// this.leftLeg.y = 17.0F;
			// this.head.y = -5.0F;
			// this.body.y = 11.2F;
			// this.leftArm.y = 0.0F;
			// this.rightArm.y = 0.0F;
		}
		else
		{
			// this.body.xRot = 0.0F;
			// this.rightLeg.z = -0.0F;
			// this.leftLeg.z = -0.0F;
			// this.rightLeg.y = 13.0F;
			// this.leftLeg.y = 13.0F;
			// this.head.y = -5.0F;
			// this.body.y = 8.0F;
			// this.leftArm.y = 0.0F;
			// this.rightArm.y = 0.0F;
		}
		
		ModelHelper.bobArms(this.rightArm, this.leftArm, p_225597_4_);
		
		if (this.swimAmount > 0.0F)
		{
			float f1 = p_225597_2_ % 26.0F;
			HandSide handside = this.getAttackArm(player);
			float f2 = handside == HandSide.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			float f3 = handside == HandSide.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			
			if (f1 < 14.0F)
			{
				this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, 0.0F);
				this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, 0.0F);
				this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float) Math.PI);
				this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float) Math.PI);
				this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float) Math.PI + 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
				this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, (float) Math.PI - 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
			}
			else if (f1 >= 14.0F && f1 < 22.0F)
			{
				float f6 = (f1 - 14.0F) / 8.0F;
				this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float) Math.PI / 2F) * f6);
				this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, ((float) Math.PI / 2F) * f6);
				this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float) Math.PI);
				this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float) Math.PI);
				this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
				this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
			}
			else if (f1 >= 22.0F && f1 < 26.0F)
			{
				float f4 = (f1 - 22.0F) / 4.0F;
				this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f4);
				this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f4);
				this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float) Math.PI);
				this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float) Math.PI);
				this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float) Math.PI);
				this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, (float) Math.PI);
			}
			
			this.leftLeg.xRot = MathHelper.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * MathHelper.cos(p_225597_2_ * 0.33333334F + (float) Math.PI));
			this.rightLeg.xRot = MathHelper.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * MathHelper.cos(p_225597_2_ * 0.33333334F));
		}
		
		this.hat.copyFrom(this.head);
	}
	
	private float quadraticArmUpdate(float p_203068_1_)
	{
		return -65.0F * p_203068_1_ + p_203068_1_ * p_203068_1_;
	}
	
	private void poseLeftArm(AbstractClientPlayerEntity p_241655_1_)
	{
		switch (this.leftArmPose)
		{
			case EMPTY:
				this.leftArm.yRot = 0.0F;
				break;
			case BLOCK:
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
				this.leftArm.yRot = ((float) Math.PI / 6F);
				break;
			case ITEM:
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
				this.leftArm.yRot = 0.0F;
				break;
			case THROW_SPEAR:
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
				this.leftArm.yRot = 0.0F;
				break;
			case BOW_AND_ARROW:
				this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
				this.leftArm.yRot = 0.1F + this.head.yRot;
				this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				break;
			case CROSSBOW_CHARGE:
				ModelHelper.animateCrossbowCharge(this.rightArm, this.leftArm, p_241655_1_, false);
				break;
			case CROSSBOW_HOLD:
				ModelHelper.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
		}
	}
	
	private void poseRightArm(AbstractClientPlayerEntity p_241654_1_)
	{
		switch (this.rightArmPose)
		{
			case EMPTY:
				this.rightArm.yRot = 0.0F;
				break;
			case BLOCK:
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
				this.rightArm.yRot = (-(float) Math.PI / 6F);
				break;
			case ITEM:
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
				this.rightArm.yRot = 0.0F;
				break;
			case THROW_SPEAR:
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
				this.rightArm.yRot = 0.0F;
				break;
			case BOW_AND_ARROW:
				this.rightArm.yRot = -0.1F + this.head.yRot;
				this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
				this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				break;
			case CROSSBOW_CHARGE:
				ModelHelper.animateCrossbowCharge(this.rightArm, this.leftArm, p_241654_1_, true);
				break;
			case CROSSBOW_HOLD:
				ModelHelper.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
		}
		
	}
	
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
	
	/*@Override
	public void renderFirstPersonHand(MatrixStack matrix, IRenderTypeBuffer buffer, int color, AbstractClientPlayerEntity player, ModelRenderer hand, ModelRenderer handOverlay,
			ResourceLocation texture)
	{
		hand.xRot = 0;
		hand.y = 1.5F;
		hand.render(matrix, buffer.getBuffer(RenderType.entitySolid(texture)), color, OverlayTexture.NO_OVERLAY);
		handOverlay.xRot = 0;
		handOverlay.y = 1.5F;
		handOverlay.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(texture)), color, OverlayTexture.NO_OVERLAY);
	}*/
	
	@Override
	public void renderFirstPersonHand(MatrixStack matrix, IRenderTypeBuffer buffer, int color, AbstractClientPlayerEntity player, ModelRenderer hand, ModelRenderer handOverlay,
			ResourceLocation texture)
	{
		hand.xRot = 0;
		hand.x = 0.0F;
		hand.y = -7.0F;
		hand.z = 0.0F;
		hand.render(matrix, buffer.getBuffer(RenderType.entitySolid(texture)), color, OverlayTexture.NO_OVERLAY);
		handOverlay.xRot = 0;
		handOverlay.y = 1.5F;
		handOverlay.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(texture)), color, OverlayTexture.NO_OVERLAY);
	}
	
	@Override
	public void renderThirdPersonItem(LivingEntity livingEntity, ItemStack itemStack, TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer,
			int light)
	{
		boolean leftHand = handSide == HandSide.LEFT;
		int sideShift = leftHand ? -1 : 1;
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90F));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F - sideShift * 15));
		matrixStack.translate(sideShift / 16D, 0.125D, -1.3D);
		Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemStack, transformType, leftHand, matrixStack, renderTypeBuffer, light);
	}
}