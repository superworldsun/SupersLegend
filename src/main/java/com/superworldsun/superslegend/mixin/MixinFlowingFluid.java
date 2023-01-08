package com.superworldsun.superslegend.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.superworldsun.superslegend.blocks.util.Floodable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@Mixin(FlowingFluid.class)
public abstract class MixinFlowingFluid {
	@Inject(at = @At("HEAD"), method = "tick", cancellable = true)
	public void tick(World world, BlockPos blockPos, FluidState fluidState, CallbackInfo callbackInfo) {
		Block originaBlock = world.getBlockState(blockPos).getBlock();

		if (originaBlock instanceof Floodable) {
			if (!fluidState.isSource()) {
				FluidState newFluidState = getNewLiquid(world, blockPos, world.getBlockState(blockPos));
				int spreadDelay = getSpreadDelay(world, blockPos, fluidState, newFluidState);

				if (newFluidState.isEmpty()) {
					fluidState = newFluidState;
					world.setBlock(blockPos, originaBlock.defaultBlockState(), 3);
				} else if (!newFluidState.equals(fluidState)) {
					fluidState = newFluidState;
					BlockState blockstate = ((Floodable) originaBlock).getBlockState(fluidState);
					world.setBlock(blockPos, blockstate, 2);
					world.getLiquidTicks().scheduleTick(blockPos, newFluidState.getType(), spreadDelay);
					world.updateNeighborsAt(blockPos, blockstate.getBlock());
				}
			}

			spread(world, blockPos, fluidState);
			callbackInfo.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "canPassThroughWall", cancellable = true)
	private void injectCanPassThroughWall(Direction direction, IBlockReader world, BlockPos blockPosFrom, BlockState blockStateFrom, BlockPos blockPosTo,
			BlockState blockStateTo, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (blockStateFrom.getBlock() instanceof Floodable || blockStateTo.getBlock() instanceof Floodable) {
			callbackInfo.setReturnValue(true);
		}
	}

	@Shadow
	protected abstract FluidState getNewLiquid(IWorldReader worldReader, BlockPos blockPos, BlockState blockState);

	@Shadow
	protected abstract int getSpreadDelay(World world, BlockPos blockPos, FluidState fluidState, FluidState newFluidState);

	@Shadow
	protected abstract void spread(IWorld world, BlockPos blockPos, FluidState fluidState);
}
