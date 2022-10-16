package com.superworldsun.superslegend.items.weapons;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.ItemToolTiers;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MegatonHammer extends HammerItem
{
	public MegatonHammer()
	{
		super(ItemToolTiers.MEGATON_HAMMER, 2, -2.8f, new Item.Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	protected int getLeftClickCooldown()
	{
		return 40;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "A large hammer from the goron tribe"));
	}
}