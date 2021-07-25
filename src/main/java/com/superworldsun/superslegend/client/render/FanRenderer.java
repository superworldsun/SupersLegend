package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.FanTileEntity;
import com.superworldsun.superslegend.client.model.FanModel;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class FanRenderer extends TileEntityRenderer<FanTileEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/block/fan.png");
	private final FanModel model = new FanModel();
	
	public FanRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	public void render(FanTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		matrixStack.pushPose();
		Direction fanDirection = te.getFanDirection();
		matrixStack.translate(0.5D, 0.5D, 0.5D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(fanDirection.toYRot() + (fanDirection == Direction.SOUTH || fanDirection == Direction.NORTH ? 180 : 0)));
		matrixStack.translate(-0.5D, -0.5D, -0.5D);
		model.render(matrixStack, buffer.getBuffer(model.renderType(TEXTURE)), combinedLight, combinedOverlay, partialTicks);
		matrixStack.popPose();
	}
}
