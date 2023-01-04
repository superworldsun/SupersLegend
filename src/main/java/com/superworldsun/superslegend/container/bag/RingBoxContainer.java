package com.superworldsun.superslegend.container.bag;

import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class RingBoxContainer extends BagContainer {
	public RingBoxContainer(int windowId, PlayerInventory playerInventory, Hand activeHand) {
		super(ContainerInit.RING_BOX.get(), windowId, playerInventory, activeHand);
	}

	public RingBoxContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData) {
		this(windowId, playerInventory, additionalData.readEnum(Hand.class));
	}

	@Override
	protected int getBagSlotRows() {
		return 1;
	}
}
