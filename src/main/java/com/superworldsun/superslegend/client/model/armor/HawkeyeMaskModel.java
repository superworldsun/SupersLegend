package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class HawkeyeMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;

	public HawkeyeMaskModel()
	{
		texWidth = 32;
		texHeight = 32;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);


		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-0.0204F, -2.0F, -5.1897F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.1806F, -0.2577F, 0.0465F);
		cube_r1.texOffs(0, 16).addBox(-0.0335F, -1.0F, -0.125F, 2.0F, 3.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(4.8093F, -4.0F, -3.85F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -1.1345F, 0.0F);
		cube_r2.texOffs(8, 16).addBox(0.0F, -1.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		cube_r2.texOffs(12, 16).addBox(0.0F, 1.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(4.8093F, -4.0F, -3.85F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -1.4399F, 0.0F);
		cube_r3.texOffs(10, 0).addBox(0.0F, -4.0F, 0.0F, 4.0F, 8.0F, 0.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-0.8897F, -3.0F, -5.377F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, -0.2618F, 0.0F);
		cube_r4.texOffs(0, 8).addBox(0.9F, -5.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-4.85F, -4.0F, -3.85F);
		base.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 1.1345F, 0.0F);
		cube_r5.texOffs(16, 16).addBox(-2.0F, 1.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		cube_r5.texOffs(8, 17).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(-4.85F, -4.0F, -3.85F);
		base.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 1.4399F, 0.0F);
		cube_r6.texOffs(10, 8).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 8.0F, 0.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(-0.2136F, -3.0F, -5.0923F);
		base.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.2618F, 0.0F);
		cube_r7.texOffs(0, 0).addBox(-4.8F, -5.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(-0.0204F, -2.0F, -5.1897F);
		base.addChild(cube_r8);
		setRotationAngle(cube_r8, -0.1806F, 0.2577F, -0.0465F);
		cube_r8.texOffs(4, 16).addBox(-1.9665F, -1.0F, -0.125F, 2.0F, 3.0F, 0.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		base.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
	
	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_)
	{
	}
}
