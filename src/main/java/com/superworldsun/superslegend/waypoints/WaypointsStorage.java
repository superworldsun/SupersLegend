package com.superworldsun.superslegend.waypoints;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WaypointsStorage implements IStorage<IWaypoints>
{
	@Override
	public CompoundNBT writeNBT(Capability<IWaypoints> capability, IWaypoints instance, Direction side)
	{
		CompoundNBT nbt = new CompoundNBT();
		ListNBT waypointsListNbt = new ListNBT();
		instance.getWaypoints().forEach(waypoint -> waypointsListNbt.add(waypoint.writeToNBT()));
		nbt.put("waypoints", waypointsListNbt);
		return nbt;
	}
	
	@Override
	public void readNBT(Capability<IWaypoints> capability, IWaypoints instance, Direction side, INBT inbt)
	{
		CompoundNBT nbt = (CompoundNBT) inbt;
		ListNBT waypointsListNbt = nbt.getList("waypoints", new CompoundNBT().getId());
		instance.getWaypoints().clear();
		waypointsListNbt.forEach(waypointNbt -> instance.getWaypoints().add(Waypoint.readFromNBT((CompoundNBT) waypointNbt)));
	}
}
