package com.superworldsun.superslegend.container.menu;

import com.superworldsun.superslegend.container.slot.SlotFactory;
import com.superworldsun.superslegend.container.slot.SlotZone;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SimpleContainerMenu extends AbstractContainerMenu {
    private final SlotZone wholeInventorySlotZone = new SlotZone(0, 36);
    private final SlotZone upperInventorySlotZone = new SlotZone(0, 27);
    private final SlotZone hotbarSlotZone = new SlotZone(27, 9);
    private final SlotZone containerSlotZone;
    private final int width;
    private final int height;
    protected final Inventory playerInventory;
    protected final Container container;

    public SimpleContainerMenu(MenuType<? extends SimpleContainerMenu> type, int windowId, Inventory playerInventory, Container container, int width, int height) {
        super(type, windowId);
        this.playerInventory = playerInventory;
        this.container = container;
        this.height = height;
        this.width = width;
        this.containerSlotZone = new SlotZone(36, height * width);
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
            int slotX = 8 + i * 18;
            int slotY = 144;
            Slot slot = getHotbarSlotFactory(i).create(playerInventory, i, slotX, slotY);
            addSlot(slot);
        }
    }

    protected SlotFactory getHotbarSlotFactory(int slotIndex) {
        return Slot::new;
    }

    protected void addContainerSlots() {
        int initialX = getContainerSlotsInitialX();
        int initialY = getContainerSlotsInitialY();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int slotIndex = j + i * width;
                int slotX = initialX + j * 18;
                int slotY = initialY + i * 18;
                Slot slot = getContainerSlotFactory(slotIndex).create(container, slotIndex, slotX, slotY);
                addSlot(slot);
            }
        }
    }

    protected SlotFactory getContainerSlotFactory(int slotIndex) {
        return Slot::new;
    }

    private int getContainerSlotsInitialX() {
        return 8 + (9 - width) * 9;
    }

    private int getContainerSlotsInitialY() {
        return 18 + (3 - height) * 9;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return container.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int sourceSlotIndex) {
        Slot sourceSlot = slots.get(sourceSlotIndex);
        if (!sourceSlot.hasItem()) {
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
