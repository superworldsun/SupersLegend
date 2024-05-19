package com.superworldsun.superslegend.items.customclass;

import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import net.minecraftforge.api.distmarker.*;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import java.util.*;

public class BottledEntityItem extends Item {

	private final EntityType<? extends LivingEntity> entityType;

	public BottledEntityItem(EntityType<? extends LivingEntity> entityType) {
		super(new Item.Properties().stacksTo(1));
		this.entityType = entityType;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			LivingEntity entity = entityType.create(player.level());
			Objects.requireNonNull(entity).load(stack.getOrCreateTag().getCompound("EntityData"));
			entity.moveTo(player.getX(), player.getY(), player.getZ(), player.yRotO, 0f);
			player.level().addFreshEntity(entity);
		}
		player.playSound(SoundInit.BOTTLE_POP.get(), 1f, 1f);
		player.getCooldowns().addCooldown(this, 6);
		stack.shrink(1);
		if (stack.isEmpty()) {
			player.getInventory().removeItem(stack);
		}
		player.addItem(new ItemStack(Items.GLASS_BOTTLE));
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		{
			tooltip.add(Component.literal("A captive Bug in a bottle").withStyle(ChatFormatting.GREEN));
			tooltip.add(Component.literal("Right-Click to Release").withStyle(ChatFormatting.DARK_GRAY));
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}