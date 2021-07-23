package com.superworldsun.superslegend.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.math.BlockPos;

public class BuildingHelper
{
	/**
	 * @return list of block positions to shape the platform
	 * @author Daripher
	 */
	public static List<BlockPos> createRoundPlatformShape(BlockPos center, int radius)
	{
		List<BlockPos> shape = new ArrayList<>();
		
		for (int x = -radius; x <= radius; x++)
		{
			for (int z = -radius; z <= radius; z++)
			{
				BlockPos pos = center.north(x).east(z);
				
				if (pos.distSqr(center) <= radius * radius)
				{
					shape.add(pos);
				}
			}
		}
		
		return shape;
	}
}
