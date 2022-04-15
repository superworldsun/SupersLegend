package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class HammeredSpikedPegBlock extends Block

{

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

	public HammeredSpikedPegBlock(Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@SuppressWarnings("deprecation")
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
	{
		{
			worldIn.setBlockAndUpdate(pos, BlockInit.SPIKED_PEG_BLOCK.get().defaultBlockState());

			BlockPos currentPos = pos;
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.PLAYERS, 1f, 1f);
		}
		super.randomTick(state, worldIn, pos, random);
	}

	@SuppressWarnings("deprecation")
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		BlockPos currentPos = player.blockPosition();
		world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.PLAYERS, 1f, 1f);


			world.setBlock(pos, BlockInit.SPIKED_PEG_BLOCK.get().defaultBlockState(), 3);

		return ActionResultType.SUCCESS;
	}
}
