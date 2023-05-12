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

public class PushStoneItem extends ModBlockItem
{
	public PushStoneItem(RegistryObject<Block> blockObject)
	{
		super(blockObject);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.RED + "Requires Goron Bracelet to push"));
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Right Click the side of the Block to push"));
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Sneak+Right Click the side of the Block to pull"));
	}
}