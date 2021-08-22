package com.superworldsun.superslegend.light;

import java.util.function.Supplier;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BlockLightEmitter extends AbstractLightEmitter
{
	private final Supplier<BlockPos> lightPositionSupplier;
	
	public BlockLightEmitter(Supplier<World> levelSupplier, Supplier<Vector3d> lightDirectionSupplier, Supplier<BlockPos> lightPositionSupplier)
	{
		super(levelSupplier, lightDirectionSupplier, () -> getLightCoordinates(lightPositionSupplier));
		this.lightPositionSupplier = lightPositionSupplier;
	}
	
	@Override
	protected RayTraceContext getLightRayTraceContext(Vector3d from, Vector3d to)
	{
		return new LightBlockRayTraceContext(from, to, lightPositionSupplier.get());
	}
	
	private static Vector3d getLightCoordinates(Supplier<BlockPos> lightPositionSupplier)
	{
		BlockPos lightPos = lightPositionSupplier.get();
		return new Vector3d(lightPos.getX() + 0.5, lightPos.getY() + 0.5, lightPos.getZ() + 0.5);
	}
}
