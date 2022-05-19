package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EmptyRenderer<T extends Entity> extends EntityRenderer<T>
{
	public EmptyRenderer(EntityRendererManager entityRendererManager)
	{
		super(entityRendererManager);
	}
	
	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
	}
	
	@Override
	public ResourceLocation getTextureLocation(T entity)
	{
		return null;
	}
}
