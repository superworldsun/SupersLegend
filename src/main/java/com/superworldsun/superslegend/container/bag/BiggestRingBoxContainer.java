package com.superworldsun.superslegend.container.bag;

import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class BiggestRingBoxContainer extends BagContainer {
	public BiggestRingBoxContainer(int windowId, PlayerInventory playerInventory, Hand activeHand) {
		super(ContainerInit.RING_BOX_BIGGEST.get(), windowId, playerInventory, activeHand, 3, 9);
	}

	public BiggestRingBoxContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData) {
		this(windowId, playerInventory, additionalData.readEnum(Hand.class));
	}
}
