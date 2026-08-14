package com.superworldsun.superslegend.blocks.entity;

//import com.superworldsun.superslegend.container.PostboxContainer;
import com.superworldsun.superslegend.inventory.PostboxInventory;
import com.superworldsun.superslegend.menus.PostboxMenu;
import com.superworldsun.superslegend.registries.BlockEntityInit;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

public class PostboxBlockEntity extends BlockEntity implements MenuProvider {
	private static final Component TITLE =
			Component.translatable("Postbox");
	private final PostboxInventory inventory = new PostboxInventory(this);
	private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.inventory);

	private boolean isLocked;
	public PostboxBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.POSTBOX_ENTITY.get(), pos, state);
	}

	public void dropInventoryContents() {
		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (!stack.isEmpty()) {
				Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack);
			}
		}
	}

	public void interact(ServerPlayer player, InteractionHand hand, BlockState state, Level level, BlockPos pos) {
		if (player.isCrouching()) {
			if (isPlayerPostman(player)) {
				toggleLockedState(player);
			}
		} else {
			if (canBeOpenedByPlayer(player)) {
				NetworkHooks.openScreen(player, this, pos);
				level.playSound(null, player, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1F, 1F);
			} else {
				ItemStack itemInHand = player.getItemInHand(hand);

				if (!addItemIntoInventory(itemInHand, 1)) {
					level.playSound(null, player, SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 1F, 1F);
				}
			}
		}
	}

	private boolean addItemIntoInventory(ItemStack itemInHand, int amount) {
		if (itemInHand.isEmpty() || itemInHand.getCount() < amount) {
			return false;
		}
		for (int i = 0; i < inventory.getSlots(); i++) {
			if (addItemInSlot(itemInHand, i, amount)) {
				return true;
			}
		}
		return false;
	}
	protected boolean addItemInSlot(ItemStack itemStack, int slotIndex, int amount) {
		ItemStack itemInSlot = inventory.getStackInSlot(slotIndex);
		boolean isSlotFull = itemInSlot.getCount() == itemInSlot.getMaxStackSize();
		if (isSlotFull) {
			return false;
		}
		boolean isSameItemInSlot = itemInSlot.is(itemStack.getItem()) && ItemStack.isSameItemSameTags(itemInSlot, itemStack);
		if (itemInSlot.isEmpty() || isSameItemInSlot) {
			int newCount = Math.min(itemInSlot.getCount() + amount, itemInSlot.getMaxStackSize());
			int remaining = amount - (newCount - itemInSlot.getCount());
			itemStack.shrink(remaining);
			ItemStack newStack = itemInSlot.copy();
			newStack.setCount(newCount);
			inventory.setStackInSlot(slotIndex, newStack);
			level.playSound(null, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1F, 1F);
			return true;
		}
		return false;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
		return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		this.optional.invalidate();
	}

	protected void setItemCopyInSlot(ItemStack itemStack, int slotIndex, int amount, boolean adding) {
		ItemStack itemCopy = itemStack.copy();
		itemCopy.setCount(amount);
		if (adding) {
			itemCopy.grow(inventory.getStackInSlot(slotIndex).getCount());
		}
		inventory.setStackInSlot(slotIndex, itemCopy);
	}

	private boolean canBeOpenedByPlayer(ServerPlayer player) {
		return !isLocked || isPlayerPostman(player);
	}

	private boolean isPlayerPostman(ServerPlayer player) {
		return CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_POSTMANSHAT.get(), player).isPresent();
	}

	private void toggleLockedState(ServerPlayer player) {
		if (isLocked) {
			level.playSound(null, player, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1F, 1F);
		} else {
			level.playSound(null, player, SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 1F, 1F);
		}
		isLocked ^= true;
		if (isLocked) {
			ModAdvancementHelper.award(player, "special_delivery", "locked_mailbox");
		}
		String lockStatus = isLocked ? "locked" : "unlocked";
		player.displayClientMessage(Component.translatable("Postbox " + lockStatus), true);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new PostboxMenu(pContainerId, pPlayerInventory, this);
	}

	@Override
	public Component getDisplayName() {
		return TITLE;
	}

	@Override
	public @NotNull void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("inventory", inventory.save(new CompoundTag()));
		tag.putBoolean("locked", isLocked);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		inventory.load(tag.getCompound("inventory"));
		isLocked = tag.getBoolean("locked");
	}


	public LazyOptional<ItemStackHandler> getOptional() {
		return this.optional;
	}

	public static BlockEntityType<PostboxBlockEntity> createType() {
		return BlockEntityType.Builder.of(PostboxBlockEntity::new, BlockInit.POSTBOX_BLOCK.get()).build(null);
	}
}
