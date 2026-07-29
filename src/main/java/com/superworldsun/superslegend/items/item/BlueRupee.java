package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.ChatFormatting;

public class BlueRupee extends Rupee {
    public BlueRupee(Properties properties) {
        super(properties, RupeeValue.BLUE, ChatFormatting.BLUE, true, false);
    }
}
