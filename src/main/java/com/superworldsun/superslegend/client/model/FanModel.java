package com.superworldsun.superslegend.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FanModel extends Model
{
	private final ModelRenderer blade_1;
	private final ModelRenderer blade_2;
	private final ModelRenderer blade_3;
	private final ModelRenderer base;
	
	public FanModel()
	{
		super(RenderType::entityCutout);
		texWidth = 58;
		texHeight = 48;
		
		blade_1 = new ModelRenderer(this);
		blade_1.setPos(8.0F, 8.0F, -17.0F);
		blade_1.texOffs(7, 39).addBox(-1.5F, -6.5F, 18.0F, 3.0F, 6.0F, 1.0F, 0.0F, false);
		
		blade_2 = new ModelRenderer(this);
		blade_2.setPos(0.0F, 0.0F, 18.0F);
		blade_1.addChild(blade_2);
		setRotationAngle(blade_2, 0.0F, 0.0F, -2.0944F);
		blade_2.texOffs(7, 39).addBox(-1.5F, -6.5F, -0.002F, 3.0F, 6.0F, 1.0F, 0.0F, false);
		
		blade_3 = new ModelRenderer(this);
		blade_3.setPos(0.0F, 0.0F, 18.0F);
		blade_1.addChild(blade_3);
		setRotationAngle(blade_3, 0.0F, 0.0F, 2.0944F);
		blade_3.texOffs(7, 39).addBox(-1.5F, -6.5F, -0.001F, 3.0F, 6.0F, 1.0F, 0.0F, false);
		
		base = new ModelRenderer(this);
		base.setPos(0.0F, 24.0F, 0.0F);
		base.texOffs(0, 0).addBox(0.0F, -24.0F, 3.0F, 16.0F, 16.0F, 13.0F, 0.0F, false);
		base.texOffs(0, 29).addBox(15.0F, -24.0F, 0.0F, 1.0F, 16.0F, 3.0F, 0.0F, true);
		base.texOffs(0, 29).addBox(0.0F, -24.0F, 0.0F, 1.0F, 16.0F, 3.0F, 0.0F, false);
		base.texOffs(8, 29).addBox(1.0F, -9.0F, 0.0F, 14.0F, 1.0F, 3.0F, 0.0F, false);
		base.texOffs(8, 29).addBox(1.0F, -24.0F, 0.0F, 14.0F, 1.0F, 3.0F, 0.0F, false);
		base.texOffs(7, 33).addBox(7.0F, -17.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
	}
	
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float partialTicks, float bladesRotation)
	{
		buffer.color(1.0F, 1.0F, 1.0F, 1.0F);
		blade_1.zRot = bladesRotation;
		blade_1.render(matrixStack, buffer, packedLight, packedOverlay);
		base.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
	
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float r, float g, float b, float a)
	{
	}
}
