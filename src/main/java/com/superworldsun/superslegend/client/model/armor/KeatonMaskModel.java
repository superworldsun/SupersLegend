package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class KeatonMaskModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;

	public KeatonMaskModel()
	{
		texWidth = 32;
		texHeight = 32;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);


		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-0.0204F, -10.5F, -4.3676F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.1872F, 0.1841F, -0.8027F);
		cube_r1.texOffs(10, 0).addBox(-5.049F, -4.5F, -0.75F, 4.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-0.8897F, -3.0F, -5.377F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -0.2618F, 0.0F);
		cube_r2.texOffs(0, 0).addBox(0.9F, -5.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(-0.0204F, -10.5F, -4.3676F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.1872F, -0.1841F, 0.8027F);
		cube_r3.texOffs(10, 6).addBox(1.049F, -4.5F, -0.75F, 4.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-0.2136F, -3.0F, -5.0923F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.2618F, 0.0F);
		cube_r4.texOffs(0, 8).addBox(-4.8F, -5.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);
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