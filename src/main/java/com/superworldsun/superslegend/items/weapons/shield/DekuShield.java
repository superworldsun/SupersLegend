package com.superworldsun.superslegend.items.weapons.shield;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class DekuShield extends ShieldItem
{
    public DekuShield(Properties builder) {
        super(builder);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            ItemStack equipped = player.getMainHandItem();
            if (stack == equipped || stack == player.getOffhandItem()) {
                if (player.isOnFire()) {
                    stack.shrink(1);
                    BlockPos currentPos = player.blockPosition();
                    level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1f, 1f);
                    player.displayClientMessage(Component.literal(ChatFormatting.RED + "Your shield is gone!"), true);
                }
            }
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Made of resilient Deku wood").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("This shield is strong against most damage").withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.literal("This will burn up instantly to fire damage").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.literal("Catching fire while holding the shield will instantly burn it up").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.literal("Tool-smiths & armorers often sell this shield").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}