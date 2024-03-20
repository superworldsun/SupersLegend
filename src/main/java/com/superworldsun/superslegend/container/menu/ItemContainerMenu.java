package com.superworldsun.superslegend.container.menu;

import com.superworldsun.superslegend.container.ItemContainer;
import com.superworldsun.superslegend.container.slot.ItemContainerSlot;
import com.superworldsun.superslegend.container.slot.LockedSlot;
import com.superworldsun.superslegend.container.slot.SlotFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public class ItemContainerMenu extends SimpleContainerMenu {
    private final InteractionHand handHoldingItem;

    public ItemContainerMenu(MenuType<? extends ItemContainerMenu> type, int windowId, Inventory playerInventory, InteractionHand handHoldingItem, int width, int height) {
        super(type, windowId, playerInventory, createItemContainer(playerInventory, handHoldingItem, height * width), width, height);
        this.handHoldingItem = handHoldingItem;
    }

    public ItemContainerMenu(MenuType<? extends ItemContainerMenu> type, int windowId, Inventory playerInventory, FriendlyByteBuf buf, int width, int height) {
        this(type, windowId, playerInventory, buf.readEnum(InteractionHand.class), width, height);
    }

    @Override
    protected SlotFactory getContainerSlotFactory(int slotIndex) {
        return ItemContainerSlot::new;
    }

    @Override
    protected SlotFactory getHotbarSlotFactory(int slotIndex) {
        return isItemInSlot(slotIndex) ? LockedSlot::new : Slot::new;
    }

    private boolean isItemInSlot(int slotIndex) {
        return handHoldingItem == InteractionHand.MAIN_HAND && playerInventory.selected == slotIndex;
    }

    private static ItemContainer createItemContainer(Inventory playerInventory, InteractionHand handHoldingBag, int containerSize) {
        return ItemContainer.fromStack(playerInventory.player.getItemInHand(handHoldingBag), containerSize);
    }
}
