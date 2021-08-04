package com.superworldsun.superslegend.blocks.tile;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GossipStoneTileEntity extends TileEntity
{
	private String message = "";
	
	public GossipStoneTileEntity()
	{
		super(TileEntityInit.GOSSIP_STONE.get());
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound)
	{
		compound.putString("message", message);
		return super.save(compound);
	}
	
	@Override
	public void load(BlockState state, CompoundNBT compound)
	{
		message = compound.getString("message");
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
	
	public ITextComponent getMessage(PlayerEntity player)
	{
		if (message.isEmpty() || player.getItemBySlot(EquipmentSlotType.HEAD).getItem() != ItemInit.MASK_MASKOFTRUTH.get())
		{
			return new TranslationTextComponent("block.superslegend.gossip_stone_block.silent");
		}
		else
		{
			return new StringTextComponent(message);
		}
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
}
