package com.superworldsun.superslegend.fluid;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.FluidInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Fluid identity stored alongside vanilla's WATERLOGGED boolean. */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public enum LoggedFluid implements StringRepresentable {
    WATER("water"),
    POISON("poison"),
    MUD("mud");

    public static final EnumProperty<LoggedFluid> PROPERTY = EnumProperty.create("logged_fluid", LoggedFluid.class);

    private final String serializedName;

    LoggedFluid(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }

    public Fluid source() {
        return switch (this) {
            case WATER -> Fluids.WATER;
            case POISON -> FluidInit.POISON_SOURCE.get();
            case MUD -> FluidInit.MUD_SOURCE.get();
        };
    }

    public FluidState sourceState() {
        return source().defaultFluidState();
    }

    public ItemStack bucket() {
        return new ItemStack(source().getBucket());
    }

    public static LoggedFluid from(Fluid fluid) {
        if (fluid.isSame(FluidInit.POISON_SOURCE.get())) {
            return POISON;
        }
        if (fluid.isSame(FluidInit.MUD_SOURCE.get())) {
            return MUD;
        }
        return WATER;
    }

    public static boolean isSupported(Fluid fluid) {
        return fluid.isSame(Fluids.WATER)
                || fluid.isSame(FluidInit.POISON_SOURCE.get())
                || fluid.isSame(FluidInit.MUD_SOURCE.get());
    }

    public static boolean supports(BlockState state) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.hasProperty(PROPERTY);
    }

    public static FluidState getFluidState(BlockState state) {
        if (!supports(state) || !state.getValue(BlockStateProperties.WATERLOGGED)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        return state.getValue(PROPERTY).sourceState();
    }

    public static BlockState fill(BlockState state, Fluid fluid) {
        return state.setValue(BlockStateProperties.WATERLOGGED, true).setValue(PROPERTY, from(fluid));
    }

    public static BlockState empty(BlockState state) {
        return state.setValue(BlockStateProperties.WATERLOGGED, false);
    }

    /** Preserves custom fluid when a waterloggable block is placed directly into it. */
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        BlockState placed = event.getPlacedBlock();
        if (!supports(placed)) {
            return;
        }

        FluidState replacedFluid = event.getBlockSnapshot().getReplacedBlock().getFluidState();
        Fluid fluid = replacedFluid.getType();
        if (fluid.isSame(FluidInit.POISON_SOURCE.get()) || fluid.isSame(FluidInit.MUD_SOURCE.get())) {
            LevelAccessor level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState filled = fill(placed, fluid);
            if (filled == placed) {
                return;
            }
            level.setBlock(pos, filled, 3);
            level.scheduleTick(pos, filled.getFluidState().getType(),
                    filled.getFluidState().getType().getTickDelay(level));
        }
    }
}
