package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.util.SupersLegendKeyboardUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class LanzaniteItem extends Item {
    public LanzaniteItem(Properties properties)
    {
        super(properties.stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(SupersLegendKeyboardUtil.isHoldingLeftShift())
        {
            tooltip.add(new StringTextComponent(TextFormatting.GOLD+"A shiny rock. Used to make weapons and tools."));
        }
        else
        {
            tooltip.add(new StringTextComponent("Hold Left Shift to see more info!"));

        }
    }
}
