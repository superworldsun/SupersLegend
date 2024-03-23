package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class WaterBombItem extends NonEnchantItem {
    // Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    public WaterBombItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Overpower your enemies with an explosive blast").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Bombs can be used to destroy walls and obstacles").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal("Right-Click to throw Bomb").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("Sneak+Right-Click to Drop Bomb").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
