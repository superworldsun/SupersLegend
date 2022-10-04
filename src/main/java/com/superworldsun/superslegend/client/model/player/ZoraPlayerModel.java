package com.superworldsun.superslegend.client.model.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.interfaces.IHandRenderer;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;

public class ZoraPlayerModel extends PlayerModel<AbstractClientPlayerEntity> implements IHandRenderer
{
	private final ModelRenderer Zoralink;
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
	public void setupAnim(AbstractClientPlayerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		
	}
	
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		Zoralink.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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
		
	}
	
	@Override
	public void renderThirdPersonItem(LivingEntity livingEntity, ItemStack itemStack, TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer,
			int light)
	{
		
	}
}