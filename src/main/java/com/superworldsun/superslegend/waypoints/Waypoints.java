package com.superworldsun.superslegend.waypoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.math.BlockPos;

public class Waypoints implements IWaypoints
{
	private final Map<BlockPos, Waypoint> waypoints = new HashMap<>();
	
	@Override
	public List<Waypoint> getWaypoints()
	{
		return new ArrayList<>(waypoints.values());
	}
	
	@Override
	public void addWaypoint(Waypoint waypoint)
	{
		waypoints.put(waypoint.getStatuePosition(), waypoint);
		
		if (waypoints.size() > getMaxWaypoints())
		{
			BlockPos firstMapKey = new ArrayList<>(waypoints.keySet()).get(0);
			waypoints.remove(firstMapKey);
		}
	}
	
	@Override
	public void removeWaypoint(Waypoint waypoint)
	{
		waypoints.remove(waypoint.getStatuePosition());
	}
	
	@Override
	public void removeWaypoint(BlockPos pos)
	{
		waypoints.remove(pos);
	}
	
	@Override
	public int getMaxWaypoints()
	{
		return 10;
	}
}
