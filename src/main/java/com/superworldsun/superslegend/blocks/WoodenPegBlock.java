package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.rmi.registry.RegistryHandler;

public class WoodenPegBlock extends Block

{

	protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);

	public WoodenPegBlock(Properties properties) {
		super(properties);
	}


	@Override
	public void attack(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity)
	{

			if (playerEntity.isHolding(ItemInit.MAGIC_HAMMER.get()) || playerEntity.isHolding(ItemInit.MEGATON_HAMMER.get())
					|| playerEntity.isHolding(ItemInit.SKULL_HAMMER.get())) {
				BlockPos currentPos = playerEntity.blockPosition();
				world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.SHIELD_BLOCK, SoundCategory.PLAYERS, 1f, 1f);

				world.setBlock(pos, BlockInit.HAMMERED_WOODEN_PEG_BLOCK.get().defaultBlockState(), 3);
			}

		super.attack(state, world, pos, playerEntity);
	}

	/*@Deprecated
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		if (player.isHolding(ItemInit.MAGIC_HAMMER.get()))
		{
			BlockPos currentPos = player.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ANVIL_USE, SoundCategory.PLAYERS, 1f, 1f);
		}
	}*/

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
