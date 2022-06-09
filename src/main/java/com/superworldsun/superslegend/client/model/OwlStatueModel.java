package com.superworldsun.superslegend.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class OwlStatueModel extends Model
{
	private final ModelRenderer Owl_Statue;
	private final ModelRenderer eyebrow1;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer eyebrow2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer Book;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	
	public OwlStatueModel()
	{
		super(RenderType::entityCutout);
		texWidth = 128;
		texHeight = 128;
		
		Owl_Statue = new ModelRenderer(this);
		Owl_Statue.setPos(8.0F, 24.0F, -8.0F);
		Owl_Statue.texOffs(28, 22).addBox(-9.0F, -19.0F, 0.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
		Owl_Statue.texOffs(0, 0).addBox(-9.0F, -20.0F, 1.0F, 2.0F, 6.0F, 3.0F, 0.0F, false);
		Owl_Statue.texOffs(0, 36).addBox(-14.0F, -17.0F, 4.0F, 1.0F, 12.0F, 9.0F, 0.0F, false);
		Owl_Statue.texOffs(20, 37).addBox(-3.0F, -17.0F, 4.0F, 1.0F, 12.0F, 9.0F, 0.0F, false);
		Owl_Statue.texOffs(0, 0).addBox(-13.0F, -23.0F, 4.0F, 10.0F, 11.0F, 11.0F, 0.0F, false);
		Owl_Statue.texOffs(31, 27).addBox(-12.0F, -12.0F, 5.0F, 8.0F, 10.0F, 9.0F, 0.0F, false);
		Owl_Statue.texOffs(0, 22).addBox(-12.0F, -2.0F, 2.0F, 8.0F, 2.0F, 12.0F, 0.0F, false);
		Owl_Statue.texOffs(0, 22).addBox(-10.0F, -19.0F, 2.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
		
		eyebrow1 = new ModelRenderer(this);
		eyebrow1.setPos(2.25F, -0.75F, 0.0F);
		Owl_Statue.addChild(eyebrow1);
		setRotationAngle(eyebrow1, 0.0F, 0.0F, -0.0873F);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-12.6423F, -22.4156F, 3.4833F);
		eyebrow1.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, 0.9599F);
		cube_r1.texOffs(0, 28).addBox(0.562F, -1.6234F, 0.0417F, 5.0F, 2.0F, 0.0F, 0.0F, false);
		
		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-12.6423F, -22.4156F, 3.4833F);
		eyebrow1.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.48F);
		cube_r2.texOffs(0, 30).addBox(-1.7519F, -1.1805F, 0.0167F, 3.0F, 2.0F, 0.0F, 0.0F, false);
		cube_r2.texOffs(7, 1).addBox(-3.7519F, -1.1805F, 0.0167F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		
		eyebrow2 = new ModelRenderer(this);
		eyebrow2.setPos(4.4047F, -21.7253F, 3.4833F);
		Owl_Statue.addChild(eyebrow2);
		setRotationAngle(eyebrow2, 0.0F, 0.0F, -0.0873F);
		
		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(-13.7884F, 3.9297F, -0.4615F);
		eyebrow2.addChild(cube_r3);
		setRotationAngle(cube_r3, -3.1416F, 0.0F, 2.8362F);
		cube_r3.texOffs(7, 0).addBox(-10.7038F, -4.1129F, -0.4792F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		cube_r3.texOffs(28, 29).addBox(-8.7038F, -4.1129F, -0.4792F, 3.0F, 2.0F, 0.0F, 0.0F, false);
		
		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-13.7884F, 3.9297F, -0.4615F);
		eyebrow2.addChild(cube_r4);
		setRotationAngle(cube_r4, -3.1416F, 0.0F, 2.3562F);
		cube_r4.texOffs(0, 9).addBox(-6.9585F, -1.0144F, -0.5042F, 5.0F, 2.0F, 0.0F, 0.0F, false);
		
		Book = new ModelRenderer(this);
		Book.setPos(0.0F, 0.0F, 0.0F);
		Owl_Statue.addChild(Book);
		
		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(-8.0F, -5.7237F, 3.1787F);
		Book.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.1444F, 0.2994F, -0.0453F);
		cube_r5.texOffs(31, 0).addBox(-0.4198F, -3.9935F, 0.3059F, 6.0F, 8.0F, 1.0F, 0.0F, false);
		
		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(-8.0F, -5.7237F, 3.1787F);
		Book.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.1444F, -0.2994F, 0.0453F);
		cube_r6.texOffs(11, 36).addBox(-5.5802F, -3.9935F, 0.3059F, 6.0F, 8.0F, 1.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		Owl_Statue.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
