package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.SupersLegendRegistries;
import com.superworldsun.superslegend.songs.OcarinaSong;
import com.superworldsun.superslegend.songs.songs.*;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class OcarinaSongInit
{
	public static final DeferredRegister<OcarinaSong> REGISTRY = DeferredRegister.create(SupersLegendRegistries.OCARINA_SONGS, SupersLegendMain.MOD_ID);
	
	/*1*/ public static final RegistryObject<OcarinaSong> ZELDAS_LULLABY = REGISTRY.register("zeldas_lullaby", ZeldasLullaby::new);
	/*2*/ public static final RegistryObject<OcarinaSong> EPONAS_SONG = REGISTRY.register("eponas_song", EponasSong::new);
	/*3*/ public static final RegistryObject<OcarinaSong> SARIAS_SONG = REGISTRY.register("sarias_song", SariasSong::new);
	/*4*/ public static final RegistryObject<OcarinaSong> SUNS_SONG = REGISTRY.register("suns_song", SunsSong::new);
	/*5*/ public static final RegistryObject<OcarinaSong> SONG_OF_TIME = REGISTRY.register("song_of_time", SongOfTime::new);
	/*6*/ public static final RegistryObject<OcarinaSong> SONG_OF_DOUBLE_TIME = REGISTRY.register("song_of_double_time", SongOfDoubleTime::new);
	/*7*/ public static final RegistryObject<OcarinaSong> INVERTED_SONG_OF_TIME = REGISTRY.register("inverted_song_of_time", InvertedSongOfTime::new);

	/*8*/ public static final RegistryObject<OcarinaSong> SONG_OF_STORMS = REGISTRY.register("song_of_storms", SongOfStorms::new);
	/*9*/ public static final RegistryObject<OcarinaSong> MINUET_OF_FOREST = REGISTRY.register("minuet_of_forest", MinuetOfForest::new);
	/*10*/ public static final RegistryObject<OcarinaSong> BOLERO_OF_FIRE = REGISTRY.register("bolero_of_fire", BoleroOfFire::new);
	/*11*/ public static final RegistryObject<OcarinaSong> SERENADE_OF_WATER = REGISTRY.register("serenade_of_water", SerenadeOfWater::new);
	/*12*/ public static final RegistryObject<OcarinaSong> REQUIEM_OF_SPIRIT = REGISTRY.register("requiem_of_spirit", RequiemOfSpirit::new);
	/*13*/ public static final RegistryObject<OcarinaSong> NOCTURNE_OF_SHADOW = REGISTRY.register("nocturne_of_shadow", NocturneOfShadow::new);
	/*14*/ public static final RegistryObject<OcarinaSong> PRELUDE_OF_LIGHT = REGISTRY.register("prelude_of_light", PreludeOfLight::new);

	/*15*/ public static final RegistryObject<OcarinaSong> SONG_OF_HEALING = REGISTRY.register("song_of_healing", SongOfHealing::new);
	/*16*/ public static final RegistryObject<OcarinaSong> SONG_OF_SOARING = REGISTRY.register("song_of_soaring", SongOfSoaring::new);
	/*17*/ public static final RegistryObject<OcarinaSong> SONATA_OF_AWAKENING = REGISTRY.register("sonata_of_awakening", SonataOfAwakening::new);
	/*18*/ public static final RegistryObject<OcarinaSong> GORON_LULLABY = REGISTRY.register("goron_lullaby", GoronLullaby::new);
	/*19*/ public static final RegistryObject<OcarinaSong> NEW_WAVE_BOSSA_NOVA = REGISTRY.register("new_wave_bossa_nova", NewWaveBossaNova::new);
	/*20*/ public static final RegistryObject<OcarinaSong> ELEGY_OF_EMPTYNESS = REGISTRY.register("elegy_of_emptyness", ElegyOfEmptyness::new);
	/*21*/ public static final RegistryObject<OcarinaSong> OATH_TO_ORDER = REGISTRY.register("oath_to_order", OathToOrder::new);
}
