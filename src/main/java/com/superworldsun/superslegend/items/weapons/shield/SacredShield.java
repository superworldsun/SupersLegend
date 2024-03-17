package com.superworldsun.superslegend.items.weapons.shield;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
public class SacredShield extends ShieldItem
{
    private static final int HEAL_INTERVAL = 200; // 5 seconds (20 ticks per second)
    private static final int HEAL_AMOUNT = 1; // Amount of damage healed per interval
    private int ticksSinceLastHeal = 0;

    public SacredShield(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity player, int itemSlot, boolean isSelected)
    {
        super.inventoryTick(stack, world, player, itemSlot, isSelected);

        // Check if the shield is in the player's inventory
            // Increment tick counter
            ticksSinceLastHeal++;

            // Check if it's time to heal the shield
            if (ticksSinceLastHeal >= HEAL_INTERVAL) {
                // Reset tick counter
                ticksSinceLastHeal = 0;

                // Check if the shield's durability is less than its maximum durability
                if (stack.getDamageValue() > 0) {
                    // Calculate the amount of damage to heal
                    int damageToHeal = Math.min(stack.getDamageValue(), HEAL_AMOUNT);

                    // Heal the shield by reducing its damage
                    stack.setDamageValue(stack.getDamageValue() - damageToHeal);
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
            tooltip.add(Component.literal("A low durability shield with sacred power").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("This shield will recover 1 durability every 5 seconds when inside your inventory").withStyle(ChatFormatting.WHITE));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}