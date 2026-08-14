package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinLoggedFluidBlockState {
    @Shadow private FluidState fluidState;

    @Inject(method = "initCache", at = @At("TAIL"))
    private void superslegend$cacheLoggedFluid(CallbackInfo ci) {
        BlockState state = (BlockState) (Object) this;
        if (LoggedFluid.supports(state) && state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED)) {
            this.fluidState = LoggedFluid.getFluidState(state);
        }
    }

    @Inject(method = "updateShape", at = @At("HEAD"))
    private void superslegend$scheduleLoggedFluid(Direction direction, BlockState neighbor,
                                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos,
                                                  CallbackInfoReturnable<BlockState> cir) {
        BlockState state = (BlockState) (Object) this;
        if (LoggedFluid.supports(state)
                && state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED)
                && state.getValue(LoggedFluid.PROPERTY) != LoggedFluid.WATER) {
            FluidState logged = LoggedFluid.getFluidState(state);
            level.scheduleTick(currentPos, logged.getType(), logged.getType().getTickDelay(level));
        }
    }

    /*
     * Several vanilla waterloggable blocks (notably walls) precompute shape maps using only
     * their original states. Our extra fluid identity is intentionally visual/physical metadata;
     * it must not create a distinct geometry lookup key. Delegate every custom-fluid shape query
     * to the otherwise identical WATER variant so those vanilla caches remain valid.
     */
    private BlockState superslegend$shapeState() {
        BlockState state = (BlockState) (Object) this;
        if (LoggedFluid.supports(state) && state.getValue(LoggedFluid.PROPERTY) != LoggedFluid.WATER) {
            return state.setValue(LoggedFluid.PROPERTY, LoggedFluid.WATER);
        }
        return null;
    }

    @Inject(method = "getFaceOcclusionShape", at = @At("HEAD"), cancellable = true)
    private void superslegend$getFaceOcclusionShape(BlockGetter level, BlockPos pos, Direction direction,
                                                     CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getFaceOcclusionShape(level, pos, direction));
    }

    @Inject(method = "getOcclusionShape", at = @At("HEAD"), cancellable = true)
    private void superslegend$getOcclusionShape(BlockGetter level, BlockPos pos,
                                                 CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getOcclusionShape(level, pos));
    }

    @Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("HEAD"), cancellable = true)
    private void superslegend$getShape(BlockGetter level, BlockPos pos,
                                        CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getShape(level, pos));
    }

    @Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("HEAD"), cancellable = true)
    private void superslegend$getShape(BlockGetter level, BlockPos pos, CollisionContext context,
                                        CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getShape(level, pos, context));
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("HEAD"), cancellable = true)
    private void superslegend$getCollisionShape(BlockGetter level, BlockPos pos,
                                                 CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getCollisionShape(level, pos));
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("HEAD"), cancellable = true)
    private void superslegend$getCollisionShape(BlockGetter level, BlockPos pos, CollisionContext context,
                                                 CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getCollisionShape(level, pos, context));
    }

    @Inject(method = "getBlockSupportShape", at = @At("HEAD"), cancellable = true)
    private void superslegend$getBlockSupportShape(BlockGetter level, BlockPos pos,
                                                    CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getBlockSupportShape(level, pos));
    }

    @Inject(method = "getVisualShape", at = @At("HEAD"), cancellable = true)
    private void superslegend$getVisualShape(BlockGetter level, BlockPos pos, CollisionContext context,
                                              CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getVisualShape(level, pos, context));
    }

    @Inject(method = "getInteractionShape", at = @At("HEAD"), cancellable = true)
    private void superslegend$getInteractionShape(BlockGetter level, BlockPos pos,
                                                   CallbackInfoReturnable<VoxelShape> cir) {
        BlockState shapeState = superslegend$shapeState();
        if (shapeState != null) cir.setReturnValue(shapeState.getInteractionShape(level, pos));
    }
}
