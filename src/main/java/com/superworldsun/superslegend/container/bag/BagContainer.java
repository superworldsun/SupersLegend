package com.superworldsun.superslegend.container.bag;

import com.superworldsun.superslegend.container.SimpleContainer;
import com.superworldsun.superslegend.container.slot.BagSlot;
import com.superworldsun.superslegend.container.slot.LockedSlot;
import com.superworldsun.superslegend.container.util.SlotFactory;
import com.superworldsun.superslegend.inventory.BagInventory;
import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class BagContainer extends SimpleContainer {
	private final Hand handHoldingBag;

	public BagContainer(ContainerType<? extends BagContainer> type, int windowId, PlayerInventory playerInventory, Hand handHoldingBag, int slotRows, int slotColumns) {
		super(type, windowId, playerInventory, BagContainer.createBagInventory(playerInventory, handHoldingBag, slotRows * slotColumns), slotRows, slotColumns);
		this.handHoldingBag = handHoldingBag;
	}

	public BagContainer(int windowId, PlayerInventory playerInventory, Hand handHoldingBag) {
		this(ContainerInit.BAG.get(), windowId, playerInventory, handHoldingBag, 3, 9);
	}

	public BagContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData) {
		this(windowId, playerInventory, additionalData.readEnum(Hand.class));
	}

	@Override
	protected SlotFactory getContainerSlotFactory(int slotIndex) {
		return BagSlot::new;
	}

	@Override
	protected SlotFactory getHotbarSlotFactory(int slotIndex) {
		return isBagInSlot(slotIndex) ? LockedSlot::new : Slot::new;
	}

	private boolean isBagInSlot(int slotIndex) {
		return handHoldingBag == Hand.MAIN_HAND && playerInventory.selected == slotIndex;
	}

	private static BagInventory createBagInventory(PlayerInventory playerInventory, Hand handHoldingBag, int containerSize) {
		return BagInventory.fromStack(playerInventory.player.getItemInHand(handHoldingBag), containerSize);
	}
}
