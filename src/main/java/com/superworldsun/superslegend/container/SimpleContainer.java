package com.superworldsun.superslegend.container;

import com.superworldsun.superslegend.container.util.SlotFactory;
import com.superworldsun.superslegend.container.util.SlotZone;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public abstract class SimpleContainer extends Container {
	private final SlotZone wholeInventorySlotZone = new SlotZone(0, 36);
	private final SlotZone upperInventorySlotZone = new SlotZone(0, 27);
	private final SlotZone hotbarSlotZone = new SlotZone(27, 9);
	private final SlotZone containerSlotZone;
	private final int containerSlotRows;
	private final int containerSlotColumns;
	protected final PlayerInventory playerInventory;
	protected final IInventory containerInventory;

	public SimpleContainer(ContainerType<? extends SimpleContainer> type, int windowId, PlayerInventory playerInventory, IInventory containerInventory, int slotRows,
			int slotColumns) {
		super(type, windowId);
		this.playerInventory = playerInventory;
		this.containerInventory = containerInventory;
		this.containerSlotRows = slotRows;
		this.containerSlotColumns = slotColumns;
		this.containerSlotZone = new SlotZone(36, slotRows * slotColumns);
		addPlayerSlots();
		addContainerSlots();
	}

	protected void addPlayerSlots() {
		addUpperInventorySlots();
		addHotbarSlots();
	}

	protected void addUpperInventorySlots() {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}
	}

	protected void addHotbarSlots() {
		for (int i = 0; i < 9; ++i) {
			int slotIndex = i;
			int slotX = 8 + i * 18;
			int slotY = 144;
			Slot slot = getHotbarSlotFactory(slotIndex).create(playerInventory, slotIndex, slotX, slotY);
			addSlot(slot);
		}
	}

	protected SlotFactory getHotbarSlotFactory(int slotIndex) {
		return Slot::new;
	}

	protected void addContainerSlots() {
		int initialX = getContainerSlotsInitialX();
		int initialY = getContainerSlotsInitialY();

		for (int i = 0; i < containerSlotRows; ++i) {
			for (int j = 0; j < containerSlotColumns; ++j) {
				int slotIndex = j + i * containerSlotColumns;
				int slotX = initialX + j * 18;
				int slotY = initialY + i * 18;
				Slot slot = getContainerSlotFactory(slotIndex).create(containerInventory, slotIndex, slotX, slotY);
				addSlot(slot);
			}
		}
	}

	protected SlotFactory getContainerSlotFactory(int slotIndex) {
		return Slot::new;
	}

	private int getContainerSlotsInitialX() {
		return 8 + (9 - containerSlotColumns) * 9;
	}

	private int getContainerSlotsInitialY() {
		return 18 + (3 - containerSlotRows) * 9;
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return containerInventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int sourceSlotIndex) {
		Slot sourceSlot = slots.get(sourceSlotIndex);

		if (sourceSlot == null || !sourceSlot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack sourceStackBeforeMerge = sourceStack.copy();
		boolean canMergeStack = tryMergingStack(sourceSlotIndex, sourceStack);

		if (!canMergeStack) {
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
		if (containerSlotZone.containsSlot(sourceSlotIndex)) {
			return mergeInto(wholeInventorySlotZone, sourceStack, false);
		}

		if (wholeInventorySlotZone.containsSlot(sourceSlotIndex)) {
			if (mergeInto(containerSlotZone, sourceStack, false)) {
				return true;
			}

			if (hotbarSlotZone.containsSlot(sourceSlotIndex)) {
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
