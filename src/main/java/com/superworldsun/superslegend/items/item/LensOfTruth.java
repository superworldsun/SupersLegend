package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.capability.magic.MagicProvider;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LensOfTruth extends Item {
	private static final int MANA_SPENDING_FREQUENCY = 20;
	private static final float MANA_COST = 0.5F;

	public LensOfTruth(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack) {
		return 72000;
	}

	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (MagicProvider.hasMagic(player, MANA_COST)) {
			player.playSound(SoundInit.LENS_OF_TRUTH_ON.get(), 1f, 1f);
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemstack);
		} else {
			player.playSound(SoundInit.ZELDA_ERROR.get(), 1f, 1f);
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, LivingEntity player, int timeInUse) {
		player.playSound(SoundInit.LENS_OF_TRUTH_OFF.get(), 1f, 1f);
	}

	@Override
	public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack stack, int time) {
		if (!(user instanceof Player player)) return;
		if (!MagicProvider.hasMagic(player, MANA_COST)) {
			user.stopUsingItem();
		}
		if (time % MANA_SPENDING_FREQUENCY == 0) {
			MagicProvider.spendMagic(player, MANA_COST);
		}
	}
}
