package com.superworldsun.superslegend.items.curios.head.masks;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class ScentsMask extends Item implements ICurioItem {
    public ScentsMask(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.YELLOW + "This mask is said to"));
        list.add(new StringTextComponent(TextFormatting.YELLOW + "enhance ones Piglike senses"));
    }
}