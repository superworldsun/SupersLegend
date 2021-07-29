package com.superworldsun.superslegend.cooldowns;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;

public class Cooldowns implements ICooldowns
{
	private Map<Item, Integer> cooldowns = new HashMap<>();
	
	@Override
	public int getCooldown(Item item)
	{
		return cooldowns.getOrDefault(item, 0);
	}
	
	@Override
	public boolean hasCooldown(Item item)
	{
		return getCooldown(item) > 0;
	}
	
	@Override
	public void setCooldown(Item item, int cooldown)
	{
		if (cooldown == 0)
		{
			cooldowns.remove(item);
		}
		else
		{
			cooldowns.put(item, cooldown);
		}
	}

	@Override
	public Set<Item> getItemsOnCooldown()
	{
		return cooldowns.keySet();
	}
}
