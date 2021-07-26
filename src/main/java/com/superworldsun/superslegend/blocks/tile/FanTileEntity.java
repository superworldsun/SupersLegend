package com.superworldsun.superslegend.blocks.tile;

import java.util.List;

import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class FanTileEntity extends TileEntity implements ITickableTileEntity
{
	private AxisAlignedBB coveredArea;
	public float bladesRotation;
	
	public FanTileEntity()
	{
		super(TileEntityInit.FAN.get());
	}
	
	public FanTileEntity(TileEntityType<? extends FanTileEntity> type)
	{
		super(type);
	}
	
	@Override
	public void tick()
	{
		if (isPowered())
		{
			List<Entity> affectedEntities = level.getEntities(null, getCoveredArea());
			affectedEntities.forEach(entity -> entity.move(MoverType.PISTON, getAirflowVector()));
		}
	}
	
	private AxisAlignedBB getCoveredArea()
	{
		if (coveredArea == null)
		{
			setCoveredArea();
		}
		
		return coveredArea;
	}
	
	public void setCoveredArea()
	{
		Direction fanDirection = getFanDirection();
		this.coveredArea = new AxisAlignedBB(worldPosition, worldPosition.offset(1, 1, 1).relative(fanDirection, 15));
	}
	
	public Direction getFanDirection()
	{
		return level.getBlockState(worldPosition).getValue(DirectionalBlock.FACING);
	}
	
	private Vector3d getAirflowVector()
	{
		return new Vector3d(getFanDirection().step()).multiply(0.3D, 0.3D, 0.3D);
	}
	
	public boolean isPowered()
	{
		return true;
	}
}
