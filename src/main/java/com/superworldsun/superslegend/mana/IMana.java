package com.superworldsun.superslegend.mana;

import net.minecraft.entity.player.ServerPlayerEntity;

public interface IMana
{
	float getMana();
	
	float getMaxMana();
	
	void spendMana(float amount);

	void restoreMana(float amount);

	void setMana(float amount);

	void sync(ServerPlayerEntity player);
}
