package com.superworldsun.superslegend.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;

public class MagicMushroomBlock extends MushroomBlock

{

	public MagicMushroomBlock(Properties p_i48363_1_) {
		super(p_i48363_1_);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.INVISIBLE;
	}

}
	