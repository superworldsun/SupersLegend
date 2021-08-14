package com.superworldsun.superslegend.client.screen;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BagScreen<T extends Container> extends SimpleContainerScreen<T>
{
	public BagScreen(T container, PlayerInventory playerInventory, ITextComponent title)
	{
		super(container, playerInventory, title);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/bag.png");
	}
}
