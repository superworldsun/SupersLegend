package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.SelectContainer;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SelectInteractionMessage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SelectScreen extends SimpleContainerScreen<SelectContainer>
{
	final SelectContainer container;
	final PlayerInventory playerInventory;

	int buttonYOffset = 0;

	public SelectScreen(SelectContainer container, PlayerInventory playerInventory, ITextComponent title)
	{
		this(container, playerInventory, title, 0);
	}

	private SelectScreen(SelectContainer container, PlayerInventory playerInventory, ITextComponent title, int menuIndex)
	{
		super(container, playerInventory, title);

		this.container = container;
		this.playerInventory = playerInventory;
		this.menuIndex = menuIndex;

		switch(menuIndex)
		{
			case 0:
				imageWidth = 215;
				imageHeight = 98;

				titleLabelX = 27;
				titleLabelY = 4;
				break;

			case 1:
			case 3:
				imageWidth = 215;
				imageHeight = 121;

				buttonYOffset = 121 - 98;

				titleLabelX = 27;
				titleLabelY = 4;
				break;

			case 2:
				imageWidth = 215;
				imageHeight = 188;

				buttonYOffset = 188 - 98;

				titleLabelX = 27;
				titleLabelY = 4;
				break;

			case 4:
				imageWidth = 215;
				imageHeight = 166;

				buttonYOffset = 166 - 98;

				titleLabelX = 27;
				titleLabelY = 4;
				break;
		}
	}

	@Override
	protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_)
	{
		this.font.draw(p_230451_1_, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
	}

	@Override
	protected void renderBg(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(
				new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/select" + menuIndex + ".png"));

		int i = (this.width - getXSize()) / 2;
		int j = (this.height - getYSize()) / 2;

		this.blit(stack, i, j, 0, 0, getXSize(), getYSize());
	}

	public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_)
	{
		this.renderBackground(p_230430_1_);
		super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
		this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
	}

	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/select" + menuIndex + ".png");
	}

	public int menuIndex;
	String[] titles = new String[]{ "Swords", "Equipment", "Rings", "Masks", "Status" };

	public boolean mouseClicked(double x, double y, int p_231044_5_)
	{
		int startingX = (this.width - this.imageWidth) / 2;
		int startingY = (this.height - this.imageHeight) / 2;

		int offset = 0;

		if(y - startingY >= 79 + buttonYOffset && y - startingY <= 92 + buttonYOffset)
		{
			if (x - startingX >= 4 && x - startingX <= 28) offset = -1;
			if (x - startingX >= 195 && x - startingX <= 209) offset = 1;

			if(offset != 0)
			{
				menuIndex = Math.floorMod((offset + menuIndex), container.inventories.length);
				NetworkDispatcher.networkChannel.sendToServer(new SelectInteractionMessage(offset, false));
			}
		}

		return super.mouseClicked(x, y, p_231044_5_);
	}

	public void resetScreen(int menuIndex)
	{
		this.menuIndex = menuIndex;
		minecraft.setScreen(new SelectScreen(container, playerInventory, new StringTextComponent(titles[menuIndex]), menuIndex));
	}
}
