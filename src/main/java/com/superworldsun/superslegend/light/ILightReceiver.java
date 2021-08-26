package com.superworldsun.superslegend.light;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

/**
 * Can be used with instances of {@link TileEntity} and {@link Entity}
 * 
 * @author Daripher
 *
 */
public interface ILightReceiver
{
	void receiveLight();
	
	void stopReceivingLight();

	boolean isLit();
}
