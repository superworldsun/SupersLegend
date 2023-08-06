package com.superworldsun.superslegend.items.item;

import java.util.List;

import com.superworldsun.superslegend.events.PlayerHealthEvents;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class VoidContainer extends Item {
	public VoidContainer(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!PlayerHealthEvents.canDecreaseBaseHealth(player)) {
			return InteractionResultHolder.fail(stack);
		} else {
			PlayerHealthEvents.addBaseHealthModifier(player, -2F);
			if (!player.getAbilities().instabuild) stack.shrink(1);
//			BlockPos pos = player.blockPosition();
//			level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundInit.HEART.get(), SoundCategory.PLAYERS, 1f, 1f);
			return InteractionResultHolder.consume(stack);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.literal("Decreases Maximum Health").withStyle(ChatFormatting.BLACK));
		tooltip.add(Component.literal("Right-click to use").withStyle(ChatFormatting.GREEN));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
}
