package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.fluid.MudFluid;
import com.superworldsun.superslegend.fluid.PoisonFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class FluidInit {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, SupersLegendMain.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, SupersLegendMain.MOD_ID);

    public static final RegistryObject<FluidType> POISON_TYPE =
            FLUID_TYPES.register("poison", PoisonFluid::new);
    public static final RegistryObject<FluidType> MUD_TYPE =
            FLUID_TYPES.register("mud", MudFluid::new);

    public static final RegistryObject<ForgeFlowingFluid.Source> POISON_SOURCE =
            FLUIDS.register("poison_source", () -> new ForgeFlowingFluid.Source(poisonProperties()));
    public static final RegistryObject<ForgeFlowingFluid.Flowing> POISON_FLOWING =
            FLUIDS.register("poison_flowing", () -> new ForgeFlowingFluid.Flowing(poisonProperties()));

    public static final RegistryObject<ForgeFlowingFluid.Source> MUD_SOURCE =
            FLUIDS.register("mud_source", () -> new ForgeFlowingFluid.Source(mudProperties()));
    public static final RegistryObject<ForgeFlowingFluid.Flowing> MUD_FLOWING =
            FLUIDS.register("mud_flowing", () -> new ForgeFlowingFluid.Flowing(mudProperties()));

    private FluidInit() {
    }

    private static ForgeFlowingFluid.Properties poisonProperties() {
        return new ForgeFlowingFluid.Properties(POISON_TYPE, POISON_SOURCE, POISON_FLOWING)
                .block(BlockInit.LIQUID_POISON)
                .bucket(ItemInit.POISON_BUCKET)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1)
                .tickRate(5)
                .explosionResistance(100.0F);
    }

    private static ForgeFlowingFluid.Properties mudProperties() {
        return new ForgeFlowingFluid.Properties(MUD_TYPE, MUD_SOURCE, MUD_FLOWING)
                .block(BlockInit.LIQUID_MUD)
                .bucket(ItemInit.MUD_BUCKET)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(2)
                .tickRate(20)
                .explosionResistance(100.0F);
    }
}
