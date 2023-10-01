package com.superworldsun.superslegend.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GoldRupee extends Item {

    public GoldRupee(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            components.add(Component.literal("300 Rupee").withStyle(ChatFormatting.GOLD));
            components.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            components.add(Component.literal("The maximum value of a rupee. Rupees can be used for amo, fuel, or trading").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
            components.add(Component.literal("You can also put this in a crafting table to break it down").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            components.add(Component.literal("Rupees can be found by slaying monsters").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}