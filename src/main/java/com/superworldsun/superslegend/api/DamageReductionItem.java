package com.superworldsun.superslegend.api;

import net.minecraft.world.damagesource.DamageSource;

/**
 * Use with equipment and curios
 */
public interface DamageReductionItem {
	boolean canReduceDamage(DamageSource damage);

	float getDamageReduction();
}
