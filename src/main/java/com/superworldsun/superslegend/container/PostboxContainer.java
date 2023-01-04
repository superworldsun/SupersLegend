package com.superworldsun.superslegend.container;

import com.superworldsun.superslegend.blocks.entity.PostboxTileEntity;
import com.superworldsun.superslegend.container.util.SlotZone;
import com.superworldsun.superslegend.inventory.PostboxInventory;
import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class PostboxContainer extends Container {
	private final SlotZone wholeInventorySlotZone = new SlotZone(0, 36);
	private final SlotZone upperInventorySlotZone = new SlotZone(0, 27);
	private final SlotZone hotbarSlotZone = new SlotZone(27, 9);
	private final SlotZone postboxSlotZone = new SlotZone(36, 9);
	private final PostboxInventory postboxInventory;

	public PostboxContainer(int windowId, PlayerInventory playerInventory, PostboxInventory postboxInventory) {
		super(ContainerInit.POSTBOX.get(), windowId);

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlot(new Slot(postboxInventory, j + i * 3, 62 + j * 18, 18 + i * 18));
			}
		}

		this.postboxInventory = postboxInventory;
	}

	public PostboxContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData) {
		this(windowId, playerInventory, new PostboxInventory((PostboxTileEntity) playerInventory.player.level.getBlockEntity(additionalData.readBlockPos())));
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return postboxInventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int sourceSlotIndex) {
		Slot sourceSlot = slots.get(sourceSlotIndex);

		if (sourceSlot == null || !sourceSlot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack sourceStackBeforeMerge = sourceStack.copy();
		boolean successfulTransfer = tryMergingStack(sourceSlotIndex, sourceStack);

		if (!successfulTransfer) {
			return ItemStack.EMPTY;
		}

		if (sourceStack.isEmpty()) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}

		if (sourceStack.getCount() == sourceStackBeforeMerge.getCount()) {
			return ItemStack.EMPTY;
		}

		sourceSlot.onTake(player, sourceStack);
		return sourceStackBeforeMerge;
	}

	private boolean tryMergingStack(int sourceSlotIndex, ItemStack sourceStack) {
		if (postboxSlotZone.contains(sourceSlotIndex)) {
			return mergeInto(wholeInventorySlotZone, sourceStack, false);
		}

		if (wholeInventorySlotZone.contains(sourceSlotIndex)) {
			if (mergeInto(postboxSlotZone, sourceStack, false)) {
				return true;
			}

			if (hotbarSlotZone.contains(sourceSlotIndex)) {
				return mergeInto(upperInventorySlotZone, sourceStack, false);
			} else {
				return mergeInto(hotbarSlotZone, sourceStack, false);
			}
		}

		return false;
	}

	private boolean mergeInto(SlotZone destinationZone, ItemStack sourceItemStack, boolean fillFromEnd) {
		return moveItemStackTo(sourceItemStack, destinationZone.firstSlotIndex, destinationZone.lastSlotIndex, fillFromEnd);
	}
}
