package com.superworldsun.superslegend;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SupersLegendItemGroupApparel extends ItemGroup
{
	public SupersLegendItemGroupApparel()
	{
		super("supers_legend_apparel");
	}
	
	// Tab Icon
	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack makeIcon()
	{
		return new ItemStack(ItemInit.KOKIRI_TUNIC.get());
	}
}
