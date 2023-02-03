package com.superworldsun.superslegend.items.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class WarpPadBlockItem extends ModBlockItem {
	public WarpPadBlockItem(RegistryObject<Block> blockObject) {
		super(blockObject);
	}

	@Override
	protected boolean canPlace(BlockItemUseContext itemUseContext, BlockState blockState) {
		World level = itemUseContext.getLevel();
		BlockPos blockPos = itemUseContext.getClickedPos();

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				BlockPos blockPartPos = blockPos.offset(x, 0, z);

				if (!level.getBlockState(blockPartPos).canBeReplaced(itemUseContext)) {
					return false;
				}
			}
		}

		return super.canPlace(itemUseContext, blockState);
	}
}
