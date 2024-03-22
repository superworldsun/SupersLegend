package com.superworldsun.superslegend.items.weapons.swords;

import com.superworldsun.superslegend.items.customclass.ItemCustomSword;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BiggoronsSword extends ItemCustomSword {
    public BiggoronsSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
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
            tooltip.add(Component.literal("A Large Sword that requires two hands to wield").withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("When wielding the Giants Sword it will need your offhand to be free").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Any item placed in your offhand will be dropped").withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}