package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class BarbarianArmorSet extends ArmorSet {
	public BarbarianArmorSet() {
		super("goron", 1, 2, 2, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
