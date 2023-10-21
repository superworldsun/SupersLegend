package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class RocsCapeArmorSet extends ArmorSet {
	public RocsCapeArmorSet() {
		super("rocscape", 0, 2, 0, 0, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
