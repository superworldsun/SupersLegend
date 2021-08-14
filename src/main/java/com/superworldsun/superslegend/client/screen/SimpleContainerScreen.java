package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class SimpleContainerScreen<T extends Container> extends net.minecraft.client.gui.screen.inventory.ContainerScreen<T>
{
	public SimpleContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title)
	{
		super(container, playerInventory, title);
		imageHeight = 168;
		inventoryLabelY = 75;
	}
	
	@Override
	protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY)
	{
		renderBackground(matrix);
		minecraft.textureManager.bind(getBackgroundTexture());
		blit(matrix, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}
	
	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		super.render(matrix, mouseX, mouseY, partialTicks);
		renderTooltip(matrix, mouseX, mouseY);
	}
	
	protected abstract ResourceLocation getBackgroundTexture();
}
