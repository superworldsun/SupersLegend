package com.superworldsun.superslegend.items.weapons.bow;

import com.superworldsun.superslegend.items.customclass.MultishotBowItem;
import net.minecraft.world.item.ItemStack;

public class LynelBowX5 extends MultishotBowItem {
	public LynelBowX5(Properties properties) {
		super(properties, 5);
	}

	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
}