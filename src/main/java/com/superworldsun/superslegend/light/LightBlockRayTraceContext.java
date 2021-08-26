package com.superworldsun.superslegend.light;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class LightBlockRayTraceContext extends RayTraceContext
{
	private final BlockPos sourcePos;
	
	public LightBlockRayTraceContext(Vector3d from, Vector3d to, BlockPos sourcePos)
	{
		super(from, to, RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, null);
		this.sourcePos = sourcePos;
	}
	
	public VoxelShape getBlockShape(BlockState state, IBlockReader world, BlockPos pos)
	{
		if (sourcePos.equals(pos))
		{
			return VoxelShapes.empty();
		}
		
		return super.getBlockShape(state, world, pos);
	}
}
