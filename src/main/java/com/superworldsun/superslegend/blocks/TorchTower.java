package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TorchTower extends BaseTorchTower {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);
    public static final IntegerProperty OUTPUT_POWER = BlockStateProperties.POWER;

    public TorchTower(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OUTPUT_POWER, 0).setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(OUTPUT_POWER, POWERED);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState pState) {
        return true;
    }

    @Override
    protected int getSignalStrength(Level var1, BlockPos var2) {
        return 15;
    }

    @Override
    protected int getSignalForState(BlockState pState) {
        return pState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected @NotNull BlockState setSignalForState(BlockState pState, int pStrength) {
        return pState.setValue(POWERED, Boolean.valueOf(pStrength > 0));
    }

//    @Override
//    public int getSignal(BlockState blockState, @NotNull BlockGetter iBlockReader, @NotNull BlockPos blockPos, @NotNull Direction direction) {
//        return blockState.getValue(OUTPUT_POWER);
//    }

    @Override
    public void onPlace(@NotNull BlockState state, Level level, BlockPos pos, @NotNull BlockState oldState, boolean flag)
    {
        if (level.getBlockState(pos.above()).isAir()) {
            level.setBlockAndUpdate(pos.above(), BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState());
        }
    }

    public void updateNeighbours(Level world, BlockPos pPos) {
        world.updateNeighborsAt(pPos, this);
        world.updateNeighborsAt(pPos.below(), this);
    }
}
