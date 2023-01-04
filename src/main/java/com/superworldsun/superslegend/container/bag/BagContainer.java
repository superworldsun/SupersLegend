package com.superworldsun.superslegend.container.bag;

import com.superworldsun.superslegend.container.slot.BagSlot;
import com.superworldsun.superslegend.container.slot.LockedSlot;
import com.superworldsun.superslegend.container.util.SlotZone;
import com.superworldsun.superslegend.inventory.BagInventory;
import com.superworldsun.superslegend.items.bags.BagItem;
import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class BagContainer extends Container {
	private final SlotZone wholeInventorySlotZone = new SlotZone(0, 36);
	private final SlotZone upperInventorySlotZone = new SlotZone(0, 27);
	private final SlotZone hotbarSlotZone = new SlotZone(27, 9);
	private final SlotZone bagSlotZone = new SlotZone(36, getBagInventorySize());
	private final BagItem bagItem;

	public BagContainer(ContainerType<? extends BagContainer> type, int windowId, PlayerInventory playerInventory, Hand activeHand) {
		super(type, windowId);
		ItemStack bagItemStack = playerInventory.player.getItemInHand(activeHand);
		BagInventory bagInventory = BagInventory.fromStack(bagItemStack, getBagInventorySize());
		this.bagItem = (BagItem) bagItemStack.getItem();
		addInventorySlots(playerInventory, activeHand);
		addBagSlots(bagInventory);
	}

	public BagContainer(int windowId, PlayerInventory playerInventory, Hand activeHand) {
		this(ContainerInit.BAG.get(), windowId, playerInventory, activeHand);
	}

	public BagContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData) {
		this(windowId, playerInventory, additionalData.readEnum(Hand.class));
	}

	private void addBagSlots(BagInventory bagInventory) {
		int initialX = getBagSlotsInitialX();
		int initialY = getBagSlotsInitialY();

		for (int i = 0; i < getBagSlotRows(); ++i) {
			for (int j = 0; j < getBagSlotColumns(); ++j) {
				addSlot(new BagSlot(bagInventory, j + i * 9, initialX + j * 18, initialY + i * 18, bagItem));
			}
		}
	}

	private int getBagSlotsInitialX() {
		return 8 + (9 - getBagSlotColumns()) * 9;
	}

	private int getBagSlotsInitialY() {
		return 18 + (3 - getBagSlotRows()) * 9;
	}

	private void addInventorySlots(PlayerInventory playerInventory, Hand activeHand) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			if (activeHand == Hand.MAIN_HAND && playerInventory.selected == i) {
				addSlot(new LockedSlot(playerInventory, i, 8 + i * 18, 144));
			} else {
				addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
			}
		}
	}

	protected int getBagSlotColumns() {
		return 9;
	}

	protected int getBagSlotRows() {
		return 3;
	}

	private int getBagInventorySize() {
		return getBagSlotColumns() * getBagSlotRows();
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return player.isAlive();
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
		if (bagSlotZone.contains(sourceSlotIndex)) {
			return mergeInto(wholeInventorySlotZone, sourceStack, false);
		}

		if (wholeInventorySlotZone.contains(sourceSlotIndex)) {
			if (bagItem.canHoldItem(sourceStack)) {
				return mergeInto(bagSlotZone, sourceStack, false);
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
