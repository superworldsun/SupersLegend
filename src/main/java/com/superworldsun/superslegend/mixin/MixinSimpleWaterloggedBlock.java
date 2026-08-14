package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(SimpleWaterloggedBlock.class)
public interface MixinSimpleWaterloggedBlock {
    /** @author SupersLegend @reason Allow the two mod fluids in every vanilla-waterloggable block. */
    @Overwrite
    default boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return LoggedFluid.supports(state)
                && !state.getValue(BlockStateProperties.WATERLOGGED)
                && LoggedFluid.isSupported(fluid);
    }

    /** @author SupersLegend @reason Store and synchronize the actual logged fluid identity. */
    @Overwrite
    default boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!LoggedFluid.supports(state)
                || state.getValue(BlockStateProperties.WATERLOGGED)
                || !LoggedFluid.isSupported(fluidState.getType())) {
            return false;
        }
        if (!level.isClientSide()) {
            BlockState filled = LoggedFluid.fill(state, fluidState.getType());
            // A filled torch flame is extinguished in-place. Replacing only the top preserves
            // the base and keeps the incoming water, poison, or mud in that same block space.
            if (state.is(BlockInit.TORCH_TOWER_TOP_LIT.get())) {
                filled = LoggedFluid.fill(BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState(),
                        fluidState.getType());
            }
            level.setBlock(pos, filled, 3);
            FluidState stored = LoggedFluid.getFluidState(filled);
            level.scheduleTick(pos, stored.getType(), stored.getType().getTickDelay(level));
        }
        return true;
    }

    /** @author SupersLegend @reason Return the correct bucket for water, poison, or mud. */
    @Overwrite
    default ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        if (!LoggedFluid.supports(state) || !state.getValue(BlockStateProperties.WATERLOGGED)) {
            return ItemStack.EMPTY;
        }
        ItemStack bucket = state.getValue(LoggedFluid.PROPERTY).bucket();
        BlockState emptied = LoggedFluid.empty(state);
        level.setBlock(pos, emptied, 3);
        if (!emptied.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
        return bucket;
    }
}
