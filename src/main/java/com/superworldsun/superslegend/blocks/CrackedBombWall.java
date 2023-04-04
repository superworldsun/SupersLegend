package com.superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class CrackedBombWall extends Block

{
	   public CrackedBombWall(Properties properties) {
	      super(properties);
	   }

	@Override
	public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
		world.destroyBlock(pos, false); // destroy the block
	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
		return false; // block cannot be destroyed by entities
	}

	@Override
	public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return false; // block cannot be harvested
	}

	@Override
	public ToolType getHarvestTool(BlockState state) {
		return null; // block cannot be harvested with any tool
	}

	// Override the method to prevent the block from being broken by explosions with a lower power than 4

}
	