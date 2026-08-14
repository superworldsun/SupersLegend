package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BaseTorchTower extends Block {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 16.0D, 13.0D);

    public BaseTorchTower(Properties properties) {
        super(properties);
    }

    protected int getPressedTime() {
        return -1;
    }

    public boolean isPossibleToRespawnInThis(BlockState pState) {
        return false;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int signal = this.getSignalForState(pState);
        this.checkLit(pLevel, pPos, pState, signal);
    }

//    private void checkLit(Level level, BlockPos pPos, BlockState pState, int pCurrentSignal) {
//        int signalStrength = this.getSignalStrength(level, pPos);
//        System.out.println("Checking lit");
//        BlockPos abovePos = pPos.above();
//        BlockState stateBelow = level.getBlockState(abovePos);
//        Block blockAbove = stateBelow.getBlock();
//        if (blockAbove.equals(BlockInit.TORCH_TOWER_TOP_LIT.get()) && !isactive)
//        {
//            this.updateNeighbours(level, pPos);
//            level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pPos);
//            isactive = true;
//            System.out.println("Lit");
//        }
//        else if (blockAbove.equals(BlockInit.TORCH_TOWER_TOP_UNLIT.get()) && isactive)
//        {
//            this.updateNeighbours(level, pPos);
//            level.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, pPos);
//            isactive = false;
//            System.out.println("Unlit");
//        }
//    }

    private void checkLit(Level level, BlockPos pPos, BlockState pState, int pCurrentSignal) {
        if (level.isClientSide) {
            return;
        }
        BlockPos abovePos = pPos.above();
        BlockState stateAbove = level.getBlockState(abovePos);

        if (stateAbove.is(BlockInit.TORCH_TOWER_TOP_LIT.get()) && !pState.getValue(TorchTower.POWERED)) {
            BlockState newState = this.setSignalForState(pState, this.getSignalStrength(level, pPos));
            level.setBlock(pPos, newState, Block.UPDATE_ALL);
            this.updateNeighbours(level, pPos);
            level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pPos);
        }
        else if (stateAbove.is(BlockInit.TORCH_TOWER_TOP_UNLIT.get()) && pState.getValue(TorchTower.POWERED)) {
            BlockState newState = this.setSignalForState(pState, 0);
            level.setBlock(pPos, newState, Block.UPDATE_ALL);
            this.updateNeighbours(level, pPos);
            level.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, pPos);
        }
    }

    /** Changes the top on the logical server and broadcasts both halves to tracking clients. */
    static void setTopLit(Level level, BlockPos topPos, boolean lit) {
        if (level.isClientSide) {
            return;
        }

        BlockState oldTop = level.getBlockState(topPos);
        // A submerged flame cannot remain lit. Preserve the logged fluid while swapping the
        // invisible top block so water, poison, and mud never replace or orphan the tower.
        if (isSubmerged(oldTop)) {
            lit = false;
        }
        BlockState newTop = (lit ? BlockInit.TORCH_TOWER_TOP_LIT.get()
                : BlockInit.TORCH_TOWER_TOP_UNLIT.get()).defaultBlockState();
        newTop = copyLoggedFluid(oldTop, newTop);
        if (!oldTop.is(newTop.getBlock())) {
            level.setBlock(topPos, newTop, Block.UPDATE_ALL);
        } else {
            level.sendBlockUpdated(topPos, oldTop, oldTop, Block.UPDATE_ALL);
        }
        syncBaseFromTop(level, topPos, lit);
    }

    static boolean isSubmerged(BlockState state) {
        return LoggedFluid.supports(state) && state.getValue(BlockStateProperties.WATERLOGGED);
    }

    static BlockState copyLoggedFluid(BlockState from, BlockState to) {
        if (!isSubmerged(from) || !LoggedFluid.supports(to)) {
            return to;
        }
        return LoggedFluid.fill(to, from.getValue(LoggedFluid.PROPERTY).source());
    }

    /** Also catches top replacements made by other mechanics with insufficient update flags. */
    static void topPlaced(Level level, BlockPos topPos, BlockState oldState, BlockState newState, boolean lit) {
        if (level.isClientSide) {
            return;
        }
        level.sendBlockUpdated(topPos, oldState, newState, Block.UPDATE_ALL);
        syncBaseFromTop(level, topPos, lit);
    }

    private static void syncBaseFromTop(Level level, BlockPos topPos, boolean lit) {
        BlockPos basePos = topPos.below();
        BlockState base = level.getBlockState(basePos);
        if (!base.is(BlockInit.TORCH_TOWER.get())) {
            return;
        }
        BlockState updated = base.setValue(TorchTower.POWERED, lit)
                .setValue(TorchTower.OUTPUT_POWER, lit ? 15 : 0);
        if (updated != base) {
            level.setBlock(basePos, updated, Block.UPDATE_ALL);
        } else {
            level.sendBlockUpdated(basePos, base, base, Block.UPDATE_ALL);
        }
        level.updateNeighborsAt(basePos, base.getBlock());
        level.updateNeighborsAt(basePos.below(), base.getBlock());
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            if (this.getSignalForState(pState) > 0) {
                this.updateNeighbours(pLevel, pPos);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block pBlock, BlockPos fromPos, boolean pIsMoving) {
        if (fromPos.equals(pos.above())) {
            int signal = this.getSignalForState(state);
            this.checkLit(level, pos, state, signal);
        }
    }

    protected void updateNeighbours(Level pLevel, BlockPos pPos) {
        pLevel.updateNeighborsAt(pPos, this);
        pLevel.updateNeighborsAt(pPos.below(), this);
    }

    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return this.getSignalForState(pBlockState);
    }

    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pSide == Direction.UP ? this.getSignalForState(pBlockState) : 0;
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    protected abstract int getSignalStrength(Level var1, BlockPos var2);

    protected abstract int getSignalForState(BlockState var1);

    protected abstract BlockState setSignalForState(BlockState var1, int var2);
}
