package com.superworldsun.superslegend.interfaces;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.PlayerEntity;

/**
 * 
 * Only use it for helmet items, otherwise it will not work
 * 
 * @author Daripher
 *
 */
public interface IMaskAbility
{
	/**
	 * Do not use this field directly
	 */
	public static final Set<PlayerEntity> PLAYERS_USING_MASKS = new HashSet<>();
	
	default void startUsingAbility(PlayerEntity player)
	{
		PLAYERS_USING_MASKS.add(player);
	};
	
	default void stopUsingAbility(PlayerEntity player)
	{
		PLAYERS_USING_MASKS.remove(player);
	};
	
	default boolean isPlayerUsingAbility(PlayerEntity player)
	{
		return PLAYERS_USING_MASKS.contains(player);
	}
}