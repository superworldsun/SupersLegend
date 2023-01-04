package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.blocks.entity.LightEmitterTileEntity;
import com.superworldsun.superslegend.light.LightRayRenderer;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class LightEmitterRenderer extends TileEntityRenderer<LightEmitterTileEntity>
{
	public LightEmitterRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	public void render(LightEmitterTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int combinedLight,
			int combinedOverlay)
	{
		matrixStack.translate(-te.getBlockPos().getX(), -te.getBlockPos().getY(), -te.getBlockPos().getZ());
		LightRayRenderer.render(te.lightEmitter, partialTicks, matrixStack, renderBuffer, combinedLight);
	}
	
	@Override
	public boolean shouldRenderOffScreen(LightEmitterTileEntity te)
	{
		return true;
	}
}
