package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.util.SupersLegendKeyboardUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class FriedEggItem extends Item {
    public FriedEggItem(Properties properties)
    {
        super(properties.stacksTo(64));
    }

    @Override
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.EAT;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 20;
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(SupersLegendKeyboardUtil.isHoldingLeftShift())
        {
            tooltip.add(new StringTextComponent(TextFormatting.GOLD+"A fried turtle egg."));
        }
        else
        {
            tooltip.add(new StringTextComponent("Hold Left Shift to see more info!"));

        }
    }
}
