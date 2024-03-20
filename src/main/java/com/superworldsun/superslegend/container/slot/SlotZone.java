package com.superworldsun.superslegend.container.slot;

public class SlotZone {
    public final int firstSlotIndex;
    public final int lastSlotIndex;

    public SlotZone(int firstSlotIndex, int numberOfSlots) {
        this.firstSlotIndex = firstSlotIndex;
        this.lastSlotIndex = firstSlotIndex + numberOfSlots;
    }

    public boolean containsSlot(int slotIndex) {
        return slotIndex >= firstSlotIndex && slotIndex < lastSlotIndex;
    }
}
