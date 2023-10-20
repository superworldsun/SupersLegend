package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.api.IncomingDamageModifier;
import com.superworldsun.superslegend.items.customclass.RingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlueLuckRing extends RingItem implements IncomingDamageModifier {
	public BlueLuckRing(Properties properties) {
		super(new Properties());
	}

	@Override
	public float modifyIncomingDamage(DamageSource source, float amount) {
		return source.getEntity() instanceof Guardian && !source.is(DamageTypes.THORNS) ? amount / 2 : amount;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.add(Component.literal("1/2 Damage From Beams").withStyle(ChatFormatting.BLUE));
	}
}