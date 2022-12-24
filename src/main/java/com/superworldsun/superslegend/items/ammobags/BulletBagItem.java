package com.superworldsun.superslegend.items.ammobags;

import java.util.List;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BulletBagItem extends AmmoContainerItem
{
	public BulletBagItem(int capacity)
	{
		super(capacity);
	}
	
	@Override
	public boolean canHoldItem(ItemStack itemStack)
	{
		return TagInit.PELLETS.contains(itemStack.getItem());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if (getContents(stack) != null)
		{
			tooltip.add(new StringTextComponent(TextFormatting.AQUA + getContents(stack).getRight().toString()));
			tooltip.add(new StringTextComponent(TextFormatting.WHITE + getContents(stack).getLeft().getHoverName().getString()));
			tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Right click to get pellets."));
		}
	}
}
