package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class SwitchableFanTileEntity extends FanTileEntity {
	public boolean isPowered;

	public SwitchableFanTileEntity() {
		super(TileEntityInit.SWITCHABLE_FAN.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putBoolean("powered", isPowered);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		isPowered = compound.getBoolean("powered");
		super.load(state, compound);
	}

	public boolean isPowered() {
		return isPowered;
	}
}
