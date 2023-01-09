package com.superworldsun.superslegend.blocks;

import java.util.Random;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.util.Floodable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GrateBlock extends Block implements Floodable {
	public GrateBlock(Block.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FLUID_STATE_PROPERTY, 0));
	}

	@Override
	public boolean isRandomlyTicking(BlockState blockState) {
		return blockState.getFluidState().isRandomlyTicking();
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		state.getFluidState().randomTick(worldIn, pos, random);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}

	@Override
	public FluidState getFluidState(BlockState blockState) {
		int fluidId = blockState.getValue(FLUID_STATE_PROPERTY);
		FluidState fluidState = Fluid.FLUID_STATE_REGISTRY.byId(fluidId);
		return fluidState;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FLUID_STATE_PROPERTY);
	}

	@Override
	public boolean canPlaceLiquid(IBlockReader p_204510_1_, BlockPos p_204510_2_, BlockState p_204510_3_, Fluid p_204510_4_) {
		return true;
	}

	@Override
	public boolean placeLiquid(IWorld world, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		world.setBlock(blockPos, getBlockState(fluidState), 11);
		return true;
	}

	@Override
	public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos neighborBlockState, boolean b) {
		Fluid fluid = blockState.getFluidState().getType();
		world.getLiquidTicks().scheduleTick(blockPos, fluid, fluid.getTickDelay(world));
	}

	@Override
	public void onPlace(BlockState blockState, World world, BlockPos blockPos, BlockState oldBlockState, boolean b) {
		Fluid fluid = blockState.getFluidState().getType();
		world.getLiquidTicks().scheduleTick(blockPos, fluid, fluid.getTickDelay(world));
	}

	@Override
	public VoxelShape getOcclusionShape(BlockState state, IBlockReader world, BlockPos pos) {
		return VoxelShapes.empty();
	}

	@Override
	public Fluid takeLiquid(IWorld world, BlockPos blockPos, BlockState blockState) {
		FluidState fluidState = getFluidState(blockState);

		if (fluidState.isSource()) {
			world.setBlock(blockPos, defaultBlockState(), 11);
			return fluidState.getType();
		}

		return Fluids.EMPTY;
	}

	@Override
	public BlockState getBlockState(FluidState fluidState) {
		int fluidStateId = 0;

		if (fluidState.getType() != Fluids.EMPTY) {
			fluidStateId = Fluid.FLUID_STATE_REGISTRY.getId(fluidState);
		}

		return defaultBlockState().setValue(FLUID_STATE_PROPERTY, fluidStateId);
	}

	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		Block placedBlock = event.getState().getBlock();

		if (placedBlock instanceof GrateBlock) {
			BlockState oldBlockState = event.getBlockSnapshot().getReplacedBlock();

			if (oldBlockState.getBlock() instanceof FlowingFluidBlock) {
				GrateBlock grateBlock = (GrateBlock) placedBlock;
				BlockState floodedGrateState = grateBlock.getBlockState(oldBlockState.getFluidState());
				event.getWorld().setBlock(event.getPos(), floodedGrateState, 11);
			}
		}
	}
}
