package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.fluid.MudFluid;
import com.superworldsun.superslegend.fluid.PoisonFluid;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit
{
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<FlowingFluid> POISON_SOURCE = FLUIDS.register("poison_source", PoisonFluid.Source::new);
	public static final RegistryObject<FlowingFluid> POISON_FLOWING = FLUIDS.register("poison_flowing", PoisonFluid.Flowing::new);
	public static final RegistryObject<FlowingFluid> MUD_SOURCE = FLUIDS.register("mud_source", MudFluid.Source::new);
	public static final RegistryObject<FlowingFluid> MUD_FLOWING = FLUIDS.register("mud_flowing", MudFluid.Flowing::new);
}
