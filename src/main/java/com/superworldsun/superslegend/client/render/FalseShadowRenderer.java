package com.superworldsun.superslegend.client.render;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.blocks.tile.FalseShadowTileEntity;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.EmptyModelData;

public class FalseShadowRenderer extends TileEntityRenderer<FalseShadowTileEntity>
{
	public FalseShadowRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	public void render(FalseShadowTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		Minecraft client = Minecraft.getInstance();
		
		// Do not render if player is using lens
		if (client.player.isUsingItem() && client.player.getUseItem().getItem() == ItemInit.LENS_OF_TRUTH.get())
		{
			return;
		}
		
		BlockState blockstate = BlockInit.SHADOW_BLOCK.get().defaultBlockState();
		
		if (blockstate.getRenderShape() == BlockRenderType.MODEL)
		{
			World world = te.getLevel();
			matrixStack.pushPose();
			BlockPos blockpos = new BlockPos(te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ());
			BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
			
			for (RenderType type : RenderType.chunkBufferLayers())
			{
				if (RenderTypeLookup.canRenderInLayer(blockstate, type))
				{
					ForgeHooksClient.setRenderLayer(type);
					blockRenderer.getModelRenderer().renderModel(world, blockRenderer.getBlockModel(blockstate), blockstate, blockpos, matrixStack,
							buffer.getBuffer(type), false, new Random(), blockstate.getSeed(te.getBlockPos()), OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
				}
			}
			
			ForgeHooksClient.setRenderLayer(null);
			matrixStack.popPose();
		}
	}
}
