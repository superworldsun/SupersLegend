package io.superworldsun.superslegend.items;

import io.superworldsun.superslegend.SupersLegend;
import io.superworldsun.superslegend.lists.ItemList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

// TODO: Document this class and methods inside
public class GenericItem extends Item {

	public GenericItem(String name, String unlocalized, CreativeTabs tab) {
		setRegistryName(SupersLegend.MODID, name).setUnlocalizedName(unlocalized).setCreativeTab(tab);
		ItemList.addGenericItem(this);
	}
}
