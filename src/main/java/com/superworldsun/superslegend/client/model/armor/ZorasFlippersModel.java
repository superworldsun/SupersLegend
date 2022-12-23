package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ZorasFlippersModel extends BipedModel<LivingEntity> {
	private final ModelRenderer LeftFoot_r1;
	private final ModelRenderer RightFoot_r1;

	//TODO low priority,model could use small adjustments with its placement as its off a bit.

	public ZorasFlippersModel() {
		super(0.0F);

		texWidth = 64;
		texHeight = 64;

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(-1.3F, 11.7F, -2.3F);
		leftLeg.texOffs(0, 9).addBox(-2.65F, 11.85F, -6.6F, 6.0F, 0.0F, 9.0F, 0.0F, false);
		leftLeg.texOffs(21, 6).addBox(-2.15F, 10.0F, -2.2F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(0, 19).addBox(1.85F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		leftLeg.texOffs(21, 3).addBox(-2.15F, 10.0F, 1.8F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(0, 10).addBox(-2.15F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		leftLeg.texOffs(9, 18).addBox(-1.15F, 11.0F, -1.2F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		/*leftLeg.setPos(-1.3F, 11.7F, -2.3F);
		leftLeg.texOffs(0, 9).addBox(-1.65F, 12.04F, -6.6F, 6.0F, 0.0F, 9.0F, 0.0F, false);
		leftLeg.texOffs(21, 6).addBox(-1.15F, 10.0F, -2.2F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(0, 19).addBox(2.85F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		leftLeg.texOffs(21, 3).addBox(-1.15F, 10.0F, 1.8F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(0, 10).addBox(-1.15F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		leftLeg.texOffs(9, 18).addBox(-0.15F, 11.0F, -1.2F, 3.0F, 1.0F, 3.0F, 0.0F, false);*/

		LeftFoot_r1 = new ModelRenderer(this);
		leftLeg.addChild(LeftFoot_r1);
		setRotationAngle(LeftFoot_r1, 0.2182F, 0.0F, 0.0F);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(3.7F, 11.7F, -2.3F);
		rightLeg.texOffs(0, 0).addBox(-3.50F, 11.85F, -6.6F, 6.0F, 0.0F, 9.0F, 0.0F, false);
		rightLeg.texOffs(21, 0).addBox(-3.0F, 10.0F, -2.2F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(18, 18).addBox(-3.0F, 10.0F, 1.8F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(0, 5).addBox(1.0F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		rightLeg.texOffs(0, 0).addBox(-3.0F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		rightLeg.texOffs(0, 15).addBox(-2.0F, 11.0F, -1.2F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		//SECOND
		/*rightLeg.setPos(3.7F, 11.7F, -2.3F);
		rightLeg.texOffs(0, 0).addBox(-3.65F, 12.04F, -6.6F, 6.0F, 0.0F, 9.0F, 0.0F, false);
		rightLeg.texOffs(21, 0).addBox(-3.15F, 10.0F, -2.2F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(18, 18).addBox(-3.15F, 10.0F, 1.8F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(0, 5).addBox(0.85F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		rightLeg.texOffs(0, 0).addBox(-3.15F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		rightLeg.texOffs(0, 15).addBox(-2.15F, 11.0F, -1.2F, 3.0F, 1.0F, 3.0F, 0.0F, false);*/

		//FIRST
		/*rightLeg.setPos(3.7F, 11.7F, -2.3F);
		rightLeg.texOffs(0, 0).addBox(-5.65F, 12.04F, -6.6F, 6.0F, 0.0F, 9.0F, 0.0F, false);
		rightLeg.texOffs(21, 0).addBox(-5.15F, 10.0F, -2.2F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(18, 18).addBox(-5.15F, 10.0F, 1.8F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(0, 5).addBox(-1.15F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		rightLeg.texOffs(0, 0).addBox(-5.15F, 10.0F, -1.2F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		rightLeg.texOffs(0, 15).addBox(-4.15F, 11.0F, -1.2F, 3.0F, 1.0F, 3.0F, 0.0F, false);*/

		RightFoot_r1 = new ModelRenderer(this);
		rightLeg.addChild(RightFoot_r1);
		setRotationAngle(RightFoot_r1, 0.2182F, 0.0F, 0.0F);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of(leftLeg, rightLeg);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
