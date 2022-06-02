package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class MaskOfScentsModel<T extends LivingEntity> extends EntityModel<T>
{
	public final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;

	public MaskOfScentsModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);
		base.texOffs(0, 0).addBox(-5.0F, -7.1F, -4.75F, 10.0F, 6.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 11).addBox(-4.5F, -8.1F, -4.75F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 19).addBox(-3.0F, -0.1F, -4.75F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 9).addBox(-5.0F, -2.1F, -5.25F, 10.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 15).addBox(-4.5F, -1.1F, -5.25F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 13).addBox(-4.5F, -1.1F, -4.75F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(19, 12).addBox(-3.0F, -0.1F, -5.25F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 7).addBox(-5.0F, -7.1F, -5.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 17).addBox(-4.5F, -8.1F, -5.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(14, 19).addBox(-3.0F, -9.1F, -4.75F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(19, 14).addBox(-3.0F, -9.1F, -5.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		base.texOffs(0, 21).addBox(-2.0F, -4.1F, -6.75F, 4.0F, 2.0F, 2.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(6.0F, -4.0905F, -5.4679F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0873F, 0.0F, 0.0F);
		cube_r1.texOffs(12, 21).addBox(-3.75F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-4.75F, -3.5924F, -5.4243F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0873F, 0.0F, 0.0F);
		cube_r2.texOffs(16, 21).addBox(0.5F, -3.0F, 0.0F, 2.0F, 5.0F, 0.0F, 0.0F, false);
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
