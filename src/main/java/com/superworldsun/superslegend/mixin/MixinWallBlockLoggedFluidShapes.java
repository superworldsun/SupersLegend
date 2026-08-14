package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Keeps vanilla WallBlock's state-keyed shape caches compatible with custom logged fluids. */
@Mixin(WallBlock.class)
public abstract class MixinWallBlockLoggedFluidShapes {
    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void superslegend$getLoggedShape(BlockState state, BlockGetter level, BlockPos pos,
                                              CollisionContext context,
                                              CallbackInfoReturnable<VoxelShape> cir) {
        if (state.hasProperty(LoggedFluid.PROPERTY)
                && state.getValue(LoggedFluid.PROPERTY) != LoggedFluid.WATER) {
            cir.setReturnValue(state.getBlock().getShape(
                    state.setValue(LoggedFluid.PROPERTY, LoggedFluid.WATER), level, pos, context));
        }
    }

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void superslegend$getLoggedCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                                       CollisionContext context,
                                                       CallbackInfoReturnable<VoxelShape> cir) {
        if (state.hasProperty(LoggedFluid.PROPERTY)
                && state.getValue(LoggedFluid.PROPERTY) != LoggedFluid.WATER) {
            cir.setReturnValue(state.getBlock().getCollisionShape(
                    state.setValue(LoggedFluid.PROPERTY, LoggedFluid.WATER), level, pos, context));
        }
    }
}
