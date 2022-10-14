package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

// this is used for rendering all the shadow blocks, because they don't have models themselves
public class ShadowModelBlock extends Block
{
	public ShadowModelBlock()
	{
		super(Block.Properties.of(Material.METAL));
	}
}