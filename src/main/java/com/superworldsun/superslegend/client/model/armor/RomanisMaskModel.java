package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class RomanisMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;

	public RomanisMaskModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 23.0F, 0.0F);
		base.texOffs(0, 14).addBox(-4.5F, -9.0F, -5.25F, 9.0F, 1.0F, 10.0F, 0.0F, false);
		base.texOffs(0, 14).addBox(-4.5F, -8.0F, -2.25F, 1.0F, 8.0F, 2.0F, 0.0F, false);
		base.texOffs(0, 0).addBox(3.5F, -8.0F, -2.25F, 1.0F, 8.0F, 2.0F, 0.0F, false);
		base.texOffs(18, 25).addBox(-4.5F, -0.25F, -2.25F, 9.0F, 1.0F, 2.0F, 0.0F, false);
		base.texOffs(0, 0).addBox(-4.0F, -13.0F, -5.25F, 8.0F, 4.0F, 10.0F, 0.0F, false);
		base.texOffs(0, 25).addBox(-3.0F, -11.0F, -11.25F, 6.0F, 3.0F, 6.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -14.0F, 2.75F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.48F, 0.0F, 0.0F);
		cube_r1.texOffs(4, 0).addBox(1.5F, -1.9F, 1.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(6, 2).addBox(-2.5F, -1.9F, 1.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(28, 14).addBox(1.0F, -0.9F, 0.6F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		cube_r1.texOffs(28, 18).addBox(-3.0F, -0.9F, 0.6F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, -11.5F, 0.75F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.6037F, -0.0998F, 0.1434F);
		cube_r2.texOffs(26, 0).addBox(4.0F, -0.5F, -2.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, -11.5F, 0.75F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.6037F, 0.0998F, -0.1434F);
		cube_r3.texOffs(26, 4).addBox(-7.0F, -0.5F, -2.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
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
