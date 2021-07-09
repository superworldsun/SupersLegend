package superworldsun.superslegend.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

public class RupeepouchInventory extends Inventory {
	
	private final ItemStack stack;
	
	public RupeepouchInventory(ItemStack stack, int count) {
		super(count);
		this.stack = stack;
		readItemStack();
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	public void readItemStack() {
		if (stack.getTag() != null) {
			readNBT(stack.getTag());
		}
	}
	
	public void writeItemStack() {
		if (isEmpty()) {
			stack.removeTagKey("Items");
		} else {
			writeNBT(stack.getOrCreateTag());
		}
	}
	
	private void readNBT(CompoundNBT compound) {
		final NonNullList<ItemStack> list = NonNullList.<ItemStack> withSize(getContainerSize(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, list);
		for (int i = 0; i < list.size(); i++) {
			setItem(i, list.get(i));
		}
	}
	
	private void writeNBT(CompoundNBT compound) {
		final NonNullList<ItemStack> list = NonNullList.<ItemStack> withSize(getContainerSize(), ItemStack.EMPTY);
		for (int i = 0; i < list.size(); i++) {
			list.set(i, getItem(i));
		}
		ItemStackHelper.saveAllItems(compound, list, false);
	}
}