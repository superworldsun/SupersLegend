package com.superworldsun.superslegend;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SupersLegendItemGroupBlocks extends ItemGroup
{
	public SupersLegendItemGroupBlocks()
	{
		super("supers_legend_blocks");
	}
	
	// Tab Icon
	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack makeIcon()
	{
		return new ItemStack(BlockInit.BLOCK_OF_TIME.get());
	}
}
