package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.itemgroup.ModItemGroup;

import net.minecraft.item.ItemGroup;

public class ItemGroupInit {
	public static final ItemGroup RESOURCES = new ModItemGroup("supers_legend", ItemInit.TRIFORCE);
	public static final ItemGroup APPAREL = new ModItemGroup("supers_legend_apparel", ItemInit.KOKIRI_TUNIC);
	public static final ItemGroup BLOCKS = new ModItemGroup("supers_legend_blocks", BlockInit.BLOCK_OF_TIME);
}
