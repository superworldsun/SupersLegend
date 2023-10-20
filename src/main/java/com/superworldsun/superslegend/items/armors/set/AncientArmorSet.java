package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.item.crafting.Ingredient;

public class AncientArmorSet extends ArmorSet {
	public AncientArmorSet() {
		super("ancient", 1, 5, 3, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
		setToughness(3f);
	}
}
