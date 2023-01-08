package com.superworldsun.superslegend.container;

import com.superworldsun.superslegend.inventory.PostboxInventory;
import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class PostboxContainer extends SimpleContainer {
	public PostboxContainer(int windowId, PlayerInventory playerInventory, PostboxInventory postboxInventory) {
		super(ContainerInit.POSTBOX.get(), windowId, playerInventory, postboxInventory, 3, 3);
	}

	public PostboxContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData) {
		this(windowId, playerInventory, new PostboxInventory(null));
	}
}
