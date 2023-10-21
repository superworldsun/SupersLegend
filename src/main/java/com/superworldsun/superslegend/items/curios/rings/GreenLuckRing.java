package com.superworldsun.superslegend.items.curios.rings;

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

import java.util.List;

public class GreenLuckRing extends RingItem implements IncomingDamageModifier {
	public GreenLuckRing(Properties properties) {
		super(new Properties());
	}

	@Override
	public float modifyIncomingDamage(DamageSource source, float amount) {
		return source.is(DamageTypes.FALLING_ANVIL) || source.is(DamageTypes.HOT_FLOOR) ? amount / 2f : amount;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, Level level, List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {
		tooltip.add(Component.literal("1/2 Damage From Traps").withStyle(ChatFormatting.BLUE));
	}
}