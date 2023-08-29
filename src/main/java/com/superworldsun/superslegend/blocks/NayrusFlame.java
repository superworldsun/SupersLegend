package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class NayrusFlame extends Block {
    private final int fireDamage;
    private Object ItemEntity;
    private Object Entity;

    public NayrusFlame(Properties properties) {
        super(properties);
        this.fireDamage = 0;
    }


    //  16        1         16
    protected static final VoxelShape shapeDown = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 1.0D, 15.99D);

    //0,        0,            0
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return shapeDown;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return this.canSurvive(stateIn, worldIn, currentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    public boolean canSurvive(BlockState state, LevelAccessor worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return worldIn.getBlockState(blockpos).isFaceSturdy(worldIn, blockpos, Direction.UP);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
        if(entity instanceof ItemEntity) {
            ItemEntity itemEntity = (ItemEntity) entity;
            ItemStack stack = itemEntity.getItem();
            ItemStack newsword = new ItemStack(ItemInit.GODDESS_WHITE_SWORD.get());
            ItemEntity sword = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), newsword);
            if(stack.getItem() == ItemInit.GODDESS_LONGSWORD.get()) {
                stack.getEntityRepresentation().discard();
                worldIn.addFreshEntity(sword);
            }else {
                itemEntity.teleportTo(itemEntity.getX() + ((worldIn.random.nextDouble() * 2D) - 1D), itemEntity.getY() + ((worldIn.random.nextDouble() * 4D) - 2D), itemEntity.getZ() + ((worldIn.random.nextDouble() * 2D) - 1D));
            }
        }
        
        super.entityInside(state, worldIn, pos, entity);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState bstate, Level level, BlockPos bpos, Random random) {
        if (random.nextInt(24) == 0) {
            level.playLocalSound((double)bpos.getX() + 0.5D, (double)bpos.getY() + 0.5D, (double)bpos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos blockpos = bpos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        if (!this.canBurn(blockstate) && !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
            if (this.canBurn(level.getBlockState(bpos.west()))) {
                for(int j = 0; j < 2; ++j) {
                    double d3 = (double)bpos.getX() + random.nextDouble() * (double)0.1F;
                    double d8 = (double)bpos.getY() + random.nextDouble();
                    double d13 = (double)bpos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(level.getBlockState(bpos.east()))) {
                for(int k = 0; k < 2; ++k) {
                    double d4 = (double)(bpos.getX() + 1) - random.nextDouble() * (double)0.1F;
                    double d9 = (double)bpos.getY() + random.nextDouble();
                    double d14 = (double)bpos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(level.getBlockState(bpos.north()))) {
                for(int l = 0; l < 2; ++l) {
                    double d5 = (double)bpos.getX() + random.nextDouble();
                    double d10 = (double)bpos.getY() + random.nextDouble();
                    double d15 = (double)bpos.getZ() + random.nextDouble() * (double)0.1F;
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(level.getBlockState(bpos.south()))) {
                for(int i1 = 0; i1 < 2; ++i1) {
                    double d6 = (double)bpos.getX() + random.nextDouble();
                    double d11 = (double)bpos.getY() + random.nextDouble();
                    double d16 = (double)(bpos.getZ() + 1) - random.nextDouble() * (double)0.1F;
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(level.getBlockState(bpos.above()))) {
                for(int j1 = 0; j1 < 2; ++j1) {
                    double d7 = (double)bpos.getX() + random.nextDouble();
                    double d12 = (double)(bpos.getY() + 1) - random.nextDouble() * (double)0.1F;
                    double d17 = (double)bpos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }
        } else {
            for(int i = 0; i < 3; ++i) {
                double d0 = (double)bpos.getX() + random.nextDouble();
                double d1 = (double)bpos.getY() + random.nextDouble() * 0.5D + 0.5D;
                double d2 = (double)bpos.getZ() + random.nextDouble();
                level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected boolean canBurn(BlockState state) {
        return true;
    }
}
