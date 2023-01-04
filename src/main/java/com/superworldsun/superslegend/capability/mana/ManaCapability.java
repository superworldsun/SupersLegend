package com.superworldsun.superslegend.capability.mana;

public class ManaCapability {
	// TODO: make an attribute for that
	private final float maxMana = 20.0F;
	private float mana = maxMana;

	float getMana() {
		return mana;
	}

	float getMaxMana() {
		return maxMana;
	}

	void setMana(float amount) {
		mana = Math.max(0, Math.min(maxMana, amount));
	}

	void spendMana(float amount) {
		setMana(mana - amount);
	}

	void restoreMana(float amount) {
		mana = Math.min(maxMana, mana + amount);
	}

	boolean isFullMana() {
		return mana == maxMana;
	}

	boolean hasMana(float amount) {
		return mana >= amount;
	}
}
