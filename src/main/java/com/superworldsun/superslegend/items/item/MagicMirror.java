package com.superworldsun.superslegend.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicMirror extends Item {
	private static final int USE_DURATION = 25;

	public MagicMirror(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
		if (isPositionSafe(level, entity)) {
			setReturnPosition(stack, entity.blockPosition());
		}
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

	@Override
	public void onUseTick(@NotNull Level level, LivingEntity entity, @NotNull ItemStack stack, int remainingUseDuration) {
		RandomSource rand = entity.level().random;
		for (int i = 0; i < 45; i++) {
			entity.level().addParticle(ParticleTypes.CLOUD,
					entity.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
					entity.yo + rand.nextFloat() * 3 - 2,
					entity.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
					0,
					0.105D,
					0);
		}
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
		if (level.isClientSide) {
			return stack;
		}
		ServerPlayer player = (ServerPlayer) entity;
		if (!level.dimension().equals(Level.OVERWORLD)) {
			player.displayClientMessage(Component.literal("Can only use in OverWorld"), true);
			return stack;
		}
		BlockPos returnLoc = getReturnPosition(stack);
		if (returnLoc == null) {
			player.displayClientMessage(Component.literal("No position saved"), true);
			return stack;
		}
		if (player.isPassenger()) {
			player.stopRiding();
		}
		teleportEntity(entity, returnLoc);
		BlockPos currentPos = player.blockPosition();
		level.playSound(null,
				currentPos.getX(),
				currentPos.getY(),
				currentPos.getZ(),
				SoundEvents.CHORUS_FRUIT_TELEPORT,
				SoundSource.PLAYERS,
				1f,
				1f);
		player.displayClientMessage(Component.literal("Returned to saved position"), true);
		return stack;
	}

	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack) {
		return USE_DURATION;
	}

	@Override
	public boolean isFoil(@NotNull ItemStack stack) {
		return getReturnPosition(stack) != null;
	}

	public void teleportEntity(LivingEntity entity, BlockPos pos) {
		entity.teleportTo(pos.getX() + 0.5F, pos.getY() + 0.6F, pos.getZ() + 0.5F);
		entity.fallDistance = 0;
	}

	private static boolean isPositionSafe(@NotNull Level level, @NotNull Entity entity) {
		return entity.position().y > 60 && level.getBrightness(LightLayer.SKY, entity.blockPosition()) > 11
				&& !level.getBlockState(entity.blockPosition().below()).isAir();
	}

	public static @Nullable BlockPos getReturnPosition(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("SavedPos")) {
			return NbtUtils.readBlockPos(tag.getCompound("SavedPos"));
		}
		return null;
	}

	public static void setReturnPosition(ItemStack stack, BlockPos pos) {
		stack.getOrCreateTag().put("SavedPos", NbtUtils.writeBlockPos(pos));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @javax.annotation.Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		if(!Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Gaze into this mirror").withStyle(ChatFormatting.AQUA));
			tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
		}
		else if(Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Use the Magic Mirror to warp back to any entrance").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
			tooltip.add(Component.literal("The mirror will shimmer when it can be used").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
			tooltip.add(Component.literal("Hold Right-click to return").withStyle(ChatFormatting.GREEN));
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}
