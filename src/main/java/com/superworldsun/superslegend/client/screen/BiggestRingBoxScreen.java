package com.superworldsun.superslegend.client.screen;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.BiggestRingBoxContainer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BiggestRingBoxScreen extends SimpleContainerScreen<BiggestRingBoxContainer>
{
	public BiggestRingBoxScreen(BiggestRingBoxContainer container, PlayerInventory playerInventory, ITextComponent title)
	{
		super(container, playerInventory, title);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/biggest_ring_box.png");
	}
}