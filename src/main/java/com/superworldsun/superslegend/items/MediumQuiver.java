package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.MediumQuiverContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.Hand;

public class MediumQuiver extends QuiverItem
{
	public MediumQuiver()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public Container getContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, Hand hand)
	{
		return new MediumQuiverContainer(windowId, playerInventory, hand);
	}
}
