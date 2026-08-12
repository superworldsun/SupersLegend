package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TorchTower extends BaseTorchTower implements SimpleWaterloggedBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);
    public static final IntegerProperty OUTPUT_POWER = BlockStateProperties.POWER;

    public TorchTower(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(OUTPUT_POWER, 0)
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(OUTPUT_POWER, POWERED, WATERLOGGED);
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
        if (level.isClientSide || oldState.is(this)) {
            return;
        }

        BlockPos topPos = pos.above();
        FluidState fluid = level.getFluidState(topPos);
        if (level.getBlockState(topPos).isAir() || LoggedFluid.isSupported(fluid.getType())) {
            BlockState top = BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState();
            if (LoggedFluid.isSupported(fluid.getType())) {
                top = LoggedFluid.fill(top, fluid.getType());
            }
            level.setBlock(topPos, top, Block.UPDATE_ALL);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState();
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return LoggedFluid.isSupported(fluid.getType()) ? LoggedFluid.fill(state, fluid.getType()) : state;
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction,
                                            @NotNull BlockState neighborState, @NotNull LevelAccessor level,
                                            @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (BaseTorchTower.isSubmerged(state)) {
            FluidState fluid = LoggedFluid.getFluidState(state);
            level.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public void updateNeighbours(Level world, BlockPos pPos) {
        world.updateNeighborsAt(pPos, this);
        world.updateNeighborsAt(pPos.below(), this);
    }
}
