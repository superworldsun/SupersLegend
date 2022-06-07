package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.OwlStatueTileEntity;
import com.superworldsun.superslegend.client.model.OpenOwlStatueModel;
import com.superworldsun.superslegend.client.model.OwlStatueModel;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class OwlStatueRenderer extends TileEntityRenderer<OwlStatueTileEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/block/owl_statue.png");
	private final OwlStatueModel closedStatueModel = new OwlStatueModel();
	private final OpenOwlStatueModel openStatueModel = new OpenOwlStatueModel();
	
	public OwlStatueRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	public void render(OwlStatueTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		Minecraft client = Minecraft.getInstance();
		matrixStack.pushPose();
		matrixStack.translate(0.5D, 1.5D, 0.5D);
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(180F));
		
		if (WaypointsProvider.get(client.player).getWaypoints().contains(te.getBlockPos()))
		{
			openStatueModel.render(matrixStack, buffer.getBuffer(closedStatueModel.renderType(TEXTURE)), combinedLight, combinedOverlay);
		}
		else
		{
			closedStatueModel.render(matrixStack, buffer.getBuffer(closedStatueModel.renderType(TEXTURE)), combinedLight, combinedOverlay);
		}
		
		matrixStack.popPose();
	}
}
