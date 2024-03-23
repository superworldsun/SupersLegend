package com.superworldsun.superslegend.items.weapons.hammer;

import com.superworldsun.superslegend.items.customclass.HammerItem;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MegatonHammer extends HammerItem
{
    public MegatonHammer(Properties properties)
    {
        super(ItemToolTiers.MEGATON_HAMMER, 2, new Properties());
    }

    //TODO When breaking plants there is no particle effect or sound played
    //TODO Add blocks to the Init, list isnt full
    //TODO Add a sound for when the hammer hits a block & entity
    @Override
    protected int getLeftClickCooldown() {
        return 18;
    }

    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected)
    {
        if(entity instanceof Player)
        {
            Player player = (Player)entity;
            ItemStack equipped = player.getMainHandItem();
            {
                if(stack == equipped)
                {
                    if(player.hasItemInSlot(EquipmentSlot.OFFHAND))
                    {
                        player.spawnAtLocation(player.getOffhandItem());
                        player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("A Large hammer from the Goron tribe").withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Requires two hands to wield").withStyle(ChatFormatting.RED));
            tooltip.add(Component.literal("Use this to deal high damage").withStyle(ChatFormatting.YELLOW));
            tooltip.add(Component.literal("Used to pound down rusted pegs & switches").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
