package com.superworldsun.superslegend.items.bags;

import com.superworldsun.superslegend.container.bag.RingBoxContainer;
import com.superworldsun.superslegend.items.RingItem;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class RingBoxItem extends BagItem
{
	public RingBoxItem()
	{
		super(new Properties().tab(ItemGroupInit.RESOURCES));
	}
	
	@Override
	public Container getContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, Hand hand)
	{
		return new RingBoxContainer(windowId, player.inventory, hand);
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		return stack.getItem() instanceof RingItem;
	}
}
