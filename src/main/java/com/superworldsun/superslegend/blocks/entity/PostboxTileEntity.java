package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.container.PostboxContainer;
import com.superworldsun.superslegend.inventory.PostboxInventory;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import top.theillusivec4.curios.api.CuriosApi;

public class PostboxTileEntity extends SyncableTileEntity implements INamedContainerProvider {
	private final PostboxInventory inventory = new PostboxInventory(this);
	private boolean isLocked;

	public PostboxTileEntity() {
		super(TileEntityInit.POSTBOX.get());
	}

	public void dropInventoryContents() {
		InventoryHelper.dropContents(level, getBlockPos(), inventory);
	}

	public void interact(ServerPlayerEntity player, Hand hand) {
		if (player.isCrouching()) {
			if (isPlayerPostman(player)) {
				toggleLockedState(player);
			}
		} else {
			if (canBeOpenedByPlayer(player)) {
				NetworkHooks.openGui(player, this, packetBuffer -> packetBuffer.writeBlockPos(getBlockPos()));
			} else {
				ItemStack itemInHand = player.getItemInHand(hand);
				addItemIntoInventoryIfPossible(itemInHand, 1);
			}
		}
	}

	private void addItemIntoInventoryIfPossible(ItemStack itemInHand, int amount) {
		if (itemInHand.getCount() < amount) {
			return;
		}

		for (int i = 0; i < inventory.getContainerSize(); i++) {
			if (addItemInSlot(itemInHand, i, amount)) {
				return;
			}
		}
	}

	protected boolean addItemInSlot(ItemStack itemStack, int slotIndex, int amount) {
		ItemStack itemInSlot = inventory.getItem(slotIndex);
		boolean isSlotFull = itemInSlot.getCount() == itemInSlot.getMaxStackSize();

		if (isSlotFull) {
			return false;
		}

		boolean isSameItemInSlot = ItemStack.isSame(itemInSlot, itemStack) && ItemStack.tagMatches(itemInSlot, itemStack);

		if (itemInSlot.isEmpty() || isSameItemInSlot) {
			setItemCopyInSlot(itemStack, slotIndex, amount, isSameItemInSlot);
			itemStack.shrink(amount);
			level.playSound(null, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 1F, 1F);
			return true;
		}

		return false;
	}

	protected void setItemCopyInSlot(ItemStack itemStack, int slotIndex, int amount, boolean adding) {
		ItemStack itemCopy = itemStack.copy();
		itemCopy.setCount(amount);

		if (adding) {
			itemCopy.grow(inventory.getItem(slotIndex).getCount());
		}

		inventory.setItem(slotIndex, itemCopy);
	}

	private boolean canBeOpenedByPlayer(ServerPlayerEntity player) {
		return !isLocked || isLocked && isPlayerPostman(player);
	}

	private boolean isPlayerPostman(ServerPlayerEntity player) {
		return CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_POSTMANSHAT.get(), player).isPresent();
	}

	private void toggleLockedState(ServerPlayerEntity player) {
		if (isLocked) {
			level.playSound(null, player, SoundEvents.IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1F, 1F);
		} else {
			level.playSound(null, player, SoundEvents.IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1F, 1F);
		}

		isLocked ^= true;
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
		return new PostboxContainer(windowId, playerInventory, inventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		String blockDescriptionId = level.getBlockState(getBlockPos()).getBlock().getDescriptionId();
		return new TranslationTextComponent(blockDescriptionId);
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt.put("inventory", inventory.save(new CompoundNBT()));
		nbt.putBoolean("locked", isLocked);
		return super.save(nbt);
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		inventory.load(nbt.getCompound("inventory"));
		isLocked = nbt.getBoolean("locked");
		super.load(state, nbt);
	}

	public static TileEntityType<PostboxTileEntity> createType() {
		return Builder.of(PostboxTileEntity::new, BlockInit.POSTBOX_BLOCK.get()).build(null);
	}
}
