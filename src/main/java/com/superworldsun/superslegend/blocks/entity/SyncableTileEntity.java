package com.superworldsun.superslegend.blocks.entity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class SyncableTileEntity extends TileEntity {
	public SyncableTileEntity(TileEntityType<?> type) {
		super(type);
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(worldPosition, 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
		load(level.getBlockState(packet.getPos()), packet.getTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return save(new CompoundNBT());
	}
}
