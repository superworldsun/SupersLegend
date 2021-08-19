package com.superworldsun.superslegend.client.screen;

import com.superworldsun.superslegend.SupersLegendMain;

import com.superworldsun.superslegend.container.BagContainer;
import com.superworldsun.superslegend.container.LetterContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BagScreen extends SimpleContainerScreen<BagContainer>
{
	public BagScreen(BagContainer container, PlayerInventory playerInventory, ITextComponent title)
	{
		super(container, playerInventory, title);
	}

	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/letter.png");
	}
}
