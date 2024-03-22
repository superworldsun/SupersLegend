package com.superworldsun.superslegend.items.item.ammo;

import com.superworldsun.superslegend.entities.projectiles.arrows.MagicIceArrowEntity;
import com.superworldsun.superslegend.items.item.MagicArrow;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MagicArrowIce extends MagicArrow
{
    @Override
    public AbstractArrow createMagicArrow(Level world, ItemStack stack, LivingEntity shooter)
    {
        return new MagicIceArrowEntity(world, shooter);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Infuses the power of Ice to Arrows").withStyle(ChatFormatting.AQUA));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Can be placed in offhand or in next order of arrows to be used").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Uses magic and turns a regular arrow into a magic ice arrow when fired").withStyle(ChatFormatting.DARK_RED));
            tooltip.add(Component.literal("If you do not have enough magic, a the next arrow will shot unchanged").withStyle(ChatFormatting.DARK_RED));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}