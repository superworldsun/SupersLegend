package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import com.superworldsun.superslegend.registries.FluidInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Makes poison/mud logging part of the initial client-predicted placement state. */
@Mixin(BlockItem.class)
public abstract class MixinBlockItemLoggedFluidPlacement {
    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void superslegend$preserveCustomFluid(BlockPlaceContext context,
                                                   CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state == null || !LoggedFluid.supports(state)) {
            return;
        }

        BlockPos placementPos = context.getClickedPos();
        Fluid fluid = context.getLevel().getFluidState(placementPos).getType();
        if (fluid.isSame(FluidInit.POISON_SOURCE.get()) || fluid.isSame(FluidInit.MUD_SOURCE.get())) {
            cir.setReturnValue(LoggedFluid.fill(state, fluid));
        }
    }
}
