package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.SupersLegendRegistries;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class OcarinaSongInit
{
	public static final DeferredRegister<OcarinaSong> REGISTRY = DeferredRegister.create(SupersLegendRegistries.OCARINA_SONGS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<OcarinaSong> ZELDAS_LULLABY = REGISTRY.register("zeldas_lullaby", () -> new OcarinaSong("lurlur"));
	public static final RegistryObject<OcarinaSong> EPONAS_SONG = REGISTRY.register("eponas_song", () -> new OcarinaSong("ulrulr"));
	public static final RegistryObject<OcarinaSong> SARIAS_SONG = REGISTRY.register("sarias_song", () -> new OcarinaSong("drldrl"));
	public static final RegistryObject<OcarinaSong> SONG_OF_TIME = REGISTRY.register("song_of_time", () -> new OcarinaSong("radrad"));
	public static final RegistryObject<OcarinaSong> SUNS_SONG = REGISTRY.register("suns_song", () -> new OcarinaSong("rdurdu"));
	public static final RegistryObject<OcarinaSong> MINUET_OF_FOREST = REGISTRY.register("minuet_of_forest", () -> new OcarinaSong("aulrlr"));
	public static final RegistryObject<OcarinaSong> BOLERO_OF_FIRE = REGISTRY.register("bolero_of_fire", () -> new OcarinaSong("dadardrd"));
	public static final RegistryObject<OcarinaSong> SERENADE_OF_WATER = REGISTRY.register("serenade_of_water", () -> new OcarinaSong("adrrl"));
	public static final RegistryObject<OcarinaSong> SONG_OF_STORMS = REGISTRY.register("song_of_storms", () -> new OcarinaSong("aduadu"));
	public static final RegistryObject<OcarinaSong> NOCTURNE_OF_SHADOW = REGISTRY.register("nocturne_of_shadow", () -> new OcarinaSong("lrralrd"));
	public static final RegistryObject<OcarinaSong> REQUIEM_OF_SPIRIT = REGISTRY.register("requiem_of_spirit", () -> new OcarinaSong("adarda"));
	public static final RegistryObject<OcarinaSong> SONG_OF_SOARING = REGISTRY.register("song_of_soaring", () -> new OcarinaSong("dludlu"));
	public static final RegistryObject<OcarinaSong> INVERTED_SONG_OF_TIME = REGISTRY.register("inverted_song_of_time", () -> new OcarinaSong("dardar"));
	public static final RegistryObject<OcarinaSong> SONATA_OF_AWAKENING = REGISTRY.register("sonata_of_awakening", () -> new OcarinaSong("ululara"));
	public static final RegistryObject<OcarinaSong> SONG_OF_DOUBLE_TIME = REGISTRY.register("song_of_double_time", () -> new OcarinaSong("rraadd"));
	public static final RegistryObject<OcarinaSong> PRELUDE_OF_LIGHT = REGISTRY.register("prelude_of_light", () -> new OcarinaSong("ururlu"));
	public static final RegistryObject<OcarinaSong> CORON_LULLABY = REGISTRY.register("coron_lullaby", () -> new OcarinaSong("arlarlra"));
	public static final RegistryObject<OcarinaSong> OATH_TO_ORDER = REGISTRY.register("oath_to_order", () -> new OcarinaSong("rdadru"));
	public static final RegistryObject<OcarinaSong> NEW_WAVE_BOSSA_NOVA = REGISTRY.register("new_wave_bossa_nova", () -> new OcarinaSong("lulrdlr"));
	public static final RegistryObject<OcarinaSong> ELEGY_OF_EMPTYNESS = REGISTRY.register("elegy_of_emptyness", () -> new OcarinaSong("rlrdrul"));
	public static final RegistryObject<OcarinaSong> SONG_OF_HEALING = REGISTRY.register("song_of_healing", () -> new OcarinaSong("lrdlrd"));
}
