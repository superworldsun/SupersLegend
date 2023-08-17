package com.superworldsun.superslegend.items.item;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.events.PlayerHealthEvents;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class HeartContainer extends Item {

	public HeartContainer(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!PlayerHealthEvents.canIncreaseBaseHealth(player)) {
			return InteractionResultHolder.fail(stack);
		} else {
			PlayerHealthEvents.addBaseHealthModifier(player, 2F);
			if (!player.getAbilities().instabuild) stack.shrink(1);
			BlockPos pos = player.blockPosition();
			level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundInit.HEART.get(), SoundSource.PLAYERS, 1f, 1f);
			return InteractionResultHolder.consume(stack);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.literal("Increases Maximum Health").withStyle(ChatFormatting.RED));
		tooltip.add(Component.literal("Right-click to use").withStyle(ChatFormatting.GREEN));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
}
