package com.superworldsun.superslegend.items.block;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;

public class RustedPegItem extends ModBlockItem
{
	public RustedPegItem(RegistryObject<Block> blockObject)
	{
		super(blockObject);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GRAY + "Using a heavy hammer will bury the peg"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "When buried you can mine easier"));
	}
}