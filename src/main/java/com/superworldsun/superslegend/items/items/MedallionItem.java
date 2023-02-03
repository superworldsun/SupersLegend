package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.blocks.WarpPadBlock;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class MedallionItem extends Item {
	private final RegistryObject<Block> warpPadBlockObject;

	public MedallionItem(RegistryObject<Block> warpPadBlockObject) {
		super(new Properties().tab(ItemGroupInit.RESOURCES));
		this.warpPadBlockObject = warpPadBlockObject;
	}

	public BlockState transformWarpPadState(BlockState blockState) {
		BlockState defaultBlockState = warpPadBlockObject.get().defaultBlockState();
		return defaultBlockState.setValue(WarpPadBlock.FACING, blockState.getValue(WarpPadBlock.FACING));
	}
}
