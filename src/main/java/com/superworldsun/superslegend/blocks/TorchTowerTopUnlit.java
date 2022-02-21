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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TorchTowerTopUnlit extends Block implements IHasNoItem

{
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 9.0D, 13.0D);

	   public TorchTowerTopUnlit(Properties properties) {
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
		if(entityIn instanceof FireArrowEntity || entityIn instanceof MagicFireArrowEntity)
		{
			worldIn.setBlock(pos , BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 1);
		}

		if(entityIn instanceof ArrowEntity || entityIn instanceof AbstractArrowEntity)
		{
			if (entityIn.isOnFire())
			{
				worldIn.setBlock(pos , BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 1);
			}
		}

		if(entityIn instanceof BoomerangEntity)
		{
			if (entityIn.isOnFire())
			{
				worldIn.setBlock(pos , BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 1);
			}
		}
	}

	public ActionResultType use(BlockState blockstate, World worldIn, BlockPos pos, PlayerEntity playerentity, Hand hand, BlockRayTraceResult blocktrace) {
		ItemStack itemstack = playerentity.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE)
		{
			return super.use(blockstate, worldIn, pos, playerentity, hand, blocktrace);
		}
		else
		{
			catchFire(blockstate, worldIn, pos, blocktrace.getDirection(), playerentity);
			worldIn.setBlock(pos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 1);
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!playerentity.isCreative()) {
				if (item == Items.FLINT_AND_STEEL) {
					itemstack.hurtAndBreak(1, playerentity, (p_220287_1_) -> {
						p_220287_1_.broadcastBreakEvent(hand);
					});
				} else {
					itemstack.shrink(1);
				}
			}

			return ActionResultType.sidedSuccess(worldIn.isClientSide);
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
}
	