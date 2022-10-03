package com.superworldsun.superslegend.client.model;

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
	private final ModelRenderer Zoralink;
	private final ModelRenderer head;
	private final ModelRenderer Head_r1;
	private final ModelRenderer Head_r2;
	private final ModelRenderer Head_r3;
	private final ModelRenderer body;
	private final ModelRenderer rightArm;
	private final ModelRenderer RightArm_r1;
	private final ModelRenderer leftArm;
	private final ModelRenderer LeftArm_r1;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;

	public ZoraPlayerModel() {
		super(0, false);
		texWidth = 128;
		texHeight = 128;

		Zoralink = new ModelRenderer(this);
		Zoralink.setPos(0.0F, 24.0F, 2.0F);


		head = new ModelRenderer(this);
		head.setPos(0.0F, -32.0F, 0.0F);
		Zoralink.addChild(head);
		head.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
		head.texOffs(0, 55).addBox(5.0F, -6.0F, -4.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
		head.texOffs(50, 28).addBox(-6.0F, -6.0F, -4.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(0.0F, -5.0F, -8.0F, 0.0F, 4.0F, 3.0F, 0.0F, false);

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
		body.setPos(0.0F, 0.0F, 0.0F);
		Zoralink.addChild(body);
		body.texOffs(0, 20).addBox(-5.5F, -32.0F, -3.0F, 11.0F, 16.0F, 5.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-7.5724F, -29.9024F, -0.75F);
		Zoralink.addChild(rightArm);
		rightArm.texOffs(23, 52).addBox(-1.9276F, -2.0976F, -2.25F, 4.0F, 18.0F, 5.0F, 0.0F, true);

		RightArm_r1 = new ModelRenderer(this);
		RightArm_r1.setPos(7.5724F, 29.9024F, 0.75F);
		rightArm.addChild(RightArm_r1);
		setRotationAngle(RightArm_r1, 0.0F, 0.0F, -0.48F);
		RightArm_r1.texOffs(32, 28).addBox(-6.0F, -43.0F, -1.0F, 9.0F, 24.0F, 0.0F, 0.0F, true);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(7.5724F, -29.9024F, -0.75F);
		Zoralink.addChild(leftArm);
		leftArm.texOffs(23, 52).addBox(-2.0724F, -2.0976F, -2.25F, 4.0F, 18.0F, 5.0F, 0.0F, false);

		LeftArm_r1 = new ModelRenderer(this);
		LeftArm_r1.setPos(-7.5724F, 29.9024F, 0.75F);
		leftArm.addChild(LeftArm_r1);
		setRotationAngle(LeftArm_r1, 0.0F, 0.0F, 0.48F);
		LeftArm_r1.texOffs(32, 28).addBox(-3.0F, -43.0F, -1.0F, 9.0F, 24.0F, 0.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-3.0F, -15.75F, -0.5F);
		Zoralink.addChild(rightLeg);
		rightLeg.texOffs(45, 47).addBox(-2.5F, -0.25F, -2.5F, 5.0F, 16.0F, 5.0F, 0.0F, true);
		rightLeg.texOffs(30, 0).addBox(-2.5F, 12.75F, -5.5F, 5.0F, 3.0F, 3.0F, 0.0F, true);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(3.0F, -15.75F, -0.5F);
		Zoralink.addChild(leftLeg);
		leftLeg.texOffs(45, 47).addBox(-2.5F, -0.25F, -2.5F, 5.0F, 16.0F, 5.0F, 0.0F, false);
		leftLeg.texOffs(30, 0).addBox(-2.5F, 12.75F, -5.5F, 5.0F, 3.0F, 3.0F, 0.0F, false);
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
		
		this.body.yRot = 0.0F;
		this.rightArm.z = 0.5F;
		this.rightArm.x = -4.0F;
		this.leftArm.z = 0.5F;
		this.leftArm.x = 4.0F;
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
		
		this.rightArm.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float) Math.PI) * 2.0F * p_225597_3_ * 0.5F / f;
		this.leftArm.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 2.0F * p_225597_3_ * 0.5F / f;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;
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
			this.body.xRot = 0.5F;
			this.rightArm.xRot += 0.4F;
			this.leftArm.xRot += 0.4F;
			this.rightLeg.z = 3.5F;
			this.leftLeg.z = 3.5F;
			this.rightLeg.y = 16.2F;
			this.leftLeg.y = 16.2F;
			this.head.y = 12.2F;
			this.body.y = 11.2F;
			this.leftArm.y = 12.7F;
			this.rightArm.y = 12.7F;
		}
		else
		{
			this.body.xRot = 0.0F;
			this.rightLeg.z = -0.4F;
			this.leftLeg.z = -0.4F;
			this.rightLeg.y = 16.0F;
			this.leftLeg.y = 16.0F;
			this.head.y = 8.0F;
			this.body.y = 8.0F;
			this.leftArm.y = 9.5F;
			this.rightArm.y = 9.5F;
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
			
			this.rightArm.z = MathHelper.sin(this.body.yRot) * 4.0F;
			this.rightArm.x = -MathHelper.cos(this.body.yRot) * 4.0F;
			this.leftArm.z = -MathHelper.sin(this.body.yRot) * 4.0F;
			this.leftArm.x = MathHelper.cos(this.body.yRot) * 4.0F;
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
	
	private float quadraticArmUpdate(float p_203068_1_)
	{
		return -65.0F * p_203068_1_ + p_203068_1_ * p_203068_1_;
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
		hand.y = 7.5F;
		hand.render(matrix, buffer.getBuffer(RenderType.entitySolid(texture)), color, OverlayTexture.NO_OVERLAY);
		handOverlay.xRot = 0;
		handOverlay.y = 7.5F;
		handOverlay.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(texture)), color, OverlayTexture.NO_OVERLAY);
	}
	
	@Override
	public void renderThirdPersonItem(LivingEntity livingEntity, ItemStack itemStack, TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer,
			int light)
	{
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90F));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(170F));
		boolean leftHand = handSide == HandSide.LEFT;
		matrixStack.translate((leftHand ? -1 : 1) / 16D, 0.125D, -0.625D);
		Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemStack, transformType, leftHand, matrixStack, renderTypeBuffer, light);
	}
}
