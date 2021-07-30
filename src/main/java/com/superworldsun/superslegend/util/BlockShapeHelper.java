package com.superworldsun.superslegend.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class BlockShapeHelper
{
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape)
	{
		VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };
		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		
		for (int i = 0; i < times; i++)
		{
			buffer[0].forAllBoxes(
					(minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}
		
		return buffer[0];
	}
}
