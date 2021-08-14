package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BagScreen<T extends Container> extends ContainerScreen<T>
{
	public BagScreen(T container, PlayerInventory playerInventory, ITextComponent title)
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
	
	protected ResourceLocation getBackgroundTexture()
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/bag.png");
	}
}
