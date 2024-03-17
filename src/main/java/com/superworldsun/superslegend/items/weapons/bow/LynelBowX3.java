package com.superworldsun.superslegend.items.weapons.bow;

import com.superworldsun.superslegend.items.customclass.MultishotBowItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class LynelBowX3 extends MultishotBowItem {
	public LynelBowX3(Properties properties) {
		super(properties, 3);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		if(!Screen.hasShiftDown()) {
			tooltip.add(Component.literal("A bow originally crafted by the fearsome Lynels").withStyle(ChatFormatting.GREEN));
			tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
		}
		else if(Screen.hasShiftDown()) {
			tooltip.add(Component.literal("This bow unleashes a barrage of 3 arrows with each shot").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
			tooltip.add(Component.literal("The bow can use any arrow as ammo").withStyle(ChatFormatting.GRAY));
			tooltip.add(Component.literal("Uses 3 durability with each shot").withStyle(ChatFormatting.GRAY));
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}