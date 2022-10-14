package com.superworldsun.superslegend.client.model.player;

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

public class ZoraPlayerModel extends PlayerModel<AbstractClientPlayerEntity> implements IHandRenderer
{
	private final ModelRenderer Head_r1;
	private final ModelRenderer Head_r2;
	private final ModelRenderer Head_r3;
	private final ModelRenderer RightArm_r1;
	private final ModelRenderer LeftArm_r1;
	
	public ZoraPlayerModel()
	{
		super(0.0F, false);
		texWidth = 128;
		texHeight = 128;
		
		head = new ModelRenderer(this);
		head.setPos(0.0F, -8.0F, 2.0F);
		head.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
		head.texOffs(0, 55).addBox(5.0F, -6.0F, -4.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
		head.texOffs(50, 28).addBox(-6.0F, -6.0F, -4.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(0.01F, -5.0F, -8.0F, 0.0F, 4.0F, 3.0F, 0.0F, false);
		
		Head_r1 = new ModelRenderer(this);
		Head_r1.setPos(-3.0F, -19.075F, -34.8401F);
		head.addChild(Head_r1);
		setRotationAngle(Head_r1, -1.5708F, 0.0F, 0.0F);
		Head_r1.texOffs(40, 0).addBox(1.0F, -45.0F, 23.0F, 4.0F, 4.0F, 8.0F, 0.0F, false);
		
		Head_r2 = new ModelRenderer(this);
		Head_r2.setPos(-2.0F, -8.8959F, -32.6536F);
		head.addChild(Head_r2);
		setRotationAngle(Head_r2, -1.3526F, 0.0F, 0.0F);
		Head_r2.texOffs(0, 41).addBox(-1.0F, -40.0F, 14.0F, 6.0F, 6.0F, 8.0F, 0.0F, false);
		
		Head_r3 = new ModelRenderer(this);
		Head_r3.setPos(-1.0F, 14.6884F, -29.3436F);
		head.addChild(Head_r3);
		setRotationAngle(Head_r3, -0.829F, 0.0F, 0.0F);
		Head_r3.texOffs(32, 12).addBox(-3.0F, -42.0F, 5.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		
		body = new ModelRenderer(this);
		body.setPos(0.0F, 8.0F, 2.0F);
		body.texOffs(0, 20).addBox(-5.5F, -16.0F, -3.0F, 11.0F, 16.0F, 5.0F, 0.0F, false);
		
		rightArm = new ModelRenderer(this);
		rightArm.setPos(-7.5724F, -5.9024F, 1.25F);
		rightArm.texOffs(23, 52).addBox(-1.9276F, -2.0976F, -2.25F, 4.0F, 18.0F, 5.0F, 0.0F, true);
		
		RightArm_r1 = new ModelRenderer(this);
		RightArm_r1.setPos(7.5724F, 29.9024F, 0.75F);
		rightArm.addChild(RightArm_r1);
		setRotationAngle(RightArm_r1, 0.0F, 0.0F, -0.48F);
		RightArm_r1.texOffs(32, 28).addBox(-6.0F, -43.0F, -1.0F, 9.0F, 24.0F, 0.01F, 0.0F, true);
		
		leftArm = new ModelRenderer(this);
		leftArm.setPos(7.5724F, -5.9024F, 1.25F);
		leftArm.texOffs(23, 52).addBox(-2.0724F, -2.0976F, -2.25F, 4.0F, 18.0F, 5.0F, 0.0F, false);
		
		LeftArm_r1 = new ModelRenderer(this);
		LeftArm_r1.setPos(-7.5724F, 29.9024F, 0.75F);
		leftArm.addChild(LeftArm_r1);
		setRotationAngle(LeftArm_r1, 0.0F, 0.0F, 0.48F);
		LeftArm_r1.texOffs(32, 28).addBox(-3.0F, -43.0F, -1.0F, 9.0F, 24.0F, 0.01F, 0.0F, false);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-3.0F, 8.25F, 1.5F);
		rightLeg.texOffs(45, 47).addBox(-2.5F, -0.25F, -2.5F, 5.0F, 16.0F, 5.0F, 0.0F, true);
		rightLeg.texOffs(30, 0).addBox(-2.5F, 12.75F, -5.5F, 5.0F, 3.0F, 3.0F, 0.0F, true);
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(3.0F, 8.25F, 1.5F);
		leftLeg.texOffs(45, 47).addBox(-2.5F, -0.25F, -2.5F, 5.0F, 16.0F, 5.0F, 0.0F, false);
		leftLeg.texOffs(30, 0).addBox(-2.5F, 12.75F, -5.5F, 5.0F, 3.0F, 3.0F, 0.0F, false);
	}
	
	@Override
	public void setupAnim(AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		boolean flag = player.getFallFlyingTicks() > 4;
		boolean flag1 = player.isVisuallySwimming();
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		
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
				this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, headPitch * ((float) Math.PI / 180F));
			}
		}
		else
		{
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
		}
		
		this.body.yRot = 0F;
		this.rightArm.z = 1.25F;
		this.rightArm.x = -7.5724F;
		this.leftArm.z = 1.25F;
		this.leftArm.x = 7.5724F;
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
		
		this.leftArm.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.rightArm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
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
		
		this.setupAttackAnimation(player, ageInTicks);
		
		if (this.crouching)
		{
			this.body.xRot = 0.5F;
			this.rightArm.xRot += 0.4F;
			this.leftArm.xRot += 0.4F;
			this.rightLeg.z = 0.5F;
			this.leftLeg.z = 0.5F;
			this.rightLeg.y = 8.45F;
			this.leftLeg.y = 8.45F;
			this.head.y = -2.8F;
			this.head.z = -5.0F;
			this.body.y = 11.2F;
			this.leftArm.y = -1.1F;
			this.rightArm.y = -1.1F;
			this.leftArm.z = -5.75F;
			this.rightArm.z = -5.75F;
		}
		
		ModelHelper.bobArms(this.rightArm, this.leftArm, ageInTicks);
		
		if (this.swimAmount > 0.0F)
		{
			float f1 = limbSwing % 26.0F;
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
			
			this.leftLeg.xRot = MathHelper.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * MathHelper.cos(limbSwing * 0.33333334F + (float) Math.PI));
			this.rightLeg.xRot = MathHelper.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * MathHelper.cos(limbSwing * 0.33333334F));
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
	
	@Override
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
	
	@Override
	public void renderFirstPersonHand(MatrixStack matrix, IRenderTypeBuffer buffer, int color, AbstractClientPlayerEntity player, ModelRenderer hand, ModelRenderer handOverlay,
			ResourceLocation texture)
	{
		hand.xRot = 0;
		hand.x = 0.0F;
		hand.y = 2.0F;
		hand.z = 3.0F;
		hand.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(texture)), color, OverlayTexture.NO_OVERLAY);
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
		matrixStack.translate(sideShift * -0.2375F, 0.16F, -0.93F);
		Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemStack, transformType, leftHand, matrixStack, renderTypeBuffer, light);
	}
}