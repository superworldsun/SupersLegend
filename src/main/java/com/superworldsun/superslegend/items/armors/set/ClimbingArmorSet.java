package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class ClimbingArmorSet extends ArmorSet {
	public ClimbingArmorSet() {
		super("climbing", 1, 3, 2, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
