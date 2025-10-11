package com.superworldsun.superslegend.blocks;


import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.util.Floodable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GrateBlock extends Block implements Floodable {

    public GrateBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FLUID_STATE_PROPERTY, 0));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        int fluidId = state.getValue(FLUID_STATE_PROPERTY);
        return Fluid.FLUID_STATE_REGISTRY.byId(fluidId);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLUID_STATE_PROPERTY);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return true;
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        level.setBlock(pos, getBlockState(fluidState), 11);
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        level.scheduleTick(pos, state.getFluidState().getType(), state.getFluidState().getType().getTickDelay(level));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        level.scheduleTick(pos, state.getFluidState().getType(), state.getFluidState().getType().getTickDelay(level));
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        FluidState fluidState = getFluidState(pState);
        if (fluidState.isSource()) {
            pLevel.setBlock(pPos, defaultBlockState(), 11);
            return new ItemStack(fluidState.getType().getBucket());
        }
        return ItemStack.EMPTY;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
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
        if (event.getPlacedBlock().getBlock() instanceof GrateBlock && event.getPlacedAgainst().getBlock() instanceof LiquidBlock) {
            GrateBlock grateBlock = (GrateBlock) event.getPlacedBlock().getBlock();
            BlockState floodedGrateState = grateBlock.getBlockState(event.getPlacedAgainst().getFluidState());
            event.getLevel().setBlock(event.getPos(), floodedGrateState, 11);
        }
    }
}
