package com.superworldsun.superslegend.items.weapons.bow;

import com.superworldsun.superslegend.items.customclass.MultishotBowItem;
import net.minecraft.world.item.ItemStack;

public class LynelBowX3 extends MultishotBowItem {
	public LynelBowX3(Properties properties) {
		super(properties, 3);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
}