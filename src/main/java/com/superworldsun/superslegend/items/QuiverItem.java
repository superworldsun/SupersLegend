package com.superworldsun.superslegend.items;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class QuiverItem extends AmmoContainerItem
{
	public QuiverItem(int capacity)
	{
		super(capacity);
	}
	
	@Override
	public boolean canHoldItem(ItemStack itemStack)
	{
		Item ammoItem = itemStack.getItem();
		
		if (ammoItem == ItemInit.MAGIC_FIRE_ARROW.get() || ammoItem == ItemInit.MAGIC_ICE_ARROW.get() || ammoItem == ItemInit.MAGIC_LIGHT_ARROW.get())
		{
			return false;
		}
		
		return ItemTags.ARROWS.contains(ammoItem);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if (getContents(stack) != null)
		{
			tooltip.add(new StringTextComponent(TextFormatting.AQUA + Objects.requireNonNull(getContents(stack)).getRight().toString()));
			tooltip.add(new StringTextComponent(TextFormatting.WHITE + getContents(stack).getLeft().getHoverName().getString()));
			tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Right click to get arrows."));
		}
	}
}
