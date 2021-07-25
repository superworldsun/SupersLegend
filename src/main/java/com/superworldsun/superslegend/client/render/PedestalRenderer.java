package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.blocks.PedestalBlock;
import com.superworldsun.superslegend.blocks.tile.PedestalTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3f;

public class PedestalRenderer extends TileEntityRenderer<PedestalTileEntity>
{
	public PedestalRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	public void render(PedestalTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		ItemStack sword = te.getSword();
		if (!sword.isEmpty())
		{
			this.renderItem(te, sword, matrixStack, buffer, combinedLight, combinedOverlay);
		}
	}
	
	private void rotateItem(MatrixStack matrixStack, float a, float b, float c)
	{
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(a));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(b));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(c));
	}
	
	private void renderItem(TileEntity te, ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		matrixStack.pushPose();		
		matrixStack.translate(0.5D, 0.6D, 0.5D);
		
		switch (te.getBlockState().getValue(PedestalBlock.FACING))
		{
			case WEST:
			case EAST:
				this.rotateItem(matrixStack, 180f, 90f, -45f);
				break;
			case NORTH:
			case SOUTH:
				this.rotateItem(matrixStack, 180f, 180f, -45f);
				break;
			default:
				break;
		}
		
		renderer.renderStatic(stack, TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);		
		matrixStack.popPose();
	}
}
