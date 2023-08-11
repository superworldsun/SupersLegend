package com.superworldsun.superslegend.api;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface MagicContainer {
	float getMagic();

	float setMagic(float value);

	float getMaxMagic();

	default void copyFrom(MagicContainer other) {
		setMagic(other.getMagic());
	}
	
	default void restoreMagic(float value) {
		setMagic(Math.min(getMaxMagic(), getMagic() + value));
	}

	default void spendMagic(float value) {
		setMagic(Math.max(0F, getMagic() - value));
	}

	default boolean hasMagic(float value) {
		return getMagic() >= value;
	}
}
