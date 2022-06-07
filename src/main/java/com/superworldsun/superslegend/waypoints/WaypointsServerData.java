package com.superworldsun.superslegend.waypoints;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class WaypointsServerData extends WorldSavedData
{
	private final Map<BlockPos, Waypoint> waypoints = new HashMap<BlockPos, Waypoint>();
	
	private WaypointsServerData()
	{
		super("waypoints");
	}
	
	@Override
	public void load(CompoundNBT nbt)
	{
		ListNBT waypointsListNbt = nbt.getList("waypoints", new CompoundNBT().getId());
		waypoints.clear();
		waypointsListNbt.forEach(waypointNbt -> addWaypoint(Waypoint.readFromNBT((CompoundNBT) waypointNbt)));
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt)
	{
		ListNBT waypointsListNbt = new ListNBT();
		waypoints.values().forEach(waypoint -> waypointsListNbt.add(waypoint.writeToNBT()));
		nbt.put("waypoints", waypointsListNbt);
		return nbt;
	}
	
	public void createWaypoint(BlockPos pos, String name)
	{
		waypoints.put(pos, new Waypoint(name, pos));
		setDirty();
	}
	
	public Waypoint getWaypoint(BlockPos pos)
	{
		return waypoints.get(pos);
	}
	
	private void addWaypoint(Waypoint waypoint)
	{
		waypoints.put(waypoint.getStatuePosition(), waypoint);
	}
	
	public static WaypointsServerData instance()
	{
		return ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage().computeIfAbsent(WaypointsServerData::new, "waypoints");
	}
}
