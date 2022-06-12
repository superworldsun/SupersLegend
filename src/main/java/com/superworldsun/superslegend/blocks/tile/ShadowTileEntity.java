package com.superworldsun.superslegend.blocks.tile;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ShadowTileEntity extends TileEntity
{
	private BlockState disguise;
	
	protected ShadowTileEntity(TileEntityType<? extends ShadowTileEntity> tileEntityType)
	{
		super(tileEntityType);
	}
	
	public ShadowTileEntity()
	{
		super(TileEntityInit.SHADOW.get());
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return VoxelShapes.block().bounds().move(getBlockPos());
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compoundNBT)
	{
		compoundNBT.putInt("disguise", Block.getId(disguise));
		return super.save(compoundNBT);
	}
	
	@Override
	public void load(BlockState blockState, CompoundNBT compoundNBT)
	{
		int disguiseId = compoundNBT.getInt("disguise");
		
		if (disguiseId != 0)
		{
			disguise = Block.stateById(disguiseId);
		}
		
		super.load(blockState, compoundNBT);
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
	
	public BlockState getDisguise()
	{
		return disguise;
	}
	
	public void setDisguise(BlockState disguise)
	{
		this.disguise = disguise;
	}
	
	public BlockState getAppearance()
	{
		return disguise == null ? BlockInit.SHADOW_MODEL_BLOCK.get().defaultBlockState() : disguise;
	}
	
	public static TileEntityType<ShadowTileEntity> createType()
	{
		return Builder.of(ShadowTileEntity::new, BlockInit.SHADOW_BLOCK.get()).build(null);
	}
}
