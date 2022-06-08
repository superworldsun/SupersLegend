package com.superworldsun.superslegend.waypoints;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;

public class Waypoint
{
	private final String name;
	private final BlockPos statuePos;
	
	public Waypoint(String name, BlockPos statuePos)
	{
		this.name = name;
		this.statuePos = statuePos;
	}
	
	public String getName()
	{
		return name;
	}
	
	public BlockPos getStatuePosition()
	{
		return statuePos;
	}
	
	public CompoundNBT writeToNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putString("name", name);
		nbt.put("pos", NBTUtil.writeBlockPos(statuePos));
		return nbt;
	}
	
	public static Waypoint readFromNBT(CompoundNBT nbt)
	{
		String name = nbt.getString("name");
		BlockPos statuePos = NBTUtil.readBlockPos(nbt.getCompound("pos"));
		return new Waypoint(name, statuePos);
	}
}
