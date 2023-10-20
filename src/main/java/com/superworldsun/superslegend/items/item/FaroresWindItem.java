package com.superworldsun.superslegend.items.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FaroresWindItem extends Item {
	public FaroresWindItem() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		BlockPos returnPosition = getReturnPosition(stack);
		ResourceLocation returnDimension = getReturnPositionDimension(stack);
		if (returnPosition != null && Objects.equals(returnDimension, level.dimension().location())) {
			teleportEntity(player, returnPosition);
			player.displayClientMessage(Component.literal("Returned to saved position"), true);
		} else {
			setReturnPosition(stack, player.blockPosition(), level.dimension().location());
		}
		return InteractionResultHolder.success(stack);
	}

	public void teleportEntity(LivingEntity entity, BlockPos pos) {
		entity.teleportTo(pos.getX() + 0.5F, pos.getY() + 0.6F, pos.getZ() + 0.5F);
		entity.fallDistance = 0;
	}

	public static @Nullable BlockPos getReturnPosition(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("SavedPos")) {
			return NbtUtils.readBlockPos(tag.getCompound("SavedPos"));
		}
		return null;
	}

	public static @Nullable ResourceLocation getReturnPositionDimension(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("SavedPosDimension")) {
			return new ResourceLocation(tag.getString("SavedPosDimension"));
		}
		return null;
	}

	public static void setReturnPosition(ItemStack stack, BlockPos pos, ResourceLocation dimensionId) {
		stack.getOrCreateTag().put("SavedPos", NbtUtils.writeBlockPos(pos));
		stack.getOrCreateTag().putString("SavedPosDimension", dimensionId.toString());
	}
}
