package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BaitBagItem extends BagItem
{
	public BaitBagItem()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		//TODO add so the bag can hold all types of food, crops, and seeds of any kind
		Item item = stack.getItem();
		return item == Items.WHEAT || item == Items.POTATO || item == Items.BEETROOT || item == Items.WHEAT_SEEDS
			|| item == Items.PUMPKIN_SEEDS || item == Items.MELON_SEEDS || item == Items.BEETROOT_SEEDS
			|| item == Items.GOLDEN_APPLE || item == Items.APPLE || item == Items.GOLDEN_CARROT
			|| item == Items.BEEF || item == Items.COOKED_BEEF || item == Items.CHICKEN || item == Items.COOKED_CHICKEN
			|| item == ItemInit.RED_JELLY.get();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GOLD + "Holds all types of parchment and letters"));
	}
}
