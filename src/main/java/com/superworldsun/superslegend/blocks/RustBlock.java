package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.IceBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;

import java.util.List;

public class RustBlock extends Block {
    public RustBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag
            flagIn) {
        tooltip.add(new StringTextComponent("This is a rusted big chunk of metal."));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}

