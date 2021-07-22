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

    public static final RegistryObject<SoundEvent> NAYRUS_LOVE_CAST = SOUNDS.register("item.nayrus_love_cast", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.nayrus_love_cast")));

    public static final RegistryObject<SoundEvent> ZELDA_ERROR = SOUNDS.register("item.zelda_error", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.zelda_error")));

    public static final RegistryObject<SoundEvent> DASH = SOUNDS.register("item.dash", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.dash")));

    public static final RegistryObject<SoundEvent> MAGIC_CAPE_OFF = SOUNDS.register("item.magic_cape_off", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.magic_cape_off")));

    public static final RegistryObject<SoundEvent> MAGIC_CAPE_ON = SOUNDS.register("item.magic_cape_on", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.magic_cape_on")));

    public static final RegistryObject<SoundEvent> LENS_OF_TRUTH_ON = SOUNDS.register("item.lens_of_truth_on", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.lens_of_truth_on")));

    public static final RegistryObject<SoundEvent> LENS_OF_TRUTH_OFF = SOUNDS.register("item.lens_of_truth_off", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.lens_of_truth_off")));

    public static final RegistryObject<SoundEvent> RUPEE_GREEN = SOUNDS.register("item.rupee_green", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.rupee_green")));

    public static final RegistryObject<SoundEvent> RUPEE_BLUE = SOUNDS.register("item.rupee_blue", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.rupee_blue")));

    public static final RegistryObject<SoundEvent> RUPEE_RED = SOUNDS.register("item.rupee_red", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.rupee_red")));

    public static final RegistryObject<SoundEvent> RUPEE_SILVER = SOUNDS.register("item.rupee_silver", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.rupee_silver")));

    public static final RegistryObject<SoundEvent> BOOK_OF_MUDORA = SOUNDS.register("item.book_of_mudora", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.book_of_mudora")));

    public static final RegistryObject<SoundEvent> ARROW_HIT_FIRE = SOUNDS.register("item.arrow_hit_fire", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.arrow_hit_fire")));

    public static final RegistryObject<SoundEvent> ARROW_HIT_ICE = SOUNDS.register("item.arrow_hit_ice", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.arrow_hit_ice")));

    public static final RegistryObject<SoundEvent> ARROW_HIT_SHOCK = SOUNDS.register("item.arrow_hit_shock", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.arrow_hit_shock")));

    public static final RegistryObject<SoundEvent> ARROW_HIT_ANCIENT = SOUNDS.register("item.arrow_hit_ancient", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.arrow_hit_ancient")));

    public static final RegistryObject<SoundEvent> BITBOW_ARROW = SOUNDS.register("item.bitbow_arrow", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.bitbow_arrow")));

    public static final RegistryObject<SoundEvent> HOVER_BOOTS = SOUNDS.register("item.hover_boots", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.hover_boots")));

    public static final RegistryObject<SoundEvent> BOOMERANG_FLY_LOOP = SOUNDS.register("item.boomerang_fly_loop", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.boomerang_fly_loop")));

    public static final RegistryObject<SoundEvent> BOOMERANG_THROW = SOUNDS.register("item.boomerang_throw", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.boomerang_throw")));

    public static final RegistryObject<SoundEvent> BOMB_FUSE = SOUNDS.register("item.bomb_fuse", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.bomb_fuse")));

    public static final RegistryObject<SoundEvent> BOMB_DEFUSE = SOUNDS.register("item.bomb_defuse", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.bomb_defuse")));

    public static final RegistryObject<SoundEvent> BOTTLE_POP = SOUNDS.register("item.bottle_pop", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.bottle_pop")));

    public static final RegistryObject<SoundEvent> FAIRY_BOTTLE_USE = SOUNDS.register("item.fairy_bottle_use", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.fairy_bottle_use")));

    public static final RegistryObject<SoundEvent> FAIRY_HEAL_ON_TOUCH = SOUNDS.register("item.fairy_heal_on_touch", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.fairy_heal_on_touch")));

    public static final RegistryObject<SoundEvent> FAIRY_TWINKLE = SOUNDS.register("item.fairy_twinkle", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.fairy_twinkle")));

    public static final RegistryObject<SoundEvent> POE_LAUGH = SOUNDS.register("item.poe_laugh", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.poe_laugh")));

    public static final RegistryObject<SoundEvent> SUNS_SONG = SOUNDS.register("item.suns_song", ()
            -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.suns_song")));


    public static final Lazy<SoundEvent> BUBBLEGLOOP_DISC_LAZY = Lazy
            .of(() -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, "item.bubblegloop_disc")));

    public static final RegistryObject<SoundEvent> BUBBLEGLOOP_DISC = SOUNDS.register("item.bubblegloop_disc.disc", BUBBLEGLOOP_DISC_LAZY);

}