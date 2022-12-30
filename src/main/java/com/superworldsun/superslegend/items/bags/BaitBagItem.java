package com.superworldsun.superslegend.items.bags;

import java.util.List;

import com.superworldsun.superslegend.registries.ItemGroupInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.block.CropsBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BaitBagItem extends BagItem
{
	public BaitBagItem()
	{
		super(new Properties().tab(ItemGroupInit.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		Item item = stack.getItem();
		boolean isCrops = item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof CropsBlock;
		boolean isFood = item.getFoodProperties() != null;
		return isCrops || isFood || item == Items.WHEAT;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GOLD + "Holds all types of parchment and letters"));
	}
}
