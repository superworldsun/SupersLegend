package com.superworldsun.superslegend.light;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EntityLightEmitter extends AbstractLightEmitter
{
	private final Entity sourceEntity;
	
	public EntityLightEmitter(Supplier<World> levelSupplier, Supplier<Vector3d> lightDirectionSupplier, Supplier<Vector3d> coordinatesSupplier, Entity sourceEntity)
	{
		super(levelSupplier, lightDirectionSupplier, coordinatesSupplier);
		this.sourceEntity = sourceEntity;
	}
	
	@Override
	protected RayTraceContext getLightRayTraceContext(Vector3d from, Vector3d to)
	{
		return new RayTraceContext(from, to, RayTraceContext.BlockMode.VISUAL, RayTraceContext.FluidMode.NONE, sourceEntity);
	}
	
	@Override
	protected Entity getSourceEntity()
	{
		return sourceEntity;
	}
}
