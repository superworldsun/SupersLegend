package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.entity.SwitchableFanTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SwitchableFanBlock extends FanBlock
{
	public SwitchableFanBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean flag)
	{
		if (!world.isClientSide)
		{
			SwitchableFanTileEntity fan = (SwitchableFanTileEntity) world.getBlockEntity(pos);
			
			if (fan.isPowered != world.hasNeighborSignal(pos))
			{
				fan.isPowered = world.hasNeighborSignal(pos);
				// 2 means we send changes to client
				world.sendBlockUpdated(pos, state, state, 2);
			}
		}
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new SwitchableFanTileEntity();
	}
}
