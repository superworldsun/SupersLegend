package com.superworldsun.superslegend.light;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class AbstractLightEmitter
{
	private static final Vector3d LIGHT_LENGTH = new Vector3d(48, 48, 48);
	private final Supplier<Vector3d> lightDirectionSupplier;
	private final Supplier<Vector3d> positionSupplier;
	private final Supplier<World> levelSupplier;
	public Vector3d lightVector = Vector3d.ZERO;
	public Vector3d prevLightVector = Vector3d.ZERO;
	public Object prevLitObject;
	public Object litObject;
	
	public AbstractLightEmitter(Supplier<World> levelSupplier, Supplier<Vector3d> lightDirectionSupplier, Supplier<Vector3d> coordinatesSupplier)
	{
		this.lightDirectionSupplier = lightDirectionSupplier;
		this.positionSupplier = coordinatesSupplier;
		this.levelSupplier = levelSupplier;
	}
	
	public void tick()
	{
		Vector3d lightDirection = lightDirectionSupplier.get();
		prevLitObject = litObject;
		litObject = null;
		
		if (lightDirection == Vector3d.ZERO)
		{
			return;
		}
		
		Vector3d coordinates = positionSupplier.get();
		World level = levelSupplier.get();
		prevLightVector = lightVector;
		lightVector = lightDirection.multiply(LIGHT_LENGTH);
		AxisAlignedBB boundingBox = new AxisAlignedBB(coordinates, Vector3d.ZERO).expandTowards(lightVector);
		Vector3d lightEnd = coordinates.add(lightVector);
		Vector3d vectorToEntity = lightVector;
		Vector3d vectorToBlock = lightVector;
		RayTraceContext rayTraceCtx = getLightRayTraceContext(coordinates, lightEnd);
		RayTraceResult rayTraceResult = level.clip(rayTraceCtx);
		
		if (rayTraceResult != null)
		{
			BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) rayTraceResult;
			litObject = level.getBlockEntity(blockRayTraceResult.getBlockPos());
			double length = rayTraceResult.getLocation().subtract(coordinates).length();
			vectorToBlock = lightDirection.multiply(length, length, length);
		}
		
		rayTraceResult = ProjectileHelper.getEntityHitResult(level, getSourceEntity(), coordinates, lightEnd, boundingBox, e -> true);
		
		if (rayTraceResult != null)
		{
			double length = rayTraceResult.getLocation().subtract(coordinates).length();
			vectorToEntity = lightDirection.multiply(length, length, length);
		}
		
		if (rayTraceResult != null && vectorToEntity.length() < vectorToBlock.length())
		{
			EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) rayTraceResult;
			litObject = entityRayTraceResult.getEntity();
			lightVector = vectorToEntity;
		}
		else
		{
			lightVector = vectorToBlock;
		}
		
		if (litObject instanceof ILightReceiver && !((ILightReceiver) litObject).isLit())
		{
			((ILightReceiver) litObject).receiveLight();
		}
		
		if (litObject != prevLitObject)
		{
			if (prevLitObject instanceof ILightReceiver)
			{
				((ILightReceiver) prevLitObject).stopReceivingLight();
			}
		}
	}
	
	public Vector3d getLightDirection()
	{
		return lightDirectionSupplier.get();
	}
	
	public Vector3d getLightPosition()
	{
		return positionSupplier.get();
	}
	
	protected Entity getSourceEntity()
	{
		return null;
	}
	
	protected abstract RayTraceContext getLightRayTraceContext(Vector3d from, Vector3d to);
}
