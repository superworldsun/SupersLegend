package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<SoundEvent> PIGFLY = register("entity.actions.special");
	public static final RegistryObject<SoundEvent> NAYRUS_LOVE_CAST = register("item.nayrus_love_cast");
	public static final RegistryObject<SoundEvent> ZELDA_ERROR = register("item.zelda_error");
	public static final RegistryObject<SoundEvent> DASH = register("item.dash");
	public static final RegistryObject<SoundEvent> MAGIC_CAPE_OFF = register("item.magic_cape_off");
	public static final RegistryObject<SoundEvent> MAGIC_CAPE_ON = register("item.magic_cape_on");
	public static final RegistryObject<SoundEvent> LENS_OF_TRUTH_ON = register("item.lens_of_truth_on");
	public static final RegistryObject<SoundEvent> LENS_OF_TRUTH_OFF = register("item.lens_of_truth_off");
	public static final RegistryObject<SoundEvent> RUPEE_GREEN = register("item.rupee_green");
	public static final RegistryObject<SoundEvent> RUPEE_BLUE = register("item.rupee_blue");
	public static final RegistryObject<SoundEvent> RUPEE_RED = register("item.rupee_red");
	public static final RegistryObject<SoundEvent> RUPEE_SILVER = register("item.rupee_silver");
	public static final RegistryObject<SoundEvent> BOOK_OF_MUDORA = register("item.book_of_mudora");
	public static final RegistryObject<SoundEvent> ARROW_HIT_FIRE = register("item.arrow_hit_fire");
	public static final RegistryObject<SoundEvent> ARROW_HIT_ICE = register("item.arrow_hit_ice");
	public static final RegistryObject<SoundEvent> ARROW_HIT_SHOCK = register("item.arrow_hit_shock");
	public static final RegistryObject<SoundEvent> ARROW_HIT_ANCIENT = register("item.arrow_hit_ancient");
	public static final RegistryObject<SoundEvent> BITBOW_ARROW = register("item.bitbow_arrow");
	public static final RegistryObject<SoundEvent> HOVER_BOOTS = register("item.hover_boots");
	public static final RegistryObject<SoundEvent> BOOMERANG_FLY_LOOP = register("item.boomerang_fly_loop");
	public static final RegistryObject<SoundEvent> BOOMERANG_THROW = register("item.boomerang_throw");
	public static final RegistryObject<SoundEvent> BOMB_FUSE = register("item.bomb_fuse");
	public static final RegistryObject<SoundEvent> BOMB_DEFUSE = register("item.bomb_defuse");
	public static final RegistryObject<SoundEvent> HOOKSHOT_SET = register("item.hookshot_set");
	public static final RegistryObject<SoundEvent> HOOKSHOT_EXTENDED = register("item.hookshot_extended");
	public static final RegistryObject<SoundEvent> HOOKSHOT_FIRE = register("item.hookshot_fire");
	public static final RegistryObject<SoundEvent> HOOKSHOT_TARGET = register("item.hookshot_target");
	public static final RegistryObject<SoundEvent> HOOKSHOT_CLANG = register("item.hookshot_clang");
	public static final RegistryObject<SoundEvent> CLAWSHOT_SET = register("item.clawshot_set");
	public static final RegistryObject<SoundEvent> CLAWSHOT_FIRE = register("item.clawshot_fire");
	public static final RegistryObject<SoundEvent> CLAWSHOT_EXTENDED = register("item.clawshot_extended");
	public static final RegistryObject<SoundEvent> CLAWSHOT_RETURN = register("item.clawshot_return");
	public static final RegistryObject<SoundEvent> MEGATON_HAMMER_SWING = register("item.megaton_hammer_swing");
	public static final RegistryObject<SoundEvent> MEGATON_HAMMER_HIT = register("item.megaton_hammer_hit");
	public static final RegistryObject<SoundEvent> SLINGSHOT_PULL = register("item.slingshot_pull");
	public static final RegistryObject<SoundEvent> SLINGSHOT_SHOOT = register("item.slingshot_shoot");
	public static final RegistryObject<SoundEvent> BOTTLE_POP = register("item.bottle_pop");
	public static final RegistryObject<SoundEvent> FAIRY_BOTTLE_USE = register("item.fairy_bottle_use");
	public static final RegistryObject<SoundEvent> FAIRY_HEAL_ON_TOUCH = register("item.fairy_heal_on_touch");
	public static final RegistryObject<SoundEvent> FAIRY_TWINKLE = register("item.fairy_twinkle");
	public static final RegistryObject<SoundEvent> POE_LAUGH = register("item.poe_laugh");
	public static final RegistryObject<SoundEvent> SUNS_SONG = register("item.suns_song");
	public static final RegistryObject<SoundEvent> SONG_OF_STORMS = register("item.song_of_storms");
	public static final RegistryObject<SoundEvent> BREMEN_MARCH = register("item.bremen_march");
	public static final RegistryObject<SoundEvent> BUBBLEGLOOP_DISC = register("item.bubblegloop_disc");
	public static final RegistryObject<SoundEvent> FOREST_TEMPLE_DISC = register("item.forest_temple_disc");
	public static final RegistryObject<SoundEvent> FIRE_TEMPLE_DISC = register("item.fire_temple_disc");
	public static final RegistryObject<SoundEvent> WATER_TEMPLE_DISC = register("item.water_temple_disc");
	public static final RegistryObject<SoundEvent> SHADOW_TEMPLE_DISC = register("item.shadow_temple_disc");
	public static final RegistryObject<SoundEvent> SPIRIT_TEMPLE_DISC = register("item.spirit_temple_disc");
	public static final RegistryObject<SoundEvent> WOODFALL_TEMPLE_DISC = register("item.woodfall_temple_disc");
	public static final RegistryObject<SoundEvent> SNOWHEAD_TEMPLE_DISC = register("item.snowhead_temple_disc");
	public static final RegistryObject<SoundEvent> GREAT_BAY_TEMPLE_DISC = register("item.great_bay_temple_disc");
	public static final RegistryObject<SoundEvent> STONE_TOWER_TEMPLE_REALITY_DISC = register("item.stone_tower_temple_reality_disc");
	public static final RegistryObject<SoundEvent> STONE_TOWER_TEMPLE_ILLUSION_DISC = register("item.stone_tower_temple_illusion_disc");
	public static final RegistryObject<SoundEvent> OCARINA_NOTE_D = register("item.ocarina.note_d");
	public static final RegistryObject<SoundEvent> OCARINA_NOTE_R = register("item.ocarina.note_r");
	public static final RegistryObject<SoundEvent> OCARINA_NOTE_L = register("item.ocarina.note_l");
	public static final RegistryObject<SoundEvent> OCARINA_NOTE_U = register("item.ocarina.note_u");
	public static final RegistryObject<SoundEvent> OCARINA_NOTE_A = register("item.ocarina.note_a");

	private static RegistryObject<SoundEvent> register(String soundName)
	{
		return SOUNDS.register(soundName, Lazy.of(() -> new SoundEvent(new ResourceLocation(SupersLegendMain.MOD_ID, soundName))));
	}
}