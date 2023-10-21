package com.superworldsun.superslegend.api;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ArmorMaterial;

/**
 * Can be used with instances of {@link ArmorMaterial}
 */
public interface SetIncomingDamageModifier {
	float modifyIncomingDamage(DamageSource source, float amount, int armorPieces);
}
