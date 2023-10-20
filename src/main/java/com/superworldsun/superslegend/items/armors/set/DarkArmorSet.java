package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class DarkArmorSet extends ArmorSet {
	public DarkArmorSet() {
		super("dark", 1, 3, 2, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
