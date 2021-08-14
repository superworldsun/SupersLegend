package com.superworldsun.superslegend.container;

import com.superworldsun.superslegend.container.slot.BagSlot;
import com.superworldsun.superslegend.container.slot.LockedSlot;
import com.superworldsun.superslegend.inventory.BagInventory;
import com.superworldsun.superslegend.items.BagItem;
import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class BagContainer extends Container
{
	private final BagItem bagItem;
	
	public BagContainer(int windowId, PlayerInventory playerInventory, Hand activeHand)
	{
		super(ContainerInit.BAG.get(), windowId);
		ItemStack bagStack = playerInventory.player.getItemInHand(activeHand);
		BagInventory bagInventory = BagInventory.fromStack(bagStack, 27);
		bagItem = (BagItem) bagStack.getItem();
		
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; ++i)
		{
			if (activeHand == Hand.MAIN_HAND && playerInventory.selected == i)
			{
				addSlot(new LockedSlot(playerInventory, i, 8 + i * 18, 144));
			}
			else
			{
				addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
			}
		}
		
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				addSlot(new BagSlot(bagInventory, j + i * 9, 8 + j * 18, 18 + i * 18, bagItem));
			}
		}
	}
	
	public BagContainer(int windowId, PlayerInventory playerInventory, PacketBuffer additionalData)
	{
		this(windowId, playerInventory, additionalData.readEnum(Hand.class));
	}
	
	@Override
	public boolean stillValid(PlayerEntity player)
	{
		return player.isAlive();
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int sourceSlotIndex)
	{
		Slot sourceSlot = slots.get(sourceSlotIndex);
		
		if (sourceSlot == null || !sourceSlot.hasItem())
		{
			return ItemStack.EMPTY;
		}
		
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack sourceStackBeforeMerge = sourceStack.copy();
		boolean successfulTransfer = false;
		
		if (SlotZone.BAG.contains(sourceSlotIndex))
		{
			successfulTransfer = mergeInto(SlotZone.WHOLE_INVENTORY, sourceStack, false);
		}
		
		if (SlotZone.WHOLE_INVENTORY.contains(sourceSlotIndex))
		{
			if (bagItem.canHoldItem(sourceStack))
			{
				successfulTransfer = mergeInto(SlotZone.BAG, sourceStack, false);
			}
			
			if (!successfulTransfer)
			{
				if (SlotZone.HOTBAR.contains(sourceSlotIndex))
				{
					successfulTransfer = mergeInto(SlotZone.INVENTORY, sourceStack, false);
				}
				else
				{
					successfulTransfer = mergeInto(SlotZone.HOTBAR, sourceStack, false);
				}
			}
		}
		
		if (!successfulTransfer)
		{
			return ItemStack.EMPTY;
		}
		
		if (sourceStack.isEmpty())
		{
			sourceSlot.set(ItemStack.EMPTY);
		}
		else
		{
			sourceSlot.setChanged();
		}
		
		if (sourceStack.getCount() == sourceStackBeforeMerge.getCount())
		{
			return ItemStack.EMPTY;
		}
		
		sourceSlot.onTake(player, sourceStack);
		return sourceStackBeforeMerge;
	}
	
	private boolean mergeInto(SlotZone destinationZone, ItemStack sourceItemStack, boolean fillFromEnd)
	{
		return moveItemStackTo(sourceItemStack, destinationZone.firstIndex, destinationZone.lastIndexPlus1, fillFromEnd);
	}
	
	private enum SlotZone
	{
		WHOLE_INVENTORY(0, 36), INVENTORY(0, 27), HOTBAR(27, 9), BAG(36, 27);
		
		SlotZone(int firstIndex, int numberOfSlots)
		{
			this.firstIndex = firstIndex;
			this.lastIndexPlus1 = firstIndex + numberOfSlots;
		}
		
		public final int firstIndex;
		public final int lastIndexPlus1;
		
		public boolean contains(int slotIndex)
		{
			return slotIndex >= firstIndex && slotIndex < lastIndexPlus1;
		}
	}
}
