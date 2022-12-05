package com.superworldsun.superslegend.items.bags;

import com.superworldsun.superslegend.container.LetterContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.Hand;

public abstract class LetterItem extends BagItem
{
	public LetterItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public Container getContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, Hand hand)
	{
		return new LetterContainer(windowId, player.inventory, hand);
	}
}
