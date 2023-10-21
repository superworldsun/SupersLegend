package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.api.SetIncomingDamageModifier;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.crafting.Ingredient;

public class FlameBreakerArmorSet extends ArmorSet implements SetIncomingDamageModifier {
	public FlameBreakerArmorSet() {
		super("flamebreaker", 1, 5, 3, 1, () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get));
	}

	@Override
	public float modifyIncomingDamage(DamageSource source, float amount, int armorPieces) {
		if (source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA)) {
			return amount / (1f + 0.25f * armorPieces);
		}
		return amount;
	}
}
