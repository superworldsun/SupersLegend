package com.superworldsun.superslegend.client.render.ister;

import java.util.Optional;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.items.block.ShadowBlockBaseItem;
import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

public class ShadowBlockIster extends ItemStackTileEntityRenderer {
	@Override
	public void renderByItem(ItemStack itemStack, TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight,
			int combinedOverlay) {
		BlockState blockStateToRender = ShadowBlockBaseItem.loadDisguiseFromStack(itemStack);
		Block blockToRender = Optional.ofNullable(blockStateToRender).map(BlockState::getBlock).orElse(BlockInit.SHADOW_MODEL_BLOCK.get());
		matrixStack.pushPose();
		matrixStack.translate(0.5, 0.5, 0.5);
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		itemRenderer.renderStatic(new ItemStack(blockToRender), transformType, combinedLight, combinedOverlay, matrixStack, buffer);
		matrixStack.popPose();
	}
}
