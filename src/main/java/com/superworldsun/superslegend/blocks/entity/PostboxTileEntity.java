package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.container.PostboxContainer;
import com.superworldsun.superslegend.inventory.PostboxInventory;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

public class PostboxTileEntity extends SyncableTileEntity implements INamedContainerProvider {
	public final PostboxInventory inventory = new PostboxInventory(this);

	public PostboxTileEntity() {
		super(TileEntityInit.POSTBOX.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.put("inventory", inventory.save(new CompoundNBT()));
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		inventory.load(compound.getCompound("inventory"));
		super.load(state, compound);
	}

	public void openGui(BlockPos blockPos, PlayerEntity player) {
		NetworkHooks.openGui((ServerPlayerEntity) player, this, packetBuffer -> packetBuffer.writeBlockPos(blockPos));
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
		return new PostboxContainer(windowId, playerInventory, inventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		String blockDescriptionId = level.getBlockState(getBlockPos()).getBlock().getDescriptionId();
		return new TranslationTextComponent(blockDescriptionId);
	}

	public static TileEntityType<PostboxTileEntity> createType() {
		return Builder.of(PostboxTileEntity::new, BlockInit.POSTBOX_BLOCK.get()).build(null);
	}
}
