package com.superworldsun.superslegend.cooldowns;

import java.util.Set;

import net.minecraft.item.Item;

public interface ICooldowns
{
	int getCooldown(Item item);

	boolean hasCooldown(Item item);

	void setCooldown(Item item, int cooldown);
	
	Set<Item> getItemsOnCooldown();
}
