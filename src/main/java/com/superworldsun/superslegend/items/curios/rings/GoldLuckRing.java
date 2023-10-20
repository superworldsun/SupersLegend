package com.superworldsun.superslegend.items.curios.rings;

import java.util.List;

import com.superworldsun.superslegend.api.IncomingDamageModifier;
import com.superworldsun.superslegend.items.customclass.RingItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GoldLuckRing extends RingItem implements IncomingDamageModifier {
	public GoldLuckRing(Properties properties) {
		super(new Properties());
	}

	@Override
	public boolean canModifyDamage(DamageSource damage) {
		return damage.is(DamageTypes.FALL);
	}

	@Override
	public float getDamageModifier() {
		return -0.5F;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.add(Component.literal("1/2 Damage From Falls").withStyle(ChatFormatting.BLUE));
	}
}