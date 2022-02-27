package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicFireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangEntity;
import com.superworldsun.superslegend.interfaces.IHasNoItem;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static com.superworldsun.superslegend.blocks.TorchTower.OUTPUT_POWER;

public class TorchTowerTopLit extends Block implements IHasNoItem

{
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 9.0D, 13.0D);

	   public TorchTowerTopLit(Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }

	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.5D;
		double d2 = (double)pos.getZ() + 0.5D;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);

		double d3 = (double)pos.getX() + 0.6D;
		double d4 = (double)pos.getY() + 0.4D;
		double d5 = (double)pos.getZ() + 0.6D;
		worldIn.addParticle(ParticleTypes.SMOKE, d3, d4, d5, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D);

		double d6 = (double)pos.getX() + 0.4D;
		double d7 = (double)pos.getY() + 0.4D;
		double d8 = (double)pos.getZ() + 0.4D;
		worldIn.addParticle(ParticleTypes.SMOKE, d6, d7, d8, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d6, d7, d8, 0.0D, 0.0D, 0.0D);

		double  d9 = (double)pos.getX() + 0.6D;
		double d10 = (double)pos.getY() + 0.4D;
		double d11 = (double)pos.getZ() + 0.4D;
		worldIn.addParticle(ParticleTypes.SMOKE, d9, d10, d11, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d9, d10, d11, 0.0D, 0.0D, 0.0D);

		double d12 = (double)pos.getX() + 0.4D;
		double d13 = (double)pos.getY() + .4D;
		double d14 = (double)pos.getZ() + 0.6D;
		worldIn.addParticle(ParticleTypes.SMOKE, d12, d13, d14, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d12, d13, d14, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		pos = pos.below();
		return world.getBlockState(pos).getBlock().getPickBlock(state, target, world, pos, player);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos)
	{
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.below()).is(BlockInit.TORCH_TOWER.get()));
	}

	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
	}

	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn instanceof ArrowEntity || entityIn instanceof AbstractArrowEntity)
		{
			entityIn.setSecondsOnFire(15);
		}
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean flag)
	{
		DebugPacketSender.sendNeighborsUpdatePacket(world, pos);

		if (!canSurvive(state, world, pos))
		{
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}

	@Override
	public void onPlace(BlockState blockState, World world, BlockPos blockPos, BlockState oldBlockState, boolean b) {
		world.setBlockAndUpdate(blockPos.below(), BlockInit.TORCH_TOWER.get().defaultBlockState().setValue(OUTPUT_POWER, 15));
		world.setBlockAndUpdate(blockPos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState());
	}
}
	