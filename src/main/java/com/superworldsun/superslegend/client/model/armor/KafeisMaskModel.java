package com.superworldsun.superslegend.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class KafeisMaskModel<T extends LivingEntity> extends EntityModel<T>
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

	public KafeisMaskModel()
	{
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);


		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(2.2429F, -4.9531F, -5.1525F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.2653F, -0.3904F, -0.3923F);
		cube_r1.texOffs(22, 6).addBox(-0.5F, -4.5F, -1.0F, 6.0F, 5.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-4.0742F, -0.75F, -2.3933F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -0.2618F, 0.0F);
		cube_r2.texOffs(0, 10).addBox(4.9F, -8.0F, -4.0F, 6.0F, 10.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(2.0062F, -9.0F, -4.5482F);
		base.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.3927F, -0.2618F, 0.0F);
		cube_r3.texOffs(9, 3).addBox(-2.1118F, 0.0F, -0.0511F, 6.0F, 0.0F, 3.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-0.8897F, -3.0F, -5.377F);
		base.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, -0.2618F, 0.0F);
		cube_r4.texOffs(12, 6).addBox(0.9F, -6.0F, 0.0F, 5.0F, 9.0F, 0.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-2.2837F, -4.9531F, -5.1525F);
		base.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.2653F, 0.3904F, 0.3923F);
		cube_r5.texOffs(0, 20).addBox(-5.5F, -4.5F, -1.0F, 6.0F, 5.0F, 0.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(-2.925F, -2.25F, -3.6346F);
		base.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 0.2618F, 0.0F);
		cube_r6.texOffs(0, 0).addBox(-4.5F, -6.5F, -1.0F, 6.0F, 10.0F, 0.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(-2.047F, -9.0F, -4.5482F);
		base.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.3927F, 0.2618F, 0.0F);
		cube_r7.texOffs(9, 0).addBox(-3.8882F, 0.0F, -0.0511F, 6.0F, 0.0F, 3.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(-2.4352F, -4.5F, -4.497F);
		base.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.2618F, 0.0F);
		cube_r8.texOffs(12, 15).addBox(-2.5F, -4.5F, 0.0F, 5.0F, 9.0F, 0.0F, 0.0F, false);
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
