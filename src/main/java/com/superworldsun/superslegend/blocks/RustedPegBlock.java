package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class RustedPegBlock extends Block {

	protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);
	protected static final VoxelShape HITBOX_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 24.0D, 12.0D);

	public RustedPegBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
		return HITBOX_SHAPE;
	}

	@Override
	public void attack(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity)
	{

		if (playerEntity.isHolding(ItemInit.MEGATON_HAMMER.get()) || playerEntity.isHolding(ItemInit.SKULL_HAMMER.get()))
		{
			BlockPos currentPos = playerEntity.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.PLAYERS, 1f, 1f);

			world.setBlock(pos, BlockInit.HAMMERED_RUSTED_PEG_BLOCK.get().defaultBlockState(), 3);
		}

		if (playerEntity.isHolding(ItemInit.MAGIC_HAMMER.get()))
		{
			BlockPos currentPos = playerEntity.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ANCIENT_DEBRIS_BREAK, SoundCategory.PLAYERS, 1f, 1f);
		}

		super.attack(state, world, pos, playerEntity);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
