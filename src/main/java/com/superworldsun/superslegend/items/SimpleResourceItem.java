package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.item.Item;

public class SimpleResourceItem extends Item
{
	public SimpleResourceItem()
	{
		super(new Properties().tab(ItemGroupInit.RESOURCES));
	}
}
