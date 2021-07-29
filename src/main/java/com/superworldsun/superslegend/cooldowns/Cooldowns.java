package com.superworldsun.superslegend.cooldowns;

import java.util.HashMap;
import java.util.HashSet;
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
		if (cooldown <= 0 && cooldowns.containsKey(item))
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
		// We are making a copy instead of returning the original set
		return new HashSet<>(cooldowns.keySet());
	}
}
