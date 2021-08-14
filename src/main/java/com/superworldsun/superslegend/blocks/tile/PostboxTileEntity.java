package com.superworldsun.superslegend.blocks.tile;

import com.superworldsun.superslegend.inventory.PostboxInventory;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.math.AxisAlignedBB;

public class PostboxTileEntity extends TileEntity
{
	public final PostboxInventory inventory = new PostboxInventory(this);
	
	public PostboxTileEntity()
	{
		super(TileEntityInit.POSTBOX.get());
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound)
	{
		compound.put("inventory", inventory.save(new CompoundNBT()));
		return super.save(compound);
	}
	
	@Override
	public void load(BlockState state, CompoundNBT compound)
	{
		inventory.load(compound.getCompound("inventory"));
		super.load(state, compound);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(worldPosition, worldPosition.offset(1.0D, 1.5D, 1.0D));
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
	
	public static TileEntityType<PostboxTileEntity> createType()
	{
		return Builder.of(PostboxTileEntity::new, BlockInit.POSTBOX_BLOCK.get()).build(null);
	}
}
