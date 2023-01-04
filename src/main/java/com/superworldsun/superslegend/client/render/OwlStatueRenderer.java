package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.entity.OwlStatueTileEntity;
import com.superworldsun.superslegend.client.model.OpenOwlStatueModel;
import com.superworldsun.superslegend.client.model.OwlStatueModel;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;

import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class OwlStatueRenderer extends TileEntityRenderer<OwlStatueTileEntity>
{
	private static final ResourceLocation TEXTURE_CLOSED = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/block/owl_statue.png");
	private static final ResourceLocation TEXTURE_OPEN = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/block/owl_statue_open.png");
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
		Direction blockFacing = te.getBlockState().getValue(HorizontalBlock.FACING);
		matrixStack.translate(0.5D, 1.5D, 0.5D);
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(blockFacing.toYRot()));
		boolean waypointSaved = WaypointsProvider.get(client.player).getWaypoint(te.getBlockPos().relative(blockFacing)) != null;
		ResourceLocation texture = TEXTURE_CLOSED;
		Model model = closedStatueModel;
		
		if (waypointSaved)
		{
			texture = TEXTURE_OPEN;
			model = openStatueModel;
		}
		
		model.renderToBuffer(matrixStack, buffer.getBuffer(RenderType.entityCutout(texture)), combinedLight, combinedOverlay, 1F, 1F, 1F, 1F);
		matrixStack.popPose();
	}
}
