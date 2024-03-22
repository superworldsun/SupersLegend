package com.superworldsun.superslegend.items.item.ammo;

import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ArrowFire extends ArrowItem {
    public ArrowFire(Properties properties)
    {
        super(properties.stacksTo(64));
    }

    @Override @Nonnull
    @ParametersAreNonnullByDefault
    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        return new FireArrowEntity(worldIn, shooter);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("An arrow with a hot touch").withStyle(ChatFormatting.RED));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Unleash flames upon your enemies and obstacles with each shot").withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}

