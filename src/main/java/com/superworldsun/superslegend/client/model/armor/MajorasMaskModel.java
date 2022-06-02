package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class MajorasMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;

	public MajorasMaskModel()
	{
		texWidth = 32;
		texHeight = 32;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);
		base.texOffs(20, 13).addBox(-1.5F, 1.25F, -5.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 13).addBox(-2.5F, 0.25F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 11).addBox(-3.5F, -0.75F, -5.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 9).addBox(-4.5F, -1.75F, -5.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 0).addBox(-5.5F, -6.75F, -5.0F, 11.0F, 5.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 15).addBox(-4.0F, -6.25F, -5.15F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		base.texOffs(12, 13).addBox(1.0F, -6.25F, -5.15F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 6).addBox(-4.5F, -8.75F, -5.0F, 9.0F, 2.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 21).addBox(-3.5F, -9.25F, -5.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(20, 15).addBox(1.5F, -9.25F, -5.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(10, 21).addBox(1.5F, -12.25F, -4.5F, 2.0F, 3.0F, 0.0F, 0.0F, false);
		base.texOffs(6, 21).addBox(-3.5F, -12.25F, -4.5F, 2.0F, 3.0F, 0.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(3.3165F, 2.2203F, -4.5F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, 0.7854F);
		cube_r1.texOffs(16, 19).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(5.1014F, -0.7257F, -4.5F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.6109F);
		cube_r2.texOffs(8, 17).addBox(-2.0F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(7.5F, -3.25F, -4.5F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, 0.3927F);
		cube_r3.texOffs(0, 19).addBox(-2.25F, 1.0F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);
		cube_r3.texOffs(20, 6).addBox(-3.25F, -1.25F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-3.3165F, 2.2203F, -4.5F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, -0.7854F);
		cube_r4.texOffs(8, 19).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-5.1014F, -0.7257F, -4.5F);
		base.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, -0.6109F);
		cube_r5.texOffs(16, 11).addBox(-3.0F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(-7.5F, -3.25F, -4.5F);
		base.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 0.0F, -0.3927F);
		cube_r6.texOffs(18, 17).addBox(-1.75F, 1.0F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);
		cube_r6.texOffs(20, 8).addBox(-0.75F, -1.25F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);
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
