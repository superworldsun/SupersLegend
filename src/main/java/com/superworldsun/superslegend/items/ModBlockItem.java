package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockItem extends BlockItem
{
	public ModBlockItem(RegistryObject<Block> blockObject)
	{
		super(blockObject.get(), new Properties().tab(SupersLegendMain.BLOCKS));
	}
}
