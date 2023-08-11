package com.superworldsun.superslegend.api;

import net.minecraft.world.damagesource.DamageSource;

/**
 * Use with equipment and curios
 */
public interface IncomingDamageModifier {
	boolean canModifyDamage(DamageSource damage);

	float getDamageModifier();
}
