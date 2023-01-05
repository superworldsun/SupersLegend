package com.superworldsun.superslegend.items.bags;

import com.superworldsun.superslegend.container.bag.BagContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class BagItem extends Item {
	public BagItem(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClientSide) {
			NetworkHooks.openGui((ServerPlayerEntity) player, createContainerProvider(player, hand), packetBuffer -> packetBuffer.writeEnum(hand));
			world.playSound(null, player, SoundEvents.ARMOR_EQUIP_ELYTRA, SoundCategory.PLAYERS, 1f, 1f);
		}

		return ActionResult.success(player.getItemInHand(hand));
	}

	protected INamedContainerProvider createContainerProvider(PlayerEntity player, Hand hand) {
		return new INamedContainerProvider() {
			@Override
			public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
				return createContainer(windowId, playerInventory, player, hand);
			}

			@Override
			public ITextComponent getDisplayName() {
				return player.getItemInHand(hand).getHoverName();
			}
		};
	}

	public Container createContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, Hand handIn) {
		return new BagContainer(windowId, player.inventory, handIn);
	}

	public abstract boolean canHoldItem(ItemStack stack);
}
