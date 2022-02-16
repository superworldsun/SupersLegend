package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TorchTower extends Block

{
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);

	   public TorchTower(Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 1.5D;
		double d2 = (double)pos.getZ() + 0.5D;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);

		double d3 = (double)pos.getX() + 0.6D;
		double d4 = (double)pos.getY() + 1.4D;
		double d5 = (double)pos.getZ() + 0.6D;
		worldIn.addParticle(ParticleTypes.SMOKE, d3, d4, d5, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D);

		double d6 = (double)pos.getX() + 0.4D;
		double d7 = (double)pos.getY() + 1.4D;
		double d8 = (double)pos.getZ() + 0.4D;
		worldIn.addParticle(ParticleTypes.SMOKE, d6, d7, d8, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d6, d7, d8, 0.0D, 0.0D, 0.0D);

		double d9 = (double)pos.getX() + 0.6D;
		double d10 = (double)pos.getY() + 1.4D;
		double d11 = (double)pos.getZ() + 0.4D;
		worldIn.addParticle(ParticleTypes.SMOKE, d9, d10, d11, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d9, d10, d11, 0.0D, 0.0D, 0.0D);

		double d12 = (double)pos.getX() + 0.4D;
		double d13 = (double)pos.getY() + 1.4D;
		double d14 = (double)pos.getZ() + 0.6D;
		worldIn.addParticle(ParticleTypes.SMOKE, d12, d13, d14, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d12, d13, d14, 0.0D, 0.0D, 0.0D);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return facing == Direction.DOWN && !canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean flag)
	{
		world.setBlockAndUpdate(pos.above(), BlockInit.TORCH_TOWER_TOP.get().defaultBlockState());
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos)
	{
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.TORCH_TOWER_TOP.get()) || world.isEmptyBlock(pos.above()));
	}
}
	