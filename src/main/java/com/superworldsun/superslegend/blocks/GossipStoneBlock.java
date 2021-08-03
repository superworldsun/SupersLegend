package com.superworldsun.superslegend.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class GossipStoneBlock extends AbstractGossipStoneBlock
{
	public GossipStoneBlock(Properties builder)
	{
		super(builder);
	}
	
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return null;
	}
}
