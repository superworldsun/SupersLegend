package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.blocks.entity.HiddenShadowTileEntity;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class HiddenShadowRenderer extends ShadowRenderer<HiddenShadowTileEntity>
{
	public HiddenShadowRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	public void render(HiddenShadowTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		Minecraft client = Minecraft.getInstance();
		
		// Do not render if player is not using lens
		if (!client.player.isUsingItem() || client.player.getUseItem().getItem() != ItemInit.LENS_OF_TRUTH.get())
		{
			return;
		}
		
		super.render(te, partialTicks, matrixStack, buffer, combinedLight, combinedOverlay);
	}
}
