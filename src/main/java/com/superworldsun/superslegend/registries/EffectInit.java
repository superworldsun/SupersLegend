package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.effect.*;

import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit
{
	public static final DeferredRegister<Effect> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<Effect> CLOAKED = REGISTRY.register("cloaked", CloakedEffect::new);
	public static final RegistryObject<Effect> FREEZE = REGISTRY.register("freeze", FreezeEffect::new);
}
