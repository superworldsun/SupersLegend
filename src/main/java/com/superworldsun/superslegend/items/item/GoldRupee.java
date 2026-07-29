package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.ChatFormatting;

public class GoldRupee extends Rupee {
    public GoldRupee(Properties properties) {
        super(properties, RupeeValue.GOLD, ChatFormatting.GOLD, true, true);
    }
}
