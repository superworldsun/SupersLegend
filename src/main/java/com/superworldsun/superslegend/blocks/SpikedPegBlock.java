package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SpikedPegBlock extends Block {

	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape HITBOX_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 24.0D, 14.0D);

	public SpikedPegBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
		return HITBOX_SHAPE;
	}

	@Override
	public void attack(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity)
	{

			if (playerEntity.isHolding(ItemInit.SKULL_HAMMER.get())) {
				BlockPos currentPos = playerEntity.blockPosition();
				world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.IRON_TRAPDOOR_CLOSE, SoundCategory.PLAYERS, 1f, 1f);

				world.setBlock(pos, BlockInit.HAMMERED_SPIKED_PEG_BLOCK.get().defaultBlockState(), 3);
			}

		super.attack(state, world, pos, playerEntity);
	}

	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.hurt(DamageSource.CACTUS, 2.0F);
	}

	@Override
	public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
		return false;
	}
}
