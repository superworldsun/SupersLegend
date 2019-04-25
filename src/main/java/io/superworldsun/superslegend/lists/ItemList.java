package io.superworldsun.superslegend.lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.ListIterator;

import io.superworldsun.superslegend.SupersLegend;
import io.superworldsun.superslegend.items.GenericItem;

//TODO: Document this class and methods inside
public class ItemList
{
	private static ArrayList<GenericItem> generics = new ArrayList<GenericItem>();
	/* 
	 *  Generic Items
	 */
	public static Item rupee = new GenericItem("rupee", CreativeTabs.MISC);
	
	public static void addGenericItem(GenericItem item) {
		generics.add(item);
	}
	public static void registerItems(IForgeRegistry<Item> registry) {
		ListIterator<GenericItem> genericsList = generics.listIterator();
		while (genericsList.hasNext()) {
			GenericItem genericItem = genericsList.next();
			SupersLegend.logger.info("Registering item: " + genericItem.getRegistryName());
			registry.register(genericItem);
		}
	}
}
