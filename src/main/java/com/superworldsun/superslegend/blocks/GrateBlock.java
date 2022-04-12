package com.superworldsun.superslegend.blocks;

import java.util.Random;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.world.BlockEvent.FluidPlaceBlockEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;


@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GrateBlock extends Block implements ILiquidContainer, IBucketPickupHandler{
	public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;
	public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public GrateBlock(Block.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));

	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}


	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return true;

	}

	public boolean isRandomlyTicking(BlockState p_149653_1_) {
		return p_149653_1_.getFluidState().isRandomlyTicking();
	}

	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		state.getFluidState().randomTick(worldIn, pos, random);
	}

	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return true;
	}

	public FluidState getFluidState(BlockState state) {
		int i = state.getValue(LEVEL);
		if(i == 0) {
			return Fluids.EMPTY.defaultFluidState();
		}
		if(i > 8) {
			return Fluids.FLOWING_WATER.getSource(false);
		}
		if(i == 8) {
			return Fluids.FLOWING_WATER.getFlowing(8, true);
		}
		return Fluids.FLOWING_WATER.getFlowing(i, false);
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(LEVEL);
	}

	@Override
	public boolean canPlaceLiquid(IBlockReader p_204510_1_, BlockPos p_204510_2_, BlockState p_204510_3_,
			Fluid p_204510_4_) {
		return true;
	}

	@Override
	public boolean placeLiquid(IWorld world, BlockPos blockPos, BlockState blockState, FluidState fluidState) {

		BlockState blockstate = ((GrateBlock)world.getBlockState(blockPos).getBlock()).getBlockState(fluidState);
		world.setBlock(blockPos, blockstate, 2);
		return true;
	}

	public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
		p_220069_2_.getLiquidTicks().scheduleTick(p_220069_3_, p_220069_1_.getFluidState().getType(), Fluids.WATER.getTickDelay(p_220069_2_));
	}


	public void onPlace(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {

		p_220082_2_.getLiquidTicks().scheduleTick(p_220082_3_, p_220082_1_.getFluidState().getType(), Fluids.WATER.getTickDelay(p_220082_2_));
	}
	
	public BlockState getBlockState(FluidState fluidState) {
		if(fluidState.isEmpty())
		{
			return this.defaultBlockState().setValue(LEVEL, 0);			
		}
		else if(fluidState.isSource()) {
			return this.defaultBlockState().setValue(LEVEL, 9);
		}
		return this.defaultBlockState().setValue(LEVEL, fluidState.getAmount());
	}
	
	@Override
	public VoxelShape getOcclusionShape(BlockState state, IBlockReader world, BlockPos pos)
	{
		return VoxelShapes.empty();
	}
	
	@Override
	public boolean hasDynamicShape() {
		return true;
		
	}

	@Override
	public Fluid takeLiquid(IWorld world, BlockPos blockPos, BlockState blockState) {
		if (blockState.getValue(LEVEL) > 8) {
			world.setBlock(blockPos, BlockInit.GRATE_BLOCK.get().defaultBlockState(), 11);
			return Fluids.WATER;
		} else {
			return Fluids.EMPTY;
		}
	}
	
	@SubscribeEvent
	public static void onPlace(EntityPlaceEvent event)
	{
		if(event.getState().getBlock() instanceof GrateBlock) {
			BlockState blockState = event.getBlockSnapshot().getReplacedBlock();
			if(blockState.getBlock() instanceof FlowingFluidBlock && blockState.getFluidState().getType() instanceof WaterFluid) {
				BlockState grate = ((GrateBlock)(BlockInit.GRATE_BLOCK.get())).getBlockState(blockState.getFluidState());
				event.getWorld().setBlock(event.getPos(), grate, 11);

			}
		}
	}

}
