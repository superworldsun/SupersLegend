package com.superworldsun.superslegend.items.curios.head.masks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class FierceDeitysMask extends Item implements ICurioItem {
    public FierceDeitysMask(Properties pProperties) {
        super(pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Contains a dark, godlike power..").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal("Grants Strength and removes some negative effects").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("Uses Magic upon use").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
