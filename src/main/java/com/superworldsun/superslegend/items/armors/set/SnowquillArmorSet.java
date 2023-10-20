package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class SnowquillArmorSet extends ArmorSet {
	public SnowquillArmorSet() {
		super("snowquill", 1, 3, 2, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}
}
