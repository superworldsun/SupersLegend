package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SpoilsBagItem extends BagItem
{
	public SpoilsBagItem()
	{
		super(new Item.Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == Items.GUNPOWDER || item == Items.ROTTEN_FLESH || item == Items.BONE || item == Items.BLAZE_ROD
			|| item == Items.PRISMARINE_SHARD || item == Items.PRISMARINE_CRYSTALS || item == Items.ENDER_PEARL
			|| item == Items.MAGMA_CREAM || item == Items.PHANTOM_MEMBRANE || item == Items.SHULKER_SHELL
			|| item == Items.SLIME_BALL || item == Items.SPIDER_EYE || item == Items.STRING || item == Items.GHAST_TEAR
			|| item == ItemInit.RED_JELLY.get() || item == ItemInit.GREEN_JELLY.get() || item == ItemInit.BLUE_JELLY.get()
			|| item == ItemInit.TRIFORCE_COURAGE_SHARD.get() || item == ItemInit.TRIFORCE_WISDOM_SHARD.get()
			|| item == ItemInit.TRIFORCE_POWER_SHARD.get();
	}
}
