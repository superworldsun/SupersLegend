package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class HerosNewArmorSet extends ArmorSet {
	public HerosNewArmorSet() {
		super("heros_new", 1, 5, 3, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
