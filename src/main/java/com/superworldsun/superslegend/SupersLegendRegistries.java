package com.superworldsun.superslegend;

import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class SupersLegendRegistries
{
	public static final IForgeRegistry<OcarinaSong> OCARINA_SONGS = createRegistry(OcarinaSong.class, "ocarina_songs");
	
	private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> createRegistry(Class<T> type, String name)
	{
		return new RegistryBuilder<T>().setName(new ResourceLocation(SupersLegendMain.MOD_ID, name)).setType(type).tagFolder(name).create();
	}
}
