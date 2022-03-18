package com.superworldsun.superslegend.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.superworldsun.superslegend.blocks.GrateBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@Mixin(FlowingFluid.class)
public class MixinFlowingFluid {

	@Overwrite
	public void tick(World world, BlockPos blockPos, FluidState fluidStateIn) {
		if (!fluidStateIn.isSource()) {
			FluidState fluidstate = null;
			if(world.getBlockState(blockPos).getBlock() instanceof GrateBlock) {
				BlockState grate = world.getBlockState(blockPos);
				int level = grate.getValue(BlockStateProperties.LEVEL);
				if(level == 0) {
					world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
				}
				else if(level == 8) {
					world.setBlock(blockPos, Blocks.WATER.defaultBlockState(), 2);
				}
				else if(level > 8) {
					level = 8;
					world.setBlock(blockPos, Blocks.WATER.defaultBlockState().setValue(BlockStateProperties.LEVEL, level), 3);
				}
				else {
					level = 8 - level;
					world.setBlock(blockPos, Blocks.WATER.defaultBlockState().setValue(BlockStateProperties.LEVEL, level), 3);
				}
				fluidstate = this.getNewLiquid(world, blockPos, world.getBlockState(blockPos));
				world.setBlock(blockPos, grate, 2);
			}
			else {
				fluidstate = this.getNewLiquid(world, blockPos, world.getBlockState(blockPos));
			}
			int i = this.getSpreadDelay(world, blockPos, fluidStateIn, fluidstate);
			BlockState blockstate = null;
			if (fluidstate.isEmpty()) {
				fluidStateIn = fluidstate;
				if(world.getBlockState(blockPos).getBlock() instanceof GrateBlock) {
					blockstate = ((GrateBlock)world.getBlockState(blockPos).getBlock()).getBlockState(fluidStateIn);
					world.setBlock(blockPos, blockstate, 2);
				}
				else {
					world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);		
				}
			} else if (!fluidstate.equals(fluidStateIn)) {
				fluidStateIn = fluidstate;
				if(world.getBlockState(blockPos).getBlock() instanceof GrateBlock) {
					blockstate = ((GrateBlock)world.getBlockState(blockPos).getBlock()).getBlockState(fluidStateIn);
				}
				else {
					blockstate = fluidstate.createLegacyBlock();
				}
				world.setBlock(blockPos, blockstate, 2);
				world.getLiquidTicks().scheduleTick(blockPos, fluidstate.getType(), i);
				world.updateNeighborsAt(blockPos, blockstate.getBlock());
			}
		}

		if(world.getBlockState(blockPos).getBlock() instanceof GrateBlock) {
			BlockState grate = world.getBlockState(blockPos);
			int level = grate.getValue(BlockStateProperties.LEVEL);
			if(level == 0) {
				world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
			}
			else if(level == 8) {
				world.setBlock(blockPos, Blocks.WATER.defaultBlockState(), 2);
			}
			else if(level > 8) {
				level = 8;
				world.setBlock(blockPos, Blocks.WATER.defaultBlockState().setValue(BlockStateProperties.LEVEL, level), 3);
			}
			else {
				level = 8 - level;
				world.setBlock(blockPos, Blocks.WATER.defaultBlockState().setValue(BlockStateProperties.LEVEL, level), 3);
			}
			
			this.spread(world, blockPos, fluidStateIn);
			world.setBlock(blockPos, grate, 2);
		}
		else {
			this.spread(world, blockPos, fluidStateIn);
		}
	}
	
	@Inject(
		at = @At("HEAD"),
		method = "canPassThroughWall",	
		cancellable = true
	)
	private void canPassThroughGrateWalls(Direction dir, IBlockReader reader, BlockPos blockPos1, BlockState blockState1, BlockPos blockPos2, BlockState blockState2, CallbackInfoReturnable<Boolean> callback) {
		if(blockState1.getBlock() instanceof GrateBlock) {
			if(blockState2.getBlock() instanceof ILiquidContainer || blockState2.getBlock() instanceof IBucketPickupHandler) {
				callback.setReturnValue(true);
				callback.cancel();
			}
		}
		if(blockState2.getBlock() instanceof GrateBlock) {
			if(blockState1.getBlock() instanceof ILiquidContainer || blockState1.getBlock() instanceof IBucketPickupHandler) {
				callback.setReturnValue(true);
				callback.cancel();
			}
		}
	}
	
	private BlockState grate_tmp = null;
	
	@Shadow
	protected FluidState getNewLiquid(IWorldReader iWorldReader, BlockPos blockPos, BlockState blockState) {
		throw new IllegalStateException("Mixin failed to shadow getNewLiquid()");
	}
	
	@Shadow
	protected int getSpreadDelay(World world, BlockPos blockPos, FluidState fluidState1, FluidState fluidState2) {
		throw new IllegalStateException("Mixin failed to shadow getSpreadDelay()");
		
	}
	
	@Shadow
	protected void spread(IWorld iWorld, BlockPos blockPos, FluidState fluidState) {
		throw new IllegalStateException("Mixin failed to shadow spread()");
		
	}
}
