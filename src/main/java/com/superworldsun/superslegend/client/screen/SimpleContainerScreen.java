package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SimpleContainerScreen<T extends Container> extends ContainerScreen<T> {
	private final ResourceLocation textureLocation;

	public SimpleContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, String texture) {
		super(container, playerInventory, title);
		this.imageHeight = 168;
		this.inventoryLabelY = 75;
		this.textureLocation = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/" + texture + ".png");
	}

	@Override
	protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
		renderBackground(matrix);
		minecraft.textureManager.bind(textureLocation);
		blit(matrix, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		super.render(matrix, mouseX, mouseY, partialTicks);
		renderTooltip(matrix, mouseX, mouseY);
	}
}
