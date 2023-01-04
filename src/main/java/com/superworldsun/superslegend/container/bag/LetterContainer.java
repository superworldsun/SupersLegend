package com.superworldsun.superslegend.container.bag;

import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class LetterContainer extends BagContainer {
	public LetterContainer(int windowId, PlayerInventory playerInventory, Hand activeHand) {
		super(ContainerInit.LETTER.get(), windowId, playerInventory, activeHand);
	}

	public LetterContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData) {
		this(windowId, playerInventory, additionalData.readEnum(Hand.class));
	}

	@Override
	protected int getBagSlotRows() {
		return 1;
	}

	@Override
	protected int getBagSlotColumns() {
		return 1;
	}
}
