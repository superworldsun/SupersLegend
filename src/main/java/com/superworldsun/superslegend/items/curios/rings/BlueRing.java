package com.superworldsun.superslegend.items.curios.rings;

import java.util.List;

import com.superworldsun.superslegend.api.IncomingDamageModifier;
import com.superworldsun.superslegend.items.customclass.RingItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BlueRing extends RingItem implements IncomingDamageModifier {
	public BlueRing(Properties properties) {
		super(new Item.Properties());
	}

	@Override
	public boolean canModifyDamage(DamageSource damage) {
		return true;
	}

	@Override
	public float getDamageModifier() {
		return -0.5F;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.literal("Damage taken is 1/2").withStyle(ChatFormatting.BLUE));
	}
}