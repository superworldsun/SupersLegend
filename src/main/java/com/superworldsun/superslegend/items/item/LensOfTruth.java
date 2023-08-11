package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.capability.magic.MagicProvider;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class LensOfTruth extends Item {
	private static final int MANA_SPENDING_FREQUENCY = 20;
	private static final float MANA_COST = 0.5F;

	public LensOfTruth(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (MagicProvider.hasMagic(player, MANA_COST)) {
//            player.level().playSound(null, player, SoundInit.LENS_OF_TRUTH_ON.get(), SoundCategory.PLAYERS, 1f, 1f);
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemstack);
		} else {
//            player.level().playSound(null, player, SoundInit.ZELDA_ERROR.get(), SoundCategory.PLAYERS, 1f, 1f);
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity player, int timeInUse) {
		// player.level().playSound(null, player, SoundInit.LENS_OF_TRUTH_OFF.get(), SoundCategory.PLAYERS, 1f, 1f);
	}

	@Override
	public void onUseTick(Level level, LivingEntity user, ItemStack stack, int time) {
		if (!(user instanceof Player player)) return;
		if (!MagicProvider.hasMagic(player, MANA_COST)) {
			user.stopUsingItem();
		}
		if (time % MANA_SPENDING_FREQUENCY == 0) {
			MagicProvider.spendMagic(player, MANA_COST);
		}
	}
}
