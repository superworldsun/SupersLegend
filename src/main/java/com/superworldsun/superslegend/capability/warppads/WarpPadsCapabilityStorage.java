package com.superworldsun.superslegend.capability.warppads;

import org.apache.commons.lang3.tuple.Pair;

import com.superworldsun.superslegend.blocks.WarpPadBlock;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.registries.ForgeRegistries;

public class WarpPadsCapabilityStorage implements IStorage<WarpPadsCapability> {
	@Override
	public ListNBT writeNBT(Capability<WarpPadsCapability> capability, WarpPadsCapability instance, Direction side) {
		ListNBT listNBT = new ListNBT();
		instance.getWarpPositions().forEach((warpPad, pos) -> writeWarpPosition(listNBT, warpPad, pos));
		return listNBT;
	}

	@Override
	public void readNBT(Capability<WarpPadsCapability> capability, WarpPadsCapability instance, Direction side, INBT nbt) {
		ListNBT listNBT = (ListNBT) nbt;
		listNBT.stream().map(this::readWarpPosition).forEach(instance::saveWarpPosition);
	}

	private void writeWarpPosition(ListNBT nbt, WarpPadBlock warpPad, BlockPos pos) {
		CompoundNBT warpPosNbt = new CompoundNBT();
		ResourceLocation warpPadId = ForgeRegistries.BLOCKS.getKey(warpPad);
		warpPosNbt.putString("WarpPad", warpPadId.toString());
		warpPosNbt.putInt("X", pos.getX());
		warpPosNbt.putInt("Y", pos.getY());
		warpPosNbt.putInt("Z", pos.getZ());
		nbt.add(warpPosNbt);
	}

	private Pair<WarpPadBlock, BlockPos> readWarpPosition(INBT nbt) {
		CompoundNBT warpPosNbt = (CompoundNBT) nbt;
		ResourceLocation warpPadId = new ResourceLocation(warpPosNbt.getString("WarpPad"));
		WarpPadBlock warpPad = (WarpPadBlock) ForgeRegistries.BLOCKS.getValue(warpPadId);
		int x = warpPosNbt.getInt("X");
		int y = warpPosNbt.getInt("Y");
		int z = warpPosNbt.getInt("Z");
		BlockPos pos = new BlockPos(x, y, z);
		return Pair.of(warpPad, pos);
	}
}
