package com.superworldsun.superslegend.mana;

public interface IMana
{
	float getMana();
	
	float getMaxMana();
	
	void spendMana(float amount);

	void restoreMana(float amount);

	void setMana(float amount);
}
