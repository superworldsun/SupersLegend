package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.ChatFormatting;

public class RedRupee extends Rupee {
    public RedRupee(Properties properties) {
        super(properties, RupeeValue.RED, ChatFormatting.RED, true, false);
    }
}
