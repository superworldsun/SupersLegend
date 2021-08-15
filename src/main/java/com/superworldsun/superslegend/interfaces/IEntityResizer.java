package com.superworldsun.superslegend.interfaces;

import net.minecraft.entity.player.PlayerEntity;

public interface IEntityResizer
{
	float getScale(PlayerEntity player);
	
	default float getRenderScale(PlayerEntity player)
	{
		return getScale(player);
	}
}
