package com.superworldsun.superslegend.container;

import com.superworldsun.superslegend.items.containers.ContainerItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemContainer implements Container {
    private final NonNullList<ItemStack> stacks;
    private final ItemStack containerStack;

    private ItemContainer(ItemStack containerStack, int size) {
        this.containerStack = containerStack;
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
    public @NotNull ItemStack getItem(int index) {
        return stacks.get(index);
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int amount) {
        ItemStack itemstack = ContainerHelper.removeItem(stacks, index, amount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }
        return itemstack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = stacks.get(index);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            stacks.set(index, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        stacks.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public void setChanged() {
        saveToStack(containerStack);
    }

    @Override
    public boolean canPlaceItem(int slotIndex, @NotNull ItemStack itemStack) {
        return containerStack.getItem() instanceof ContainerItem container && container.canContainItem(itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    public static ItemContainer fromStack(ItemStack stack, int size) {
        ItemContainer container = new ItemContainer(stack, size);
        container.load(stack.getOrCreateTagElement("Inventory"));
        return container;
    }

    public void saveToStack(ItemStack stack) {
        save(stack.getOrCreateTagElement("Inventory"));
    }

    public void load(CompoundTag tag) {
        ListTag stackTags = tag.getList("Slots", Tag.TAG_COMPOUND);
        for (int i = 0; i < stackTags.size(); i++) {
            stacks.set(i, ItemStack.of((CompoundTag) stackTags.get(i)));
        }
    }

    public void save(CompoundTag nbt) {
        ListTag stackTags = new ListTag();
        stacks.forEach(stack -> stackTags.add(stack.save(new CompoundTag())));
        nbt.put("Slots", stackTags);
    }
}
