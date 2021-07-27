package com.superworldsun.superslegend.blocks.tile;

import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;

public class SwitchableFanTileEntity extends FanTileEntity
{
	public boolean powered;
	
	public SwitchableFanTileEntity()
	{
		super(TileEntityInit.SWITCHABLE_FAN.get());
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound)
	{
		compound.putBoolean("powered", powered);
		return super.save(compound);
	}
	
	@Override
	public void load(BlockState state, CompoundNBT compound)
	{
		powered = compound.getBoolean("powered");
		super.load(state, compound);
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(worldPosition, 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
	{
		load(level.getBlockState(packet.getPos()), packet.getTag());
	}
	
	@Override
	public CompoundNBT getUpdateTag()
	{
		return save(new CompoundNBT());
	}
	
	public boolean isPowered()
	{
		return powered;
	}
}
