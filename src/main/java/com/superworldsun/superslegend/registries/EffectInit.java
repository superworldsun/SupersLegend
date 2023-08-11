package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.effect.CloakedEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit
{
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SupersLegendMain.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECT.register(eventBus);
    }

    public static final RegistryObject<MobEffect> CLOAKED = MOB_EFFECT.register("cloaked", CloakedEffect::new);

}
