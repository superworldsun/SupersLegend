package com.superworldsun.superslegend.client.render;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.blocks.tile.ShadowTileEntity;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.EmptyModelData;

public class ShadowRenderer<T extends ShadowTileEntity> extends TileEntityRenderer<T> {
	public ShadowRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(T te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		BlockState blockAppearance = te.getAppearance();
		Minecraft minecraft = Minecraft.getInstance();
		boolean renderingShadowBlock = te.getBlockState().getBlock() == BlockInit.SHADOW_BLOCK.get();
		boolean clientPlayerUsingLens = minecraft.player.isUsingItem() && minecraft.player.getUseItem().getItem() == ItemInit.LENS_OF_TRUTH.get();

		if (renderingShadowBlock && clientPlayerUsingLens) {
			blockAppearance = BlockInit.SHADOW_MODEL_BLOCK.get().defaultBlockState();
		}

		if (blockAppearance.getRenderShape() == BlockRenderType.MODEL) {
			World world = te.getLevel();
			matrixStack.pushPose();
			BlockPos blockPos = new BlockPos(te.getBlockPos().getX(), te.getRenderBoundingBox().maxY, te.getBlockPos().getZ());

			for (RenderType renderType : RenderType.chunkBufferLayers()) {
				if (RenderTypeLookup.canRenderInLayer(blockAppearance, renderType)) {
					renderBlock(world, blockPos, blockAppearance, matrixStack, buffer, renderType);
				}
			}

			ForgeHooksClient.setRenderLayer(null);
			matrixStack.popPose();
		}
	}

	private void renderBlock(World world, BlockPos blockPos, BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, RenderType renderType) {
		BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
		ForgeHooksClient.setRenderLayer(renderType);
		IBakedModel model = blockRenderer.getBlockModel(blockState);
		IVertexBuilder vertexBuffer = renderTypeBuffer.getBuffer(renderType);
		long seed = blockState.getSeed(blockPos);
		int overlay = OverlayTexture.NO_OVERLAY;
		blockRenderer.getModelRenderer().renderModel(world, model, blockState, blockPos, matrixStack, vertexBuffer, false, new Random(), seed, overlay,
				EmptyModelData.INSTANCE);
	}
}
