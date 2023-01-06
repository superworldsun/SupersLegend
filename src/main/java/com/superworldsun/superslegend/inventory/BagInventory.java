package com.superworldsun.superslegend.inventory;

import com.superworldsun.superslegend.items.bags.BagItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

public class BagInventory implements IInventory {
	private final NonNullList<ItemStack> stacks;
	private final ItemStack bag;

	private BagInventory(ItemStack bag, int size) {
		this.bag = bag;
		this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	@Override
	public void clearContent() {
		stacks.clear();
		setChanged();
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		return stacks.isEmpty();
	}

	@Override
	public ItemStack getItem(int index) {
		return stacks.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int amount) {
		ItemStack itemstack = ItemStackHelper.removeItem(stacks, index, amount);

		if (!itemstack.isEmpty()) {
			this.setChanged();
		}

		return itemstack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		ItemStack stack = stacks.get(index);

		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			stacks.set(index, ItemStack.EMPTY);
			return stack;
		}
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		stacks.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}

		setChanged();
	}

	@Override
	public void setChanged() {
		saveToStack(bag);
	}

	@Override
	public boolean canPlaceItem(int slotIndex, ItemStack itemStack) {
		return bag.getItem() instanceof BagItem && ((BagItem) bag.getItem()).canHoldItem(itemStack);
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return player.isAlive();
	}

	public void load(CompoundNBT nbt) {
		ListNBT stackTags = nbt.getList("Slots", new CompoundNBT().getId());

		for (int i = 0; i < stackTags.size(); i++) {
			stacks.set(i, ItemStack.of((CompoundNBT) stackTags.get(i)));
		}
	}

	public CompoundNBT save(CompoundNBT nbt) {
		ListNBT stackTags = new ListNBT();
		stacks.forEach(stack -> stackTags.add(stack.save(new CompoundNBT())));
		nbt.put("Slots", stackTags);
		return nbt;
	}

	public static BagInventory fromStack(ItemStack stack, int size) {
		BagInventory inventory = new BagInventory(stack, size);
		inventory.load(stack.getOrCreateTagElement("Inventory"));
		return inventory;
	}

	public void saveToStack(ItemStack stack) {
		save(stack.getOrCreateTagElement("Inventory"));
	}
}
