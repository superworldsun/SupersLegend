package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class DinsFlame extends Block {
	private final int fireDamage;
	private Object ItemEntity;
	private Object Entity;

	public DinsFlame(AbstractBlock.Properties properties) {
		super(properties);
		this.fireDamage = 0;
	}


																														//  16        1         16
	protected static final VoxelShape shapeDown = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 1.0D, 15.99D);

																												//0,        0,            0
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shapeDown;
	}

	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}


	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return this.canSurvive(stateIn, worldIn, currentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		return worldIn.getBlockState(blockpos).isFaceSturdy(worldIn, blockpos, Direction.UP);
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		if(entity instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity) entity;
			ItemStack stack = itemEntity.getItem();
			ItemStack newsword = new ItemStack(ItemInit.MASTER_SWORD.get());
			ItemEntity sword = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), newsword);
			if(stack.getItem() == ItemInit.GODDESS_WHITE_SWORD.get()) {
				stack.getEntityRepresentation().remove();
				worldIn.addFreshEntity(sword);
			}else {
				itemEntity.teleportTo(itemEntity.getX() + ((worldIn.random.nextDouble() * 2D) - 1D), itemEntity.getY() + ((worldIn.random.nextDouble() * 4D) - 2D), itemEntity.getZ() + ((worldIn.random.nextDouble() * 2D) - 1D));
			}
		}

		/*if(entity instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity) entity;
			ItemStack stack = itemEntity.getItem();
			ItemStack newsword = new ItemStack(ItemInit.MASTER_SWORD_DN.get());
			ItemEntity sword = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), newsword);
			if(stack.getItem() == ItemInit.MASTER_SWORD_N.get()) {
				stack.getEntityRepresentation().remove();
				worldIn.addFreshEntity(sword);
			}else {
				itemEntity.teleportTo(itemEntity.getX() + ((worldIn.random.nextDouble() * 2D) - 1D), itemEntity.getY() + ((worldIn.random.nextDouble() * 4D) - 2D), itemEntity.getZ() + ((worldIn.random.nextDouble() * 2D) - 1D));
			}
		}

		if(entity instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity) entity;
			ItemStack stack = itemEntity.getItem();
			ItemStack newsword = new ItemStack(ItemInit.MASTER_SWORD_FD.get());
			ItemEntity sword = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), newsword);
			if(stack.getItem() == ItemInit.MASTER_SWORD_F.get()) {
				stack.getEntityRepresentation().remove();
				worldIn.addFreshEntity(sword);
			}else {
				itemEntity.teleportTo(itemEntity.getX() + ((worldIn.random.nextDouble() * 2D) - 1D), itemEntity.getY() + ((worldIn.random.nextDouble() * 4D) - 2D), itemEntity.getZ() + ((worldIn.random.nextDouble() * 2D) - 1D));
			}
		}

		if(entity instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity) entity;
			ItemStack stack = itemEntity.getItem();
			ItemStack newsword = new ItemStack(ItemInit.TRUE_MASTER_SWORD.get());
			ItemEntity sword = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), newsword);
			if(stack.getItem() == ItemInit.MASTER_SWORD_NF.get()) {
				stack.getEntityRepresentation().remove();
				worldIn.addFreshEntity(sword);
			}else {
				itemEntity.teleportTo(itemEntity.getX() + ((worldIn.random.nextDouble() * 2D) - 1D), itemEntity.getY() + ((worldIn.random.nextDouble() * 4D) - 2D), itemEntity.getZ() + ((worldIn.random.nextDouble() * 2D) - 1D));
			}
		}*/
		super.entityInside(state, worldIn, pos, entity);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState bstate, World p_180655_2_, BlockPos bpos, Random random) {
		if (random.nextInt(24) == 0) {
			p_180655_2_.playLocalSound((double)bpos.getX() + 0.5D, (double)bpos.getY() + 0.5D, (double)bpos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
		}

		BlockPos blockpos = bpos.below();
		BlockState blockstate = p_180655_2_.getBlockState(blockpos);
		if (!this.canBurn(blockstate) && !blockstate.isFaceSturdy(p_180655_2_, blockpos, Direction.UP)) {
			if (this.canBurn(p_180655_2_.getBlockState(bpos.west()))) {
				for(int j = 0; j < 2; ++j) {
					double d3 = (double)bpos.getX() + random.nextDouble() * (double)0.1F;
					double d8 = (double)bpos.getY() + random.nextDouble();
					double d13 = (double)bpos.getZ() + random.nextDouble();
					p_180655_2_.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canBurn(p_180655_2_.getBlockState(bpos.east()))) {
				for(int k = 0; k < 2; ++k) {
					double d4 = (double)(bpos.getX() + 1) - random.nextDouble() * (double)0.1F;
					double d9 = (double)bpos.getY() + random.nextDouble();
					double d14 = (double)bpos.getZ() + random.nextDouble();
					p_180655_2_.addParticle(ParticleTypes.LARGE_SMOKE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canBurn(p_180655_2_.getBlockState(bpos.north()))) {
				for(int l = 0; l < 2; ++l) {
					double d5 = (double)bpos.getX() + random.nextDouble();
					double d10 = (double)bpos.getY() + random.nextDouble();
					double d15 = (double)bpos.getZ() + random.nextDouble() * (double)0.1F;
					p_180655_2_.addParticle(ParticleTypes.LARGE_SMOKE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canBurn(p_180655_2_.getBlockState(bpos.south()))) {
				for(int i1 = 0; i1 < 2; ++i1) {
					double d6 = (double)bpos.getX() + random.nextDouble();
					double d11 = (double)bpos.getY() + random.nextDouble();
					double d16 = (double)(bpos.getZ() + 1) - random.nextDouble() * (double)0.1F;
					p_180655_2_.addParticle(ParticleTypes.LARGE_SMOKE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.canBurn(p_180655_2_.getBlockState(bpos.above()))) {
				for(int j1 = 0; j1 < 2; ++j1) {
					double d7 = (double)bpos.getX() + random.nextDouble();
					double d12 = (double)(bpos.getY() + 1) - random.nextDouble() * (double)0.1F;
					double d17 = (double)bpos.getZ() + random.nextDouble();
					p_180655_2_.addParticle(ParticleTypes.LARGE_SMOKE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
				}
			}
		} else {
			for(int i = 0; i < 3; ++i) {
				double d0 = (double)bpos.getX() + random.nextDouble();
				double d1 = (double)bpos.getY() + random.nextDouble() * 0.5D + 0.5D;
				double d2 = (double)bpos.getZ() + random.nextDouble();
				p_180655_2_.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}

	}

	protected boolean canBurn(BlockState state) {
		return true;
	}
}
