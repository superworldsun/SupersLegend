package com.superworldsun.superslegend.warppads;

import java.util.HashMap;
import java.util.Map;

import com.superworldsun.superslegend.blocks.WarpPadBlock;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class WarpPadsServerData extends WorldSavedData {
	private final Map<BlockPos, WarpPadBlock> warpPads = new HashMap<BlockPos, WarpPadBlock>();
	private static final String DATA_ID = "warppads";

	private WarpPadsServerData() {
		super(DATA_ID);
	}

	@Override
	public void load(CompoundNBT nbt) {
		ListNBT waypointsListNbt = nbt.getList(DATA_ID, new CompoundNBT().getId());
		warpPads.clear();
		waypointsListNbt.stream().map(CompoundNBT.class::cast).forEach(this::loadWarpPadFromNBT);
	}

	private void loadWarpPadFromNBT(CompoundNBT nbt) {
		int posX = nbt.getInt("x");
		int posY = nbt.getInt("y");
		int posZ = nbt.getInt("z");
		BlockPos pos = new BlockPos(posX, posY, posZ);
		ResourceLocation warpPadId = new ResourceLocation(nbt.getString("WarpPadId"));
		WarpPadBlock warpPad = (WarpPadBlock) ForgeRegistries.BLOCKS.getValue(warpPadId);
		addWarpPad(pos, warpPad);
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		ListNBT warpPadsListNbt = new ListNBT();
		warpPads.forEach((pos, warpPad) -> saveWarpPadToNBT(pos, warpPad, warpPadsListNbt));
		nbt.put(DATA_ID, warpPadsListNbt);
		return nbt;
	}

	private void saveWarpPadToNBT(BlockPos pos, WarpPadBlock warpPad, ListNBT listNbt) {
		CompoundNBT warpPadNBT = new CompoundNBT();
		warpPadNBT.putInt("x", pos.getX());
		warpPadNBT.putInt("y", pos.getY());
		warpPadNBT.putInt("z", pos.getZ());
		String warpPadId = ForgeRegistries.BLOCKS.getKey(warpPad).toString();
		warpPadNBT.putString("WarpPadId", warpPadId);
		listNbt.add(warpPadNBT);
	}

	public void placeWarpPad(BlockPos pos, WarpPadBlock warpPad) {
		warpPads.put(pos, warpPad);
		setDirty();
	}

	public WarpPadBlock getWarpPad(BlockPos pos) {
		return warpPads.get(pos);
	}

	private void addWarpPad(BlockPos pos, WarpPadBlock warpPad) {
		warpPads.put(pos, warpPad);
	}

	public void removeWarpPad(BlockPos waypointPos) {
		warpPads.remove(waypointPos);
		setDirty();
	}

	public static WarpPadsServerData instance() {
		return ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage().computeIfAbsent(WarpPadsServerData::new, DATA_ID);
	}
}
