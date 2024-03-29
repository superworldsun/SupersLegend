package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BlueFloorSwitch extends BasePressurePlateBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private final SensitivityMod sensitivity;
    private final BlockSetType type;

    public BlueFloorSwitch(SensitivityMod pSensitivity, BlockBehaviour.Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
        this.sensitivity = pSensitivity;
        this.type = pType;
    }

    /**
     * Returns the signal encoded in the given block state.
     */
    protected int getSignalForState(BlockState pState) {
        return pState.getValue(POWERED) ? 15 : 0;
    }


    protected int getPressedTime() {
        return 5;
    }
    /**
     * Returns the block state that encodes the given signal.
     */
    protected @NotNull BlockState setSignalForState(BlockState pState, int pStrength) {
        return pState.setValue(POWERED, Boolean.valueOf(pStrength > 0));
    }

    public void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        int i = this.getSignalForState(pState);
        if (i > 0) {
            this.checkPressed(null, pLevel, pPos, pState, i);
        }
    }

    public void entityInside(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if (!pLevel.isClientSide) {
            int i = this.getSignalForState(pState);
            if (i == 0) {
                this.checkPressed(pEntity, pLevel, pPos, pState, i);
            }

        }
    }

    private void checkPressed(@Nullable Entity pEntity, Level pLevel, BlockPos pPos, BlockState pState, int pCurrentSignal) {
        int i = this.getSignalStrength(pLevel, pPos);
        boolean flag = pCurrentSignal > 0;
        boolean flag1 = i > 0;
        if (pCurrentSignal != i) {
            BlockState blockstate = this.setSignalForState(pState, i);
            pLevel.setBlock(pPos, blockstate, 2);
            this.updateNeighbours(pLevel, pPos);
            pLevel.setBlocksDirty(pPos, pState, blockstate);
        }

        if (!flag1 && flag) {
            pLevel.playSound(null, pPos, SoundInit.FLOOR_SWITCH.get(), SoundSource.BLOCKS, 0.3f, 0.6f);
            pLevel.gameEvent(pEntity, GameEvent.BLOCK_DEACTIVATE, pPos);
        } else if (flag1 && !flag) {
            pLevel.playSound(null, pPos, SoundInit.FLOOR_SWITCH.get(), SoundSource.BLOCKS, 0.3f, 0.4f);
            pLevel.gameEvent(pEntity, GameEvent.BLOCK_ACTIVATE, pPos);
        }
        if (flag1) {
            pLevel.scheduleTick(new BlockPos(pPos), this, this.getPressedTime());
        }
    }

    /**
     * Calculates what the signal strength of a pressure plate at the given location should be.
     */
    protected int getSignalStrength(@NotNull Level world, @NotNull BlockPos pos) {
        net.minecraft.world.phys.AABB axisalignedbb = TOUCH_AABB.move(pos);
        List<? extends Entity> list;
        switch(this.sensitivity) {
            case EVERYTHING:
                list = world.getEntities(null, axisalignedbb);
                break;
            case MOBS:
                list = world.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
                break;
            case PLAYER:
                list = world.getEntitiesOfClass(Player.class, axisalignedbb);
                break;
            default:
                return 0;
        }

        if (!list.isEmpty()) {
            for(Entity entity : list) {
                if (!entity.isIgnoringBlockTriggers()) {
                    return 15;
                }
            }
        }

        return 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }

    public enum SensitivityMod {
        EVERYTHING,
        MOBS,
        PLAYER
	}
}