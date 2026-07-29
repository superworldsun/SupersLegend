package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.ChatFormatting;

public class SilverRupee extends Rupee {
    public SilverRupee(Properties properties) {
        super(properties, RupeeValue.SILVER, ChatFormatting.GRAY, true, false);
    }
}
