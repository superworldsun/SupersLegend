package com.superworldsun.superslegend.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;

public class ModHiddenBlockItem extends BlockItem {
	public ModHiddenBlockItem(RegistryObject<Block> blockObject) {
		super(blockObject.get(), new Properties());
	}
}
