package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.api.IncomingDamageModifier;
import com.superworldsun.superslegend.items.customclass.RingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlueRing extends RingItem implements IncomingDamageModifier {
	public BlueRing(Properties properties) {
		super(new Item.Properties());
	}

	@Override
	public float modifyIncomingDamage(DamageSource source, float amount) {
		return amount / 2f;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.add(Component.literal("Damage taken is 1/2").withStyle(ChatFormatting.BLUE));
	}
}