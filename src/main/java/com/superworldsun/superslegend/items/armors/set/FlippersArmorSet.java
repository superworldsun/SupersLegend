package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class FlippersArmorSet extends ArmorSet {
	public FlippersArmorSet() {
		super("flippers", 0, 0, 0, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
