package com.superworldsun.superslegend.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BarbarianHelmetModel extends BipedModel<LivingEntity> {
	private final ModelRenderer body;
	private final ModelRenderer body_r1;
	private final ModelRenderer body_r2;
	private final ModelRenderer body_r3;
	private final ModelRenderer body_r4;
	private final ModelRenderer body_r5;
	private final ModelRenderer body_r6;
	private final ModelRenderer body_r7;
	private final ModelRenderer body_r8;
	private final ModelRenderer body_r9;
	private final ModelRenderer body_r10;
	private final ModelRenderer body_r11;
	private final ModelRenderer body_r12;
	private final ModelRenderer body_r13;
	private final ModelRenderer body_r14;
	private final ModelRenderer body_r15;
	private final ModelRenderer body_r16;
	private final ModelRenderer body_r17;

	public BarbarianHelmetModel() {
		super(0.0F);
		texWidth = 128;
		texHeight = 128;
		head = new ModelRenderer(this, 0, 0);
		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 12).addBox(-4.6F, -8.0F, -3.0F, 1.0F, 2.0F, 2.0F, 0.1F, false);
		body.texOffs(20, 22).addBox(3.6F, -8.0F, -3.0F, 1.0F, 2.0F, 2.0F, 0.1F, false);
		body.texOffs(58, 10).addBox(-4.0F, -7.0F, -7.42F, 8.0F, 2.0F, 2.0F, 0.21F, false);
		body.texOffs(60, 28).addBox(-4.25F, -5.0F, -7.52F, 8.0F, 1.0F, 0.0F, 0.0F, false);
		body.texOffs(4, 11).addBox(-3.96F, -4.79F, -7.38F, 0.0F, 2.0F, 1.0F, 0.0F, false);
		body.texOffs(0, 11).addBox(4.04F, -4.79F, -7.38F, 0.0F, 2.0F, 1.0F, 0.0F, false);
		body.texOffs(63, 0).addBox(-3.0F, -8.0F, 4.0F, 6.0F, 3.0F, 2.0F, 0.3F, false);
		body.texOffs(0, 16).addBox(-0.5F, 2.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.1F, false);
		body.texOffs(0, 24).addBox(-4.1F, 1.3F, 4.1F, 1.0F, 1.0F, 1.0F, 0.1F, false);
		body.texOffs(20, 30).addBox(3.1F, 1.3F, 4.1F, 1.0F, 1.0F, 1.0F, 0.1F, false);
		body.texOffs(24, 30).addBox(-4.0F, -2.8F, 3.0F, 1.0F, 1.0F, 2.0F, 0.1F, false);
		body.texOffs(32, 8).addBox(-2.5F, -5.0F, 3.75F, 5.0F, 2.0F, 2.0F, 0.2F, false);
		body.texOffs(24, 0).addBox(3.1F, -1.7F, 4.1F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		body.texOffs(72, 22).addBox(-1.5F, -3.0F, 3.5F, 3.0F, 2.0F, 2.0F, 0.1F, false);
		body.texOffs(0, 37).addBox(-0.5F, -2.0F, 4.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
		body.texOffs(0, 28).addBox(-4.1F, -1.7F, 4.1F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		body.texOffs(24, 33).addBox(3.0F, -2.8F, 3.0F, 1.0F, 1.0F, 2.0F, 0.1F, false);
		body.texOffs(0, 28).addBox(-4.0F, -8.0F, 1.0F, 8.0F, 5.0F, 4.0F, 0.1F, false);
		body.texOffs(24, 0).addBox(-4.0F, -9.0F, -5.0F, 8.0F, 4.0F, 4.0F, 0.21F, false);

		body_r1 = new ModelRenderer(this);
		body_r1.setPos(0.0F, -7.0F, -1.5F);
		body.addChild(body_r1);
		setRotationAngle(body_r1, 0.48F, 0.0F, 0.0F);
		body_r1.texOffs(0, 0).addBox(-4.0F, -1.5F, -0.5F, 8.0F, 4.0F, 8.0F, 0.2F, false);

		body_r2 = new ModelRenderer(this);
		body_r2.setPos(5.803F, -6.6515F, -2.0162F);
		body.addChild(body_r2);
		setRotationAngle(body_r2, 0.0172F, 0.1298F, 0.4374F);
		body_r2.texOffs(20, 14).addBox(-0.552F, -0.9478F, -1.037F, 1.0F, 2.0F, 2.0F, -0.2F, false);

		body_r3 = new ModelRenderer(this);
		body_r3.setPos(5.4153F, -6.7745F, -2.0F);
		body.addChild(body_r3);
		setRotationAngle(body_r3, 0.0F, 0.0F, 0.3054F);
		body_r3.texOffs(72, 38).addBox(-1.65F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, -0.1F, false);

		body_r4 = new ModelRenderer(this);
		body_r4.setPos(5.803F, -6.6515F, -2.0162F);
		body.addChild(body_r4);
		setRotationAngle(body_r4, 0.0339F, 0.3468F, 0.4878F);
		body_r4.texOffs(72, 29).addBox(-0.9644F, -0.9573F, -1.0526F, 2.0F, 2.0F, 2.0F, -0.3F, false);

		body_r5 = new ModelRenderer(this);
		body_r5.setPos(7.3549F, -5.5907F, -2.718F);
		body.addChild(body_r5);
		setRotationAngle(body_r5, 0.1629F, 0.5881F, 0.758F);
		body_r5.texOffs(29, 71).addBox(-2.1F, -0.9F, -1.4F, 2.0F, 2.0F, 2.0F, -0.4F, false);

		body_r6 = new ModelRenderer(this);
		body_r6.setPos(7.4955F, -5.302F, -3.1012F);
		body.addChild(body_r6);
		setRotationAngle(body_r6, 0.4376F, 0.8733F, 1.1175F);
		body_r6.texOffs(0, 70).addBox(-1.7F, -0.8F, -1.6F, 2.0F, 2.0F, 2.0F, -0.5F, false);

		body_r7 = new ModelRenderer(this);
		body_r7.setPos(7.4769F, -5.0088F, -3.681F);
		body.addChild(body_r7);
		setRotationAngle(body_r7, 0.89F, 1.1017F, 1.6343F);
		body_r7.texOffs(59, 71).addBox(-1.55F, -0.8F, -1.7F, 3.0F, 2.0F, 2.0F, -0.6F, false);

		body_r8 = new ModelRenderer(this);
		body_r8.setPos(6.4381F, -4.57F, -5.5646F);
		body.addChild(body_r8);
		setRotationAngle(body_r8, 1.5574F, 1.1686F, 2.3633F);
		body_r8.texOffs(49, 71).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, -0.7F, false);

		body_r9 = new ModelRenderer(this);
		body_r9.setPos(6.4381F, -4.57F, -5.5646F);
		body.addChild(body_r9);
		setRotationAngle(body_r9, 1.8784F, 0.875F, 2.7013F);
		body_r9.texOffs(70, 70).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, -0.8F, false);

		body_r10 = new ModelRenderer(this);
		body_r10.setPos(-6.4381F, -4.57F, -5.5646F);
		body.addChild(body_r10);
		setRotationAngle(body_r10, 1.8784F, -0.875F, -2.7013F);
		body_r10.texOffs(68, 66).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, -0.8F, false);

		body_r11 = new ModelRenderer(this);
		body_r11.setPos(-6.4381F, -4.57F, -5.5646F);
		body.addChild(body_r11);
		setRotationAngle(body_r11, 1.5574F, -1.1686F, -2.3633F);
		body_r11.texOffs(68, 51).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, -0.7F, false);

		body_r12 = new ModelRenderer(this);
		body_r12.setPos(-7.4769F, -5.0088F, -3.681F);
		body.addChild(body_r12);
		setRotationAngle(body_r12, 0.89F, -1.1017F, -1.6343F);
		body_r12.texOffs(18, 38).addBox(-1.45F, -0.8F, -1.7F, 3.0F, 2.0F, 2.0F, -0.6F, false);

		body_r13 = new ModelRenderer(this);
		body_r13.setPos(-7.4955F, -5.302F, -3.1012F);
		body.addChild(body_r13);
		setRotationAngle(body_r13, 0.4376F, -0.8733F, -1.1175F);
		body_r13.texOffs(16, 67).addBox(-0.3F, -0.8F, -1.6F, 2.0F, 2.0F, 2.0F, -0.5F, false);

		body_r14 = new ModelRenderer(this);
		body_r14.setPos(-7.3549F, -5.5907F, -2.718F);
		body.addChild(body_r14);
		setRotationAngle(body_r14, 0.1629F, -0.5881F, -0.758F);
		body_r14.texOffs(44, 61).addBox(0.1F, -0.9F, -1.4F, 2.0F, 2.0F, 2.0F, -0.4F, false);

		body_r15 = new ModelRenderer(this);
		body_r15.setPos(-5.803F, -6.6515F, -2.0162F);
		body.addChild(body_r15);
		setRotationAngle(body_r15, 0.0339F, -0.3468F, -0.4878F);
		body_r15.texOffs(10, 59).addBox(-1.0356F, -0.9573F, -1.0526F, 2.0F, 2.0F, 2.0F, -0.3F, false);

		body_r16 = new ModelRenderer(this);
		body_r16.setPos(-5.4153F, -6.7745F, -2.0F);
		body.addChild(body_r16);
		setRotationAngle(body_r16, 0.0F, 0.0F, -0.3054F);
		body_r16.texOffs(50, 48).addBox(-0.35F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, -0.1F, false);

		body_r17 = new ModelRenderer(this);
		body_r17.setPos(-5.803F, -6.6515F, -2.0162F);
		body.addChild(body_r17);
		setRotationAngle(body_r17, 0.0172F, -0.1298F, -0.4374F);
		body_r17.texOffs(0, 20).addBox(-0.448F, -0.9478F, -1.037F, 1.0F, 2.0F, 2.0F, -0.2F, false);
		head.addChild(body);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}