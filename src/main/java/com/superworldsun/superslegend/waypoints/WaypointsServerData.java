package com.superworldsun.superslegend.waypoints;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class WaypointsServerData extends WorldSavedData {
	private final Map<BlockPos, Waypoint> waypoints = new HashMap<BlockPos, Waypoint>();
	private static final String DATA_ID = "waypoints";

	private WaypointsServerData() {
		super(DATA_ID);
	}

	@Override
	public void load(CompoundNBT nbt) {
		ListNBT waypointsListNbt = nbt.getList(DATA_ID, new CompoundNBT().getId());
		waypoints.clear();
		waypointsListNbt.stream().map(CompoundNBT.class::cast).map(Waypoint::readFromNBT).forEach(this::addWaypoint);
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		ListNBT waypointsListNbt = new ListNBT();
		waypoints.values().stream().map(Waypoint::writeToNBT).forEach(waypointsListNbt::add);
		nbt.put(DATA_ID, waypointsListNbt);
		return nbt;
	}

	public void createWaypoint(BlockPos pos, String name) {
		waypoints.put(pos, new Waypoint(name, pos));
		setDirty();
	}

	public Waypoint getWaypoint(BlockPos pos) {
		return waypoints.get(pos);
	}

	private void addWaypoint(Waypoint waypoint) {
		waypoints.put(waypoint.getStatuePosition(), waypoint);
	}

	public void removeWaypoint(BlockPos waypointPos) {
		waypoints.remove(waypointPos);
		setDirty();
	}

	public static WaypointsServerData instance() {
		return ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage().computeIfAbsent(WaypointsServerData::new, DATA_ID);
	}
}
