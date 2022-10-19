package com.superworldsun.superslegend.items.weapons;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.ItemToolTiers;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkullHammer extends HammerItem
{
	public SkullHammer()
	{
		super(ItemToolTiers.SKULL_HAMMER, 2, new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	protected int getLeftClickCooldown()
	{
		return 40;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "A giant hammer with strong power"));
	}
}