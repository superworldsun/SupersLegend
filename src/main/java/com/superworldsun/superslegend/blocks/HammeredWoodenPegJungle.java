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

public class HammeredWoodenPegJungle extends Block

{

	protected static final VoxelShape SHAPE = Block.box(3.75D, 0.0D, 3.75D, 12.25D, 1.0D, 12.25D);

	public HammeredWoodenPegJungle(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
	{
		{
			worldIn.setBlockAndUpdate(pos, BlockInit.JUNGLE_PEG_BLOCK.get().defaultBlockState());

			BlockPos currentPos = pos;
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WOOD_HIT, SoundCategory.PLAYERS, 1f, 1f);
		}
		super.randomTick(state, worldIn, pos, random);
	}

	@SuppressWarnings("deprecation")
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		BlockPos currentPos = player.blockPosition();
		world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WOOD_HIT, SoundCategory.PLAYERS, 1f, 1f);


			world.setBlock(pos, BlockInit.JUNGLE_PEG_BLOCK.get().defaultBlockState(), 3);

		return ActionResultType.SUCCESS;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
