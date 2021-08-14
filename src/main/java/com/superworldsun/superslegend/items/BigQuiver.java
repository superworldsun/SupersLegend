package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.BigQuiverContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.Hand;

public class BigQuiver extends QuiverItem
{
	public BigQuiver()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public Container getContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, Hand hand)
	{
		return new BigQuiverContainer(windowId, playerInventory, hand);
	}
}
