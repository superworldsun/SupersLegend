package io.superworldsun.superslegend.init;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

import io.superworldsun.superslegend.items.ItemBase;

//TODO: Document this class and methods inside
public class ItemList
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	/* 
	 *  Items
	 */
	public static final Item RUPEE = new ItemBase("rupee");
	public static final Item BLUE_RUPEE = new ItemBase("blue_rupee");
	public static final Item RED_RUPEE = new ItemBase("red_rupee");
	public static final Item ORANGE_RUPEE = new ItemBase("orange_rupee");
	
}
