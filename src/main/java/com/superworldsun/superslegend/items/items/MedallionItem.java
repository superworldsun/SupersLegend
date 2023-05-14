package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.blocks.WarpPadBlock;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class MedallionItem extends Item {
	private final RegistryObject<WarpPadBlock> warpPadBlockObject;

	public MedallionItem(RegistryObject<WarpPadBlock> warpPadBlockObject) {
		super(new Properties().tab(ItemGroupInit.RESOURCES));
		this.warpPadBlockObject = warpPadBlockObject;
	}

	public BlockState transformWarpPadState(BlockState blockState) {
		BlockState transformedState = warpPadBlockObject.get().defaultBlockState();
		transformedState = transformedState.setValue(WarpPadBlock.FACING, blockState.getValue(WarpPadBlock.FACING));
		transformedState = transformedState.setValue(WarpPadBlock.BLOCK_PART_X, blockState.getValue(WarpPadBlock.BLOCK_PART_X));
		transformedState = transformedState.setValue(WarpPadBlock.BLOCK_PART_Z, blockState.getValue(WarpPadBlock.BLOCK_PART_Z));
		return transformedState;
	}
}
