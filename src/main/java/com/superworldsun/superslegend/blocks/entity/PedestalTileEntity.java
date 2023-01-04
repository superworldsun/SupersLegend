package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.ItemStackHandler;

public class PedestalTileEntity extends SyncableTileEntity {
	private ItemStackHandler inventory = new ItemStackHandler(1);

	public PedestalTileEntity() {
		super(TileEntityInit.PEDESTAL.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.put("inventory", inventory.serializeNBT());
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		inventory.deserializeNBT(compound.getCompound("inventory"));
		super.load(state, compound);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(worldPosition, worldPosition.offset(1.0D, 1.5D, 1.0D));
	}

	public ItemStack getSword() {
		return inventory.getStackInSlot(0);
	}

	public void setSword(ItemStack stack) {
		inventory.setStackInSlot(0, stack);
		setChanged();
	}

	public void dropSword() {
		ItemEntity itemEntity = new ItemEntity(level, worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getSword());
		level.addFreshEntity(itemEntity);
	}

	public static TileEntityType<PedestalTileEntity> createType() {
		return Builder.of(PedestalTileEntity::new, BlockInit.PEDESTAL.get()).build(null);
	}
}
