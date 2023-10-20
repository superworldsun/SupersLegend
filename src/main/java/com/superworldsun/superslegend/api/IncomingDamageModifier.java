package com.superworldsun.superslegend.api;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ArmorItem;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * Can be used with instances of {@link ArmorItem} and {@link ICurioItem}
 */
public interface IncomingDamageModifier {
	float modifyIncomingDamage(DamageSource source, float amount);
}
