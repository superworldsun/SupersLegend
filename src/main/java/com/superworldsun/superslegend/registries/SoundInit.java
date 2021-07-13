package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SupersLegendMain.MOD_ID);

    public static final RegistryObject<SoundEvent> PIGFLY = SOUNDS.register("entity.actions.special", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "entity.actions.special")));

    public static final Lazy<SoundEvent> BUBBLEGLOOP_DISC_LAZY = Lazy
            .of(() -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.bubblegloop_disc")));

    public static final RegistryObject<SoundEvent> BUBBLEGLOOP_DISC = SOUNDS.register("item.bubblegloop_disc.disc", BUBBLEGLOOP_DISC_LAZY);

}