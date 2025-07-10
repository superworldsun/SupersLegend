package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
        int signalStrength = this.getSignalStrength(level, pPos);
        System.out.println("Checking lit");
        BlockPos abovePos = pPos.above();
        BlockState stateAbove = level.getBlockState(abovePos);

        if (stateAbove.is(BlockInit.TORCH_TOWER_TOP_LIT.get()) && !pState.getValue(TorchTower.POWERED)) {
            BlockState newState = this.setSignalForState(pState, signalStrength);
            level.setBlock(pPos, newState, 3);
            this.updateNeighbours(level, pPos);
            level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pPos);
            System.out.println("Lit");
        }
        else if (stateAbove.is(BlockInit.TORCH_TOWER_TOP_UNLIT.get()) && pState.getValue(TorchTower.POWERED)) {
            BlockState newState = this.setSignalForState(pState, 0);
            level.setBlock(pPos, newState, 3);
            this.updateNeighbours(level, pPos);
            level.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, pPos);
            System.out.println("Unlit");
        }
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
            System.out.println("[TorchTower] Neighbor changed above torch tower at " + pos);
            int signal = this.getSignalForState(state);
            this.checkLit(level, pos, state, signal);
        }
        if (fromPos.above().equals(BlockInit.TORCH_TOWER_TOP_LIT.get()))
        {

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