package com.superworldsun.superslegend.items.block;

import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockItem extends BlockItem
{
	public ModBlockItem(RegistryObject<Block> blockObject)
	{
		super(blockObject.get(), new Properties().tab(ItemGroupInit.BLOCKS));
	}
}
