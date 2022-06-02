package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class GreatfairyMaskModel<T extends LivingEntity> extends EntityModel<T>
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

	public GreatfairyMaskModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);
		base.texOffs(0, 10).addBox(-4.0F, -8.099F, -6.0F, 8.0F, 0.0F, 10.0F, 0.0F, false);
		base.texOffs(0, 36).addBox(-3.99F, -8.1F, 4.01F, 8.0F, 8.0F, 0.0F, 0.0F, false);
		base.texOffs(20, 20).addBox(-4.01F, -8.1F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, false);
		base.texOffs(20, 12).addBox(4.01F, -8.1F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, false);
		base.texOffs(28, 0).addBox(-4.5F, -8.35F, 4.499F, 9.0F, 8.0F, 0.0F, 0.0F, false);
		base.texOffs(26, 10).addBox(-4.5F, -8.35F, -5.501F, 9.0F, 8.0F, 0.0F, 0.0F, false);
		base.texOffs(0, 18).addBox(4.5F, -8.35F, -5.5F, 0.0F, 8.0F, 10.0F, 0.0F, false);
		base.texOffs(0, 0).addBox(-4.5F, -8.349F, -5.5F, 9.0F, 0.0F, 10.0F, 0.0F, false);
		base.texOffs(0, 10).addBox(-4.499F, -8.35F, -5.5F, 0.0F, 8.0F, 10.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-0.5145F, -1.4041F, 6.0556F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.3927F, -0.3491F, 0.0F);
		cube_r1.texOffs(24, 36).addBox(-4.0F, -4.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.01F, -1.4041F, 5.5408F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.3927F, 0.0F, 0.0F);
		cube_r2.texOffs(36, 24).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(2.0425F, 0.4433F, 7.8018F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0873F, -0.3491F, 0.0F);
		cube_r3.texOffs(16, 36).addBox(-7.0F, 0.0F, -0.001F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(3.01F, 0.4433F, 6.3071F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0873F, 0.0F, 0.0F);
		cube_r4.texOffs(36, 18).addBox(-4.0F, 0.0F, -0.001F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(3.6056F, 0.4433F, 5.7496F);
		base.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0873F, 0.3491F, 0.0F);
		cube_r5.texOffs(20, 36).addBox(-1.0F, 0.0F, -0.001F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.5245F, -1.4041F, 6.0556F);
		base.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.3927F, 0.3491F, 0.0F);
		cube_r6.texOffs(28, 36).addBox(2.0F, -4.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(-0.8897F, 20.9F, -5.377F);
		base.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, -0.2618F, 0.0F);
		cube_r7.texOffs(0, 0).addBox(0.9F, -29.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(-0.2136F, 20.9F, -5.0923F);
		base.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.2618F, 0.0F);
		cube_r8.texOffs(0, 8).addBox(-4.8F, -29.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);
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
