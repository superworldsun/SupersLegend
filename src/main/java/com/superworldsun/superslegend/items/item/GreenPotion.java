package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.capability.magic.MagicProvider;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class GreenPotion extends Item {
	public GreenPotion(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (MagicProvider.isFullMagic(player) && !player.isHurt()) {
			return InteractionResultHolder.fail(stack);
		}
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		Player player = entity instanceof Player ? (Player) entity : null;
		if (player instanceof Player) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
		}
		if (player != null) {
			MagicProvider.restoreMagic(player, 20F);
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
		}
		if (player == null || !player.getAbilities().instabuild) {
			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}
			if (player != null) {
				player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		return stack;
	}
}
