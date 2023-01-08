package com.superworldsun.superslegend.inventory;

import java.util.Optional;

import com.superworldsun.superslegend.blocks.entity.PostboxTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

public class PostboxInventory implements IInventory {
	private final NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
	private final PostboxTileEntity postbox;

	public PostboxInventory(PostboxTileEntity postbox) {
		this.postbox = postbox;
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
		Optional.ofNullable(postbox).ifPresent(PostboxTileEntity::setChanged);
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return player.isAlive() && postbox.getLevel().getBlockEntity(postbox.getBlockPos()) == postbox;
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
}
