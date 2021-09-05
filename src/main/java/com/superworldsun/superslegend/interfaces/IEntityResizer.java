package com.superworldsun.superslegend.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;

/**
 * Can be used with instances of {@link ArmorItem} to change player's size
 * 
 * @author Daripher
 *
 */
public interface IEntityResizer
{
	float getScale(PlayerEntity player);
	
	default float getRenderScale(PlayerEntity player)
	{
		return getScale(player);
	}
}
