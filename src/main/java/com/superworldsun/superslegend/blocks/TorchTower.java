package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TorchTower extends Block {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);
    public static final IntegerProperty OUTPUT_POWER = BlockStateProperties.POWER;

    public TorchTower(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OUTPUT_POWER, Integer.valueOf(0)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(OUTPUT_POWER);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState p_149744_1_) {
        return true;
    }

    @Override
    public int getSignal(BlockState blockState, @NotNull BlockGetter iBlockReader, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        return blockState.getValue(OUTPUT_POWER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos)
    {
        return facing == Direction.DOWN && !canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
                : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void onPlace(@NotNull BlockState state, Level world, BlockPos pos, @NotNull BlockState oldState, boolean flag)
    {
        world.setBlockAndUpdate(pos.above(), BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState());
    }

    public boolean canSurvive(BlockState state, LevelAccessor world, BlockPos pos)
    {
        return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.TORCH_TOWER_TOP_UNLIT.get())
                || (world.getBlockState(pos.above()).is(BlockInit.TORCH_TOWER_TOP_LIT.get()) || world.isEmptyBlock(pos.above())));
    }
}
