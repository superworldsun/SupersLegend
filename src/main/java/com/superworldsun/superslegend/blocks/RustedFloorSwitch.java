package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Random;

public class RustedFloorSwitch extends BasePressurePlateBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RustedFloorSwitch(RedFloorSwitch.SensitivityMod player, Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
    }

    /**
     * Returns the signal encoded in the given block state.
     */
    protected int getSignalForState(BlockState pState) {
        return pState.getValue(POWERED) ? 15 : 0;
    }

    //How long it stays down, 30 seconds
    @Override
    protected int getPressedTime() {
        return 40;
    }

    /**
     * Returns the block state that encodes the given signal.
     */
    protected BlockState setSignalForState(BlockState pState, int pStrength) {
        return pState.setValue(POWERED, Boolean.valueOf(pStrength > 0));
    }


    protected void playOnSound(Level world, BlockPos pos)
    {
        world.playSound(null, pos, SoundInit.FLOOR_SWITCH.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
    }


    protected void playOffSound(Level world, BlockPos pos)
    {
        world.playSound(null, pos, SoundInit.FLOOR_SWITCH.get(), SoundSource.BLOCKS, 0.3F, 0.4F);
    }

    public void attack(BlockState blockState, Level level, BlockPos blockPos, Player playerEntity)
    {
        if (playerEntity.isHolding(ItemInit.MEGATON_HAMMER.get()) || playerEntity.isHolding(ItemInit.SKULL_HAMMER.get()))
        {
            level.playSound(null, playerEntity, SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1f, 1f);
            int signal = getSignalForState(blockState);

            if (signal == 0)
            {
                level.setBlockAndUpdate(blockPos, setSignalForState(blockState, 15));
                //TODO, this is needed for the floor switch to pop out, cant fix error
                //level.getBlockTicks().hasScheduledTick(blockPos, this, getPressedTime());
                playOnSound(level, blockPos);
            }
        }
    }


    public void tick(BlockState blockState, ServerLevel serverWorld, BlockPos blockPos, Random random)
    {
        int signal = this.getSignalForState(blockState);

        if (signal > 0)
        {
            serverWorld.setBlockAndUpdate(blockPos, setSignalForState(blockState, 0));
            playOffSound(serverWorld, blockPos);
        }
    }

    /**
     * Calculates what the signal strength of a pressure plate at the given location should be.
     */
    protected int getSignalStrength(Level world, BlockPos pos) {
        return getSignalForState(world.getBlockState(pos));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }
}