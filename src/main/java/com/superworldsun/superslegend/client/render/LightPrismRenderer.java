package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.blocks.tile.LightPrismTileEntity;
import com.superworldsun.superslegend.light.LightRayRenderer;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class LightPrismRenderer extends TileEntityRenderer<LightPrismTileEntity>
{
	public LightPrismRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	public void render(LightPrismTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int combinedLight,
			int combinedOverlay)
	{
		LightRayRenderer.render(te.lightEmitter, partialTicks, matrixStack, renderBuffer, combinedLight);
	}
	
	@Override
	public boolean shouldRenderOffScreen(LightPrismTileEntity te)
	{
		return true;
	}
}
