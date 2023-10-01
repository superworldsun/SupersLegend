package com.superworldsun.superslegend.items.item;

import java.util.List;

import com.superworldsun.superslegend.events.PlayerHealthEvents;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

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
			BlockPos pos = player.blockPosition();
			level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundInit.HEART.get(), SoundSource.PLAYERS, 1f, 0.1f);
			return InteractionResultHolder.consume(stack);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if(!Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Decreases Maximum Health").withStyle(ChatFormatting.DARK_GRAY));
			tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
		}
		else if(Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Right-click to use, 1 time use").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
			tooltip.add(Component.literal("You can have a minimum of 1 heart").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}
