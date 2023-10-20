package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class ZoraArmorSet extends ArmorSet {
	public ZoraArmorSet() {
		super("zora", 1, 5, 3, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
